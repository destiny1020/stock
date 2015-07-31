package com.destiny1020.stock.ths.xls;

import static com.destiny1020.stock.ths.xls.THSReaderUtils.NON_EXISTENCE;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.extractNumber;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.elasticsearch.client.Client;

import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.indexer.StockBlockDailyIndexer;
import com.destiny1020.stock.model.StockBlockDaily;
import com.google.common.collect.Sets;

/**
 * Read daily exported block details data from THS.
 * 
 * @author destiny
 *
 */
public class StockBlockDailyReader {

  /**
   * Define blocks that need to be indexed.
   */
  private static final Set<String> NEEDED_BKS = Sets.newHashSet("DFJ", "GFJG", "JSJYY", "YJS",
      "YLGG");

  // file name patterns [Date]_BK_[BLOCK_ABBR].xls
  private static final String FILE_PATH_PATTERN = "data/%s_BK_%s.xls";

  public static void main(String[] args) throws Exception {
    Date parsedDate = THSReaderUtils.SDF.parse("2015-07-30");
    load(ElasticsearchUtils.getClient(), parsedDate);
  }

  public static void load(Client client, Date targetDate) throws Exception {
    for (String blockAbbr : NEEDED_BKS) {
      loadCore(client, targetDate, blockAbbr);
    }
  }

  private static void loadCore(Client client, Date targetDate, String blockAbbr)
      throws IOException, InterruptedException, ExecutionException {
    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(targetDate);

    FileInputStream file =
        new FileInputStream(String.format(FILE_PATH_PATTERN, dateStr, blockAbbr));

    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    // SYMBOL -> BLOCK DAILY
    Map<String, StockBlockDaily> stocks = new HashMap<String, StockBlockDaily>();

    // SKIP the header row
    if (rowIterator.hasNext()) {
      rowIterator.next();
    }

    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      StockBlockDaily sd = new StockBlockDaily();
      sd.setRecordDate(targetDate);
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
          case 2: // .
            break;
          case 3: // 涨幅%
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPercentage(new BigDecimal(content));
            }
            break;
          case 4: // 现价
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCurrent(new BigDecimal(content));
            }
            break;
          case 5: // 主力净量
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMfNetFactor(new BigDecimal(content));
            }
            break;
          case 6: // 主力金额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMfAmount(new BigDecimal(content));
            }
            break;
          case 7: // 涨跌
            if (!content.equals(NON_EXISTENCE)) {
              sd.setChange(new BigDecimal(content));
            }
            break;
          case 8: // 涨速
            break;
          case 9: // 总手
            if (!content.equals(NON_EXISTENCE)) {
              sd.setVolume(new BigDecimal(content));
            }
            break;
          case 10: // 换手
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTurnover(new BigDecimal(content));
            }
            break;
          case 11: // 量比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setQrr(new BigDecimal(content));
            }
            break;
          case 12: // 所属行业
            sd.setBlock(content);
            break;
          case 13: // 5日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setFiveIncPercentage(new BigDecimal(content));
            }
            break;
          case 14: // 10日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTenIncPercentage(new BigDecimal(content));
            }
            break;
          case 15: // 20日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTwentyIncPercentage(new BigDecimal(content));
            }
            break;
          case 16: // 现手
            sd.setLatestVolume(extractNumber(content));
            break;
          case 17: // 开盘
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOpen(new BigDecimal(content));
            }
            break;
          case 18: // 昨收
            if (!content.equals(NON_EXISTENCE)) {
              sd.setLastClose(new BigDecimal(content));
            }
            break;
          case 19: // 最高
            if (!content.equals(NON_EXISTENCE)) {
              sd.setHigh(new BigDecimal(content));
            }
            break;
          case 20: // 最低
            if (!content.equals(NON_EXISTENCE)) {
              sd.setLow(new BigDecimal(content));
            }
            break;
          case 21: // 市盈率(动)
            if (!content.equals(NON_EXISTENCE)) {
              sd.setDpe(new BigDecimal(content));
            }
            break;
          case 22: // 振幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAmplitude(new BigDecimal(content));
            }
            break;
          case 23: // 总金额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAmount(new BigDecimal(content));
            }
            break;
          case 24: // 均笔额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAmountPerDeal(new BigDecimal(content));
            }
            break;
          case 25: // 笔数
            if (!content.equals(NON_EXISTENCE)) {
              sd.setDealNumber(new BigDecimal(content));
            }
            break;
          case 26: // 股/笔
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAverageSharePerDeal(new BigDecimal(content));
            }
            break;
          case 27: // 流通市值
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCirculationMarketCapital(new BigDecimal(content));
            }
            break;
          case 28: // 总市值
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTotalMarketCapital(new BigDecimal(content));
            }
            break;
          default:
            break;
        }
        idx++;
      }
      // calculate the averagePrice
      if (sd.getDealNumber() != null && sd.getAverageSharePerDeal() != null) {
        sd.setAveragePrice(sd.getAmountPerDeal().divide(sd.getAverageSharePerDeal(), 3,
            RoundingMode.HALF_UP));
      }

      stocks.put(sd.getSymbol(), sd);
    }

    // output stocks into ES
    StockBlockDailyIndexer.reindexStockBlockDaily(client, targetDate, blockAbbr.toLowerCase(),
        new ArrayList<StockBlockDaily>(stocks.values()));
  }

}
