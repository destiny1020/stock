package com.destiny1020.stock.tushare;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.sort.SortOrder;

import com.destiny1020.stock.es.ElasticsearchCommons;
import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.es.ElasticsearchUtils;

/**
 * Export data to the XLS.
 * 
 * @author destiny1020
 *
 */
public class Exporter {

  /**
   * Export history data into MYSQL.
   * 
   * @param symbol
   * @param startDay
   * @param endDay
   * @throws IOException
   * @throws InterruptedException 
   */
  private static void exportToESCore(String symbol, String startDay, String endDay,
      boolean isHistory, boolean forceUpdate) throws IOException, InterruptedException {
    String scriptDest =
        isHistory ? "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_es_hist.py "
            : "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_es.py ";
    String command = scriptDest + symbol + " " + startDay + " " + endDay;
    if (forceUpdate) {
      command += " true";
    } else {
      command += " false";
    }
    Process proc = Runtime.getRuntime().exec(command);
    proc.waitFor();
  }

  /**
   * Export history data for symbol, from start day to end day.
   * 
   * @param symbol
   * @param startDay
   * @param endDay
   * @throws IOException 
   * @throws InterruptedException 
   */
  public static void exportToExcel(String symbol, String startDay, String endDay)
      throws IOException, InterruptedException {

    Process proc =
        Runtime.getRuntime().exec(
            "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_excel.py "
                + symbol + " " + startDay + " " + endDay);
    proc.waitFor();
  }

  /**
   * Main Entry for export daily data to ES.
   * 
   * @throws IOException
   * @throws InterruptedException
   * @throws ParseException
   */
  public static void exportToES(boolean forceUpdate) throws IOException, InterruptedException,
      ParseException {
    // TODO: configurable
    String endDate = "2015-12-31";

    Node node = ElasticsearchUtils.getNode();
    Client client = node.client();

    // first retrieve the last available date
    String maxDateStr =
        ElasticsearchCommons.getMaxOrMinFieldValue(client, ElasticsearchConsts.TUSHARE_INDEX,
            ElasticsearchConsts.TUSHARE_TYPE_DAILY, "date", SortOrder.DESC);
    if (StringUtils.isBlank(maxDateStr)) {
      maxDateStr = "2000-12-31";
    }

    LocalDate maxDate = LocalDate.parse(maxDateStr);
    String startDate = maxDate.plusDays(1).toString();

    // retrieve all symbols
    Map<String, String> symbolToNamesMap = ElasticsearchCommons.getSymbolToNamesMap(client);

    for (String symbol : symbolToNamesMap.keySet()) {
      String target = symbol.substring(2);
      System.out.println(String.format("Exporting Daily %s into ES for %s --- %s", symbol,
          startDate, endDate));
      exportToESCore(target, startDate, endDate, false, forceUpdate);
    }

    node.close();
  }

  /**
   * Main Entry for export daily data to ES.
   * 
   * @throws IOException
   * @throws InterruptedException
   * @throws ParseException
   */
  public static void exportHistoryToES(boolean forceUpdate, boolean rewriteAll) throws IOException,
      InterruptedException, ParseException {
    // TODO: configurable
    String endDate = "2015-12-31";
    String startDate = "2000-01-01";

    Node node = ElasticsearchUtils.getNode();
    Client client = node.client();

    // retrieve all symbols
    Map<String, String> symbolToNamesMap = ElasticsearchCommons.getSymbolToNamesMap(client);

    int idx = 1;
    for (String symbol : symbolToNamesMap.keySet()) {
      String target = symbol.substring(2);

      if (!rewriteAll) {
        // first retrieve the last available date
        String maxDateStr =
            ElasticsearchCommons.getMaxOrMinFieldValueWithTermCriteria(client,
                ElasticsearchConsts.TUSHARE_INDEX, ElasticsearchConsts.TUSHARE_TYPE_HISTORY,
                "date", "code", target, SortOrder.DESC);
        if (StringUtils.isBlank(maxDateStr)) {
          maxDateStr = "1999-12-31";
        }

        LocalDate maxDate = LocalDate.parse(maxDateStr);
        startDate = maxDate.plusDays(1).toString();
      }

      System.out.println(String.format("%d: Exporting History %s into ES for %s --- %s", idx++,
          symbol, startDate, endDate));

      exportToESCore(target, startDate, endDate, true, forceUpdate);
    }

    node.close();
  }

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
    long startMillis = System.currentTimeMillis();
    exportHistoryToES(false, false);
    long endMillis = System.currentTimeMillis();
    System.out.println(String.format(
        "Finished batch execution for importing history data in: %.2f Seconds",
        (endMillis - startMillis) / 1000.0));
  }
}
