package com.destiny1020.stock.es.indexer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.mapping.delete.DeleteMappingResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.destiny1020.stock.es.ElasticsearchConsts;
import com.destiny1020.stock.ths.model.StockDaily;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockDailyIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockDailyIndexer.class);

  public static void reindexStockDaily(Client client, Date date, List<StockDaily> stocks)
      throws IOException {
    // default to recreate type
    reindexStockDaily(client, stocks, date, true);
  }

  public static void reindexStockDaily(Client client, List<StockDaily> stocks, Date date,
      boolean recreate) throws IOException {
    String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
    String typeName = String.format("%s-%s", ElasticsearchConsts.TYPE_DAILY, dateStr);

    // create mappings for stock/daily
    recreateMappings(client, typeName, recreate);

    // import stock daily records
    importStocks(client, typeName, stocks);
  }

  public static void recreateMappings(Client client, String typeName, boolean recreate)
      throws IOException {
    // delete if any
    // delete mapping will cause all existing data been removed
    GetMappingsResponse getResponse =
        client.admin().indices().prepareGetMappings(ElasticsearchConsts.INDEX_STOCK)
            .setTypes(typeName).execute().actionGet();

    if (getResponse.getMappings().size() == 1 && recreate) {
      LOGGER.info(String.format("Recreate mapping for %s....", typeName));
      DeleteMappingResponse deleteResponse =
          client.admin().indices().prepareDeleteMapping(ElasticsearchConsts.INDEX_STOCK)
              .setType(typeName).execute().actionGet();

      if (deleteResponse != null && deleteResponse.isAcknowledged()) {
        LOGGER.info(String.format("Delete mapping for %s completed.", typeName));
      } else {
        LOGGER.info(String.format("Delete mapping for %s failed.", typeName));
      }
      createMapping(client, typeName);
    } else if (getResponse.getMappings().size() == 0) {
      LOGGER.info(String.format("Create mapping for %s.... Since it is not existing.", typeName));
      createMapping(client, typeName);
    } else {
      LOGGER.info(String.format("Mapping for %s is already existed.", typeName));
    }
  }

  private static void createMapping(Client client, String typeName) throws IOException {
    PutMappingResponse response =
        client.admin().indices().preparePutMapping(ElasticsearchConsts.INDEX_STOCK)
            .setType(typeName).setSource(getStockDailyMappings(typeName)).execute().actionGet();

    if (response.isAcknowledged()) {
      LOGGER.info(String.format("%s and mapping created !", typeName));
    } else {
      LOGGER.error(String.format("%s and mapping creation failed !", typeName));
    }
  }

  private static XContentBuilder getStockDailyMappings(String typeName) throws IOException {
    // @formatter:off
    XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
              .startObject(typeName)
                .field("dynamic", "strict")
                .startObject("_id")
                  .field("path", "symbol")
                .endObject()
                .startObject("_all")
                  .field("enabled", "true")
                .endObject()
                .startObject("properties")
                  // recordDate
                  .startObject("recordDate")
                    .field("type", "date")
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
                    .field("analyzer", "ik")
                    .startObject("fields")
                      .startObject("raw")
                        .field("type", "string")
                        .field("index", "not_analyzed")
                      .endObject()
                      .startObject("standard")
                        .field("type", "string")
                      .endObject()
                    .endObject()
                  .endObject()
                  // canFinancing
                  .startObject("canFinancing")
                    .field("type", "boolean")
                  .endObject()
                  // percentage
                  .startObject("percentage")
                    .field("type", "double")
                  .endObject()
                  // current
                  .startObject("current")
                    .field("type", "double")
                  .endObject()
                  // change
                  .startObject("change")
                    .field("type", "double")
                  .endObject()
                  // volume --- 总手(实际上导出的数据以股为单位)
                  .startObject("volume")
                    .field("type", "double")
                  .endObject()
                  // turnover --- 换手率
                  .startObject("turnover")
                    .field("type", "double")
                  .endObject()
                  // qrr --- 量比
                  .startObject("qrr")
                    .field("type", "double")
                  .endObject()
                  // buySignal
                  .startObject("buySignal")
                    .field("type", "integer")
                  .endObject()
                  // hasGood
                  .startObject("hasGood")
                    .field("type", "boolean")
                  .endObject()
                  // hasBad
                  .startObject("hasBad")
                    .field("type", "boolean")
                  .endObject()
                  // block --- 版块
                  .startObject("block")
                    .field("type", "string")
                    .field("index", "not_analyzed")
                  .endObject()
                  // latestVolume --- 现手
                  .startObject("latestVolume")
                    .field("type", "double")
                  .endObject()
                  // open
                  .startObject("open")
                    .field("type", "double")
                  .endObject()
                  // lastClose
                  .startObject("lastClose")
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
                  // buy
                  .startObject("buy")
                    .field("type", "double")
                  .endObject()
                  // sell
                  .startObject("sell")
                    .field("type", "double")
                  .endObject()
                  // mfNetFactor
                  .startObject("mfNetFactor")
                    .field("type", "double")
                  .endObject()
                  // mfAmount
                  .startObject("mfAmount")
                    .field("type", "double")
                  .endObject()
                  // iiNumber
                  .startObject("iiNumber")
                    .field("type", "double")
                  .endObject()
                  // dpe
                  .startObject("dpe")
                    .field("type", "double")
                  .endObject()
                  // pb
                  .startObject("pb")
                    .field("type", "double")
                  .endObject()
                  // buyVolume
                  .startObject("buyVolume")
                    .field("type", "double")
                  .endObject()
                  // sellVolume
                  .startObject("sellVolume")
                    .field("type", "double")
                  .endObject()
                  // delegateRatio
                  .startObject("delegateRatio")
                    .field("type", "double")
                  .endObject()
                  // amplitude
                  .startObject("amplitude")
                    .field("type", "double")
                  .endObject()
                  // amount
                  .startObject("amount")
                    .field("type", "double")
                  .endObject()
                  // amountPerDeal
                  .startObject("amountPerDeal")
                    .field("type", "double")
                  .endObject()
                  // dealNumber
                  .startObject("dealNumber")
                    .field("type", "double")
                  .endObject()
                  // averageSharePerDeal
                  .startObject("averageSharePerDeal")
                    .field("type", "double")
                  .endObject()
                  // averagePrice
                  .startObject("averagePrice")
                    .field("type", "double")
                  .endObject()
                  // outVolume
                  .startObject("outVolume")
                    .field("type", "double")
                  .endObject()
                  // inVolume
                  .startObject("inVolume")
                    .field("type", "double")
                  .endObject()
                  // totalMarketCapital
                  .startObject("totalMarketCapital")
                    .field("type", "double")
                  .endObject()
                  // circulationMarketCapital
                  .startObject("circulationMarketCapital")
                    .field("type", "double")
                  .endObject()
                  // indexContribution
                  .startObject("indexContribution")
                    .field("type", "double")
                  .endObject()
                  // star
                  .startObject("star")
                    .field("type", "double")
                  .endObject()
                  // delegateDiff
                  .startObject("delegateDiff")
                    .field("type", "double")
                  .endObject()
                  // star
                  .startObject("star")
                    .field("type", "double")
                  .endObject()
                  // orgTrend
                  .startObject("orgTrend")
                    .field("type", "double")
                  .endObject()
                  // openPercentage
                  .startObject("openPercentage")
                    .field("type", "double")
                  .endObject()
                  // barPercentage
                  .startObject("barPercentage")
                    .field("type", "double")
                  .endObject()
                  // totalShares
                  .startObject("totalShares")
                    .field("type", "double")
                  .endObject()
                  // circulationShares
                  .startObject("circulationShares")
                    .field("type", "double")
                  .endObject()
                  // circulationAShares
                  .startObject("circulationAShares")
                    .field("type", "double")
                  .endObject()
                  // circulationBShares
                  .startObject("circulationBShares")
                    .field("type", "double")
                  .endObject()
                  // circulationRatio
                  .startObject("circulationRatio")
                    .field("type", "double")
                  .endObject()
                  // shareholders
                  .startObject("shareholders")
                    .field("type", "integer")
                  .endObject()
                  // sharePerHolder
                  .startObject("sharePerHolder")
                    .field("type", "integer")
                  .endObject()
                  // pe
                  .startObject("pe")
                    .field("type", "double")
                  .endObject()
                  // totalProfit
                  .startObject("totalProfit")
                    .field("type", "double")
                  .endObject()
                  // netProfit
                  .startObject("netProfit")
                    .field("type", "double")
                  .endObject()
                  // profitIncreasePercentage
                  .startObject("profitIncreasePercentage")
                    .field("type", "double")
                  .endObject()
                  // eps
                  .startObject("eps")
                    .field("type", "double")
                  .endObject()
                  // navps
                  .startObject("navps")
                    .field("type", "double")
                  .endObject()
                  // pfps
                  .startObject("pfps")
                    .field("type", "double")
                  .endObject()
                  // totalAssetAmount
                  .startObject("totalAssetAmount")
                    .field("type", "double")
                  .endObject()
                  // currentAssetAmount
                  .startObject("currentAssetAmount")
                    .field("type", "double")
                  .endObject()
                  // fixedAssetAmount
                  .startObject("fixedAssetAmount")
                    .field("type", "double")
                  .endObject()
                  // intangibleAssetAmount
                  .startObject("intangibleAssetAmount")
                    .field("type", "double")
                  .endObject()
                  // por
                  .startObject("por")
                    .field("type", "double")
                  .endObject()
                  // popr
                  .startObject("popr")
                    .field("type", "double")
                  .endObject()
                  // roe
                  .startObject("roe")
                    .field("type", "double")
                  .endObject()
                  // alr
                  .startObject("alr")
                    .field("type", "double")
                  .endObject()
                  // totalLiabilities
                  .startObject("totalLiabilities")
                    .field("type", "double")
                  .endObject()
                  // tse
                  .startObject("tse")
                    .field("type", "double")
                  .endObject()
                  // pf
                  .startObject("pf")
                    .field("type", "double")
                  .endObject()
                  // roir
                  .startObject("roir")
                    .field("type", "double")
                  .endObject()
                  // netCapitalIn
                  .startObject("netCapitalIn")
                    .field("type", "double")
                  .endObject()
                  // bigCapitalIn
                  .startObject("bigCapitalIn")
                    .field("type", "double")
                  .endObject()
                  // bigCapitalOut
                  .startObject("bigCapitalOut")
                    .field("type", "double")
                  .endObject()
                  // bigNetCapital
                  .startObject("bigNetCapital")
                    .field("type", "double")
                  .endObject()
                  // bigNetCapitalPercentage
                  .startObject("bigNetCapitalPercentage")
                    .field("type", "double")
                  .endObject()
                  // bigCapitalTotal
                  .startObject("bigCapitalTotal")
                    .field("type", "double")
                  .endObject()
                  // bigCapitalTotalPercentage
                  .startObject("bigCapitalTotalPercentage")
                    .field("type", "double")
                  .endObject()
                  // midCapitalIn
                  .startObject("midCapitalIn")
                    .field("type", "double")
                  .endObject()
                  // midCapitalOut
                  .startObject("midCapitalOut")
                    .field("type", "double")
                  .endObject()
                  // midNetCapital
                  .startObject("midNetCapital")
                    .field("type", "double")
                  .endObject()
                  // midNetCapitalPercentage
                  .startObject("midNetCapitalPercentage")
                    .field("type", "double")
                  .endObject()
                  // midCapitalTotal
                  .startObject("midCapitalTotal")
                    .field("type", "double")
                  .endObject()
                  // midCapitalTotalPercentage
                  .startObject("midCapitalTotalPercentage")
                    .field("type", "double")
                  .endObject()
                  // smallCapitalIn
                  .startObject("smallCapitalIn")
                    .field("type", "double")
                  .endObject()
                  // smallCapitalOut
                  .startObject("smallCapitalOut")
                    .field("type", "double")
                  .endObject()
                  // smallNetCapital
                  .startObject("smallNetCapital")
                    .field("type", "double")
                  .endObject()
                  // smallNetCapitalPercentage
                  .startObject("smallNetCapitalPercentage")
                    .field("type", "double")
                  .endObject()
                  // smallCapitalTotal
                  .startObject("smallCapitalTotal")
                    .field("type", "double")
                  .endObject()
                  // smallCapitalTotalPercentage
                  .startObject("smallCapitalTotalPercentage")
                    .field("type", "double")
                  .endObject()
                  // oneIncPercentage
                  .startObject("oneIncPercentage")
                    .field("type", "double")
                  .endObject()
                  // oneIncRank
                  .startObject("oneIncRank")
                    .field("type", "integer")
                  .endObject()
                  // oneIncChange
                  .startObject("oneIncChange")
                    .field("type", "double")
                  .endObject()
                  // twoIncPercentage
                  .startObject("twoIncPercentage")
                    .field("type", "double")
                  .endObject()
                  // twoIncRank
                  .startObject("twoIncRank")
                    .field("type", "integer")
                  .endObject()
                  // twoIncChange
                  .startObject("twoIncChange")
                    .field("type", "double")
                  .endObject()
                  // threeIncPercentage
                  .startObject("threeIncPercentage")
                    .field("type", "double")
                  .endObject()
                  // threeIncRank
                  .startObject("threeIncRank")
                    .field("type", "integer")
                  .endObject()
                  // threeIncChange
                  .startObject("threeIncChange")
                    .field("type", "double")
                  .endObject()
                  // fiveIncPercentage
                  .startObject("fiveIncPercentage")
                    .field("type", "double")
                  .endObject()
                  // fiveIncRank
                  .startObject("fiveIncRank")
                    .field("type", "integer")
                  .endObject()
                  // fiveIncChange
                  .startObject("fiveIncChange")
                    .field("type", "double")
                  .endObject()
                  // tenIncPercentage
                  .startObject("tenIncPercentage")
                    .field("type", "double")
                  .endObject()
                  // tenIncRank
                  .startObject("tenIncRank")
                    .field("type", "integer")
                  .endObject()
                  // tenIncChange
                  .startObject("tenIncChange")
                    .field("type", "double")
                  .endObject()
                  // XUEQIU related fields below
                  // followers_count
                  .startObject("xqFollowersCount")
                    .field("type", "integer")
                  .endObject()
                .endObject()
              .endObject()
            .endObject();
    // @formatter:on

    return builder;
  }

  private static void importStocks(Client client, String typeName, List<StockDaily> stocks) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();

    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    stocks.forEach(stock -> {
      String json;
      try {
        json = mapper.writeValueAsString(stock);
        bulkRequest.add(client.prepareIndex(ElasticsearchConsts.INDEX_STOCK, typeName,
            stock.getSymbol()).setSource(json));
      } catch (Exception e) {
        e.printStackTrace();
        LOGGER.error(String.format("Serializing %d:%s has some errors.", stock.getSymbol(),
            stock.getName()));
      }
    });

    // execute bulk indexing
    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
    if (bulkResponse.hasFailures()) {
      LOGGER.error("Bulk indexing has failures: " + bulkResponse.buildFailureMessage());
    }
  }
}
