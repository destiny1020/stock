package com.destiny1020.stock.tushare;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.destiny1020.stock.model.StockSymbol;
import com.destiny1020.stock.rdb.model.PeriodType;
import com.destiny1020.stock.rdb.service.StockData15MinService;
import com.destiny1020.stock.rdb.service.StockData30MinService;
import com.destiny1020.stock.rdb.service.StockData60MinService;
import com.destiny1020.stock.rdb.service.StockDataDailyService;
import com.destiny1020.stock.rdb.service.StockSymbolService;

public class BatchMySQLExporter {

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

    System.exit(0);
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
    String startDate = "2000-01-01";

    // step 1: find the latest available date --- daily
    startDate = StockDataDailyService.INSTANCE.latestDate(symbol.getCode());

    // step 2: execute the script --- daily
    exportToMySQLCore(PeriodType.D, symbol.getCode(), startDate, endDate);

    // step 3: find the latest available date --- 60 minutes
    startDate = StockData60MinService.INSTANCE.latestDate(symbol.getCode());

    // step 4: execute the script --- 60 minutes
    exportToMySQLCore(PeriodType.M60, symbol.getCode(), startDate, endDate);

    //     step 5: find the latest available date --- 30 minutes
    startDate = StockData30MinService.INSTANCE.latestDate(symbol.getCode());

    // step 6: execute the script --- 30 minutes
    exportToMySQLCore(PeriodType.M30, symbol.getCode(), startDate, endDate);

    // step 7: find the latest available date --- 15 minutes
    startDate = StockData15MinService.INSTANCE.latestDate(symbol.getCode());

    // step 8: execute the script --- 15 minutes
    exportToMySQLCore(PeriodType.M15, symbol.getCode(), startDate, endDate);
  }

  private static void exportToMySQLCore(PeriodType pt, String code, String startDate, String endDate)
      throws InterruptedException, IOException {
    String command = pt.getScriptDest() + code + " " + startDate + " " + endDate;
    System.out.println(String.format("Exporting %s %s into DB for %s --- %s", pt.getExplain(),
        code, startDate, endDate));
    Process proc = Runtime.getRuntime().exec(command);
    proc.waitFor();
  }

}
