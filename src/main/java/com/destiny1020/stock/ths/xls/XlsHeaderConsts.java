package com.destiny1020.stock.ths.xls;

public class XlsHeaderConsts {

    public static final String HEADER_CODE = "代码";
    public static final String HEADER_NAME = "名称";

    public static final String HEADER_PERCENTAGE = "涨幅%";
    public static final String HEADER_CURRENT = "现价";
    public static final String HEADER_CHANGE = "涨跌";
    public static final String HEADER_VOLUME = "总手";
    public static final String HEADER_TURNOVER = "换手%";
    public static final String HEADER_QRR = "量比";

    public static final String HEADER_BUY_SIGNAL = "买入信号";
    public static final String HEADER_HAS_GOOD = "利好";
    public static final String HEADER_HAS_BAD = "利空";
    public static final String HEADER_BLOCK = "所属行业";
    public static final String HEADER_LATEST_VOLUME = "现手";
    public static final String HEADER_OPEN = "开盘";
    public static final String HEADER_LAST_CLOSE = "昨收";
    public static final String HEADER_HIGH = "最高";
    public static final String HEADER_LOW = "最低";
    public static final String HEADER_BUY = "买价";
    public static final String HEADER_SELL = "卖价";

    public static final String HEADER_MF_NET_FACTOR = "主力净量";
    public static final String HEADER_MF_AMOUNT = "主力金额";
    public static final String HEADER_II_NUMBER = "散户数量";

    public static final String HEADER_DPE = "市盈(动)";
    public static final String HEADER_PB = "市净率";
    public static final String HEADER_BUY_BOLUME = "买量";
    public static final String HEADER_SELL_VOLUME = "卖量";
    public static final String HEADER_DELEGATE_RATIO = "委比%";
    public static final String HEADER_AMPLITUDE = "振幅%";

    public static final String HEADER_AMOUNT = "总金额";
    public static final String HEADER_AMOUNT_PER_DEAL = "均笔额";
    public static final String HEADER_DEAL_NUMBER = "笔数";
    public static final String HEADER_AVG_SHARE_PER_DEAL = "手/笔"; // 实际上：股/笔 --- 平均每笔成交股数
    public static final String HEADER_DEAL_AVG_PRICE = "成交均价"; // Calculated: 成交均价 --- 均笔额 / (股/笔)
    public static final String HEADER_OUT_VOLUME = "外盘";
    public static final String HEADER_IN_VOLUME = "内盘";

    public static final String HEADER_TOTAL_MARKET_CAPITAL = "总市值";
    public static final String HEADER_CIRCULATION_MARKET_CAPITAL = "流通市值";
    public static final String HEADER_INDEX_CONTRIBUTION = "贡献度";
    public static final String HEADER_STAR = "星级";
    public static final String HEADER_DELEGATE_DIFF = "委差";
    public static final String HEADER_ORG_TREND = "机构动向";
    public static final String HEADER_OPEN_PERCENTAGE = "开盘涨幅%";
    public static final String HEADER_BAR_PERCENTAGE = "实体涨幅%";

    public static final String HEADER_TOTAL_SHARES = "总股本";
    public static final String HEADER_CIRCULATION_SHARES = "流通股本";
    public static final String HEADER_CIRCULATION_A_SHARES = "流通A股";
    public static final String HEADER_CIRCULATION_B_SHARES = "流通B股";
    public static final String HEADER_CIRCULATION_RATIO = "流通比例%";

    public static final String HEADER_SHARE_HOLDERS = "股东总数";
    public static final String HEADER_SHARE_PER_HOLDER = "人均持股数";

    public static final String HEADER_PE = "市盈(静)";
    public static final String HEADER_TOTAL_PROFIT = "利润总额";
    public static final String HEADER_NET_PROFIT = "净利润";
    public static final String HEADER_NET_PROFIT_INC_PERCENTAGE = "净利润增长率";
    public static final String HEADER_EPS = "每股盈利";
    public static final String HEADER_NAVPS = "每股净资产";
    public static final String HEADER_PFPS = "每股公积金";
    public static final String HEADER_TOTAL_ASSET_AMOUNT = "资产总计";
    public static final String HEADER_CURRENT_ASSET_AMOUNT = "流动资产";
    public static final String HEADER_FIXED_ASSET_AMOUNT = "固定资产";
    public static final String HEADER_INTANGIBLE_ASSET_AMOUNT = "无形资产";
    public static final String HEADER_PRIME_OPERATING_REVENUE = "主营业务收入";
    public static final String HEADER_PRIME_OPERATING_PROFIT_RATIO = "主营利润率";
    public static final String HEADER_RETURN_ON_EQUITY = "净资产收益率";
    public static final String HEADER_ASSET_LIABILITY_RATIO = "资产负债比率";
    public static final String HEADER_TOTAL_LIABILITIES = "负债合计";
    public static final String HEADER_TOTAL_SHAREHOLDERS_EQUITY = "股东权益合计";
    public static final String HEADER_PROVIDENT_FUND = "公积金";
    public static final String HEADER_RETURN_ON_INVESTMENT_RATIO = "投资收益占比";

