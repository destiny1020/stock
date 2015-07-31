package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.setting.CommonSettings;
import com.destiny1020.stock.model.StockBlockDaily;

public class StockBlockDailyIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockBlockDailyIndexer.class);

  /**
   * Index block daily records into its belonging type under index_block.
   * The type name should have the format as dfj-20150723.
   * 
   * @param client
   * @param targetDate
   * @param blockAbbr
   * @param blockDailyList
   * @throws IOException
   * @throws ExecutionException 
   * @throws InterruptedException 
   */
  public static void reindexStockBlockDaily(Client client, Date targetDate, String blockAbbr,
      List<StockBlockDaily> blockDailyList) throws IOException, InterruptedException,
      ExecutionException {
    String dateStr = new SimpleDateFormat("yyyyMMdd").format(targetDate);

    // type name should have format as dfj-20150723
    String typeName = String.format("%s-%s", blockAbbr, dateStr);

    // create index with settings if necessary
    createIndexAndSettings(client);

    // create mappings for INDEX_BLOCK/typeName
    recreateMappings(client, typeName);

    // import stock block daily records
    importStocks(client, typeName, blockDailyList);
  }

  // TODO: to refactor to remove duplicated code
  private static void createIndexAndSettings(Client client) throws InterruptedException,
      ExecutionException, IOException {
    // only create when not exist
    if (!ElasticsearchUtils.isIndexExisting(client, ElasticsearchConsts.INDEX_BLOCK)) {
      LOGGER.error(ElasticsearchConsts.INDEX_BLOCK + " is not existed.. Create one right now.");
      if (ElasticsearchUtils.putIndexWithSetting(client, ElasticsearchConsts.INDEX_BLOCK,
          CommonSettings.getSymbolAnalyzerSettings())) {
        LOGGER.info("INDEX_BLOCK has been updated with filter and analyzer !");
      } else {
        LOGGER.error("INDEX_BLOCK Stock updating failed !");
      }
    }
  }

  private static void importStocks(Client client, String typeName,
      List<StockBlockDaily> blockDailyList) {
    if (!ElasticsearchUtils.bulkIndexing(client, ElasticsearchConsts.INDEX_BLOCK, typeName,
        blockDailyList)) {
      String errMsg = "[Stock Block Daily] Bulk indexing is failed for: " + typeName;
      LOGGER.error(errMsg);
      throw new RuntimeException(errMsg);
    } else {
      LOGGER.info("[Stock Block Daily] Bulk indexing is successful for: " + typeName);
    }
  }

  private static void recreateMappings(Client client, String typeName) throws IOException {
    String indexName = ElasticsearchConsts.INDEX_BLOCK;
    // only create when not exist
    if (!ElasticsearchUtils.isTypeExisting(client, indexName, typeName)) {
      if (!ElasticsearchUtils.putTypeMapping(client, indexName, typeName,
          getStockBlockDailyMappings(typeName))) {
        String errMsg = "[Stock Block Daily] Mapping creation failed for: " + typeName;
        LOGGER.error(errMsg);
        throw new RuntimeException(errMsg);
      }
    }
  }

  private static XContentBuilder getStockBlockDailyMappings(String typeName) throws IOException {
    // @formatter:off
    XContentBuilder builder = XContentFactory.jsonBuilder()
        .startObject()
          .startObject(typeName)
            .field("dynamic", "strict")
            .startObject("_id")
              .field("path", "symbol")
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
              // percentage
              .startObject("percentage")
                .field("type", "double")
              .endObject()
              // current
              .startObject("current")
                .field("type", "double")
              .endObject()
              // mfNetFactor
              .startObject("mfNetFactor")
                .field("type", "double")
              .endObject()
              // mfAmount
              .startObject("mfAmount")
                .field("type", "double")
              .endObject()
              // change
              .startObject("change")
                .field("type", "double")
              .endObject()
              // volume
              .startObject("volume")
                .field("type", "double")
              .endObject()
              // turnover
              .startObject("turnover")
                .field("type", "double")
              .endObject()
              // qrr
              .startObject("qrr")
                .field("type", "double")
              .endObject()
              // block
              .startObject("block")
                .field("type", "string")
                .field("index", "not_analyzed")
              .endObject()
              // fiveIncPercentage
              .startObject("fiveIncPercentage")
                .field("type", "double")
              .endObject()
              // tenIncPercentage
              .startObject("tenIncPercentage")
                .field("type", "double")
              .endObject()
              // twentyIncPercentage
              .startObject("twentyIncPercentage")
                .field("type", "double")
              .endObject()
              // latestVolume
              .startObject("latestVolume")
                .field("type", "double")
              .endObject()
              // open
              .startObject("open")
                .field("type", "double")
              .endObject()
              // lastClose
              .startObject("lastClose")
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
              // dpe
              .startObject("dpe")
                .field("type", "double")
              .endObject()
              // amplitude
              .startObject("amplitude")
                .field("type", "double")
              .endObject()
              // amount
              .startObject("amount")
                .field("type", "double")
              .endObject()
              // amountPerDeal
              .startObject("amountPerDeal")
                .field("type", "double")
              .endObject()
              // dealNumber
              .startObject("dealNumber")
                .field("type", "double")
              .endObject()
              // averageSharePerDeal
              .startObject("averageSharePerDeal")
                .field("type", "double")
              .endObject()
              // averagePrice
              .startObject("averagePrice")
                .field("type", "double")
              .endObject()
              // circulationMarketCapital
              .startObject("circulationMarketCapital")
                .field("type", "double")
              .endObject()
              // totalMarketCapital
              .startObject("totalMarketCapital")
                .field("type", "double")
              .endObject()
            .endObject()
          .endObject()
        .endObject();
    // @formatter:on

    return builder;
  }
}
