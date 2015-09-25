import sys
import types
from sqlalchemy import create_engine
import tushare as ts
import pandas as pd
from datetime import datetime, date
import numpy as np

symbol = sys.argv[1]
start = sys.argv[2]
end = sys.argv[3]

df_latest = ts.get_hist_data(symbol, start=start, end=end, ktype='60')

# print df_latest.index

if type(df_latest) is types.NoneType or df_latest.size == 0:
    print '%s is not a valid symbol or no available data' % symbol 
    sys.exit(0)

# retrieve the current data
engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')
try:
    df_previous = pd.read_sql_query('select * from data_60min where code = ' + symbol + ' order by sequence', engine, index_col='date')
    if df_previous.size == 0:
        no_previous_data = True
    else:
        no_previous_data = False
except:
    no_previous_data = True

if not no_previous_data:
    # check duplicates
    last_sequence_id = df_previous.tail(1)['sequence'].ix[0]
    for row_index, row in df_latest.iterrows():
        if row_index not in df_previous.index:
            df_previous = df_previous.append(row)
    df = df_previous

    # since the index for thedata retrieved from ts is of type unicode, need to convert to datetime for sorting
    df.index = pd.DatetimeIndex(df.index)

    # reorder by date
    df = df.sort_index()

    # extract last sequence id
    first_sequence_id = df.head(1)['sequence'].ix[0]
else:
    df = df_latest
    first_sequence_id = 1
    last_sequence_id = np.nan

# print df, date_field

# process the data to add additional cols
# print df.index

# only save the date part
if type(df.index) == pd.tseries.index.DatetimeIndex:
    pdt = df.index.to_pydatetime()
    sdt = np.vectorize(lambda s: s.strftime('%Y-%m-%d'))(pdt)
    df['date_only'] = sdt
else:
    df['date_only'] = df.index.str.split(' ').str[0]

df['code'] = symbol
df['period_type'] = 'M60'

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
# df['ma888'] = pd.rolling_mean(df['close'], 888)

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
# df['max888'] = pd.rolling_max(df['high'], 888)

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
# df['min888'] = pd.rolling_min(df['low'], 888)

df['ema17'] = pd.ewma(df['close'], span=17)
df['ema34'] = pd.ewma(df['close'], span=34)
df['ema55'] = pd.ewma(df['close'], span=55)

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

# df['dif-12-26-9-rise'] = df['dif-12-26-9'] - df['dif-12-26-9'].shift(1) > 0
# df['dif-12-26-9-fall'] = df['dif-12-26-9'] - df['dif-12-26-9'].shift(-1) > 0

df['ema10'] = pd.ewma(df['close'], span=10)
df['ema20'] = pd.ewma(df['close'], span=20)
df['dif-10-20-5'] = df['ema10'] - df['ema20']
df['dea-10-20-5'] = pd.ewma(df['dif-10-20-5'], span=5)
df['macd-10-20-5'] = 2 * (df['dif-10-20-5'] - df['dea-10-20-5'])

df['ema9'] = pd.ewma(df['close'], span=9)
df['dif-9-12-6'] = df['ema9'] - df['ema12']
df['dea-9-12-6'] = pd.ewma(df['dif-9-12-6'], span=6)
df['macd-9-12-6'] = 2 * (df['dif-9-12-6'] - df['dea-9-12-6'])

# used below print statement to check correctness of certain columns
# print df[['dif-9-12-6', 'dea-9-12-6', 'macd-9-12-6', 'dif-10-20-5', 'dea-10-20-5', 'macd-10-20-5', 'dif-12-26-9', 'dea-12-26-9', 'macd-12-26-9']].tail(10)

# add incremental sequence id
df['sequence']=pd.Series(range(int(first_sequence_id), len(df) + int(first_sequence_id)), df.index)

# remove primary column
if 'id' in df.columns:
    del df['id']

# remove the exist rows
if not np.isnan(last_sequence_id):
    df = df[df['sequence'] > int(last_sequence_id)]

df.to_sql('data_60min', engine, if_exists='append', index='date')