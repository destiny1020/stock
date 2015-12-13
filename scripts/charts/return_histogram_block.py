# -*- coding: utf-8 -*-
# draw the chart for tickers within block

import pandas as pd
import tushare as ts
import numpy as np
import matplotlib.pyplot as plt
import matplotlib

matplotlib.style.use('ggplot')
pd.options.display.mpl_style = 'default'

# software targets
targets = [
    '000555', #神州信息
    '002296', #辉煌科技
    '002657', #中科金财
    '002405', #四维图新
    '002331', #皖通科技
    '002065', #东华软件
    '002373', #千方科技
    '002232', #启明信息
    '002362', #汉王科技
    '002609', #捷顺科技
    '600571', #信雅达
    '002421', #达实智能
    '600728', #佳都科技
    '600570', #恒生电子
    '002649', #博彦科技
    '002766', #索菱股份
    '002512', #达华智能
    '601519', #大智慧
    '600446', #金证股份
    '002380', #科远股份
    '002410', #广联达
    '002153', #石基信息
    '002230', #科大讯飞
    '600797', #浙大网新
    '600536', #中国软件
    '600845', #宝信软件
    '002195', #二三四五
    '600588', #用友网络
    '002439', #启明星辰
    '002771', #真视通
    '002268', #卫 士 通
    '002063', #远光软件
    '600410', #华胜天成
    '002279', #久其软件
    '600654', #中安消
    '600756', #浪潮软件
    '002280', #联络互动
    '002642', #荣之联
    '002253', #川大智胜
    '603636', #南威软件
    '002474', #榕基软件
    '600476', #湘邮科技
    '603918', #金桥信息
    '600271', #航天信息
    '600455', #博通股份
    '600718', #东软集团
    # '603508', #思维列控
    '002368', #太极股份
    '002401'  #中海科技
    # '002777'  #久远银海
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

# chart histogram
charts_np = df_total.hist(bins=50, figsize=(40, 40))
charts_np.tolist()[0][0].get_figure().savefig("chart-hist.png")

# chart box
charts_np = df_total.plot(kind='box', figsize=(40, 40))
charts_np.set_xticklabels(map(lambda x: x.get_text(), charts_np.get_xticklabels()), rotation=90)
charts_np.get_figure().savefig("chart-box.png")

# correlation scatter chart
chart_scatter = pd.scatter_matrix(df_total, diagonal='kde', alpha=0.7, figsize=(80, 80))
chart_scatter.tolist()[0][0].get_figure().savefig("chart-scatter.png")