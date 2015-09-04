import sys
import math
import types
from sqlalchemy import create_engine
import tushare as ts
import pandas as pd
from datetime import datetime
from elasticsearch import Elasticsearch

symbol = sys.argv[1]
start = sys.argv[2]
end = sys.argv[3]

df = ts.get_hist_data(symbol, start=start, end=end, ktype='D')

if type(df) is types.NoneType:
	print '%s is not a valid symbol.' % symbol 
	sys.exit(0)

# engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')

# process the data to add additional cols
df['code'] = symbol
df['period_type'] = 'D'

df['ma25'] = pd.rolling_mean(df['close'], 25)
df['ma30'] = pd.rolling_mean(df['close'], 30)
df['ma55'] = pd.rolling_mean(df['close'], 55)
df['ma60'] = pd.rolling_mean(df['close'], 60)
df['ma99'] = pd.rolling_mean(df['close'], 99)
df['ma120'] = pd.rolling_mean(df['close'], 120)
df['ma250'] = pd.rolling_mean(df['close'], 250)
df['ma888'] = pd.rolling_mean(df['close'], 888)

df['ema17'] = pd.ewma(df['close'], span=17)
df['ema34'] = pd.ewma(df['close'], span=34)
df['ema55'] = pd.ewma(df['close'], span=55)

df['bu25'] = df['ma25'] + 2 * pd.rolling_std(df['close'], 25)
df['bl25'] = df['ma25'] - 2 * pd.rolling_std(df['close'], 25)
df['bu55'] = df['ma55'] + 2 * pd.rolling_std(df['close'], 55)
df['bl55'] = df['ma55'] - 2 * pd.rolling_std(df['close'], 55)
df['bu99'] = df['ma99'] + 2 * pd.rolling_std(df['close'], 99)
df['bl99'] = df['ma99'] - 2 * pd.rolling_std(df['close'], 99)

# df.to_sql('data_daily',engine, if_exists='append')

es = Elasticsearch()

for row_index, row in df.iterrows():
	# print('row index: %s\n row is: %s' % (row_index, row['bl99']))
	doc = {
		'code': symbol,
		'date': row_index,
		'period_type': row['period_type'],
		'open': row['open'],
		'high': row['high'],
		'close': row['close'],
		'low': row['low'],
		'volume': row['volume'],
		'price_change': row['price_change'],
		'p_change': row['p_change'],
		'ma5': row['ma5'] if not math.isnan(row['ma5']) else -1,
		'ma10': row['ma10'] if not math.isnan(row['ma10']) else -1,
		'ma20': row['ma20'] if not math.isnan(row['ma20']) else -1,
		'ma25': row['ma25'] if not math.isnan(row['ma25']) else -1,
		'ma30': row['ma30'] if not math.isnan(row['ma30']) else -1,
		'ma55': row['ma55'] if not math.isnan(row['ma55']) else -1,
		'ma60': row['ma60'] if not math.isnan(row['ma60']) else -1,
		'ma99': row['ma99'] if not math.isnan(row['ma99']) else -1,
		'ma120': row['ma120'] if not math.isnan(row['ma120']) else -1,
		'ma250': row['ma250'] if not math.isnan(row['ma250']) else -1,
		'ma888': row['ma888'] if not math.isnan(row['ma888']) else -1,
		'ema17': row['ema17'] if not math.isnan(row['ema17']) else -1,
		'ema34': row['ema34'] if not math.isnan(row['ema34']) else -1,
		'ema55': row['ema55'] if not math.isnan(row['ema55']) else -1,
		'v_ma5': row['v_ma5'] if not math.isnan(row['v_ma5']) else -1,
		'v_ma10': row['v_ma10'] if not math.isnan(row['v_ma10']) else -1,
		'v_ma20': row['v_ma20'] if not math.isnan(row['v_ma20']) else -1,
		'turnover': row['turnover'] if not math.isnan(row['turnover']) else -1,
		'bl25': row['bl25'] if not math.isnan(row['bl25']) else -1,
		'bu25': row['bu25'] if not math.isnan(row['bu25']) else -1,
		'bl55': row['bl55'] if not math.isnan(row['bl55']) else -1,
		'bu55': row['bu55'] if not math.isnan(row['bu55']) else -1,
		'bl99': row['bl99'] if not math.isnan(row['bl99']) else -1,
		'bu99': row['bu99'] if not math.isnan(row['bu99']) else -1
	}
	res = es.index(index="stock-tushare", doc_type='data-daily', id=symbol+'_'+row_index ,body=doc)
	# print doc;

