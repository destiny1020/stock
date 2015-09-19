package com.destiny1020.stock.rdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.destiny1020.stock.model.StockSymbol;

public class EntityImporters extends DBConfigBase {

  private static final Logger LOGGER = LogManager.getLogger(EntityImporters.class);

  @Test
  public void testImportSymbols() throws IOException {
    LOGGER.info("About to store all stock symbols int RDB");
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
        symbols.add(new StockSymbol(symbol, name, symbol.substring(2)));
      }
    }

    // Add index symbols
    symbols.add(new StockSymbol("SH000001", "上证指数", "sh"));
    symbols.add(new StockSymbol("SH000300", "沪深300", "hs300"));
    symbols.add(new StockSymbol("SH000016", "上证50", "sz50"));
    symbols.add(new StockSymbol("SZ399001", "深证成指", "sz"));
    symbols.add(new StockSymbol("SZ399005", "中小板指", "zxb"));
    symbols.add(new StockSymbol("SZ399006", "创业板指", "cyb"));

    LOGGER.info("About to store all stock symbols int RDB, count: " + symbols.size());
    symbols.forEach(symbol -> {
      em.persist(symbol);
    });
  }

}
