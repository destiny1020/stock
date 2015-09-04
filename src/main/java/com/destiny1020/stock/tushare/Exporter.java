package com.destiny1020.stock.tushare;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import com.destiny1020.stock.es.ElasticsearchCommons;
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

  public static void main(String[] args) throws IOException, InterruptedException {
    String startDate = "2001-01-01";
    String endDate = "2015-12-31";

    // retrieve all symbols
    Node node = ElasticsearchUtils.getNode();
    Client client = node.client();

    Map<String, String> symbolToNamesMap = ElasticsearchCommons.getSymbolToNamesMap(client);
    for (String symbol : symbolToNamesMap.keySet()) {
      System.out.println(String.format("Exporting %s into ES...", symbol));
      exportToES(symbol, startDate, endDate);
    }
  }

}
