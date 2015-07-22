package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.ths.model.StockSymbol;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockSymbolIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockSymbolIndexer.class);

  public static void reindexStockSymbols(Client client, List<StockSymbol> stocks)
      throws IOException, InterruptedException, ExecutionException {
    // create mappings for stock/symbol
    recreateMappings(client);

    // import stock symbol records
    importStocks(client, stocks);
  }

  public static void recreateMappings(Client client) throws IOException, InterruptedException,
      ExecutionException {
    // delete if any
    // delete mapping will cause all existing data been removed
    GetMappingsResponse getResponse =
        client.admin().indices().prepareGetMappings(ElasticsearchConsts.INDEX_STOCK)
            .setTypes(ElasticsearchConsts.TYPE_SYMBOL).execute().actionGet();

    if (getResponse.getMappings().size() == 1) {
      DeleteMappingResponse deleteResponse =
          client.admin().indices().prepareDeleteMapping(ElasticsearchConsts.INDEX_STOCK)
              .setType(ElasticsearchConsts.TYPE_SYMBOL).execute().actionGet();

      if (deleteResponse != null && deleteResponse.isAcknowledged()) {
        LOGGER.info("Delete mapping for TYPE_SYMBOL completed.");
      } else {
        LOGGER.info("Delete mapping for TYPE_SYMBOL failed.");
      }
    }

    PutMappingResponse response =
        client.admin().indices().preparePutMapping(ElasticsearchConsts.INDEX_STOCK)
            .setType(ElasticsearchConsts.TYPE_SYMBOL).setSource(getStockSymbolMappings()).execute()
            .actionGet();

    if (response.isAcknowledged()) {
      LOGGER.info("TYPE_SYMBOL and mapping created !");
    } else {
      LOGGER.error("TYPE_SYMBOL and mapping creation failed !");
    }
  }

  /**
   * Add tokenizer and analyzer to the existing STOCK index.
   * Firstly, close the index.
   * Secondly, update the settings.
   * Thirdly, open the index.
   * 
   * @param client
   * @throws InterruptedException
   * @throws ExecutionException
   * @throws IOException
   */
  public static void updateIndexSettings(Client client) throws InterruptedException,
      ExecutionException, IOException {
    // close the index
    CloseIndexResponse closeIndexResponse =
        client.admin().indices().prepareClose(ElasticsearchConsts.INDEX_STOCK).execute().get();
    if (closeIndexResponse.isAcknowledged()) {
      LOGGER.info("INDEX Stock has been closed !");
    } else {
      LOGGER.info("INDEX Stock closing failed !");
      throw new RuntimeException("INDEX Stock cannot close...");
    }

    // settings for the analyers
    UpdateSettingsResponse usr =
        client.admin().indices().prepareUpdateSettings()
            .setIndices(ElasticsearchConsts.INDEX_STOCK).setSettings(getStockSymbolSettings())
            .execute().get();
    if (usr.isAcknowledged()) {
      LOGGER.info("INDEX Stock has been updated with filter and analyzer !");
    } else {
      LOGGER.error("INDEX Stock updating failed !");
    }

    // open the index
    OpenIndexResponse openIndexResponse =
        client.admin().indices().prepareOpen(ElasticsearchConsts.INDEX_STOCK).execute().get();
    if (openIndexResponse.isAcknowledged()) {
      LOGGER.info("INDEX Stock has been opened !");
    } else {
      LOGGER.info("INDEX Stock opening failed !");
      throw new RuntimeException("INDEX Stock cannot open...");
    }
  }

  private static String getStockSymbolSettings() throws IOException {
    return XContentFactory.jsonBuilder()
        .startObject()
        .startObject("analysis")
        // tokenizer section
        .startObject("tokenizer").startObject("symbol_t").field("type", "nGram")
        .field("min_gram", "1").field("max_gram", "8").endObject()
        .endObject()
        // analyzer section
        .startObject("analyzer").startObject("symbol_analyzer").field("type", "custom")
        .startArray("char_filter").endArray().field("tokenizer", "symbol_t").startArray("filter")
        .value("lowercase").endArray().endObject().endObject().endObject().endObject().string();
  }

  //  {
  //    "stock": {
  //       "mappings": {
  //          "symbol": {
  //             "dynamic": "strict",
  //             "_all": {
  //                "enabled": true
  //             },
  //             "_id": {
  //                "path": "symbol"
  //             },
  //             "properties": {
  //                "name": {
  //                   "type": "string",
  //                   "analyzer": "ik",
  //                   "fields": {
  //                      "raw": {
  //                         "type": "string",
  //                         "index": "not_analyzed"
  //                      },
  //                      "standard": {
  //                         "type": "string"
  //                      }
  //                   }
  //                },
  //                "symbol": {
  //                   "type": "string",
  //                   "fields": {
  //                      "raw": {
  //                         "type": "string",
  //                         "index": "not_analyzed"
  //                      }
  //                   }
  //                }
  //             }
  //          }
  //       }
  //    }
  // }
  private static XContentBuilder getStockSymbolMappings() throws IOException {
    XContentBuilder builder =
        XContentFactory.jsonBuilder().startObject().startObject(ElasticsearchConsts.TYPE_SYMBOL)
            .field("dynamic", "strict")
            .startObject("_id")
            .field("path", "symbol")
            .endObject()
            .startObject("_all")
            .field("enabled", "true")
            .endObject()
            .startObject("properties")
            // symbol
            .startObject("symbol").field("type", "string")
            .field("index_analyzer", "symbol_analyzer").field("search_analyzer", "standard")
            .startObject("fields").startObject("raw").field("type", "string")
            .field("index", "not_analyzed")
            .endObject()
            .endObject()
            .endObject()
            // name
            .startObject("name").field("type", "string").field("analyzer", "ik")
            .startObject("fields").startObject("raw").field("type", "string")
            .field("index", "not_analyzed").endObject().startObject("standard")
            .field("type", "string").endObject().endObject().endObject()
            // block
            .startObject("block").field("type", "string").field("index", "not_analyzed")
            .endObject().endObject().endObject().endObject();

    return builder;
  }

  private static void importStocks(Client client, List<StockSymbol> stocks) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();

    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    stocks.forEach(stock -> {
      String json;
      try {
        json = mapper.writeValueAsString(stock);
        bulkRequest.add(client.prepareIndex(ElasticsearchConsts.INDEX_STOCK,
            ElasticsearchConsts.TYPE_SYMBOL, stock.getSymbol()).setSource(json));
      } catch (Exception e) {
        e.printStackTrace();
        LOGGER.error(String.format("Serializing %d:%s has some errors.", stock.getSymbol(),
            stock.getName()));
      }
    });

    // execute bulk indexing
    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
    if (bulkResponse.hasFailures()) {
      LOGGER.error("Bulk indexing has failures: " + bulkResponse.buildFailureMessage());
    }
  }
}
