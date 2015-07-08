package com.destiny1020.stock.xueqiu.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StockFollowersInfo {

  private int totalcount;
  private Object friends;

  public int getTotalcount() {
    return totalcount;
  }

  public void setTotalcount(int totalcount) {
    this.totalcount = totalcount;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public Object getFriends() {
    return friends;
  }

  public void setFriends(Object friends) {
    this.friends = friends;
  }

}
