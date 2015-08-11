package com.destiny1020.stock.ths.xls;

import static com.destiny1020.stock.ths.xls.THSReaderUtils.FT_INDEX_BLOCK;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.NON_EXISTENCE;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.SDF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.elasticsearch.client.Client;

import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.indexer.StockBlockIndexIndexer;
import com.destiny1020.stock.model.StockBlockIndex;

/**
 * Read daily block indices provided by THS.
 * 
 * @author Administrator
 *
 */
public class StockBlockReader {

  public static void main(String[] args) {
    // load today's data
    // load(new Date());

    // load specific period data --- USE WHEN THERE ARE MULTIPLE FILES TO LOAD
    String formatTemplate = "2015-07-%s";
    List<String> dates = Arrays.asList("27");

    dates.forEach(date -> {
      try {
        load(ElasticsearchUtils.getClient(), String.format(formatTemplate, date));
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public static void load(Client client, File sourceFile) throws FileNotFoundException,
      IOException, InterruptedException, ExecutionException, ParseException {
    // extract the date from the source file name
    String dateStr = sourceFile.getName().substring(0, sourceFile.getName().indexOf('_'));
    loadCore(client, SDF.parse(dateStr), new FileInputStream(sourceFile));
  }

  public static void load(Client client, String targetDate) throws Exception {
    load(client, SDF.parse(targetDate));
  }

  public static void load(Client client, Date targetDate) throws Exception {
    loadCore(client, targetDate,
        new FileInputStream(String.format(FT_INDEX_BLOCK, SDF.format(targetDate))));
  }

  private static void loadCore(Client client, Date targetDate, FileInputStream file)
      throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    // NAME -> INDEX
    Map<String, StockBlockIndex> indices = new HashMap<String, StockBlockIndex>();

    // SKIP the header row
    if (rowIterator.hasNext()) {
      rowIterator.next();
    }

    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();

      StockBlockIndex si = new StockBlockIndex();
      // recordDate
      si.setRecordDate(targetDate);
      int idx = 0;
      while (cellIterator.hasNext()) {
        Cell cell = cellIterator.next();
        String content = cell.toString();
        switch (idx) {
          case 0: // 板块名称
            si.setName(content.replaceAll("\\s+", ""));
            break;
          case 1: // --
            break;
          case 2: // 涨幅%
            if (!content.equals(NON_EXISTENCE)) {
              si.setPercentage(new BigDecimal(content));
            }
            break;
          case 3: // 涨速 - ignore it
            break;
          case 4: // 主力净量
            if (!content.equals(NON_EXISTENCE)) {
              si.setMfNetFactor(new BigDecimal(content));
            }
            break;
          case 5: // 主力金额
            if (!content.equals(NON_EXISTENCE)) {
              si.setMfAmount(new BigDecimal(content));
            }
            break;
          case 6: // 量比
            if (!content.equals(NON_EXISTENCE)) {
              si.setQrr(new BigDecimal(content));
            }
            break;
          case 7: // 涨家数
            if (!content.equals(NON_EXISTENCE)) {
              si.setRiseCount(new BigDecimal(content).intValue());
            }
            break;
          case 8: // 跌家数
            if (!content.equals(NON_EXISTENCE)) {
              si.setFallCount(new BigDecimal(content).intValue());
            }
            break;
          case 9: // 领涨股
            si.setPioneer(content.replaceAll("\\s+", ""));
            break;
          case 10: // 5日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              si.setFiveIncPercentage(new BigDecimal(content));
            }
            break;
          case 11: // 10日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              si.setTenIncPercentage(new BigDecimal(content));
            }
            break;
          case 12: // 20日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              si.setTwentyIncPercentage(new BigDecimal(content));
            }
            break;
          case 13: // 总手
            if (!content.equals(NON_EXISTENCE)) {
              si.setVolume(new BigDecimal(content));
            }
            break;
          case 14: // 总金额
            if (!content.equals(NON_EXISTENCE)) {
              si.setAmount(new BigDecimal(content));
            }
            break;
          case 15: // 总市值
            if (!content.equals(NON_EXISTENCE)) {
              si.setTotalMarketCapital(new BigDecimal(content));
            }
            break;
          case 16: // 流通市值
            if (!content.equals(NON_EXISTENCE)) {
              si.setCirculationMarketCapital(new BigDecimal(content));
            }
            break;
          default:
            break;
        }
        idx++;
      }
      // calculate risePercentage
      si.setRisePercentage(new BigDecimal(si.getRiseCount()).divide(
          new BigDecimal(si.getRiseCount() + si.getFallCount()), 3, RoundingMode.HALF_UP));

      indices.put(si.getName(), si);
    }

    // output indices into ES
    StockBlockIndexIndexer.reindexStockBlockIndex(client, targetDate,
        new ArrayList<StockBlockIndex>(indices.values()));
  }

}
