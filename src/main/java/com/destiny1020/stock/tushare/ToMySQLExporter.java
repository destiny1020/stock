package com.destiny1020.stock.tushare;

import java.io.IOException;
import java.text.ParseException;

public class ToMySQLExporter {

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    long startMillis = System.currentTimeMillis();
    exportHistoryToMySQL(false, false);
    long endMillis = System.currentTimeMillis();
    System.out.println(String.format(
        "Finished batch execution for importing history data in: %.2f Seconds",
        (endMillis - startMillis) / 1000.0));
  }

  /**
   * This method will try to update history data for the symbol to the latest.
   * 
   * @param symbol
   */
  public static void exportHistoryToMySQL(String symbol) {
    // step 1: find the latest available date


  }

}
