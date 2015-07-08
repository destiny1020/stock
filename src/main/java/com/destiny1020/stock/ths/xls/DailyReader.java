package com.destiny1020.stock.ths.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Read daily exported data from THS.
 * 
 * @author Administrator
 *
 */
public class DailyReader {

  public static void main(String[] args) throws IOException {
    FileInputStream file =
        new FileInputStream(new File("D:/stock/THS/2015-07-06.xls"));

    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    while (rowIterator.hasNext()) {
      Row next = rowIterator.next();
      Iterator<Cell> cellIterator = next.cellIterator();
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        System.out.println(cell.toString());
      }
    }
  }
}
