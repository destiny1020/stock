package com.destiny1020.stock.xueqiu.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.destiny1020.stock.model.StockHistory;

/**
 * Represents the raw wrapper result for requesting a series of stock history.
 * 
 * @author Administrator
 *
 */
public class StockHistoryWrapper {

  private boolean success;
  private List<StockHistory> chartlist;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public List<StockHistory> getChartlist() {
    return chartlist;
  }

  public void setChartlist(List<StockHistory> chartlist) {
    this.chartlist = chartlist;
  }

}
