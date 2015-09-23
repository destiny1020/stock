package com.destiny1020.stock.tushare;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.destiny1020.stock.model.StockSymbol;
import com.destiny1020.stock.rdb.service.StockDataDailyService;
import com.destiny1020.stock.rdb.service.StockSymbolService;

public class ToMySQLExporter {

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    List<StockSymbol> symbols = StockSymbolService.INSTANCE.getSymbols();

    long startMillis = System.currentTimeMillis();
    symbols.forEach(symbol -> {
      if (symbol != null && symbol.isNotCYB()) {
        try {
          exportHistoryToMySQL(symbol);
        } catch (Exception e) {
          System.err.println(e.getMessage());
          e.printStackTrace();
        }
      }
    });
    long endMillis = System.currentTimeMillis();
    System.out.println(String.format(
        "Finished batch execution for importing history data in: %.2f Seconds",
        (endMillis - startMillis) / 1000.0));
  }

  /**
   * This method will try to update history data for the symbol to the latest.
   * 
   * @param symbol
   * @throws IOException 
   * @throws InterruptedException 
   */
  public static void exportHistoryToMySQL(StockSymbol symbol) throws InterruptedException,
      IOException {
    String endDate = "2015-12-31";

    // step 1: find the latest available date
    String startDate = StockDataDailyService.INSTANCE.latestDate(symbol.getCode());

    // step 2: execute the script
    System.out.println(String.format("Exporting Daily %s into DB for %s --- %s", symbol.getCode(),
        startDate, endDate));
    exportToMySQLCore(symbol.getCode(), startDate, endDate);
  }

  private static void exportToMySQLCore(String symbol, String startDate, String endDate)
      throws InterruptedException, IOException {
    String scriptDest =
        "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_sql_hist.py ";
    String command = scriptDest + symbol + " " + startDate + " " + endDate;
    Process proc = Runtime.getRuntime().exec(command);
    proc.waitFor();
  }

}
