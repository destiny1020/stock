package com.destiny1020.stock.rdb;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.destiny1020.stock.model.StockSymbol;
import com.destiny1020.stock.rdb.model.StockConceptData;
import com.destiny1020.stock.rdb.service.StockDataDailyService;

public class MySQLLoadTest extends DBConfigBase {

  @Test
  public void testLoad() {

    // load concept data
    List<StockConceptData> data =
        em.createNamedQuery(StockConceptData.FIND_ALL, StockConceptData.class).getResultList();

    data.forEach(System.out::println);

    // load symbol data

  }

  @Test
  public void testLoadSymbol() {
    StockSymbol symbol =
        em.createNamedQuery(StockSymbol.FIND_BY_SYMBOL, StockSymbol.class)
            .setParameter("symbol", "SH600886").getSingleResult();

    Assert.assertEquals("国投电力", symbol.getName());
  }

  @Test
  public void testLoadDataDaily() {
    String latestDate = StockDataDailyService.INSTANCE.latestDate("000915");
    System.out.println(latestDate);
  }
}
