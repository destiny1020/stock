package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
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
      throws IOException {
    // create mappings for stock/symbol
    recreateMappings(client);

    // import stock symbol records
    importStocks(client, stocks);
  }

  public static void recreateMappings(Client client) throws IOException {
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
              .startObject("symbol")
                .field("type", "string")
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
            .endObject()
          .endObject()
        .endObject();

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
