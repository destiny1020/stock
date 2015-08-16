import sys
from sqlalchemy import create_engine
import tushare as ts

symbol = sys.argv[1]
start = sys.argv[2]
end = sys.argv[3]

df = ts.get_h_data(symbol,start=start,end=end).to_excel('d:/stock/tushare/' + sys.argv[1] + '_' + start + '-' + end + '.xls')
engine = create_engine('mysql://root:adobe@127.0.0.1/tushare?charset=utf8')

df.to_sql('daily_data',engine)