package com.destiny1020.stock.rdb;

import java.util.List;

import org.junit.Test;

public class MySQLLoadTest extends DBConfigBase {

  @Test
  public void testLoad() {

    // load concept data
    List<StockConceptData> data =
        em.createNamedQuery(StockConceptData.FIND_ALL, StockConceptData.class).getResultList();

    data.forEach(System.out::println);

    // load symbol data

  }
  
  @Te

}
