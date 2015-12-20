# -*- coding: utf-8 -*-
# calculate the weekly data from daily data, since direct fetched data is not qfq

import sys
import pandas as pd
import numpy as np
from sqlalchemy import create_engine
from datetime import datetime, date

symbol = sys.argv[1]

# retrieve the current data
engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')

# first try to fetch current week data
try:
    df_weekly_previous = pd.read_sql_query('select * from data_week_calc where code = ' + symbol + ' order by sequence', engine, index_col='date', parse_dates=['date'])
    if df_weekly_previous.size == 0:
        no_previous_weekly_data = True
        print 'No previous weekly data for %s' % symbol
    else:
        no_previous_weekly_data = False
except Exception, e:
    no_previous_weekly_data = True
    print '[Exception] When fetching weekly data for %s --- %s' % (symbol, str(e))

# get latest daily data
if no_previous_weekly_data:
    daily_data_sql = 'select * from data_daily where code = ' + symbol + ' order by sequence'
else:
    last_weekly_data_date = df_weekly_previous.tail(1).index[0]
    daily_data_sql = 'select * from data_daily where code = ' + symbol + ' and date >= ' + last_weekly_data_date.strftime('%Y-%m-%d') + 'order by sequence'

try:
    df_previous = pd.read_sql_query('select * from data_daily where code = ' + symbol + ' order by sequence', engine, index_col='date', parse_dates=['date'])
    if df_previous.size == 0:
        no_previous_data = True
        print 'No previous daily data for %s' % symbol
        sys.exit(0)
    else:
        no_previous_data = False
except:
    no_previous_data = True
    print '[Exception] When fetching daily data for %s' % symbol
    sys.exit(0)

def calculate_weekly_data(param_weekly_data):
    if len(param_weekly_data) == 0:
        return None

    ret_data = {}
    weekly_data_first = param_weekly_data[0]
    weekly_data_last = param_weekly_data[-1]

    ret_data['high'] = weekly_data_first['high']
    ret_data['low'] = weekly_data_first['low']
    ret_data['open'] = weekly_data_first['open']
    ret_data['close'] = weekly_data_last['close']
    ret_data['volume'] = 0
    ret_data['amount'] = 0
    ret_data['date'] = date(weekly_data_first['date'].year, weekly_data_first['date'].month, weekly_data_first['date'].day)

    for _daily_data in param_weekly_data:
        if _daily_data['high'] > ret_data['high']:
            ret_data['high'] = _daily_data['high']
        if _daily_data['low'] < ret_data['low']:
            ret_data['low'] = _daily_data['low']

        ret_data['volume'] += _daily_data['volume']
        ret_data['amount'] += _daily_data['amount']
        ret_data['date'] = _daily_data['date']

    return pd.Series(ret_data)

calc_weekly_data = []
current_weekly_data = []
previous_daily_data = None
row_number = df_previous.shape[0]
df_previous = df_previous.reset_index()
for idx, daily_data in df_previous.iterrows():
    if idx == 0:
        current_weekly_data.append(daily_data)
    else:
        days_off = (daily_data['date'] - previous_daily_data['date']).days
        if days_off < 7 and daily_data['date'].weekday() > previous_daily_data['date'].weekday():
            current_weekly_data.append(daily_data)
        else:
            # calculate the current_weekly_data
            # print 'calculate current: %d --- %s to %s ' % len(current_weekly_data), current_weekly_data[0]['date'], current_weekly_data[len(current_weekly_data) - 1]['date']
            calc_weekly_data.append(calculate_weekly_data(current_weekly_data))

            current_weekly_data = []
            current_weekly_data.append(daily_data)

    previous_daily_data = daily_data

    # calc the lastest week
    if idx == row_number - 1:
        calc_weekly_data.append(calculate_weekly_data(current_weekly_data))

# process the weekly_data
df = pd.DataFrame(calc_weekly_data).set_index('date')

# determine the last sequence id
if 'sequence' in df.head(1):
    first_sequence_id = df.head(1)['sequence'].ix[0]
else:
    first_sequence_id = 1
    last_sequence_id = np.nan

df['code'] = symbol
df['period_type'] = 'W'

# percentage change
df['p_change'] = df['close'] / df['close'].shift(1) - 1
df['price_change'] = df['close'] - df['close'].shift(1)

df['p_change2'] = df['close'] / df['close'].shift(2) - 1
df['p_change3'] = df['close'] / df['close'].shift(3) - 1
df['p_change5'] = df['close'] / df['close'].shift(5) - 1
df['p_change10'] = df['close'] / df['close'].shift(10) - 1
df['p_change15'] = df['close'] / df['close'].shift(15) - 1
df['p_change20'] = df['close'] / df['close'].shift(20) - 1
df['p_change30'] = df['close'] / df['close'].shift(30) - 1

# volume related
df['v_ma5'] = pd.rolling_mean(df['volume'], 5)
df['v_ma10'] = pd.rolling_mean(df['volume'], 10)
df['v_ma20'] = pd.rolling_mean(df['volume'], 20)

# moving average
df['ma5'] = pd.rolling_mean(df['close'], 5)
df['ma10'] = pd.rolling_mean(df['close'], 10)
df['ma15'] = pd.rolling_mean(df['close'], 15)
df['ma20'] = pd.rolling_mean(df['close'], 20)
df['ma25'] = pd.rolling_mean(df['close'], 25)
df['ma30'] = pd.rolling_mean(df['close'], 30)
df['ma55'] = pd.rolling_mean(df['close'], 55)
df['ma60'] = pd.rolling_mean(df['close'], 60)
df['ma99'] = pd.rolling_mean(df['close'], 99)
df['ma120'] = pd.rolling_mean(df['close'], 120)
df['ma250'] = pd.rolling_mean(df['close'], 250)
df['ma888'] = pd.rolling_mean(df['close'], 888)

