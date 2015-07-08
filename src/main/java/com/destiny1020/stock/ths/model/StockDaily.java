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
  private BigDecimal lastestVolume;

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
   * 买量
   */
  private BigDecimal buyVolume;

  /**
   * 卖量
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
  private BigDecimal shareholders;

  /**
   * 人均持股数
   */
  private BigDecimal sharePerHolder;

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
   * 利润增长率
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

  public StockDaily(String symbol, String name) {
    super();
    this.symbol = symbol;
    this.name = name;
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

  public BigDecimal getLastestVolume() {
    return lastestVolume;
  }

  public void setLastestVolume(BigDecimal lastestVolume) {
    this.lastestVolume = lastestVolume;
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

  public BigDecimal getShareholders() {
    return shareholders;
  }

  public void setShareholders(BigDecimal shareholders) {
    this.shareholders = shareholders;
  }

  public BigDecimal getSharePerHolder() {
    return sharePerHolder;
  }

  public void setSharePerHolder(BigDecimal sharePerHolder) {
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

}
