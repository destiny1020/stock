import sys
import math
import types
from sqlalchemy import create_engine
import tushare as ts
import pandas as pd
from datetime import datetime
from elasticsearch import Elasticsearch

def convert_if_index(symbol):
    if symbol == 'sh':
        return 'SH000001'
    elif symbol == 'sz':
        return 'SZ399001'
    elif symbol == 'hs300':
        return 'SH000300'
    elif symbol == 'sz50':
        return 'SH000016'
    elif symbol == 'zxb':
        return 'SZ399005'
    elif symbol == 'cyb':
        return 'SZ399006'
    else:
        return symbol

symbol = sys.argv[1]
start = sys.argv[2]
end = sys.argv[3]
force_update = sys.argv[4]

# df_latest = ts.get_hist_data(symbol, start=start, end=end, ktype='D')
df_latest = ts.get_h_data(symbol, start=start, end=end)

if type(df_latest) is types.NoneType or df_latest.size == 0:
    print '%s is not a valid symbol or no available data' % symbol 
    sys.exit(0)


df_latest = df_latest.sort_index()

# convert index date to unicode representation
df_latest = df_latest.reset_index()
df_latest['date'] = df_latest['date'].apply(lambda x: pd.to_datetime(str(x)).strftime('%Y-%m-%d'))
df_latest = df_latest.set_index('date')

# special handling for the index
symbol = convert_if_index(symbol)

# retrieve the current data
esNoData = False
es = Elasticsearch()
try:
    res = es.search(index="stock-tushare", doc_type="data-history", body={
            "query": {
                "term": {
                    "code": symbol
                }
            },
            "sort": [
                {
                    "date": {
                        "order": "desc"
                    }
                }
            ],
            "size": 1000
        })
except:
    esNoData = True

df_list = []

if not esNoData:
    df_previous_count = res['hits']['total']
    for hit in res['hits']['hits']:
        # print("%(code)s : %(date)s" % hit["_source"])
        df_list.append(pd.Series(hit["_source"]));

    if df_previous_count > 0:
        df_previous = pd.DataFrame(df_list).reset_index().set_index('date')
        # check duplicates
        for row_index, row in df_latest.iterrows():
            if row_index not in df_previous.index:
                df_previous = df_previous.append(row)
        df = df_previous
    else:
        df = df_latest

    # print df.index

    # reorder by date
    df = df.sort_index()

    # extract last available date
    date_field = pd.Series(res['hits']['hits'][0]["_source"])['date']
else:
    df = df_latest

    # define a valid last available date
    date_field = '2000-01-01'

# engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')

# print df.index.size, df.index

# process the data to add additional cols
df['code'] = symbol
df['period_type'] = 'D'

# percentage change
df['p_change'] = df['close'] / df['close'].shift(1) - 1
df['price_change'] = df['close'] - df['close'].shift(1)

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
df['ma888'] = pd.rolling_mean(df['close'], 888)

# deviation indicators
df['d5'] = (df['ma5'] - df['close']) / df['close']
df['d10'] = (df['ma10'] - df['close']) / df['close']
df['d15'] = (df['ma15'] - df['close']) / df['close']
df['d20'] = (df['ma20'] - df['close']) / df['close']
df['d30'] = (df['ma30'] - df['close']) / df['close']

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

df['ema17'] = pd.ewma(df['close'], span=17)
df['ema34'] = pd.ewma(df['close'], span=34)
df['ema55'] = pd.ewma(df['close'], span=55)

df['bu25'] = df['ma25'] + 2 * pd.rolling_std(df['close'], 25)
df['bl25'] = df['ma25'] - 2 * pd.rolling_std(df['close'], 25)
df['bu55'] = df['ma55'] + 2 * pd.rolling_std(df['close'], 55)
df['bl55'] = df['ma55'] - 2 * pd.rolling_std(df['close'], 55)
df['bu99'] = df['ma99'] + 2 * pd.rolling_std(df['close'], 99)
df['bl99'] = df['ma99'] - 2 * pd.rolling_std(df['close'], 99)

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

# df.to_sql('data_daily',engine, if_exists='append')

