package com.destiny1020.stock.es;

public class ElasticsearchConsts {

  /**
   * Stores StockSymbol entities.
   * The types below this index are:
   *    symbol ---> (symbol[code], name) tuples
   */
  public static final String INDEX_STOCK_CONSTANT = "stock_constant";

  /**
   * Stores StockDaily entities based on date.
   * The types below this index are daily-20150723, etc.
   */
  public static final String INDEX_STOCK = "stock";

  /**
   * Stores StockBlockIndex entities based on date.
   * The types below this index are daily-20150723, etc.
   */
  public static final String INDEX_BLOCK = "index_block";

  /**
   * Stores StockIndex entities based on date.
   * The types below this index are daily-20150723, etc.
   */
  public static final String INDEX_COMPOSITE = "index_composite";

  /**
   * Stores all stock symbols
   */
  public static final String TYPE_SYMBOL = "symbol";

  /**
   * Stores daily data exported from THS
   */
  public static final String TYPE_DAILY = "daily";

}
