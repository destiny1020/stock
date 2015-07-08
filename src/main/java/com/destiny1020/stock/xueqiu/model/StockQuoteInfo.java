package com.destiny1020.stock.xueqiu.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.destiny1020.stock.common.Market;

public class StockQuoteInfo {

  private BigDecimal amount;
  private BigDecimal amplitude;
  private BigDecimal change;
  private BigDecimal close;
  private String code;
  private BigDecimal current;
  private BigDecimal dividend;
  private BigDecimal eps;
  private Market exchange;
  private BigDecimal fall_stop;
  private BigDecimal float_market_capital;
  private BigDecimal high;
  private BigDecimal high52week;
  private BigDecimal last_close;
  private BigDecimal low;
  private BigDecimal low52week;
  private BigDecimal marketCapital;
  private String name;
  private BigDecimal net_assets;
  private BigDecimal open;
  private BigDecimal pb;
  private BigDecimal pe_lyr;
  private BigDecimal pe_ttm;
  private BigDecimal percentage;
  private BigDecimal psr;
  private BigDecimal rise_stop;
  private String symbol;
  private BigDecimal totalShares;
  private BigDecimal turnover_rate;
  private BigDecimal updateAt;
  private BigDecimal volume;
  private BigDecimal volumeAverage;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAmplitude() {
    return amplitude;
  }

  public void setAmplitude(BigDecimal amplitude) {
    this.amplitude = amplitude;
  }

  public BigDecimal getChange() {
    return change;
  }

  public void setChange(BigDecimal change) {
    this.change = change;
  }

  public BigDecimal getClose() {
    return close;
  }

  public void setClose(BigDecimal close) {
    this.close = close;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public BigDecimal getCurrent() {
    return current;
  }

  public void setCurrent(BigDecimal current) {
    this.current = current;
  }

  public BigDecimal getDividend() {
    return dividend;
  }

  public void setDividend(BigDecimal dividend) {
    this.dividend = dividend;
  }

  public BigDecimal getEps() {
    return eps;
  }

  public void setEps(BigDecimal eps) {
    this.eps = eps;
  }

  public Market getExchange() {
    return exchange;
  }

  public void setExchange(Market exchange) {
    this.exchange = exchange;
  }

  public BigDecimal getFall_stop() {
    return fall_stop;
  }

  public void setFall_stop(BigDecimal fall_stop) {
    this.fall_stop = fall_stop;
  }

  public BigDecimal getFloat_market_capital() {
    return float_market_capital;
  }

  public void setFloat_market_capital(BigDecimal float_market_capital) {
    this.float_market_capital = float_market_capital;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public void setHigh(BigDecimal high) {
    this.high = high;
  }

  public BigDecimal getHigh52week() {
    return high52week;
  }

  public void setHigh52week(BigDecimal high52week) {
    this.high52week = high52week;
  }

  public BigDecimal getLast_close() {
    return last_close;
  }

  public void setLast_close(BigDecimal last_close) {
    this.last_close = last_close;
  }

  public BigDecimal getLow() {
    return low;
  }

  public void setLow(BigDecimal low) {
    this.low = low;
  }

  public BigDecimal getLow52week() {
    return low52week;
  }

  public void setLow52week(BigDecimal low52week) {
    this.low52week = low52week;
  }

  public BigDecimal getMarketCapital() {
    return marketCapital;
  }

  public void setMarketCapital(BigDecimal marketCapital) {
    this.marketCapital = marketCapital;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getNet_assets() {
    return net_assets;
  }

  public void setNet_assets(BigDecimal net_assets) {
    this.net_assets = net_assets;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public void setOpen(BigDecimal open) {
    this.open = open;
  }

  public BigDecimal getPb() {
    return pb;
  }

  public void setPb(BigDecimal pb) {
    this.pb = pb;
  }

  public BigDecimal getPe_lyr() {
    return pe_lyr;
  }

  public void setPe_lyr(BigDecimal pe_lyr) {
    this.pe_lyr = pe_lyr;
  }

  public BigDecimal getPe_ttm() {
    return pe_ttm;
  }

  public void setPe_ttm(BigDecimal pe_ttm) {
    this.pe_ttm = pe_ttm;
  }

  public BigDecimal getPercentage() {
    return percentage;
  }

  public void setPercentage(BigDecimal percentage) {
    this.percentage = percentage;
  }

  public BigDecimal getPsr() {
    return psr;
  }

  public void setPsr(BigDecimal psr) {
    this.psr = psr;
  }

  public BigDecimal getRise_stop() {
    return rise_stop;
  }

  public void setRise_stop(BigDecimal rise_stop) {
    this.rise_stop = rise_stop;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public BigDecimal getTotalShares() {
    return totalShares;
  }

  public void setTotalShares(BigDecimal totalShares) {
    this.totalShares = totalShares;
  }

  public BigDecimal getTurnover_rate() {
    return turnover_rate;
  }

  public void setTurnover_rate(BigDecimal turnover_rate) {
    this.turnover_rate = turnover_rate;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public void setVolume(BigDecimal volume) {
    this.volume = volume;
  }

  public BigDecimal getVolumeAverage() {
    return volumeAverage;
  }

  public void setVolumeAverage(BigDecimal volumeAverage) {
    this.volumeAverage = volumeAverage;
  }

  public BigDecimal getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(BigDecimal updateAt) {
    this.updateAt = updateAt;
  }

}
