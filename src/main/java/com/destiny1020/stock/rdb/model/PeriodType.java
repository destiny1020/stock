package com.destiny1020.stock.rdb.model;

public enum PeriodType {

  D("Daily", "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_sql_hist.py "), M60(
      "60 Minutes",
      "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_sql_hist_60.py "), M30(
      "30 Minutes",
      "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_sql_hist_30.py "), M15(
      "15 Minutes",
      "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_sql_hist_15.py "), W(
      "Weekly",
      "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_sql_hist_week.py ");

  private String explain;
  private String scriptDest;

  private PeriodType(String explain, String scriptDest) {
    this.explain = explain;
    this.scriptDest = scriptDest;
  }

  public String getExplain() {
    return explain;
  }

  public String getScriptDest() {
    return scriptDest;
  }

}