for row_index, row in df.iterrows():
    if isinstance(row_index, pd.core.index.Index) or isinstance(row_index, pd.tslib.Timestamp):
        doc_date = '%d-%02d-%02d' % (row_index.year, row_index.month, row_index.day)
    else:
        doc_date = row_index
    doc_id = '%s_%s' % (symbol, doc_date)

    # print row_index, type(row_index), row_index in df_latest.index
    if row_index > date_field || force_update.lower() == 'true':
        doc = {
            'code': symbol,
            'date': doc_date,
            'period_type': row['period_type'],
            'open': row['open'],
            'high': row['high'],
            'close': row['close'],
            'low': row['low'],
            'volume': row['volume'],
            'price_change': row['price_change'] if not math.isnan(row['price_change']) else -1,
            'p_change': row['p_change'] if not math.isnan(row['p_change']) else -1,
            'ma5': row['ma5'] if not math.isnan(row['ma5']) else -1,
            'ma10': row['ma10'] if not math.isnan(row['ma10']) else -1,
            'ma15': row['ma15'] if not math.isnan(row['ma15']) else -1,
            'ma20': row['ma20'] if not math.isnan(row['ma20']) else -1,
            'ma25': row['ma25'] if not math.isnan(row['ma25']) else -1,
            'ma30': row['ma30'] if not math.isnan(row['ma30']) else -1,
            'ma55': row['ma55'] if not math.isnan(row['ma55']) else -1,
            'ma60': row['ma60'] if not math.isnan(row['ma60']) else -1,
            'ma99': row['ma99'] if not math.isnan(row['ma99']) else -1,
            'ma120': row['ma120'] if not math.isnan(row['ma120']) else -1,
            'ma250': row['ma250'] if not math.isnan(row['ma250']) else -1,
            'ma888': row['ma888'] if not math.isnan(row['ma888']) else -1,
            'd5': row['d5'] if not math.isnan(row['d5']) else -1,
            'd10': row['d10'] if not math.isnan(row['d10']) else -1,
            'd20': row['d20'] if not math.isnan(row['d20']) else -1,
            'd30': row['d30'] if not math.isnan(row['d30']) else -1,
            'min5': row['min5'] if not math.isnan(row['min5']) else -1,
            'min10': row['min10'] if not math.isnan(row['min10']) else -1,
            'min20': row['min20'] if not math.isnan(row['min20']) else -1,
            'min25': row['min25'] if not math.isnan(row['min25']) else -1,
            'min30': row['min30'] if not math.isnan(row['min30']) else -1,
            'min55': row['min55'] if not math.isnan(row['min55']) else -1,
            'min60': row['min60'] if not math.isnan(row['min60']) else -1,
            'min99': row['min99'] if not math.isnan(row['min99']) else -1,
            'min120': row['min120'] if not math.isnan(row['min120']) else -1,
            'min250': row['min250'] if not math.isnan(row['min250']) else -1,
            'min888': row['min888'] if not math.isnan(row['min888']) else -1,
            'max5': row['max5'] if not math.isnan(row['max5']) else -1,
            'max10': row['max10'] if not math.isnan(row['max10']) else -1,
            'max20': row['max20'] if not math.isnan(row['max20']) else -1,
            'max25': row['max25'] if not math.isnan(row['max25']) else -1,
            'max30': row['max30'] if not math.isnan(row['max30']) else -1,
            'max55': row['max55'] if not math.isnan(row['max55']) else -1,
            'max60': row['max60'] if not math.isnan(row['max60']) else -1,
            'max99': row['max99'] if not math.isnan(row['max99']) else -1,
            'max120': row['max120'] if not math.isnan(row['max120']) else -1,
            'max250': row['max250'] if not math.isnan(row['max250']) else -1,
            'max888': row['max888'] if not math.isnan(row['max888']) else -1,
            'ema17': row['ema17'] if not math.isnan(row['ema17']) else -1,
            'ema34': row['ema34'] if not math.isnan(row['ema34']) else -1,
            'ema55': row['ema55'] if not math.isnan(row['ema55']) else -1,
            'v_ma5': row['v_ma5'] if not math.isnan(row['v_ma5']) else -1,
            'v_ma10': row['v_ma10'] if not math.isnan(row['v_ma10']) else -1,
            'v_ma20': row['v_ma20'] if not math.isnan(row['v_ma20']) else -1,
            # index type has no turnover field
            'turnover': row['turnover'] if 'turnover' in row.index and not math.isnan(row['turnover']) else -1,
            'bl25': row['bl25'] if not math.isnan(row['bl25']) else -1,
            'bu25': row['bu25'] if not math.isnan(row['bu25']) else -1,
            'bl55': row['bl55'] if not math.isnan(row['bl55']) else -1,
            'bu55': row['bu55'] if not math.isnan(row['bu55']) else -1,
            'bl99': row['bl99'] if not math.isnan(row['bl99']) else -1,
            'bu99': row['bu99'] if not math.isnan(row['bu99']) else -1,
            'dif-12-26-9': row['dif-12-26-9'] if not math.isnan(row['dif-12-26-9']) else -1,
            'dea-12-26-9': row['dea-12-26-9'] if not math.isnan(row['dea-12-26-9']) else -1,
            'macd-12-26-9': row['macd-12-26-9'] if not math.isnan(row['macd-12-26-9']) else -1,
            'dif-10-20-5': row['dif-10-20-5'] if not math.isnan(row['dif-10-20-5']) else -1,
            'dea-10-20-5': row['dea-10-20-5'] if not math.isnan(row['dea-10-20-5']) else -1,
            'macd-10-20-5': row['macd-10-20-5'] if not math.isnan(row['macd-10-20-5']) else -1,
            'dif-9-12-6': row['dif-9-12-6'] if not math.isnan(row['dif-9-12-6']) else -1,
            'dea-9-12-6': row['dea-9-12-6'] if not math.isnan(row['dea-9-12-6']) else -1,
            'macd-9-12-6': row['macd-9-12-6'] if not math.isnan(row['macd-9-12-6']) else -1
        }
        # print type(row_index), row_index, '%s_%d-%02d-%02d' % (symbol, row_index.year, row_index.month, row_index.day)
        res = es.index(index="stock-tushare", doc_type='data-history', id=doc_id ,body=doc)
        # print 'will index: %s' % row_index 
    else:
        pass