# max/min
df['max5'] = pd.rolling_max(df['high'], 5)
df['max10'] = pd.rolling_max(df['high'], 10)
df['max20'] = pd.rolling_max(df['high'], 20)
df['max25'] = pd.rolling_max(df['high'], 25)
df['max30'] = pd.rolling_max(df['high'], 30)
df['max55'] = pd.rolling_max(df['high'], 55)
df['max60'] = pd.rolling_max(df['high'], 60)
df['max99'] = pd.rolling_max(df['high'], 99)
df['max120'] = pd.rolling_max(df['high'], 120)
df['max250'] = pd.rolling_max(df['high'], 250)
df['max888'] = pd.rolling_max(df['high'], 888)

df['min5'] = pd.rolling_min(df['low'], 5)
df['min10'] = pd.rolling_min(df['low'], 10)
df['min20'] = pd.rolling_min(df['low'], 20)
df['min25'] = pd.rolling_min(df['low'], 25)
df['min30'] = pd.rolling_min(df['low'], 30)
df['min55'] = pd.rolling_min(df['low'], 55)
df['min60'] = pd.rolling_min(df['low'], 60)
df['min99'] = pd.rolling_min(df['low'], 99)
df['min120'] = pd.rolling_min(df['low'], 120)
df['min250'] = pd.rolling_min(df['low'], 250)
df['min888'] = pd.rolling_min(df['low'], 888)

# exponential moving average
df['ema5'] = pd.ewma(df['close'], span=5, adjust=False)
df['ema17'] = pd.ewma(df['close'], span=17, adjust=False)
df['ema34'] = pd.ewma(df['close'], span=34, adjust=False)
df['ema55'] = pd.ewma(df['close'], span=55, adjust=False)
df['ema99'] = pd.ewma(df['close'], span=99, adjust=False)

# bolling related
df['bu25'] = df['ma25'] + 2 * pd.rolling_std(df['close'], 25)
df['bl25'] = df['ma25'] - 2 * pd.rolling_std(df['close'], 25)
df['bu55'] = df['ma55'] + 2 * pd.rolling_std(df['close'], 55)
df['bl55'] = df['ma55'] - 2 * pd.rolling_std(df['close'], 55)
df['bu99'] = df['ma99'] + 2 * pd.rolling_std(df['close'], 99)
df['bl99'] = df['ma99'] - 2 * pd.rolling_std(df['close'], 99)

# deviation indicators
df['deviate-ma5'] = (df['ma5'] - df['close']) / df['close'] * 100
df['deviate-ma10'] = (df['ma10'] - df['close']) / df['close'] * 100
df['deviate-ma15'] = (df['ma15'] - df['close']) / df['close'] * 100
df['deviate-ma20'] = (df['ma20'] - df['close']) / df['close'] * 100
df['deviate-ma25'] = (df['ma25'] - df['close']) / df['close'] * 100
df['deviate-ma30'] = (df['ma30'] - df['close']) / df['close'] * 100

df['deviate-ema17'] = (df['ema17'] - df['close']) / df['close'] * 100
df['deviate-ema34'] = (df['ema34'] - df['close']) / df['close'] * 100
df['deviate-ema55'] = (df['ema55'] - df['close']) / df['close'] * 100

df['deviate-bu25'] = (df['bu25'] - df['close']) / df['close'] * 100
df['deviate-bl25'] = (df['bl25'] - df['close']) / df['close'] * 100
df['deviate-bu55'] = (df['bu55'] - df['close']) / df['close'] * 100
df['deviate-bl55'] = (df['bl55'] - df['close']) / df['close'] * 100
df['deviate-bu99'] = (df['bu99'] - df['close']) / df['close'] * 100
df['deviate-bl99'] = (df['bl99'] - df['close']) / df['close'] * 100

# macd related below
df['ema12'] = pd.ewma(df['close'], span=12)
df['ema26'] = pd.ewma(df['close'], span=26)
df['dif-12-26-9'] = df['ema12'] - df['ema26']
df['dea-12-26-9'] = pd.ewma(df['dif-12-26-9'], span=9)
df['macd-12-26-9'] = 2 * (df['dif-12-26-9'] - df['dea-12-26-9'])

df['ema10'] = pd.ewma(df['close'], span=10)
df['ema20'] = pd.ewma(df['close'], span=20)
df['dif-10-20-5'] = df['ema10'] - df['ema20']
df['dea-10-20-5'] = pd.ewma(df['dif-10-20-5'], span=5)
df['macd-10-20-5'] = 2 * (df['dif-10-20-5'] - df['dea-10-20-5'])

df['ema9'] = pd.ewma(df['close'], span=9)
df['dif-9-12-6'] = df['ema9'] - df['ema12']
df['dea-9-12-6'] = pd.ewma(df['dif-9-12-6'], span=6)
df['macd-9-12-6'] = 2 * (df['dif-9-12-6'] - df['dea-9-12-6'])

df['sequence']=pd.Series(range(int(first_sequence_id), len(df) + int(first_sequence_id)), df.index)

# remove primary column
if 'id' in df.columns:
    del df['id']

# remove the exist rows
if not np.isnan(last_sequence_id):
    df = df[df['sequence'] > int(last_sequence_id)]

df.to_sql('data_week_calc', engine, if_exists='append', index='date')