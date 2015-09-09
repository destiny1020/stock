package com.destiny1020.stock.es;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Send common used requests for fetching data in ES instance.
 * 
 * @author Administrator
 *
 */
public class ElasticsearchCommons {

  private static final Logger LOGGER = LogManager.getLogger(ElasticsearchCommons.class);

  private static Map<String, String> STOCK_SYMBOL_TO_NAME_MAP = null;
  private static final int FETCH_SIZE = 3000;

  /**
   * Used to get symbol -> name map and cache it for further use.
   * 
   * @param client
   * @return
   */
  public static Map<String, String> getSymbolToNamesMap(Client client) {
    if (STOCK_SYMBOL_TO_NAME_MAP == null) {
      STOCK_SYMBOL_TO_NAME_MAP = new HashMap<>(FETCH_SIZE);
      SearchHit[] hits =
          client.prepareSearch(ElasticsearchConsts.INDEX_STOCK_CONSTANT)
              .setTypes(ElasticsearchConsts.TYPE_SYMBOL).setQuery(QueryBuilders.matchAllQuery())
              .setSize(FETCH_SIZE).get().getHits().getHits();

      for (SearchHit hit : hits) {
        Map<String, Object> sourceMap = hit.sourceAsMap();
        STOCK_SYMBOL_TO_NAME_MAP.put((String) sourceMap.get("symbol"),
            (String) sourceMap.get("name"));
      }
    }

    return STOCK_SYMBOL_TO_NAME_MAP;
  }

  /**
   * Used to get the max/min value for certain field in index/type.
   * 
   * @param client
   * @param index
   * @param type
   * @param field
   * @param order
   * @return
   */
  public static String getMaxOrMinFieldValue(Client client, String index, String type,
      String field, SortOrder order) {
    SearchRequestBuilder searchBuilder = client.prepareSearch(index).setTypes(type);
    SearchResponse response =
        searchBuilder.addSort(SortBuilders.fieldSort(field).order(order)).setSize(1).get();

    SearchHit[] hits = response.getHits().getHits();
    if (hits.length == 0) {
      LOGGER.warn(String.format("There is no %s value in the %s/%s for field %s",
          order == SortOrder.ASC ? "min" : "max", index, type, field));
      return null;
    }

    return (String) hits[0].getSource().get(field);
  }
}
