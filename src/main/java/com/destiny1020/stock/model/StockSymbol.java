package com.destiny1020.stock.model;

public class StockSymbol {

  /**
   * 代码
   */
  private String symbol;

  /**
   * 名称
   */
  private String name;

  public StockSymbol(String symbol, String name) {
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

}
