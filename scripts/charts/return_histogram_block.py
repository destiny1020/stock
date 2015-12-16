# -*- coding: utf-8 -*-
# draw the chart for tickers within block

import pandas as pd
import tushare as ts
import numpy as np
import matplotlib.pyplot as plt
import matplotlib
from pandas.tools.plotting import scatter_matrix

matplotlib.style.use('ggplot')
pd.options.display.mpl_style = 'default'

# software targets
targets = [
    '002657', #中科金财
    '002766', #索菱股份
    '002771', #真视通
    '002279', #久其软件
    '002280', #联络互动
    '603918', #金桥信息
    'sh',
    'sz',
    'zxb',
    'cyb'
]

df_total_list = []

for code in targets:
    df = ts.get_hist_data(code, start='2014-01-01', end='2015-12-31', ktype='D')
    df = df['p_change'].reset_index()
    df['ticker'] = code
    df.pivot('date', 'ticker', 'p_change')
    df_total_list.append(df)

df_total = pd.concat(df_total_list)
df_total = df_total.pivot('date', 'ticker', 'p_change')

# # chart histogram
# charts_np = df_total.hist(bins=50, figsize=(40, 40))
# charts_np.tolist()[0][0].get_figure().savefig("chart-hist.png")

# # chart box
# charts_np = df_total.plot(kind='box', figsize=(40, 40))
# charts_np.set_xticklabels(map(lambda x: x.get_text(), charts_np.get_xticklabels()), rotation=90)
# charts_np.get_figure().savefig("chart-box.png")

# correlation scatter chart
chart_scatter = scatter_matrix(df_total, diagonal='kde', alpha=0.7, figsize=(80, 80), xlim=[-10, 10], ylim=[-10, 10])
chart_scatter.tolist()[0][0].get_figure().savefig("chart-scatter.png")