package com.destiny1020.stock.es;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

/**
 * Send common used requests for fetching data in ES instance.
 * 
 * @author Administrator
 *
 */
public class ElasticsearchCommons {

  private static Map<String, String> STOCK_SYMBOL_TO_NAME_MAP = null;

  /**
   * Used to get symbol -> name map and cache it for further use.
   * 
   * @param client
   * @return
   */
  public static Map<String, String> getSymbolToNamesMap(Client client) {
    if (STOCK_SYMBOL_TO_NAME_MAP == null) {
      STOCK_SYMBOL_TO_NAME_MAP = new HashMap<>(3000);
      SearchHit[] hits =
          client.prepareSearch(ElasticsearchConsts.INDEX_STOCK_CONSTANT)
              .setTypes(ElasticsearchConsts.TYPE_SYMBOL).setQuery(QueryBuilders.matchAllQuery())
              .setSize(3000).get().getHits().getHits();

      for (SearchHit hit : hits) {
        Map<String, Object> sourceMap = hit.sourceAsMap();
        STOCK_SYMBOL_TO_NAME_MAP.put((String) sourceMap.get("symbol"),
            (String) sourceMap.get("name"));
      }
    }

    return STOCK_SYMBOL_TO_NAME_MAP;
  }

}
