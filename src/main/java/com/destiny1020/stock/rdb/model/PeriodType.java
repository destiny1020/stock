package com.destiny1020.stock.rdb.model;

public enum PeriodType {

  D("Ddaily");

  private String explain;

  private PeriodType(String explain) {
    this.explain = explain;
  }

  public String getExplain() {
    return explain;
  }

}
