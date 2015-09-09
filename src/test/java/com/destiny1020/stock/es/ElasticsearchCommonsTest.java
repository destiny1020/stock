package com.destiny1020.stock.es;

import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

/**
 * 
 * @author destiny1020
 *
 */
public class ElasticsearchCommonsTest extends ESTestBase {

  @Test
  public void testGetMaxFieldValue() {
    String maxValue =
        ElasticsearchCommons.getMaxOrMinFieldValue(client, ElasticsearchConsts.TUSHARE_INDEX,
            ElasticsearchConsts.TUSHARE_TYPE_DAILY, "date", SortOrder.DESC);

    String minValue =
        ElasticsearchCommons.getMaxOrMinFieldValue(client, ElasticsearchConsts.TUSHARE_INDEX,
            ElasticsearchConsts.TUSHARE_TYPE_DAILY, "date", SortOrder.ASC);

    System.out.println(maxValue + " : " + minValue);
  }
}
