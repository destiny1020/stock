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

  @Test
  public void testGetMaxMinFieldValueWithCriteria() {
    String maxValue =
        ElasticsearchCommons.getMaxOrMinFieldValueWithTermCriteria(client,
            ElasticsearchConsts.TUSHARE_INDEX, ElasticsearchConsts.TUSHARE_TYPE_HISTORY, "date",
            "code", "000915", SortOrder.DESC);

    String minValue =
        ElasticsearchCommons.getMaxOrMinFieldValueWithTermCriteria(client,
            ElasticsearchConsts.TUSHARE_INDEX, ElasticsearchConsts.TUSHARE_TYPE_HISTORY, "date",
            "code", "000915", SortOrder.ASC);

    System.out.println(maxValue + " : " + minValue);
  }
}
