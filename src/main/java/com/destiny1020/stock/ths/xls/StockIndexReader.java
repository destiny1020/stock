package com.destiny1020.stock.ths.xls;


import static com.destiny1020.stock.ths.xls.THSReaderUtils.FT_INDEX_COMPOSITE;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.NON_EXISTENCE;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.SDF;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.elasticsearch.client.Client;

import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.indexer.StockIndexIndexer;
import com.destiny1020.stock.model.StockIndex;

/**
 * Read daily indices provided by THS.
 * 
 * @author Administrator
 *
 */
public class StockIndexReader {

  public static void main(String[] args) {
    // load today's data
    // load(new Date());

    // load specific period data --- USE WHEN THERE ARE MULTIPLE FILES TO LOAD
    String formatTemplate = "2015-07-%s";
    List<String> dates = Arrays.asList("23");

    dates.forEach(date -> {
      try {
        load(ElasticsearchUtils.getClient(), String.format(formatTemplate, date));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public static void load(Client client, String targetDate) throws Exception {
    load(client, SDF.parse(targetDate));
  }

  public static void load(Client client, Date targetDate) throws Exception {
    String dateStr = SDF.format(targetDate);

    FileInputStream file = new FileInputStream(String.format(FT_INDEX_COMPOSITE, dateStr));

    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    // NAME -> INDEX
    Map<String, StockIndex> indices = new HashMap<String, StockIndex>();

    // SKIP the header row
    if (rowIterator.hasNext()) {
      rowIterator.next();
    }

    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();

      StockIndex si = new StockIndex();
      si.setRecordDate(targetDate);
      int idx = 0;
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        String content = cell.toString();
        switch (idx) {
          case 0: // 代码
            si.setSymbol(content);
            break;
          case 1: // 名称
            si.setName(content.replaceAll("\\s+", ""));
            break;
          case 2: // --
            break;
          case 3: // 现价
            if (!content.equals(NON_EXISTENCE)) {
              si.setCurrent(new BigDecimal(content));
            }
            break;
          case 4: // 涨跌
            if (!content.equals(NON_EXISTENCE)) {
              si.setChange(new BigDecimal(content));
            }
            break;
          case 5: // 总手
            if (!content.equals(NON_EXISTENCE)) {
              si.setVolume(new BigDecimal(content));
            }
            break;
          case 6: // 现手
            if (!content.equals(NON_EXISTENCE)) {
              si.setLatestVolume(new BigDecimal(content));
            }
            break;
          case 7: // 开盘
            if (!content.equals(NON_EXISTENCE)) {
              si.setOpen(new BigDecimal(content));
            }
            break;
          case 8: // 最高
            if (!content.equals(NON_EXISTENCE)) {
              si.setHigh(new BigDecimal(content));
            }
            break;
          case 9: // 最低
            if (!content.equals(NON_EXISTENCE)) {
              si.setLow(new BigDecimal(content));
            }
            break;
          case 10: // 涨幅%
            if (!content.equals(NON_EXISTENCE)) {
              si.setPercentage(new BigDecimal(content));
            }
            break;
          case 11: // 量比
            if (!content.equals(NON_EXISTENCE)) {
              si.setQrr(new BigDecimal(content));
            }
            break;
          case 12: // 振幅
            if (!content.equals(NON_EXISTENCE)) {
              si.setAmplitude(new BigDecimal(content));
            }
            break;
          case 13: // 总金额
            if (!content.equals(NON_EXISTENCE)) {
              si.setAmount(new BigDecimal(content));
            }
            break;
          default:
            break;
        }
        idx++;
      }
      indices.put(si.getName(), si);
    }

    // output indices into ES
    StockIndexIndexer.reindexStockIndex(client, targetDate,
        new ArrayList<StockIndex>(indices.values()));
  }
}
