package com.destiny1020.stock.es;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.node.NodeBuilder;
import org.joda.time.DateTime;
import org.junit.Test;

import com.destiny1020.stock.es.indexer.StockHistoryIndexer;

public class StockHistoryIndexerTest extends ESTestBase {

  @Test
  public void testOneIndexHistory() throws InterruptedException, ExecutionException, IOException {
    DateTime time = DateTime.parse("2015-08-03");
    StockHistoryIndexer.indexStockHistory(client, new Date(time.getMillis()), "SZ000002");
  }

  @Test
  public void testAllStockHistory() {
    // cannot test concurrent scenario.
  }

  /**
   * Workaround for concurrent indexing test
   */
  public static void main(String[] args) {
    StockHistoryIndexer.indexStockHistoryAll(
        NodeBuilder.nodeBuilder().client(true).node().client(), new Date());

    // TODO: how to judge whether all index work has completed ?
  }

}
