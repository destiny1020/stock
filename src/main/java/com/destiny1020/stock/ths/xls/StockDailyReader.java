package com.destiny1020.stock.ths.xls;

import static com.destiny1020.stock.ths.xls.THSReaderUtils.FT_DAILY;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.FT_DAILY_CAPITAL;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.FT_DAILY_POSITION;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.NON_EXISTENCE;
import static com.destiny1020.stock.ths.xls.THSReaderUtils.extractNumber;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
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
import com.destiny1020.stock.es.indexer.StockDailyIndexer;
import com.destiny1020.stock.model.StockDaily;
import com.destiny1020.stock.xueqiu.crawler.StockCrawler;

/**
 * Read daily exported data from THS.
 * 
 * @author Administrator
 *
 */
public class StockDailyReader {

  private static final String HAS_SIGNAL = "有";

  /**
   * Main Entry for loading data in THS exported data.
   * 
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
    // load today's data
    // load(new Date());

    // load specific period data --- USE WHEN THERE ARE MULTIPLE FILES TO LOAD
    String formatTemplate = "2015-07-%s";
    List<String> dates = Arrays.asList("27");

    dates.forEach(date -> {
      try {
        Date parsedDate = THSReaderUtils.SDF.parse(String.format(formatTemplate, date));
        load(ElasticsearchUtils.getClient(), parsedDate);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  public static void load(Client client, Date targetDate) throws Exception {
    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(targetDate);

    FileInputStream file = new FileInputStream(String.format(FT_DAILY, dateStr));

    // Get the workbook instance for XLS file
    HSSFWorkbook workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    HSSFSheet sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    Iterator<Row> rowIterator = sheet.iterator();

    // SYMBOL -> STOCK OBJECT
    Map<String, StockDaily> stocks = new HashMap<String, StockDaily>();

    // SKIP the header row
    if (rowIterator.hasNext()) {
      rowIterator.next();
    }
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();
      StockDaily sd = new StockDaily();
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
          case 2: // 融资融券
            boolean canFinancing = false;
            if (content.trim().equals("3.0")) {
              canFinancing = true;
            }
            sd.setCanFinancing(canFinancing);

            if (content.equals(NON_EXISTENCE)) {
              idx++;
            }
            break;
          case 4: // 涨幅%
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPercentage(new BigDecimal(content));
            }
            break;
          case 5: // 现价
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCurrent(new BigDecimal(content));
            }
            break;
          case 6: // 涨跌
            if (!content.equals(NON_EXISTENCE)) {
              sd.setChange(new BigDecimal(content));
            }
            break;
          case 8: // 总手
            if (!content.equals(NON_EXISTENCE)) {
              sd.setVolume(new BigDecimal(content));
            }
            break;
          case 9: // 换手
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTurnover(new BigDecimal(content));
            }
            break;
          case 10: // 量比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setQrr(new BigDecimal(content));
            }
            break;
          case 11: // 买入信号
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBuySignal(new BigDecimal(content).intValue());
            }
            break;
          case 12: // 利好
            boolean hasGood = content.equals(HAS_SIGNAL) ? true : false;
            sd.setHasGood(hasGood);
            break;
          case 13: // 利空
            boolean hasBad = content.equals(HAS_SIGNAL) ? true : false;
            sd.setHasBad(hasBad);
            break;
          case 14: // 所属行业
            sd.setBlock(content);
            break;
          case 15: // 现手
            sd.setLatestVolume(extractNumber(content));
            break;
          case 16: // 开盘
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOpen(new BigDecimal(content));
            }
            break;
          case 17: // 昨收
            if (!content.equals(NON_EXISTENCE)) {
              sd.setLastClose(new BigDecimal(content));
            }
            break;
          case 18: // 最高
            if (!content.equals(NON_EXISTENCE)) {
              sd.setHigh(new BigDecimal(content));
            }
            break;
          case 19: // 最低
            if (!content.equals(NON_EXISTENCE)) {
              sd.setLow(new BigDecimal(content));
            }
            break;
          case 20: // 买价
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBuy(new BigDecimal(content));
            }
            break;
          case 21: // 卖价
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSell(new BigDecimal(content));
            }
            break;
          case 22: // 主力净量
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMfNetFactor(new BigDecimal(content));
            }
            break;
          case 23: // 主力金额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMfAmount(new BigDecimal(content));
            }
            break;
          case 24: // 散户数量
            if (!content.equals(NON_EXISTENCE)) {
              sd.setIiNumber(new BigDecimal(content));
            }
            break;
          case 25: // 市盈率(动)
            if (!content.equals(NON_EXISTENCE)) {
              sd.setDpe(new BigDecimal(content));
            }
            break;
          case 26: // 市净率
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPb(new BigDecimal(content));
            }
            break;
          case 27: // 买量
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBuyVolume(new BigDecimal(content));
            }
            break;
          case 28: // 卖量
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSellVolume(new BigDecimal(content));
            }
            break;
          case 29: // 委比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setDelegateRatio(new BigDecimal(content));
            }
            break;
          case 30: // 振幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAmplitude(new BigDecimal(content));
            }
            break;
          case 31: // 总金额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAmount(new BigDecimal(content));
            }
            break;
          case 32: // 均笔额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAmountPerDeal(new BigDecimal(content));
            }
            break;
          case 33: // 笔数
            if (!content.equals(NON_EXISTENCE)) {
              sd.setDealNumber(new BigDecimal(content));
            }
            break;
          case 34: // 股/笔
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAverageSharePerDeal(new BigDecimal(content));
            }
            break;
          case 35: // 外盘
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOutVolume(new BigDecimal(content));
            }
            break;
          case 36: // 内盘
            if (!content.equals(NON_EXISTENCE)) {
              sd.setInVolume(new BigDecimal(content));
            }
            break;
          case 37: // 总市值
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTotalMarketCapital(new BigDecimal(content));
            }
            break;
          case 38: // 流通市值
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCirculationMarketCapital(new BigDecimal(content));
            }
            break;
          case 39: // 贡献度
            if (!content.equals(NON_EXISTENCE)) {
              sd.setIndexContribution(new BigDecimal(content));
            }
            break;
          case 40: // 星级
            if (!content.equals(NON_EXISTENCE)) {
              sd.setStar(new BigDecimal(content));
            }
            break;
          case 41: // 多空比
            break;
          case 42: // 委差
            if (!content.equals(NON_EXISTENCE)) {
              sd.setDelegateDiff(new BigDecimal(content));
            }
            break;
          case 43: // 机构动向
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOrgTrend(new BigDecimal(content));
            }
            break;
          case 44: // 开盘涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOpenPercentage(new BigDecimal(content));
            }
            break;
          case 45: // 实体涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBarPercentage(new BigDecimal(content));
            }
            break;
          case 46: // 总股本
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTotalShares(new BigDecimal(content));
            }
            break;
          case 47: // 流通股本
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCirculationShares(new BigDecimal(content));
            }
            break;
          case 48: // 流通A股
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCirculationAShares(new BigDecimal(content));
            }
            break;
          case 49: // 流通B股
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCirculationBShares(new BigDecimal(content));
            }
            break;
          case 50: // 流通比例
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCirculationRatio(new BigDecimal(content));
            }
            break;
          case 51: // 股东总数
            if (!content.equals(NON_EXISTENCE)) {
              sd.setShareholders(new BigDecimal(content).intValue());
            }
            break;
          case 52: // 人均持股数
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSharePerHolder(new BigDecimal(content).intValue());
            }
            break;
          case 53: // 静态市盈率
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPe(new BigDecimal(content));
            }
            break;
          case 54: // 利润总额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTotalProfit(extractNumber(content));
            }
            break;
          case 55: // 净利润
            if (!content.equals(NON_EXISTENCE)) {
              sd.setNetProfit(new BigDecimal(content));
            }
            break;
          case 56: // 净利润增长率
            if (!content.equals(NON_EXISTENCE)) {
              sd.setProfitIncreasePercentage(new BigDecimal(content));
            }
            break;
          case 57: // 每股盈利
            if (!content.equals(NON_EXISTENCE)) {
              sd.setEps(new BigDecimal(content));
            }
            break;
          case 58: // 每股净资产
            if (!content.equals(NON_EXISTENCE)) {
              sd.setNavps(new BigDecimal(content));
            }
            break;
          case 59: // 每股公积金
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPfps(new BigDecimal(content));
            }
            break;
          case 60: // 资产总计
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTotalAssetAmount(extractNumber(content));
            }
            break;
          case 61: // 流动资产
            if (!content.equals(NON_EXISTENCE)) {
              sd.setCurrentAssetAmount(extractNumber(content));
            }
            break;
          case 62: // 固定资产
            if (!content.equals(NON_EXISTENCE)) {
              sd.setFixedAssetAmount(extractNumber(content));
            }
            break;
          case 63: // 无形资产
            if (!content.equals(NON_EXISTENCE)) {
              sd.setIntangibleAssetAmount(extractNumber(content));
            }
            break;
          case 64: // 主营业务收入
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPor(extractNumber(content));
            }
            break;
          case 65: // 主营利润率
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPopr(new BigDecimal(content));
            }
            break;
          case 66: // 净资产收益率
            if (!content.equals(NON_EXISTENCE)) {
              sd.setRoe(new BigDecimal(content));
            }
            break;
          case 67: // 资产负债比率
            if (!content.equals(NON_EXISTENCE)) {
              sd.setAlr(new BigDecimal(content));
            }
            break;
          case 68: // 负债合计
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTotalLiabilities(extractNumber(content));
            }
            break;
          case 69: // 股东权益合计
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTse(extractNumber(content));
            }
            break;
          case 70: // 公积金
            if (!content.equals(NON_EXISTENCE)) {
              sd.setPf(new BigDecimal(content));
            }
            break;
          case 71: // 投资收益占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setRoir(new BigDecimal(content));
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

    // Basic information completed

    // ZJLX started
    file = new FileInputStream(new File(String.format(FT_DAILY_CAPITAL, dateStr)));

    // Get the workbook instance for XLS file
    workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    rowIterator = sheet.iterator();

    // SKIP the header row --- 2 rows
    if (rowIterator.hasNext()) {
      rowIterator.next();
      rowIterator.next();
    }
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();

      // get symbol
      Cell cell = cellIterator.next();
      String content = cell.toString();
      StockDaily sd = stocks.get(content);
      if (sd == null) {
        throw new Exception(content + " is not a valid symbol, does not exist in Stocks Map.");
      }

      int idx = 1;
      while (cellIterator.hasNext()) {
        cell = cellIterator.next();
        content = cell.toString();
        switch (idx) {
          case 0: // 代码
          case 1: // 名称
          case 2: // .
          case 3: // 涨幅
          case 4: // 现价
            break;
          case 5: // 净流入
            if (!content.equals(NON_EXISTENCE)) {
              sd.setNetCapitalIn(new BigDecimal(content));
            }
            break;
          case 6: // 实时大单统计 - 流入
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBigCapitalIn(new BigDecimal(content));
            }
            break;
          case 7: // 实时大单统计 - 流出
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBigCapitalOut(new BigDecimal(content));
            }
            break;
          case 8: // 实时大单统计 - 净额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBigNetCapital(new BigDecimal(content));
            }
            break;
          case 9: // 实时大单统计 - 净额占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBigNetCapitalPercentage(new BigDecimal(content));
            }
            break;
          case 10: // 实时大单统计 - 总额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBigCapitalTotal(new BigDecimal(content));
            }
            break;
          case 11: // 实时大单统计 - 总额占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setBigCapitalTotalPercentage(new BigDecimal(content));
            }
            break;
          case 12: // 实时中单统计 - 流入
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMidCapitalIn(new BigDecimal(content));
            }
            break;
          case 13: // 实时中单统计 - 流出
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMidCapitalOut(new BigDecimal(content));
            }
            break;
          case 14: // 实时中单统计 - 净额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMidNetCapital(new BigDecimal(content));
            }
            break;
          case 15: // 实时中单统计 - 净额占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMidNetCapitalPercentage(new BigDecimal(content));
            }
            break;
          case 16: // 实时中单统计 - 总额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMidCapitalTotal(new BigDecimal(content));
            }
            break;
          case 17: // 实时中单统计 - 总额占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setMidCapitalTotalPercentage(new BigDecimal(content));
            }
            break;
          case 18: // 实时小单统计 - 流入
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSmallCapitalIn(new BigDecimal(content));
            }
            break;
          case 19: // 实时小单统计 - 流出
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSmallCapitalOut(new BigDecimal(content));
            }
            break;
          case 20: // 实时小单统计 - 净额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSmallNetCapital(new BigDecimal(content));
            }
            break;
          case 21: // 实时小单统计 - 净额占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSmallNetCapitalPercentage(new BigDecimal(content));
            }
            break;
          case 22: // 实时小单统计 - 总额
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSmallCapitalTotal(new BigDecimal(content));
            }
            break;
          case 23: // 实时小单统计 - 总额占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setSmallCapitalTotalPercentage(new BigDecimal(content));
            }
            break;
          default:
            break;
        }
        idx++;
      }
    }

    // ZLZC started
    file = new FileInputStream(new File(String.format(FT_DAILY_POSITION, dateStr)));

    // Get the workbook instance for XLS file
    workbook = new HSSFWorkbook(file);

    // Get first sheet from the workbook
    sheet = workbook.getSheetAt(0);

    // Get iterator to all the rows in current sheet
    rowIterator = sheet.iterator();

    // SKIP the header row --- 2 rows
    if (rowIterator.hasNext()) {
      rowIterator.next();
      rowIterator.next();
    }
    while (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      Iterator<Cell> cellIterator = row.cellIterator();

      // get symbol
      Cell cell = cellIterator.next();
      String content = cell.toString();
      StockDaily sd = stocks.get(content);
      if (sd == null) {
        throw new Exception(content
            + " is not a valid symbol, does not exist in Stocks Map.(During ZLZC phase)");
      }

      int idx = 1;
      while (cellIterator.hasNext()) {
        cell = cellIterator.next();
        content = cell.toString();
        switch (idx) {
          case 0: // 代码
          case 1: // 名称
          case 2: // .
          case 3: // 现价
            break;
          case 4: // 今日增仓占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOneIncPercentage(new BigDecimal(content));
            }
            break;
          case 5: // 今日增仓排名
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOneIncRank(new BigDecimal(content).intValue());
            }
            break;
          case 6: // 今日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setOneIncChange(new BigDecimal(content));
            }
            break;
          case 7: // 2日增仓占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTwoIncPercentage(new BigDecimal(content));
            }
            break;
          case 8: // 2日增仓排名
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTwoIncRank(new BigDecimal(content).intValue());
            }
            break;
          case 9: // 2日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTwoIncChange(new BigDecimal(content));
            }
            break;
          case 10: // 3日增仓占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setThreeIncPercentage(new BigDecimal(content));
            }
            break;
          case 11: // 3日增仓排名
            if (!content.equals(NON_EXISTENCE)) {
              sd.setThreeIncRank(new BigDecimal(content).intValue());
            }
            break;
          case 12: // 3日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setThreeIncChange(new BigDecimal(content));
            }
            break;
          case 13: // 5日增仓占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setFiveIncPercentage(new BigDecimal(content));
            }
            break;
          case 14: // 5日增仓排名
            if (!content.equals(NON_EXISTENCE)) {
              sd.setFiveIncRank(new BigDecimal(content).intValue());
            }
            break;
          case 15: // 5日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setFiveIncChange(new BigDecimal(content));
            }
            break;
          case 16: // 10日增仓占比
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTenIncPercentage(new BigDecimal(content));
            }
            break;
          case 17: // 10日增仓排名
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTenIncRank(new BigDecimal(content).intValue());
            }
            break;
          case 18: // 10日涨幅
            if (!content.equals(NON_EXISTENCE)) {
              sd.setTenIncChange(new BigDecimal(content));
            }
            break;
          default:
            break;
        }
        idx++;
      }
    }

    // XUEQIU related
    stocks.values().parallelStream().forEach(stock -> {
      stock.setXqFollowersCount(StockCrawler.getFollowersInfo(stock.getSymbol()).getTotalcount());
    });

    // output stocks into ES
    StockDailyIndexer.reindexStockDaily(client, targetDate,
        new ArrayList<StockDaily>(stocks.values()));
  }

}
