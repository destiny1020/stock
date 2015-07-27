package com.destiny1020.stock.ths.model;

import java.util.Date;

public class ESEntity {

  /**
   * Taken as the @timestamp field for es entities.
   */
  private Date recordDate;

  public Date getRecordDate() {
    return recordDate;
  }

  public void setRecordDate(Date recordDate) {
    this.recordDate = recordDate;
  }

}
