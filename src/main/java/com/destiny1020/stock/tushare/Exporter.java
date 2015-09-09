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
  public static void exportToES(String symbol, String startDay, String endDay) throws IOException,
      InterruptedException {
    Process proc =
        Runtime.getRuntime().exec(
            "python E:\\Code\\STS\\workspace-sts-3.6.4.RELEASE\\stock\\scripts\\to_es.py " + symbol
                + " " + startDay + " " + endDay);
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

  public static void main(String[] args) throws IOException, InterruptedException, ParseException {
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
      System.out.println(String.format("Exporting %s into ES for %s --- %s", symbol, startDate,
          endDate));
      exportToES(target, startDate, endDate);
      Thread.sleep(1000);
    }

    node.close();
  }
}
