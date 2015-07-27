package com.destiny1020.stock.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Records under the stocklist/symbol in ES.
 * e.g stocklist/sh600588 for 用友网络
 * 
 * @author Administrator
 *
 */
public class StockHistory extends ESEntity {

  /**
   * 序列号: 表示当前周期下第sequence根K线
   */
  private int sequence;

  /**
   * 分析周期
   * 1: 日线 (period=1day)   2: 周线(period=1week)   3: 月线(period=1month)
   */
  private int period;

  /**
   * 代码
   */
  private String symbol;

  /**
   * 名称
   */
  private String name;

  /**
   * 总手 - 实际上导出的单位是**股
   */
  private BigDecimal volume;

  /**
   * 收盘
   */
  private BigDecimal close;

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
   * 涨跌
   */
  private BigDecimal chg;

  /**
   * 涨幅
   */
  private BigDecimal percent;

  /**
   * 换手率
   */
  private BigDecimal turnrate;

  /**
   * 5日均线
   */
  private BigDecimal ma5;

  /**
   * 10日均线
   */
  private BigDecimal ma10;

  /**
   * 20日均线
   */
  private BigDecimal ma20;

  /**
   * 30日均线
   */
  private BigDecimal ma30;

  /**
   * MACD-DIF
   */
  private BigDecimal dif;

  /**
   * MACD-DEA
   */
  private BigDecimal dea;

  /**
   * MACD (12, 26, 9)
   */
  private BigDecimal macd;

  /**
   * Time
   */
  private String time;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getClose() {
    return close;
  }

  public void setClose(BigDecimal close) {
    this.close = close;
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

  public BigDecimal getChg() {
    return chg;
  }

  public void setChg(BigDecimal chg) {
    this.chg = chg;
  }

  public BigDecimal getPercent() {
    return percent;
  }

  public void setPercent(BigDecimal percent) {
    this.percent = percent;
  }

  public BigDecimal getTurnrate() {
    return turnrate;
  }

  public void setTurnrate(BigDecimal turnrate) {
    this.turnrate = turnrate;
  }

  public BigDecimal getMa5() {
    return ma5;
  }

  public void setMa5(BigDecimal ma5) {
    this.ma5 = ma5;
  }

  public BigDecimal getMa10() {
    return ma10;
  }

  public void setMa10(BigDecimal ma10) {
    this.ma10 = ma10;
  }

  public BigDecimal getMa20() {
    return ma20;
  }

  public void setMa20(BigDecimal ma20) {
    this.ma20 = ma20;
  }

  public BigDecimal getMa30() {
    return ma30;
  }

  public void setMa30(BigDecimal ma30) {
    this.ma30 = ma30;
  }

  public BigDecimal getDif() {
    return dif;
  }

  public void setDif(BigDecimal dif) {
    this.dif = dif;
  }

  public BigDecimal getDea() {
    return dea;
  }

  public void setDea(BigDecimal dea) {
    this.dea = dea;
  }

  public BigDecimal getMacd() {
    return macd;
  }

  public void setMacd(BigDecimal macd) {
    this.macd = macd;
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

  public int getSequence() {
    return sequence;
  }

  public void setSequence(int sequence) {
    this.sequence = sequence;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;

    // convert it to Date object --- Fri Feb 07 00:00:00 +0800 2014

  }

}
