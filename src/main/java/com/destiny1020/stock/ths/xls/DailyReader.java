package com.destiny1020.stock.ths.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.destiny1020.stock.ths.model.StockDaily;

/**
 * Read daily exported data from THS.
 * 
 * @author Administrator
 *
 */
public class DailyReader {

  private static final String NON_EXISTENCE = "--";  
    
  public static void main(String[] args) throws IOException {
    FileInputStream file =
//        new FileInputStream(new File("D:/stock/THS/2015-07-06.xls"));
          new FileInputStream(new File("E:/Baidu Yun/2015-07-07.xls"));

    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    Map<String, StockDaily> stocks = new HashMap<String, StockDaily>();
    
    while (rowIterator.hasNext()) {
      // SKIP the header row
      Row row = rowIterator.next();
      row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      StockDaily sd = new StockDaily();
      int idx = 0;
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        String content = cell.toString();
        switch (idx) {
        case 0: // 代码
            sd.setSymbol(content);
            break;
        case 1: // 名称
            sd.setName(content);
            break;
        case 2: // 融资融券Flag
            boolean canFinancing = false;
            if(content.trim().equals("3")) {
                canFinancing = true;
            }
            sd.setCanFinancing(canFinancing);
            break;
        case 4: // 涨幅%
            if(!content.equals(NON_EXISTENCE)) {
                sd.setPercentage(new BigDecimal(content));
            }
            break;
        case 5: // 现价
            if(!content.equals(NON_EXISTENCE)) {
                sd.setCurrent(new BigDecimal(content));
            }
            break;
        case 6: // 涨跌
            if(!content.equals(NON_EXISTENCE)) {
                sd.setChange(new BigDecimal(content));
            }
            break;
        case 8: // 总手
            if(!content.equals(NON_EXISTENCE)) {
                sd.setCurrent(new BigDecimal(content));
            }
            break;
        default:
            break;
        }
        idx++;
      }
    }
  }
}
