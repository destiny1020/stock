package com.destiny1020.stock.ths.model;

import java.math.BigDecimal;

/**
 * Represent each Index provided by THS.
 * 
 * @author Administrator
 *
 */
public class StockBlockIndex {

  /**
   * 板块名称
   */
  private String name;

  /**
   * 涨幅%
   */
  private BigDecimal percentage;

  /**
   * 主力净量 --- Main Force Net Factor
   */
  private BigDecimal mfNetFactor;

  /**
   * 主力金额 --- Main Force Amount
   */
  private BigDecimal mfAmount;

  /**
   * 量比 - Quantity Relative Ratio
   */
  private BigDecimal qrr;

  /**
   * 涨家数
   */
  private int riseCount;

  /**
   * 跌家数
   */
  private int fallCount;

  /**
   * 领涨股
   */
  private String pioneer;

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
   * 总手 - 实际上导出的单位是**股
   */
  private BigDecimal volume;

  /**
   * 总金额
   */
  private BigDecimal amount;

  /**
   * 总市值
   */
  private BigDecimal totalMarketCapital;

  /**
   * 流通市值
   */
  private BigDecimal circulationMarketCapital;

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

  public BigDecimal getQrr() {
    return qrr;
  }

  public void setQrr(BigDecimal qrr) {
    this.qrr = qrr;
  }

  public int getRiseCount() {
    return riseCount;
  }

  public void setRiseCount(int riseCount) {
    this.riseCount = riseCount;
  }

  public int getFallCount() {
    return fallCount;
  }

  public void setFallCount(int fallCount) {
    this.fallCount = fallCount;
  }

  public String getPioneer() {
    return pioneer;
  }

  public void setPioneer(String pioneer) {
    this.pioneer = pioneer;
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

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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
}
