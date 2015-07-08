package com.destiny1020.stock.xueqiu.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.destiny1020.stock.common.Market;

public class StockWebInfo {

  private Market market;
  private String code;

  private StockQuoteInfo quoteInfo;
  private StockFollowersInfo followersInfo;

  public Market getMarket() {
    return market;
  }

  public void setMarket(Market market) {
    this.market = market;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public StockQuoteInfo getQuoteInfo() {
    return quoteInfo;
  }

  public void setQuoteInfo(StockQuoteInfo quoteInfo) {
    this.quoteInfo = quoteInfo;
  }

  public StockFollowersInfo getFollowersInfo() {
    return followersInfo;
  }

  public void setFollowersInfo(StockFollowersInfo followersInfo) {
    this.followersInfo = followersInfo;
  }

}
