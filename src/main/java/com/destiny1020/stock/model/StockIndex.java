package com.destiny1020.stock.model;

import java.math.BigDecimal;

/**
 * Represent each Index provided by THS. 
 * 
 * @author Administrator
 *
 */
public class StockIndex extends ESEntity {

  /**
   * 代码
   */
  private String symbol;

  /**
   * 名称
   */
  private String name;

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
   * 现手 - 导出单位也是股。对深市更有意义，因为该数值体现的是最后集合竞价的成交量
   */
  private BigDecimal latestVolume;

  /**
   * 开盘
   */
  private BigDecimal open;

  /**
   * 最高
   */
  private BigDecimal high;

  /**
   * 最低
   */
  private BigDecimal low;

  /**
   * 涨幅%
   */
  private BigDecimal percentage;

  /**
   * 量比 - Quantity Relative Ratio
   */
  private BigDecimal qrr;

  /**
   * 振幅
   */
  private BigDecimal amplitude;

  /**
   * 总金额
   */
  private BigDecimal amount;

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

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public BigDecimal getQrr() {
    return qrr;
  }

  public void setQrr(BigDecimal qrr) {
    this.qrr = qrr;
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

}
