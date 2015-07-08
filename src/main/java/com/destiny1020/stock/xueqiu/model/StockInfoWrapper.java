package com.destiny1020.stock.xueqiu.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class StockInfoWrapper<T> {

  private Map<String, T> stockInfoMap;

  public StockInfoWrapper() {
    this.stockInfoMap = new HashMap<String, T>();
  }

  public void addEntry(String key, T value) {
    this.stockInfoMap.put(key, value);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public Map<String, T> getStockInfoMap() {
    return stockInfoMap;
  }

  public void setStockInfoMap(Map<String, T> stockInfoMap) {
    this.stockInfoMap = stockInfoMap;
  }

}
