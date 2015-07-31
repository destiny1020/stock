package com.destiny1020.stock.ths.xls;

import java.util.Date;

import org.elasticsearch.client.Client;

import com.destiny1020.stock.es.ElasticsearchUtils;

/**
 * Overall entry for the indexers.
 * Will be used for daily data maintenance.
 * 
 * @author destiny
 *
 */
public class DailyReader {

  /**
   * Entry method for index all exported data.
   * @throws Exception 
   */
  public static void entry() throws Exception {
    // prepare the ES client
    Client client = ElasticsearchUtils.getClient();

    // prepare date
    Date today = new Date();

    // step 1: stock/daily-yyyyMMdd
    StockDailyReader.load(client, today);

    // step 2: index_composite/daily-yyyyMMdd
    StockIndexReader.load(client, today);

    // step 3: index_block/daily-yyyyMMdd
    StockBlockReader.load(client, today);

    // step 4: index_block/xxx-yyyyMMdd
    StockBlockDailyReader.load(client, today);
  }

}
