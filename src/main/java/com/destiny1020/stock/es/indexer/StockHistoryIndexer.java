package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.setting.CommonSettings;

/**
 * To index the stock history for various periods(daily, weekly and monthly).
 * 
 * Would send requests for fetching the history data from XQ.
 * 
 * @author Administrator
 *
 */
public class StockHistoryIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockHistoryIndexer.class);


  public static void indexStockHistory(Client client, Date date, String symbol)
      throws InterruptedException, ExecutionException, IOException {
    // create index and related settings if not existed
    createIndexAndSettings(client);

    // create mappings for INDEX_STOCKLIST/(symbol)
    recreateMappings(client, symbol);
  }

  private static void recreateMappings(Client client, String symbol) throws IOException {
    String index = ElasticsearchConsts.INDEX_STOCKLIST;
    String type = symbol;

    // create type mappings for the target symbol
    if (!ElasticsearchUtils.isTypeExisting(client, index, type)) {
      LOGGER.info(String.format("Create mapping for %s/%s   .", index, type));
      if (ElasticsearchUtils.putTypeMapping(client, index, type, getTypeMapping(type))) {
        LOGGER.info(String.format("Create mapping for %s/%s is successful !", index, type));
      }
    }
  }

  private static XContentBuilder getTypeMapping(String typeName) throws IOException {
    // @formatter:off
    XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
              .startObject(typeName)
                .field("dynamic", "strict")
                .startObject("_id")
                  .field("path", "symbol")
                .endObject()
                .startObject("_timestamp")
                  .field("path", "time")
                .endObject()
                .startObject("_all")
                  .field("enabled", "true")
                .endObject()
                .startObject("properties")
                  // recordDate
                  .startObject("recordDate")
                    .field("type", "date")
                  .endObject()
                  // symbol
                  .startObject("symbol")
                    .field("type", "string")
                    .field("index_analyzer", "symbol_analyzer")
                    .field("search_analyzer", "standard")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                    .endObject()
                  .endObject()
                  // name
                  .startObject("name")
                    .field("type", "string")
                    .field("index_analyzer", "symbol_analyzer")
                    .field("search_analyzer", "standard")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                    .endObject()
                  .endObject()
                  // volume
                  .startObject("volume")
                    .field("type", "double")
                  .endObject()
                  // close
                  .startObject("close")
                    .field("type", "double")
                  .endObject()
                  // open
                  .startObject("open")
                    .field("type", "double")
                  .endObject()
                  // high
                  .startObject("high")
                    .field("type", "double")
                  .endObject()
                  // low
                  .startObject("low")
                    .field("type", "double")
                  .endObject()
                  // chg
                  .startObject("chg")
                    .field("type", "double")
                  .endObject()
                  // percent
                  .startObject("percent")
                    .field("type", "double")
                  .endObject()
                  // turnrate
                  .startObject("turnrate")
                  .field("type", "double")
                  .endObject()
                  // ma5
                  .startObject("ma5")
                    .field("type", "double")
                  .endObject()
                  // ma10
                  .startObject("ma10")
                    .field("type", "double")
                  .endObject()
                  // ma20
                  .startObject("ma20")
                    .field("type", "double")
                  .endObject()
                  // ma30
                  .startObject("ma30")
                    .field("type", "double")
                  .endObject()
                  // dif
                  .startObject("dif")
                    .field("type", "double")
                  .endObject()
                  // dea
                  .startObject("dea")
                    .field("type", "double")
                  .endObject()
                  // macd
                  .startObject("macd")
                    .field("type", "double")
                  .endObject()
                  // time
                  .startObject("time")
                    .field("type", "date")
                  .endObject()
                .endObject()
              .endObject()
            .endObject();
    // @formatter:on

    return builder;
  }

  private static void createIndexAndSettings(Client client) throws InterruptedException,
      ExecutionException, IOException {
    // only create when not exist
    if (!ElasticsearchUtils.isIndexExisting(client, ElasticsearchConsts.INDEX_STOCKLIST)) {
      LOGGER.error(ElasticsearchConsts.INDEX_STOCKLIST + " is not existed.. Create one right now.");
      if (ElasticsearchUtils.putIndexWithSetting(client, ElasticsearchConsts.INDEX_STOCKLIST,
          getIndexStocklistSettings())) {
        LOGGER.info("INDEX_STOCKLIST has been updated with filter and analyzer !");
      } else {
        LOGGER.error("INDEX_STOCKLIST Stock updating failed !");
      }
    }
  }


  private static String getIndexStocklistSettings() throws IOException {
    return CommonSettings.getSymbolAnalyzerSettings();
  }

}
