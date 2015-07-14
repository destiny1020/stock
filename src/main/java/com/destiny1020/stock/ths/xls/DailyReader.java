package com.destiny1020.stock.ths.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  private static final String NON_EXISTENCE_LONG = "----";
  private static final String HAS_SIGNAL = "有";

  private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+", Pattern.DOTALL);

  public static void main(String[] args) throws IOException {
    FileInputStream file = new FileInputStream(new File("D:/stock/THS/2015-07-06.xls"));
    // new FileInputStream(new File("E:/Baidu Yun/2015-07-07.xls"));

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
      stocks.put(sd.getSymbol(), sd);
    }

    System.out.println(stocks.size());
    // Basic information completed

    // ZJLX started

    // ZLJC started
  }

  private static BigDecimal extractNumber(String target) {
    BigDecimal result = BigDecimal.ZERO;
    if (target != null && !target.equals(NON_EXISTENCE_LONG)) {
      final Matcher matcher = NUMBER_PATTERN.matcher(target);
      while (matcher.find()) {
        result = new BigDecimal(matcher.group());
      }
    }
    return result;
  }
}
