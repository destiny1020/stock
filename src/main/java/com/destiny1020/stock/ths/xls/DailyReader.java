package com.destiny1020.stock.ths.xls;

import java.util.Date;
import java.util.List;

import org.elasticsearch.client.Client;

import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.indexer.StockHistoryIndexer;

/**
 * Overall entry for the indexers.
 * Will be used for daily data maintenance.
 * 
 * @author destiny
 *
 */
public class DailyReader {

  // Test aim
  public static void main(String[] args) throws Exception {
    read(THSReaderUtils.SDF.parse("2015-07-31"));
  }

  /**
   * Default to use today as the target date.
   * 
   * @throws Exception
   */
  public static void read() throws Exception {
    read(new Date());
  }

  /**
   * Entry method for index all exported data.
   * 
   * @throws Exception 
   */
  public static void read(Date date) throws Exception {
    List<String> nonExistPaths = THSReaderUtils.validateDataExist(date);
    if (nonExistPaths != null && nonExistPaths.size() > 0) {
      throw new RuntimeException("Some Data files are not existing: " + nonExistPaths.toString());
    }

    // prepare the ES client
    Client client = ElasticsearchUtils.getClient();

    // step 1: stock/daily-yyyyMMdd
    StockDailyReader.load(client, date);

    // step 2: index_composite/daily-yyyyMMdd
    StockIndexReader.load(client, date);

    // step 3: index_block/daily-yyyyMMdd
    StockBlockReader.load(client, date);

    // step 4: index_block/xxx-yyyyMMdd
    StockBlockDailyReader.load(client, date);

    // step 5: stocklist/symbol
    StockHistoryIndexer.indexStockHistoryAll(client, date);

    // close the ES client
    client.close();
  }

}
