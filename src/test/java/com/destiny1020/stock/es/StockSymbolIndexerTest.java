package com.destiny1020.stock.es;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.destiny1020.stock.es.indexer.StockSymbolIndexer;
import com.destiny1020.stock.model.StockSymbol;

public class StockSymbolIndexerTest extends ESTestBase {

  private static final Logger LOGGER = LogManager.getLogger(StockSymbolIndexerTest.class);

  @Test
  public void testCreateSymbolMapping() throws IOException, InterruptedException,
      ExecutionException {
    StockSymbolIndexer.recreateMappings(client);
  }

  @Test
  public void testReindexStocks() throws IOException, InterruptedException, ExecutionException {
    StockSymbol sd1 = new StockSymbol("SZ000001", "平安银行");
    StockSymbol sd2 = new StockSymbol("SZ000002", "万科A");

    StockSymbolIndexer.reindexStockSymbols(client, Arrays.asList(sd1, sd2));
  }

  /**
   * Reindex all stock symbols
   * 
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  @Test
  public void testReindexAllStockSymbols() throws IOException, InterruptedException,
      ExecutionException {
    LOGGER.info("About to reindex all stock symbols.");
    FileInputStream file = new FileInputStream(new File("D:/stock/THS/2015-07-22.xls"));

    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    List<StockSymbol> symbols = new LinkedList<StockSymbol>();
    while (rowIterator.hasNext()) {
      Row next = rowIterator.next();
      String symbol = next.getCell(0).toString();
      String name = next.getCell(1).toString().replaceAll("\\s+", "");
      if (symbol.startsWith("SH") || symbol.startsWith("SZ")) {
        symbols.add(new StockSymbol(symbol, name));
      }
    }

    // Add index symbols
    symbols.add(new StockSymbol("SH000001", "上证指数"));
    symbols.add(new StockSymbol("SH000300", "沪深300"));
    symbols.add(new StockSymbol("SH000016", "上证50"));
    symbols.add(new StockSymbol("SZ399001", "深证成指"));
    symbols.add(new StockSymbol("SZ399005", "中小板指"));
    symbols.add(new StockSymbol("SZ399006", "创业板指"));

    StockSymbolIndexer.reindexStockSymbols(client, symbols);
  }

}
