package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import com.destiny1020.stock.es.ElasticsearchCommons;
import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.es.ElasticsearchUtils;
import com.destiny1020.stock.es.setting.CommonSettings;
import com.destiny1020.stock.model.StockHistory;
import com.destiny1020.stock.xueqiu.crawler.StockCrawler;
import com.destiny1020.stock.xueqiu.model.StockHistoryWrapper;
import com.destiny1020.stock.xueqiu.model.StockPeriod;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * To index the stock history for various periods(daily, weekly and monthly).
 * 
 * Would send requests for fetching the history data from XQ.
 * 
 * @author Administrator
 *
 */
public class StockHistoryIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockHistoryIndexer.class);

  private static final String FIELD_TIMESTAMP = "time";
  private static final Date DEFAULT_START = new Date(0);

  /**
   * Index all available stock stored in ES instance.
   * 
   * @param client
   * @param currentDate
   */
  public static void indexStockHistoryAll(Client client, Date currentDate) {
    Map<String, String> symbolToNames = ElasticsearchCommons.getSymbolToNamesMap(client);

    // index each stock in the map -- use 4 threads to do index
    ForkJoinPool forkJoinPool = new ForkJoinPool(4);
    forkJoinPool.submit(() -> symbolToNames.keySet().stream().parallel().forEach(symbol -> {
      try {
        indexStockHistory(client, currentDate, symbol);
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Index stock history failed for symbol: " + symbol);
      }
    }));
  }

  /**
   * To record how many stocks has been updated with history.
   */
  private static final AtomicInteger AI = new AtomicInteger(1);

  /**
   * Index individual stock history into ES instance.
   * 
   * @param client
   * @param currentDate
   * @param symbol
   * @throws InterruptedException
   * @throws ExecutionException
   * @throws IOException
   */
  public static void indexStockHistory(Client client, Date currentDate, String symbol)
      throws InterruptedException, ExecutionException, IOException {
    symbol = symbol.toLowerCase();

    // create index and related settings if not existed
    createIndexAndSettings(client);

    // create mappings for INDEX_STOCKLIST/(symbol)
    boolean isCreated = recreateMappings(client, symbol);

    // need to get the latest updated dates for 3 periods if mapping already exists.
    Map<StockPeriod, Date> latestRecords = null;
    if (!isCreated) {
      latestRecords = getLatestRecords(client, symbol);
    } else {
      latestRecords = new HashMap<>(3);
      for (StockPeriod period : StockPeriod.values()) {
        latestRecords.put(period, DEFAULT_START);
      }
    }

    // crawl XQ data for history
    Map<StockPeriod, List<StockHistory>> crawlHistoryData =
        crawlHistoryData(client, currentDate, latestRecords, symbol);

    // put the data into index
    importIntoIndex(client, crawlHistoryData, symbol);

    LOGGER.info(String.format("[Stocklist] Finished %d ---> %s", AI.getAndAdd(1), symbol));
  }

  private static void importIntoIndex(Client client,
      Map<StockPeriod, List<StockHistory>> crawlHistoryData, String symbol) {
    crawlHistoryData.forEach((period, historyList) -> {
      BulkRequestBuilder bulkRequest = client.prepareBulk();

      LOGGER.info("[Stocklist]Indexing history records: " + historyList.size());

      ObjectMapper mapper = new ObjectMapper();

      // @formatter:off
      historyList.forEach(stock -> {
        String json;
        try {
          json = mapper.writeValueAsString(stock);
          bulkRequest.add(client.prepareIndex(ElasticsearchConsts.INDEX_STOCKLIST,
              symbol.toLowerCase()).setSource(json));
        } catch (Exception e) {
          e.printStackTrace();
          LOGGER.error(String.format("[Stocklist]Serializing %d:%s has some errors.",
              stock.getSymbol(), stock.getName()));
        }
      });
      // execute bulk indexing
      BulkResponse bulkResponse = bulkRequest.execute().actionGet();
      if (bulkResponse.hasFailures()) {
        LOGGER.error("[Stocklist]Bulk indexing has failures: " + bulkResponse.buildFailureMessage());
      }
    });
    // @formatter:on
  }

  private static Map<StockPeriod, Date> getLatestRecords(Client client, String symbol)
      throws InterruptedException, ExecutionException {
    Map<StockPeriod, Date> latestRecords = new HashMap<>(3);

    // send requests to ES to fetch latest dates for each period
    for (StockPeriod period : StockPeriod.values()) {
      Date date = getLatestRecord(client, symbol, period);
      latestRecords.put(period, date);
    }

    return latestRecords;
  }

  private static Date getLatestRecord(Client client, String symbol, StockPeriod period)
      throws InterruptedException, ExecutionException {
    // assemble query object
    QueryBuilder query =
        QueryBuilders.filteredQuery(
            QueryBuilders.matchAllQuery(),
            FilterBuilders.boolFilter().must(
                FilterBuilders.termFilter("symbol.raw", symbol.toUpperCase()),
                FilterBuilders.termFilter("period", period.getOption())));

    // asssemble sort object
    SortBuilder order = SortBuilders.fieldSort(FIELD_TIMESTAMP).order(SortOrder.DESC);

    SearchResponse searchResponse =
        client.prepareSearch(ElasticsearchConsts.INDEX_STOCKLIST).setQuery(query).addSort(order)
            .setSize(1).execute().get();

    if (searchResponse.getHits().getTotalHits() > 0) {
      return new Date((Long) searchResponse.getHits().getHits()[0].getSource().get(FIELD_TIMESTAMP));
    } else {
      return DEFAULT_START;
    }
  }

  private static Map<StockPeriod, List<StockHistory>> crawlHistoryData(Client client, Date end,
      Map<StockPeriod, Date> latestRecords, String symbol) {
    final Map<StockPeriod, List<StockHistory>> historyData = new HashMap<>(3);

    Map<String, String> symbolToNames = ElasticsearchCommons.getSymbolToNamesMap(client);

    latestRecords.forEach((period, latestDate) -> {
      StockHistoryWrapper historyWrapper =
          StockCrawler.getHistoryWrapper(symbol, period, latestDate, end);

      if (historyWrapper != null) {
        if (historyWrapper.isSuccess()) {
          List<StockHistory> chartlist = historyWrapper.getChartlist();
          if (chartlist != null && chartlist.size() > 0) {
            historyData.put(period, chartlist);

            IntStream.range(0, chartlist.size()).forEach(idx -> {
              StockHistory history = chartlist.get(idx);

              // @formatter:off
              // set additional fields on history entities
              history.setSequence(idx + 1);
              history.setPeriod(period.getOption());
              history.setSymbol(symbol.toUpperCase());
              history.setName(symbolToNames.get(symbol.toUpperCase()));
              
              history.setRecordDate(end);
            });
          }
        } else {
          throw new RuntimeException(String.format("Crawling history for %s failed at period %s",
              symbol, period.getOption()));
        }
      }
    });

    // @formatter:on
    return historyData;
  }

  private static boolean recreateMappings(Client client, String symbol) throws IOException {
    String index = ElasticsearchConsts.INDEX_STOCKLIST;
    String type = symbol;

    // create type mappings for the target symbol
    if (!ElasticsearchUtils.isTypeExisting(client, index, type)) {
      LOGGER.info(String.format("Create mapping for %s/%s   .", index, type));
      if (ElasticsearchUtils.putTypeMapping(client, index, type, getTypeMapping(type))) {
        LOGGER.info(String.format("Create mapping for %s/%s is successful !", index, type));
        return true;
      } else {
        String failedMsg = String.format("Create mapping for %s/%s has failed !", index, type);
        LOGGER.info(failedMsg);
        throw new RuntimeException(failedMsg);
      }
    }

    return false;
  }

  private static XContentBuilder getTypeMapping(String typeName) throws IOException {
    // @formatter:off
    XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
              .startObject(typeName)
                .field("dynamic", "strict")
                .startObject("_timestamp")
                  .field("path", "time")
                .endObject()
                .startObject("_all")
                  .field("enabled", "true")
                .endObject()
                .startObject("properties")
                  // recordDate
                  .startObject("recordDate")
                    .field("type", "date")
                  .endObject()
                  // period
                  .startObject("period")
                    .field("type", "string")
                    .field("index", "not_analyzed")
                  .endObject()
                  // sequence
                  .startObject("sequence")
                    .field("type", "integer")
                  .endObject()
                  // symbol
                  .startObject("symbol")
                    .field("type", "string")
                    .field("index_analyzer", "symbol_analyzer")
                    .field("search_analyzer", "standard")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                    .endObject()
                  .endObject()
                  // name
                  .startObject("name")
                    .field("type", "string")
                    .field("index_analyzer", "symbol_analyzer")
                    .field("search_analyzer", "standard")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                    .endObject()
                  .endObject()
                  // volume
                  .startObject("volume")
                    .field("type", "double")
                  .endObject()
                  // close
                  .startObject("close")
                    .field("type", "double")
                  .endObject()
                  // open
                  .startObject("open")
                    .field("type", "double")
                  .endObject()
                  // high
                  .startObject("high")
                    .field("type", "double")
                  .endObject()
                  // low
                  .startObject("low")
                    .field("type", "double")
                  .endObject()
                  // chg
                  .startObject("chg")
                    .field("type", "double")
                  .endObject()
                  // percent
                  .startObject("percent")
                    .field("type", "double")
                  .endObject()
                  // turnrate
                  .startObject("turnrate")
                  .field("type", "double")
                  .endObject()
                  // ma5
                  .startObject("ma5")
                    .field("type", "double")
                  .endObject()
                  // ma10
                  .startObject("ma10")
                    .field("type", "double")
                  .endObject()
                  // ma20
                  .startObject("ma20")
                    .field("type", "double")
                  .endObject()
                  // ma30
                  .startObject("ma30")
                    .field("type", "double")
                  .endObject()
                  // dif
                  .startObject("dif")
                    .field("type", "double")
                  .endObject()
                  // dea
                  .startObject("dea")
                    .field("type", "double")
                  .endObject()
                  // macd
                  .startObject("macd")
                    .field("type", "double")
                  .endObject()
                  // time
                  .startObject("time")
                    .field("type", "date")
                  .endObject()
                .endObject()
              .endObject()
            .endObject();
    // @formatter:on

    return builder;
  }

  // TODO: to refactor to remove duplicated code
  private static void createIndexAndSettings(Client client) throws InterruptedException,
      ExecutionException, IOException {
    // only create when not exist
    if (!ElasticsearchUtils.isIndexExisting(client, ElasticsearchConsts.INDEX_STOCKLIST)) {
      LOGGER.error(ElasticsearchConsts.INDEX_STOCKLIST + " is not existed.. Create one right now.");
      if (ElasticsearchUtils.putIndexWithSetting(client, ElasticsearchConsts.INDEX_STOCKLIST,
          getIndexStocklistSettings())) {
        LOGGER.info("INDEX_STOCKLIST has been updated with filter and analyzer !");
      } else {
        LOGGER.error("INDEX_STOCKLIST Stock updating failed !");
      }
    }
  }


  private static String getIndexStocklistSettings() throws IOException {
    return CommonSettings.getSymbolAnalyzerSettings();
  }

}
