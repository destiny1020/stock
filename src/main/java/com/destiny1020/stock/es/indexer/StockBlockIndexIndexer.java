package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.es.setting.CommonSettings;
import com.destiny1020.stock.ths.model.StockBlockIndex;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Used to do StockBlockIndex related operations on ES.
 * 
 * @author Administrator
 *
 */
public class StockBlockIndexIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockBlockIndexIndexer.class);

  /**
   * Main entry for the block index indexer.
   * 
   * @param client
   * @param date
   * @param indices
   * @throws ElasticsearchException
   * @throws IOException
   * @throws ExecutionException 
   * @throws InterruptedException 
   */
  public static void reindexStockBlockIndex(Client client, Date date,
      ArrayList<StockBlockIndex> indices) throws ElasticsearchException, IOException,
      InterruptedException, ExecutionException {
    // default to recreate type
    reindexStockBlockIndex(client, indices, date, true);
  }

  private static void reindexStockBlockIndex(Client client, ArrayList<StockBlockIndex> indices,
      Date date, boolean recreate) throws ElasticsearchException, IOException,
      InterruptedException, ExecutionException {
    String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
    String typeName = String.format("%s-%s", ElasticsearchConsts.TYPE_DAILY, dateStr);

    // create index and related settings if not existed
    createIndexAndSettings(client);

    // create mappings for stock/block
    recreateMappings(client, typeName, recreate);

    // import stock block index records
    importBlockIndices(client, typeName, indices);
  }

  private static void createIndexAndSettings(Client client) throws InterruptedException,
      ExecutionException, IOException {
    IndicesExistsResponse indicesExistsResponse =
        client.admin().indices().prepareExists(ElasticsearchConsts.INDEX_BLOCK).execute().get();

    // only create when not exist
    if (!indicesExistsResponse.isExists()) {
      LOGGER.error(ElasticsearchConsts.INDEX_BLOCK + " is not existed.. Create one right now.");
      CreateIndexResponse createIndexResponse =
          client.admin().indices().prepareCreate(ElasticsearchConsts.INDEX_BLOCK)
              .setSettings(getIndexBlockSettings()).execute().get();
      if (createIndexResponse.isAcknowledged()) {
        LOGGER.info("INDEX_BLOCK has been updated with filter and analyzer !");
      } else {
        LOGGER.error("INDEX_BLOCK Stock updating failed !");
      }
    }
  }

  private static String getIndexBlockSettings() throws IOException {
    return CommonSettings.getSymbolAnalyzerSettings();
  }

  private static void importBlockIndices(Client client, String typeName,
      ArrayList<StockBlockIndex> indices) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();

    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    indices.forEach(index -> {
      String json;
      try {
        json = mapper.writeValueAsString(index);
        bulkRequest.add(client.prepareIndex(ElasticsearchConsts.INDEX_BLOCK, typeName).setSource(
            json));
      } catch (Exception e) {
        e.printStackTrace();
        LOGGER.error(String.format("Serializing %d:%s has some errors.", index.getName(),
            index.getName()));
      }
    });

    // execute bulk indexing
    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
    if (bulkResponse.hasFailures()) {
      LOGGER.error("Bulk indexing has failures: " + bulkResponse.buildFailureMessage());
    }
  }

  private static void recreateMappings(Client client, String typeName, boolean recreate)
      throws ElasticsearchException, IOException {
    // delete if any
    // delete mapping will cause all existing data been removed
    GetMappingsResponse getResponse =
        client.admin().indices().prepareGetMappings(ElasticsearchConsts.INDEX_BLOCK)
            .setTypes(typeName).execute().actionGet();

    if (getResponse.getMappings().size() == 1 && recreate) {
      LOGGER.info(String.format("Recreate mapping for %s....", typeName));
      DeleteMappingResponse deleteResponse =
          client.admin().indices().prepareDeleteMapping(ElasticsearchConsts.INDEX_BLOCK)
              .setType(typeName).execute().actionGet();

      if (deleteResponse != null && deleteResponse.isAcknowledged()) {
        LOGGER.info(String.format("Delete mapping for %s completed.", typeName));
      } else {
        LOGGER.info(String.format("Delete mapping for %s failed.", typeName));
      }
      createMapping(client, typeName);
    } else if (getResponse.getMappings().size() == 0) {
      LOGGER.info(String.format("Create mapping for %s.... Since it is not existing.", typeName));
      createMapping(client, typeName);
    } else {
      LOGGER.info(String.format("Mapping for %s is already existed.", typeName));
    }
  }

  private static void createMapping(Client client, String typeName) throws ElasticsearchException,
      IOException {
    PutMappingResponse response =
        client.admin().indices().preparePutMapping(ElasticsearchConsts.INDEX_BLOCK)
            .setType(typeName).setSource(getStockBlockIndexMappings(typeName)).execute()
            .actionGet();

    if (response.isAcknowledged()) {
      LOGGER.info(String.format("%s and mapping created !", typeName));
    } else {
      LOGGER.error(String.format("%s and mapping creation failed !", typeName));
    }
  }

  private static XContentBuilder getStockBlockIndexMappings(String typeName) throws IOException {
    // @formatter:off
    XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
              .startObject(typeName)
                .field("dynamic", "strict")
                .startObject("_all")
                  .field("enabled", "true")
                .endObject()
                .startObject("properties")
                  // recordDate
                  .startObject("recordDate")
                    .field("type", "date")
                  .endObject()
                  // name
                  .startObject("name")
                    .field("type", "string")
                    .field("analyzer", "ik")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                      .startObject("standard")
                        .field("type", "string")
                      .endObject()
                    .endObject()
                  .endObject()
                  // precentage
                  .startObject("percentage")
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
                  // qrr --- 量比
                  .startObject("qrr")
                    .field("type", "double")
                  .endObject()
                  // riseCount
                  .startObject("riseCount")
                    .field("type", "integer")
                  .endObject()
                  // fallCount
                  .startObject("fallCount")
                    .field("type", "integer")
                  .endObject()
                  // pioneer
                  .startObject("pioneer")
                    .field("type", "string")
                    .field("analyzer", "ik")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                      .startObject("standard")
                        .field("type", "string")
                      .endObject()
                    .endObject()
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
                  // volume
                  .startObject("volume")
                    .field("type", "double")
                  .endObject()
                  // amount
                  .startObject("amount")
                    .field("type", "double")
                  .endObject()
                  // totalMarketCapital
                  .startObject("totalMarketCapital")
                    .field("type", "double")
                  .endObject()
                  // circulationMarketCapital
                  .startObject("circulationMarketCapital")
                    .field("type", "double")
                  .endObject()
                .endObject()
              .endObject()
            .endObject();
    // @formatter:on

    return builder;
  }
}
