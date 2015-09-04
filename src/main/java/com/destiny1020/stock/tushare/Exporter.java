package com.destiny1020.stock.tushare;

import java.io.IOException;

/**
 * Export data to the XLS.
 * 
 * @author destiny1020
 *
 */
public class Exporter {

  /**
   * Export history data into MYSQL.
   * 
   * @param symbol
   * @param startDay
   * @param endDay
   * @throws IOException
   * @throws InterruptedException 
   */
  public static void exportToMySQL(String symbol, String startDay, String endDay)
      throws IOException, InterruptedException {
    Process proc =
        Runtime.getRuntime().exec(
            "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_mysql.py "
                + symbol + " " + startDay + " " + endDay);
    proc.waitFor();
  }

  /**
   * Export history data for symbol, from start day to end day.
   * 
   * @param symbol
   * @param startDay
   * @param endDay
   * @throws IOException 
   * @throws InterruptedException 
   */
  public static void exportToExcel(String symbol, String startDay, String endDay)
      throws IOException, InterruptedException {

    Process proc =
        Runtime.getRuntime().exec(
            "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_excel.py "
                + symbol + " " + startDay + " " + endDay);
    proc.waitFor();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    exportToMySQL("000656", "2001-01-01", "2015-12-31");
  }

}
