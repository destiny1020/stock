package com.destiny1020.stock.ths.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StockDaily {

    /**
     * 日期
     */
    private Date date;

    /**
     * 代码
     */
    private String symbol;

    /**
     * 名称
     */
    private String name;

    private boolean canFinancing;

    /**
     * 涨幅%
     */
    private BigDecimal percentage;

    /**
     * 现价
     */
    private BigDecimal current;

    /**
     * 涨跌
     */
    private BigDecimal change;

    /**
     * 总手 - 实际上导出的单位是**股
     */
    private BigDecimal volume;

    /**
     * 换手率
     */
    private BigDecimal turnover;

    /**
     * 量比 - Quantity Relative Ratio
     */
    private BigDecimal qrr;

    /**
     * 买入信号 --- THS特有字段
     */
    private int buySignal;

    /**
     * 利好
     */
    private boolean hasGood;

    /**
     * 利空
     */
    private boolean hasBad;

    /**
     * 所属行业
     */
    private String block;

    /**
     * 现手 - 导出单位也是股。对深市更有意义，因为该数值体现的是最后集合竞价的成交量
     */
    private BigDecimal latestVolume;

    /**
     * 开盘
     */
    private BigDecimal open;

    /**
     * 昨收
     */
    private BigDecimal lastClose;

    /**
     * 最高
     */
    private BigDecimal high;

    /**
     * 最低
     */
    private BigDecimal low;

    /**
     * 买价 --- 涨停时只有该字段
     */
    private BigDecimal buy;

    /**
     * 卖价 --- 跌停时只有该字段
     */
    private BigDecimal sell;

    /**
     * 主力净量 --- Main Force Net Factor
     */
    private BigDecimal mfNetFactor;

    /**
     * 主力金额 --- Main Force Amount
     */
    private BigDecimal mfAmount;

    /**
     * 散户数量 --- Individual Investor Number
     */
    private BigDecimal iiNumber;

    /**
     * 动态市盈率
     */
    private BigDecimal dpe;

    /**
     * 市净率
     */
    private BigDecimal pb;

    /**
     * 买量 --- 买一挂单量
     */
    private BigDecimal buyVolume;

    /**
     * 卖量 --- 卖一挂单量
     */
    private BigDecimal sellVolume;

    /**
     * 委比
     */
    private BigDecimal delegateRatio;

    /**
     * 振幅
     */
    private BigDecimal amplitude;

    /**
     * 总金额
     */
    private BigDecimal amount;

    /**
     * 均笔额
     */
    private BigDecimal amountPerDeal;

    /**
     * 笔数
     */
    private BigDecimal dealNumber;

    /**
     * 股/笔 --- 平均每笔成交股数
     */
    private BigDecimal averageSharePerDeal;

    /**
     * 成交均价 --- 均笔额 / (股/笔)
     */
    private BigDecimal averagePrice;

    /**
     * 外盘
     */
    private BigDecimal outVolume;

    /**
     * 内盘
     */
    private BigDecimal inVolume;

    /**
     * 总市值
     */
    private BigDecimal totalMarketCapital;

    /**
     * 流通市值
     */
    private BigDecimal circulationMarketCapital;

    /**
     * 指数贡献度
     */
    private BigDecimal indexContribution;

    /**
     * 星级
     */
    private BigDecimal star;

    /**
     * 委差
     */
    private BigDecimal delegateDiff;

    /**
     * 机构动向
     */
    private BigDecimal orgTrend;

    /**
     * 开盘涨幅
     */
    private BigDecimal openPercentage;

    /**
     * 实体涨幅
     */
    private BigDecimal barPercentage;

    /**
     * 总股本
     */
    private BigDecimal totalShares;

    /**
     * 流通股本
     */
    private BigDecimal circulationShares;

    /**
     * 流通A股
     */
    private BigDecimal circulationAShares;

    /**
     * 流通B股
     */
    private BigDecimal circulationBShares;

    /**
     * 流通比例
     */
    private BigDecimal circulationRatio;

    /**
     * 股东人数
     */
    private int shareholders;

    /**
     * 人均持股数
     */
    private int sharePerHolder;

    /**
     * 静态市盈率
     */
    private BigDecimal pe;

    /**
     * 利润总额
     */
    private BigDecimal totalProfit;

    /**
     * 净利润
     */
    private BigDecimal netProfit;

    /**
     * 净利润增长率
     */
    private BigDecimal profitIncreasePercentage;

    /**
     * 每股盈利
     */
    private BigDecimal eps;

    /**
     * 每股净资产 --- Net Asset Value per Share
     */
    private BigDecimal navps;

    /**
     * 每股公积金 --- Provident Fund per Share
     */
    private BigDecimal pfps;

    /**
     * 资产总计
     */
    private BigDecimal totalAssetAmount;

    /**
     * 流动资产
     */
    private BigDecimal currentAssetAmount;

    /**
     * 固定资产
     */
    private BigDecimal fixedAssetAmount;

    /**
     * 无形资产
     */
    private BigDecimal intangibleAssetAmount;

    /**
     * 主营业务收入 --- Prime Operating Revenue
     */
    private BigDecimal por;

    /**
     * 主营业务利润率 --- Prime Operating Profit Ratio
     */
    private BigDecimal popr;

    /**
     * 净资产收益率 --- Return on Equity
     */
    private BigDecimal roe;

    /**
     * 资产负债比率 --- Asset Liability Ratio
     */
    private BigDecimal alr;

    /**
     * 负债合计
     */
    private BigDecimal totalLiabilities;

    /**
     * 股东权益合计 --- Total Shareholder's Equity
     */
    private BigDecimal tse;

    /**
     * 公积金 --- Provident Fund
     */
    private BigDecimal pf;

    /**
     * 投资收益占比 --- Return on Investment Ratio
     */
    private BigDecimal roir;

    /**
     * 资金流向相关 --- Below
     */

    /**
     * 净流入
     */
    private BigDecimal netCapitalIn;

    /**
     * 大单净流入
     */
    private BigDecimal bigCapitalIn;

    /**
     * 大单净流出
     */
    private BigDecimal bigCapitalOut;

    /**
     * 大单净额
     */
    private BigDecimal bigNetCapital;

    /**
     * 大单净额占比
     */
    private BigDecimal bigNetCapitalPercentage;

    /**
     * 大单总额
     */
    private BigDecimal bigCapitalTotal;

    /**
     * 大单总额占比
     */
    private BigDecimal bigCapitalTotalPercentage;

    /**
     * 中单净流入
     */
    private BigDecimal midCapitalIn;

    /**
     * 中单净流出
     */
    private BigDecimal midCapitalOut;

    /**
     * 中单净额
     */
    private BigDecimal midNetCapital;

    /**
     * 中单净额占比
     */
    private BigDecimal midNetCapitalPercentage;

    /**
     * 中单总额
     */
    private BigDecimal midCapitalTotal;

    /**
     * 中单总额占比
     */
    private BigDecimal midCapitalTotalPercentage;

    /**
     * 小单净流入
     */
    private BigDecimal smallCapitalIn;

    /**
     * 小单净流出
     */
    private BigDecimal smallCapitalOut;

    /**
     * 小单净额
     */
    private BigDecimal smallNetCapital;

    /**
     * 小单净额占比
     */
    private BigDecimal smallNetCapitalPercentage;

    /**
     * 小单总额
     */
    private BigDecimal smallCapitalTotal;

    /**
     * 小单总额占比
     */
    private BigDecimal smallCapitalTotalPercentage;

    /**
     * 主力增仓相关 --- Below
     */

    /**
     * 今日增仓占比
     */
    private BigDecimal oneIncPercentage;

    /**
     * 今日增仓排名
     */
    private int oneIncRank;

    /**
     * 今日涨幅
     */
    private BigDecimal oneIncChange;

    /**
     * 2日增仓占比
     */
    private BigDecimal twoIncPercentage;

    /**
     * 2日增仓排名
     */
    private int twoIncRank;

    /**
     * 2日涨幅
     */
    private BigDecimal twoIncChange;

    /**
     * 3日增仓占比
     */
    private BigDecimal threeIncPercentage;

    /**
     * 3日增仓排名
     */
    private int threeIncRank;

    /**
     * 3日涨幅
     */
    private BigDecimal threeIncChange;

    /**
     * 5日增仓占比
     */
    private BigDecimal fiveIncPercentage;

    /**
     * 5日增仓排名
     */
    private int fiveIncRank;

    /**
     * 5日涨幅
     */
    private BigDecimal fiveIncChange;

    /**
     * 10日增仓占比
     */
    private BigDecimal tenIncPercentage;

    /**
     * 10日增仓排名
     */
    private int tenIncRank;

    /**
     * 10日涨幅
     */
    private BigDecimal tenIncChange;

    public StockDaily(String symbol, String name) {
        super();
        this.symbol = symbol;
        this.name = name;
    }

    public StockDaily() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public BigDecimal getQrr() {
        return qrr;
    }

    public void setQrr(BigDecimal qrr) {
        this.qrr = qrr;
    }

    public int getBuySignal() {
        return buySignal;
    }

    public void setBuySignal(int buySignal) {
        this.buySignal = buySignal;
    }

    public boolean isHasGood() {
        return hasGood;
    }

    public void setHasGood(boolean hasGood) {
        this.hasGood = hasGood;
    }

    public boolean isHasBad() {
        return hasBad;
    }

    public void setHasBad(boolean hasBad) {
        this.hasBad = hasBad;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public BigDecimal getLatestVolume() {
        return latestVolume;
    }

    public void setLatestVolume(BigDecimal latestVolume) {
        this.latestVolume = latestVolume;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getLastClose() {
        return lastClose;
    }

    public void setLastClose(BigDecimal lastClose) {
        this.lastClose = lastClose;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public void setSell(BigDecimal sell) {
        this.sell = sell;
    }

    public BigDecimal getMfNetFactor() {
        return mfNetFactor;
    }

    public void setMfNetFactor(BigDecimal mfNetFactor) {
        this.mfNetFactor = mfNetFactor;
    }

    public BigDecimal getMfAmount() {
        return mfAmount;
    }

    public void setMfAmount(BigDecimal mfAmount) {
        this.mfAmount = mfAmount;
    }

    public BigDecimal getIiNumber() {
        return iiNumber;
    }

    public void setIiNumber(BigDecimal iiNumber) {
        this.iiNumber = iiNumber;
    }

    public BigDecimal getDpe() {
        return dpe;
    }

    public void setDpe(BigDecimal dpe) {
        this.dpe = dpe;
    }

    public BigDecimal getPb() {
        return pb;
    }

    public void setPb(BigDecimal pb) {
        this.pb = pb;
    }

    public BigDecimal getBuyVolume() {
        return buyVolume;
    }

    public void setBuyVolume(BigDecimal buyVolume) {
        this.buyVolume = buyVolume;
    }

    public BigDecimal getSellVolume() {
        return sellVolume;
    }

    public void setSellVolume(BigDecimal sellVolume) {
        this.sellVolume = sellVolume;
    }

    public BigDecimal getDelegateRatio() {
        return delegateRatio;
    }

    public void setDelegateRatio(BigDecimal delegateRatio) {
        this.delegateRatio = delegateRatio;
    }

    public BigDecimal getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(BigDecimal amplitude) {
        this.amplitude = amplitude;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountPerDeal() {
        return amountPerDeal;
    }

    public void setAmountPerDeal(BigDecimal amountPerDeal) {
        this.amountPerDeal = amountPerDeal;
    }

    public BigDecimal getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(BigDecimal dealNumber) {
        this.dealNumber = dealNumber;
    }

    public BigDecimal getAverageSharePerDeal() {
        return averageSharePerDeal;
    }

    public void setAverageSharePerDeal(BigDecimal averageSharePerDeal) {
        this.averageSharePerDeal = averageSharePerDeal;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getOutVolume() {
        return outVolume;
    }

    public void setOutVolume(BigDecimal outVolume) {
        this.outVolume = outVolume;
    }

    public BigDecimal getInVolume() {
        return inVolume;
    }

    public void setInVolume(BigDecimal inVolume) {
        this.inVolume = inVolume;
    }

    public BigDecimal getTotalMarketCapital() {
        return totalMarketCapital;
    }

    public void setTotalMarketCapital(BigDecimal totalMarketCapital) {
        this.totalMarketCapital = totalMarketCapital;
    }

    public BigDecimal getCirculationMarketCapital() {
        return circulationMarketCapital;
    }

    public void setCirculationMarketCapital(BigDecimal circulationMarketCapital) {
        this.circulationMarketCapital = circulationMarketCapital;
    }

    public BigDecimal getIndexContribution() {
        return indexContribution;
    }

    public void setIndexContribution(BigDecimal indexContribution) {
        this.indexContribution = indexContribution;
    }

    public BigDecimal getStar() {
        return star;
    }

    public void setStar(BigDecimal star) {
        this.star = star;
    }

    public BigDecimal getDelegateDiff() {
        return delegateDiff;
    }

    public void setDelegateDiff(BigDecimal delegateDiff) {
        this.delegateDiff = delegateDiff;
    }

    public BigDecimal getOrgTrend() {
        return orgTrend;
    }

    public void setOrgTrend(BigDecimal orgTrend) {
        this.orgTrend = orgTrend;
    }

    public BigDecimal getOpenPercentage() {
        return openPercentage;
    }

    public void setOpenPercentage(BigDecimal openPercentage) {
        this.openPercentage = openPercentage;
    }

    public BigDecimal getBarPercentage() {
        return barPercentage;
    }

    public void setBarPercentage(BigDecimal barPercentage) {
        this.barPercentage = barPercentage;
    }

    public BigDecimal getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(BigDecimal totalShares) {
        this.totalShares = totalShares;
    }

    public BigDecimal getCirculationShares() {
        return circulationShares;
    }

    public void setCirculationShares(BigDecimal circulationShares) {
        this.circulationShares = circulationShares;
    }

    public BigDecimal getCirculationAShares() {
        return circulationAShares;
    }

    public void setCirculationAShares(BigDecimal circulationAShares) {
        this.circulationAShares = circulationAShares;
    }

    public BigDecimal getCirculationBShares() {
        return circulationBShares;
    }

    public void setCirculationBShares(BigDecimal circulationBShares) {
        this.circulationBShares = circulationBShares;
    }

    public BigDecimal getCirculationRatio() {
        return circulationRatio;
    }

    public void setCirculationRatio(BigDecimal circulationRatio) {
        this.circulationRatio = circulationRatio;
    }

    public int getShareholders() {
        return shareholders;
    }

    public void setShareholders(int shareholders) {
        this.shareholders = shareholders;
    }

    public int getSharePerHolder() {
        return sharePerHolder;
    }

    public void setSharePerHolder(int sharePerHolder) {
        this.sharePerHolder = sharePerHolder;
    }

    public BigDecimal getPe() {
        return pe;
    }

    public void setPe(BigDecimal pe) {
        this.pe = pe;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public BigDecimal getProfitIncreasePercentage() {
        return profitIncreasePercentage;
    }

    public void setProfitIncreasePercentage(BigDecimal profitIncreasePercentage) {
        this.profitIncreasePercentage = profitIncreasePercentage;
    }

    public BigDecimal getEps() {
        return eps;
    }

    public void setEps(BigDecimal eps) {
        this.eps = eps;
    }

    public BigDecimal getNavps() {
        return navps;
    }

    public void setNavps(BigDecimal navps) {
        this.navps = navps;
    }

    public BigDecimal getPfps() {
        return pfps;
    }

    public void setPfps(BigDecimal pfps) {
        this.pfps = pfps;
    }

    public BigDecimal getTotalAssetAmount() {
        return totalAssetAmount;
    }

    public void setTotalAssetAmount(BigDecimal totalAssetAmount) {
        this.totalAssetAmount = totalAssetAmount;
    }

    public BigDecimal getCurrentAssetAmount() {
        return currentAssetAmount;
    }

    public void setCurrentAssetAmount(BigDecimal currentAssetAmount) {
        this.currentAssetAmount = currentAssetAmount;
    }

    public BigDecimal getFixedAssetAmount() {
        return fixedAssetAmount;
    }

    public void setFixedAssetAmount(BigDecimal fixedAssetAmount) {
        this.fixedAssetAmount = fixedAssetAmount;
    }

    public BigDecimal getIntangibleAssetAmount() {
        return intangibleAssetAmount;
    }

    public void setIntangibleAssetAmount(BigDecimal intangibleAssetAmount) {
        this.intangibleAssetAmount = intangibleAssetAmount;
    }

    public BigDecimal getPor() {
        return por;
    }

    public void setPor(BigDecimal por) {
        this.por = por;
    }

    public BigDecimal getPopr() {
        return popr;
    }

    public void setPopr(BigDecimal popr) {
        this.popr = popr;
    }

    public BigDecimal getRoe() {
        return roe;
    }

    public void setRoe(BigDecimal roe) {
        this.roe = roe;
    }

    public BigDecimal getAlr() {
        return alr;
    }

    public void setAlr(BigDecimal alr) {
        this.alr = alr;
    }

    public BigDecimal getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(BigDecimal totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public BigDecimal getTse() {
        return tse;
    }

    public void setTse(BigDecimal tse) {
        this.tse = tse;
    }

    public BigDecimal getPf() {
        return pf;
    }

    public void setPf(BigDecimal pf) {
        this.pf = pf;
    }

    public BigDecimal getRoir() {
        return roir;
    }

    public void setRoir(BigDecimal roir) {
        this.roir = roir;
    }

    public BigDecimal getNetCapitalIn() {
        return netCapitalIn;
    }

    public void setNetCapitalIn(BigDecimal netCapitalIn) {
        this.netCapitalIn = netCapitalIn;
    }

    public BigDecimal getBigCapitalIn() {
        return bigCapitalIn;
    }

    public void setBigCapitalIn(BigDecimal bigCapitalIn) {
        this.bigCapitalIn = bigCapitalIn;
    }

    public BigDecimal getBigCapitalOut() {
        return bigCapitalOut;
    }

    public void setBigCapitalOut(BigDecimal bigCapitalOut) {
        this.bigCapitalOut = bigCapitalOut;
    }

    public BigDecimal getBigNetCapital() {
        return bigNetCapital;
    }

    public void setBigNetCapital(BigDecimal bigNetCapital) {
        this.bigNetCapital = bigNetCapital;
    }

    public BigDecimal getBigNetCapitalPercentage() {
        return bigNetCapitalPercentage;
    }

    public void setBigNetCapitalPercentage(BigDecimal bigNetCapitalPercentage) {
        this.bigNetCapitalPercentage = bigNetCapitalPercentage;
    }

    public BigDecimal getBigCapitalTotal() {
        return bigCapitalTotal;
    }

    public void setBigCapitalTotal(BigDecimal bigCapitalTotal) {
        this.bigCapitalTotal = bigCapitalTotal;
    }

    public BigDecimal getBigCapitalTotalPercentage() {
        return bigCapitalTotalPercentage;
    }

    public void setBigCapitalTotalPercentage(
            BigDecimal bigCapitalTotalPercentage) {
        this.bigCapitalTotalPercentage = bigCapitalTotalPercentage;
    }

    public BigDecimal getMidCapitalIn() {
        return midCapitalIn;
    }

    public void setMidCapitalIn(BigDecimal midCapitalIn) {
        this.midCapitalIn = midCapitalIn;
    }

    public BigDecimal getMidCapitalOut() {
        return midCapitalOut;
    }

    public void setMidCapitalOut(BigDecimal midCapitalOut) {
        this.midCapitalOut = midCapitalOut;
    }

    public BigDecimal getMidNetCapital() {
        return midNetCapital;
    }

    public void setMidNetCapital(BigDecimal midNetCapital) {
        this.midNetCapital = midNetCapital;
    }

    public BigDecimal getMidNetCapitalPercentage() {
        return midNetCapitalPercentage;
    }

    public void setMidNetCapitalPercentage(BigDecimal midNetCapitalPercentage) {
        this.midNetCapitalPercentage = midNetCapitalPercentage;
    }

    public BigDecimal getMidCapitalTotal() {
        return midCapitalTotal;
    }

    public void setMidCapitalTotal(BigDecimal midCapitalTotal) {
        this.midCapitalTotal = midCapitalTotal;
    }

    public BigDecimal getMidCapitalTotalPercentage() {
        return midCapitalTotalPercentage;
    }

    public void setMidCapitalTotalPercentage(
            BigDecimal midCapitalTotalPercentage) {
        this.midCapitalTotalPercentage = midCapitalTotalPercentage;
    }

    public BigDecimal getSmallCapitalIn() {
        return smallCapitalIn;
    }

    public void setSmallCapitalIn(BigDecimal smallCapitalIn) {
        this.smallCapitalIn = smallCapitalIn;
    }

    public BigDecimal getSmallCapitalOut() {
        return smallCapitalOut;
    }

    public void setSmallCapitalOut(BigDecimal smallCapitalOut) {
        this.smallCapitalOut = smallCapitalOut;
    }

    public BigDecimal getSmallNetCapital() {
        return smallNetCapital;
    }

    public void setSmallNetCapital(BigDecimal smallNetCapital) {
        this.smallNetCapital = smallNetCapital;
    }

    public BigDecimal getSmallNetCapitalPercentage() {
        return smallNetCapitalPercentage;
    }

    public void setSmallNetCapitalPercentage(
            BigDecimal smallNetCapitalPercentage) {
        this.smallNetCapitalPercentage = smallNetCapitalPercentage;
    }

    public BigDecimal getSmallCapitalTotal() {
        return smallCapitalTotal;
    }

    public void setSmallCapitalTotal(BigDecimal smallCapitalTotal) {
        this.smallCapitalTotal = smallCapitalTotal;
    }

    public BigDecimal getSmallCapitalTotalPercentage() {
        return smallCapitalTotalPercentage;
    }

    public void setSmallCapitalTotalPercentage(
            BigDecimal smallCapitalTotalPercentage) {
        this.smallCapitalTotalPercentage = smallCapitalTotalPercentage;
    }

    public BigDecimal getOneIncPercentage() {
        return oneIncPercentage;
    }

    public void setOneIncPercentage(BigDecimal oneIncPercentage) {
        this.oneIncPercentage = oneIncPercentage;
    }

    public int getOneIncRank() {
        return oneIncRank;
    }

    public void setOneIncRank(int oneIncRank) {
        this.oneIncRank = oneIncRank;
    }

    public BigDecimal getOneIncChange() {
        return oneIncChange;
    }

    public void setOneIncChange(BigDecimal oneIncChange) {
        this.oneIncChange = oneIncChange;
    }

    public BigDecimal getTwoIncPercentage() {
        return twoIncPercentage;
    }

    public void setTwoIncPercentage(BigDecimal twoIncPercentage) {
        this.twoIncPercentage = twoIncPercentage;
    }

    public int getTwoIncRank() {
        return twoIncRank;
    }

    public void setTwoIncRank(int twoIncRank) {
        this.twoIncRank = twoIncRank;
    }

    public BigDecimal getTwoIncChange() {
        return twoIncChange;
    }

    public void setTwoIncChange(BigDecimal twoIncChange) {
        this.twoIncChange = twoIncChange;
    }

    public BigDecimal getThreeIncPercentage() {
        return threeIncPercentage;
    }

    public void setThreeIncPercentage(BigDecimal threeIncPercentage) {
        this.threeIncPercentage = threeIncPercentage;
    }

    public int getThreeIncRank() {
        return threeIncRank;
    }

    public void setThreeIncRank(int threeIncRank) {
        this.threeIncRank = threeIncRank;
    }

    public BigDecimal getThreeIncChange() {
        return threeIncChange;
    }

    public void setThreeIncChange(BigDecimal threeIncChange) {
        this.threeIncChange = threeIncChange;
    }

    public BigDecimal getFiveIncPercentage() {
        return fiveIncPercentage;
    }

    public void setFiveIncPercentage(BigDecimal fiveIncPercentage) {
        this.fiveIncPercentage = fiveIncPercentage;
    }

    public int getFiveIncRank() {
        return fiveIncRank;
    }

    public void setFiveIncRank(int fiveIncRank) {
        this.fiveIncRank = fiveIncRank;
    }

    public BigDecimal getFiveIncChange() {
        return fiveIncChange;
    }

    public void setFiveIncChange(BigDecimal fiveIncChange) {
        this.fiveIncChange = fiveIncChange;
    }

    public BigDecimal getTenIncPercentage() {
        return tenIncPercentage;
    }

    public void setTenIncPercentage(BigDecimal tenIncPercentage) {
        this.tenIncPercentage = tenIncPercentage;
    }

    public int getTenIncRank() {
        return tenIncRank;
    }

    public void setTenIncRank(int tenIncRank) {
        this.tenIncRank = tenIncRank;
    }

    public BigDecimal getTenIncChange() {
        return tenIncChange;
    }

    public void setTenIncChange(BigDecimal tenIncChange) {
        this.tenIncChange = tenIncChange;
    }

    public boolean isCanFinancing() {
        return canFinancing;
    }

    public void setCanFinancing(boolean canFinancing) {
        this.canFinancing = canFinancing;
    }

}
