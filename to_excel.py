import sys
import tushare as ts

symbol = sys.argv[1]
start = sys.argv[2]
end = sys.argv[3]

ts.get_h_data(symbol,start=start,end=end).to_excel('d:/stock/tushare/' + sys.argv[1] + '_' + start + '-' + end + '.xls')
