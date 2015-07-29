package com.destiny1020.stock.es;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.destiny1020.stock.es.indexer.StockHistoryIndexer;

public class StockHistoryIndexerTest extends ESTestBase {

  @Test
  public void testIndexHistory() throws InterruptedException, ExecutionException, IOException {
    StockHistoryIndexer.indexStockHistory(client, new Date(), "SH600588");
  }

}
