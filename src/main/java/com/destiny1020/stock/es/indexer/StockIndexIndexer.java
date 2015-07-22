package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.ths.model.StockIndex;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Used to do StockIndex related operations on ES.
 * 
 * @author Administrator
 *
 */
public class StockIndexIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockIndexIndexer.class);

  public static void reindexStockIndex(Client client, Date date, ArrayList<StockIndex> indices)
      throws ElasticsearchException, IOException {
    // default to recreate type
    reindexStockIndex(client, indices, date, true);
  }

  private static void reindexStockIndex(Client client, ArrayList<StockIndex> indices, Date date,
      boolean recreate) throws ElasticsearchException, IOException {
    String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
    String typeName = String.format("%s-%s", ElasticsearchConsts.TYPE_BLOCK, dateStr);

    // create mappings for stock/block
    recreateMappings(client, typeName, recreate);

    // import stock block index records
    importBlockIndices(client, typeName, indices);
  }

  private static void importBlockIndices(Client client, String typeName,
      ArrayList<StockIndex> indices) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();

    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    indices.forEach(index -> {
      String json;
      try {
        json = mapper.writeValueAsString(index);
        bulkRequest.add(client.prepareIndex(ElasticsearchConsts.INDEX_STOCK, typeName).setSource(
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
        client.admin().indices().prepareGetMappings(ElasticsearchConsts.INDEX_STOCK)
            .setTypes(typeName).execute().actionGet();

    if (getResponse.getMappings().size() == 1 && recreate) {
      LOGGER.info(String.format("Recreate mapping for %s....", typeName));
      DeleteMappingResponse deleteResponse =
          client.admin().indices().prepareDeleteMapping(ElasticsearchConsts.INDEX_STOCK)
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
        client.admin().indices().preparePutMapping(ElasticsearchConsts.INDEX_STOCK)
            .setType(typeName).setSource(getStockIndexMappings(typeName)).execute().actionGet();

    if (response.isAcknowledged()) {
      LOGGER.info(String.format("%s and mapping created !", typeName));
    } else {
      LOGGER.error(String.format("%s and mapping creation failed !", typeName));
    }
  }

  private static XContentBuilder getStockIndexMappings(String typeName) throws IOException {
    XContentBuilder builder =
        XContentFactory
            .jsonBuilder()
            .startObject()
            .startObject(typeName)
            .field("dynamic", "strict")
            .startObject("_all")
            .field("enabled", "true")
            .endObject()
            .startObject("properties")
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
            .startObject("pioneer").field("type", "string").field("analyzer", "ik")
            .startObject("fields").startObject("raw").field("type", "string")
            .field("index", "not_analyzed").endObject().startObject("standard")
            .field("type", "string").endObject().endObject().endObject()
            // fiveIncPercentage
            .startObject("fiveIncPercentage").field("type", "double").endObject()
            // tenIncPercentage
            .startObject("tenIncPercentage").field("type", "double").endObject()
            // twentyIncPercentage
            .startObject("twentyIncPercentage").field("type", "double").endObject()
            // volume
            .startObject("volume").field("type", "double").endObject()
            // amount
            .startObject("amount").field("type", "double").endObject()
            // totalMarketCapital
            .startObject("totalMarketCapital").field("type", "double").endObject()
            // circulationMarketCapital
            .startObject("circulationMarketCapital").field("type", "double").endObject()
            .endObject().endObject().endObject();

    return builder;
  }
}
