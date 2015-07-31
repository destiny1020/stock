package com.destiny1020.stock.model;

import java.math.BigDecimal;

import com.destiny1020.stock.es.IEsIDEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the individual stock information under its belonging block.
 * 
 * @author destiny
 *
 */
public class StockBlockDaily extends ESEntity implements IEsIDEntity {

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
   * 主力净量 --- Main Force Net Factor
   */
  private BigDecimal mfNetFactor;

  /**
   * 主力金额 --- Main Force Amount
   */
  private BigDecimal mfAmount;

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
   * 所属行业
   */
  private String block;

  /**
   * 5日涨幅
   */
  private BigDecimal fiveIncPercentage;

  /**
   * 10日涨幅
   */
  private BigDecimal tenIncPercentage;

  /**
   * 20日涨幅
   */
  private BigDecimal twentyIncPercentage;

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
   * 动态市盈率
   */
  private BigDecimal dpe;

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
   * Calculated field
   */
  private BigDecimal averagePrice;

  /**
   * 流通市值
   */
  private BigDecimal circulationMarketCapital;

  /**
   * 总市值
   */
  private BigDecimal totalMarketCapital;

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

  public String getBlock() {
    return block;
  }

  public void setBlock(String block) {
    this.block = block;
  }

  public BigDecimal getFiveIncPercentage() {
    return fiveIncPercentage;
  }

  public void setFiveIncPercentage(BigDecimal fiveIncPercentage) {
    this.fiveIncPercentage = fiveIncPercentage;
  }

  public BigDecimal getTenIncPercentage() {
    return tenIncPercentage;
  }

  public void setTenIncPercentage(BigDecimal tenIncPercentage) {
    this.tenIncPercentage = tenIncPercentage;
  }

  public BigDecimal getTwentyIncPercentage() {
    return twentyIncPercentage;
  }

  public void setTwentyIncPercentage(BigDecimal twentyIncPercentage) {
    this.twentyIncPercentage = twentyIncPercentage;
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

  public BigDecimal getDpe() {
    return dpe;
  }

  public void setDpe(BigDecimal dpe) {
    this.dpe = dpe;
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

  public BigDecimal getCirculationMarketCapital() {
    return circulationMarketCapital;
  }

  public void setCirculationMarketCapital(BigDecimal circulationMarketCapital) {
    this.circulationMarketCapital = circulationMarketCapital;
  }

  public BigDecimal getTotalMarketCapital() {
    return totalMarketCapital;
  }

  public void setTotalMarketCapital(BigDecimal totalMarketCapital) {
    this.totalMarketCapital = totalMarketCapital;
  }

  @JsonIgnore
  @Override
  public String getEsID() {
    return symbol;
  }

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public void setAveragePrice(BigDecimal averagePrice) {
    this.averagePrice = averagePrice;
  }

}
