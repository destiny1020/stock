from sqlalchemy import create_engine
import pandas as pd
import matplotlib.pyplot as plt
import matplotlib

matplotlib.style.use('ggplot')
pd.options.display.mpl_style = 'default'

# retrieve the current data
engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')

df = pd.read_sql_query('select * from data_daily where code = ' + '000001' + ' order by sequence', engine, index_col='date')

# percentage change
change_series = df['p_change']

change_series_chart = change_series.hist(bins=50)

print type(change_series_chart)

fig = change_series_chart.get_figure()
fig.savefig("output2.png")
