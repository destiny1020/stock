package com.destiny1020.stock.es;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.destiny1020.stock.es.indexer.StockSymbolIndexer;

public class StockIndexSettingTest extends ESTestBase {

  @Test
  public void testUpdateStockIndexSettings() throws InterruptedException, ExecutionException,
      IOException {

    StockSymbolIndexer.updateIndexSettings(client);

  }

}