    // 资金流向相关
    public static final String HEADER_CAPITAL_NET = "净流入"; // 净流入

    public static final String HEADER_CAPITAL_BIG_IN = "LIURU"; // 实时大单统计 - LIURU
    public static final String HEADER_CAPITAL_BIG_OUT = "LIUCHU"; // 实时大单统计 - LIUCHU
    public static final String HEADER_CAPITAL_BIG_NET = "JINGE"; // 实时大单统计 - JINGE
    public static final String HEADER_CAPITAL_BIG_NET_PERCENTAGE = "JINGEZB"; // 实时大单统计 - JINGEZB
    public static final String HEADER_CAPITAL_BIG_TOTAL = "ZE"; // 实时大单统计 - ZE
    public static final String HEADER_CAPITAL_BIG_TOTAL_PERCENTAGE = "ZEZB"; // 实时大单统计 - ZEZB

    public static final String HEADER_CAPITAL_MID_IN = "LIURU"; // 实时中单统计 - LIURU
    public static final String HEADER_CAPITAL_MID_OUT = "LIUCHU"; // 实时中单统计 - LIUCHU
    public static final String HEADER_CAPITAL_MID_NET = "JINGE"; // 实时中单统计 - JINGE
    public static final String HEADER_CAPITAL_MID_NET_PERCENTAGE = "JINGEZB"; // 实时中单统计 - JINGEZB
    public static final String HEADER_CAPITAL_MID_TOTAL = "ZE"; // 实时中单统计 - ZE
    public static final String HEADER_CAPITAL_MID_TOTAL_PERCENTAGE = "ZEZB"; // 实时中单统计 - ZEZB

    public static final String HEADER_CAPITAL_SMALL_IN = "LIURU"; // 实时小单统计 - LIURU
    public static final String HEADER_CAPITAL_SMALL_OUT = "LIUCHU"; // 实时小单统计 - LIUCHU
    public static final String HEADER_CAPITAL_SMALL_NET = "JINGE"; // 实时小单统计 - JINGE
    public static final String HEADER_CAPITAL_SMALL_NET_PERCENTAGE = "JINGEZB"; // 实时小单统计 - JINGEZB
    public static final String HEADER_CAPITAL_SMALL_TOTAL = "ZE"; // 实时小单统计 - ZE
    public static final String HEADER_CAPITAL_SMALL_TOTAL_PERCENTAGE = "ZEZB"; // 实时小单统计 - ZEZB

    // 主力增仓相关
    public static final String HEADER_POSITION_ONE_INC_PERCENTAGE = "JRZCZB"; // 今日增仓排名 - JRZCZB
    public static final String HEADER_POSITION_ONE_INC_RANK = "PAIMING"; // 今日增仓排名 - PAIMING
    public static final String HEADER_POSITION_ONE_PRICE_PERCENTAGE = "今日涨幅%"; // 今日增仓排名 - 今日涨幅%

    public static final String HEADER_POSITION_TWO_INC_PERCENTAGE = "JRZCZB"; // 今日增仓排名 - JRZCZB
    public static final String HEADER_POSITION_TWO_INC_RANK = "PAIMING"; // 今日增仓排名 - PAIMING
    public static final String HEADER_POSITION_TWO_PRICE_PERCENTAGE = "ZHANGFU"; // 今日增仓排名 - 今日涨幅%

    public static final String HEADER_POSITION_THREE_INC_PERCENTAGE = "JRZCZB"; // 今日增仓排名 - JRZCZB
    public static final String HEADER_POSITION_THREE_INC_RANK = "PAIMING"; // 今日增仓排名 - PAIMING
    public static final String HEADER_POSITION_THREE_PRICE_PERCENTAGE = "ZHANGFU"; // 今日增仓排名 - 今日涨幅%

    public static final String HEADER_POSITION_FIVE_INC_PERCENTAGE = "JRZCZB"; // 今日增仓排名 - JRZCZB
    public static final String HEADER_POSITION_FIVE_INC_RANK = "PAIMING"; // 今日增仓排名 - PAIMING
    public static final String HEADER_POSITION_FIVE_PRICE_PERCENTAGE = "ZHANGFU"; // 今日增仓排名 - 今日涨幅%

    public static final String HEADER_POSITION_TEN_INC_PERCENTAGE = "JRZCZB"; // 今日增仓排名 - JRZCZB
    public static final String HEADER_POSITION_TEN_INC_RANK = "PAIMING"; // 今日增仓排名 - PAIMING
    public static final String HEADER_POSITION_TEN_PRICE_PERCENTAGE = "ZHANGFU"; // 今日增仓排名 - 今日涨幅%

}
