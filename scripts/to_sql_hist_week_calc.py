# -*- coding: utf-8 -*-
# calculate the weekly data from daily data, since direct fetched data is not qfq

import sys
import pandas as pd
from sqlalchemy import create_engine
from datetime import datetime

symbol = sys.argv[1]

# retrieve the current data
engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')

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

    for _daily_data in param_weekly_data:
        if _daily_data['high'] > ret_data['high']:
            ret_data['high'] = _daily_data['high']
        if _daily_data['low'] < ret_data['low']:
            ret_data['low'] = _daily_data['low']

        ret_data['volume'] += _daily_data['volume']
        ret_data['amount'] += _daily_data['amount']

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

