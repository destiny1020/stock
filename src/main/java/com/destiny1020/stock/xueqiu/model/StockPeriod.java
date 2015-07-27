package com.destiny1020.stock.xueqiu.model;

/**
 * Stock Period.
 * 
 * @author Administrator
 *
 */
public enum StockPeriod {

  ONE_DAY("1day"), ONE_WEEK("1week"), ONE_MONTH("1month");

  private String option;

  private StockPeriod(String option) {
    this.option = option;
  }

  public String getOption() {
    return option;
  }

}
