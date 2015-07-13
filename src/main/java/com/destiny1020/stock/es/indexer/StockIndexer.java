package com.destiny1020.stock.es.indexer;

import java.io.IOException;
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
import com.destiny1020.stock.ths.model.StockSymbol;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockIndexer {

  private static final Logger LOGGER = LogManager.getLogger(StockIndexer.class);

  public static void reindexDailySymbols(Client client, List<StockSymbol> stocks)
      throws IOException {
    // create mappings for stock/symbol
    recreateMappings(client);

    // import stock symbol records
    importStocks(client, stocks);
  }

  public static void recreateMappings(Client client) throws IOException {
    // delete if any
    // delete mapping will cause all existing data been removed
    GetMappingsResponse getResponse =
        client.admin().indices().prepareGetMappings(ElasticsearchConsts.INDEX_STOCK)
            .setTypes(ElasticsearchConsts.TYPE_SYMBOL).execute().actionGet();

    if (getResponse.getMappings().size() == 1) {
      DeleteMappingResponse deleteResponse =
          client.admin().indices().prepareDeleteMapping(ElasticsearchConsts.INDEX_STOCK)
              .setType(ElasticsearchConsts.TYPE_SYMBOL).execute().actionGet();

      if (deleteResponse != null && deleteResponse.isAcknowledged()) {
        LOGGER.info("Delete mapping for TYPE_SYMBOL completed.");
      } else {
        LOGGER.info("Delete mapping for TYPE_SYMBOL failed.");
      }
    }

    PutMappingResponse response =
        client.admin().indices().preparePutMapping(ElasticsearchConsts.INDEX_STOCK)
            .setType(ElasticsearchConsts.TYPE_SYMBOL).setSource(getStockDailyMappings()).execute()
            .actionGet();

    if (response.isAcknowledged()) {
      LOGGER.info("TYPE_SYMBOL and mapping created !");
    } else {
      LOGGER.error("TYPE_SYMBOL and mapping creation failed !");
    }
  }

  private static XContentBuilder getStockDailyMappings() throws IOException {
    XContentBuilder builder =
        XContentFactory.jsonBuilder().startObject().startObject(ElasticsearchConsts.TYPE_SYMBOL)
            .field("dynamic", "strict").startObject("_id")
            .field("path", "symbol")
            .endObject()
            .startObject("_all")
            .field("enabled", "true")
            .endObject()
            .startObject("properties")
            // symbol
            .startObject("symbol").field("type", "string").startObject("fields").startObject("raw")
            .field("type", "string")
            .field("index", "not_analyzed")
            .endObject()
            .endObject()
            .endObject()
            // name
            .startObject("name").field("type", "string").field("analyzer", "ik")
            .startObject("fields").startObject("raw").field("type", "string")
            .field("index", "not_analyzed").endObject().startObject("standard")
            .field("type", "string").endObject().endObject().endObject()
            // precentage
            .startObject("percentage").field("type", "double")
            .endObject()
            // current
            .startObject("current").field("type", "double")
            .endObject()
            // change
            .startObject("change").field("type", "double")
            .endObject()
            // volume --- 总手(实际上导出的数据以股为单位)
            .startObject("volume").field("type", "double")
            .endObject()
            // turnover --- 换手率
            .startObject("turnover").field("type", "double")
            .endObject()
            // qrr --- 量比
            .startObject("qrr").field("type", "double")
            .endObject()
            // buySignal
            .startObject("buySignal").field("type", "integer")
            .endObject()
            // hasGood
            .startObject("hasGood").field("type", "boolean")
            .endObject()
            // hasBad
            .startObject("hasBad").field("type", "boolean")
            .endObject()
            // block --- 版块
            .startObject("block").field("type", "string")
            .field("index", "not_analyzed")
            .endObject()
            // latestVolume --- 现手
            .startObject("latestVolume").field("type", "double")
            .endObject()
            // open
            .startObject("open").field("type", "double")
            .endObject()
            // lastClose
            .startObject("lastClose").field("type", "double")
            .endObject()
            // high
            .startObject("high").field("type", "double")
            .endObject()
            // low
            .startObject("low").field("type", "double")
            .endObject()
            // buy
            .startObject("buy").field("type", "double")
            .endObject()
            // sell
            .startObject("sell").field("type", "double")
            .endObject()
            // mfNetFactor
            .startObject("mfNetFactor").field("type", "double")
            .endObject()
            // mfAmount
            .startObject("mfAmount").field("type", "double")
            .endObject()
            // iiNumber
            .startObject("iiNumber").field("type", "double")
            .endObject()
            // dpe
            .startObject("dpe").field("type", "double")
            .endObject()
            // pb
            .startObject("pb").field("type", "double")
            .endObject()
            // buyVolume
            .startObject("buyVolume").field("type", "double")
            .endObject()
            // sellVolume
            .startObject("sellVolume").field("type", "double")
            .endObject()
            // delegateRatio
            .startObject("delegateRatio").field("type", "double")
            .endObject()
            // amplitude
            .startObject("amplitude").field("type", "double")
            .endObject()
            // amount
            .startObject("amount").field("type", "double")
            .endObject()
            // amountPerDeal
            .startObject("amountPerDeal").field("type", "double")
            .endObject()
            // dealNumber
            .startObject("dealNumber").field("type", "double")
            .endObject()
            // averageSharePerDeal
            .startObject("averageSharePerDeal").field("type", "double")
            .endObject()
            // averagePrice
            .startObject("averagePrice").field("type", "double")
            .endObject()
            // outVolume
            .startObject("outVolume").field("type", "double")
            .endObject()
            // inVolume
            .startObject("inVolume").field("type", "double")
            .endObject()
            // totalMarketCapital
            .startObject("totalMarketCapital").field("type", "double")
            .endObject()
            // circulationMarketCapital
            .startObject("circulationMarketCapital").field("type", "double")
            .endObject()
            // indexContribution
            .startObject("indexContribution").field("type", "double")
            .endObject()
            // star
            .startObject("star").field("type", "double")
            .endObject()
            // delegateDiff
            .startObject("delegateDiff").field("type", "double")
            .endObject()
            // star
            .startObject("star").field("type", "double")
            .endObject()
            // orgTrend
            .startObject("orgTrend").field("type", "double")
            .endObject()
            // openPercentage
            .startObject("openPercentage").field("type", "double")
            .endObject()
            // barPercentage
            .startObject("barPercentage").field("type", "double")
            .endObject()
            // totalShares
            .startObject("totalShares").field("type", "double")
            .endObject()
            // circulationShares
            .startObject("circulationShares").field("type", "double")
            .endObject()
            // circulationAShares
            .startObject("circulationAShares").field("type", "double")
            .endObject()
            // circulationBShares
            .startObject("circulationBShares").field("type", "double")
            .endObject()
            // circulationRatio
            .startObject("circulationRatio").field("type", "double")
            .endObject()
            // shareholders
            .startObject("shareholders").field("type", "integer")
            .endObject()
            // sharePerHolder
            .startObject("sharePerHolder").field("type", "integer")
            .endObject()
            // pe
            .startObject("pe").field("type", "double")
            .endObject()
            // totalProfit
            .startObject("totalProfit").field("type", "double")
            .endObject()
            // netProfit
            .startObject("netProfit").field("type", "double")
            .endObject()
            // profitIncreasePercentage
            .startObject("profitIncreasePercentage").field("type", "double")
            .endObject()
            // eps
            .startObject("eps").field("type", "double")
            .endObject()
            // navps
            .startObject("navps").field("type", "double")
            .endObject()
            // pfps
            .startObject("pfps").field("type", "double")
            .endObject()
            // totalAssetAmount
            .startObject("totalAssetAmount").field("type", "double")
            .endObject()
            // currentAssetAmount
            .startObject("currentAssetAmount").field("type", "double")
            .endObject()
            // fixedAssetAmount
            .startObject("fixedAssetAmount").field("type", "double")
            .endObject()
            // intangibleAssetAmount
            .startObject("intangibleAssetAmount").field("type", "double")
            .endObject()
            // por
            .startObject("por").field("type", "double")
            .endObject()
            // popr
            .startObject("popr").field("type", "double")
            .endObject()
            // roe
            .startObject("roe").field("type", "double")
            .endObject()
            // alr
            .startObject("alr").field("type", "double")
            .endObject()
            // totalLiabilities
            .startObject("totalLiabilities").field("type", "double")
            .endObject()
            // tse
            .startObject("tse").field("type", "double")
            .endObject()
            // pf
            .startObject("pf").field("type", "double")
            .endObject()
            // roir
            .startObject("roir").field("type", "double")
            .endObject()
            // netCapitalIn
            .startObject("netCapitalIn").field("type", "double")
            .endObject()
            // bigCapitalIn
            .startObject("bigCapitalIn").field("type", "double")
            .endObject()
            // bigCapitalOut
            .startObject("bigCapitalOut").field("type", "double")
            .endObject()
            // bigNetCapital
            .startObject("bigNetCapital").field("type", "double")
            .endObject()
            // bigNetCapitalPercentage
            .startObject("bigNetCapitalPercentage").field("type", "double")
            .endObject()
            // bigCapitalTotal
            .startObject("bigCapitalTotal").field("type", "double")
            .endObject()
            // bigCapitalTotalPercentage
            .startObject("bigCapitalTotalPercentage").field("type", "double")
            .endObject()
            // midCapitalIn
            .startObject("midCapitalIn").field("type", "double")
            .endObject()
            // midCapitalOut
            .startObject("midCapitalOut").field("type", "double")
            .endObject()
            // midNetCapital
            .startObject("midNetCapital").field("type", "double")
            .endObject()
            // midNetCapitalPercentage
            .startObject("midNetCapitalPercentage").field("type", "double")
            .endObject()
            // midCapitalTotal
            .startObject("midCapitalTotal").field("type", "double")
            .endObject()
            // midCapitalTotalPercentage
            .startObject("midCapitalTotalPercentage").field("type", "double")
            .endObject()
            // smallCapitalIn
            .startObject("smallCapitalIn").field("type", "double")
            .endObject()
            // smallCapitalOut
            .startObject("smallCapitalOut").field("type", "double")
            .endObject()
            // smallNetCapital
            .startObject("smallNetCapital").field("type", "double")
            .endObject()
            // smallNetCapitalPercentage
            .startObject("smallNetCapitalPercentage").field("type", "double")
            .endObject()
            // smallCapitalTotal
            .startObject("smallCapitalTotal").field("type", "double")
            .endObject()
            // smallCapitalTotalPercentage
            .startObject("smallCapitalTotalPercentage").field("type", "double")
            .endObject()
            // oneIncPercentage
            .startObject("oneIncPercentage").field("type", "double")
            .endObject()
            // oneIncRank
            .startObject("oneIncRank").field("type", "integer")
            .endObject()
            // oneIncChange
            .startObject("onePricePercentage").field("type", "double")
            .endObject()
            // twoIncPercentage
            .startObject("twoIncPercentage").field("type", "double")
            .endObject()
            // twoIncRank
            .startObject("twoIncRank").field("type", "integer")
            .endObject()
            // twoIncChange
            .startObject("twoPricePercentage").field("type", "double")
            .endObject()
            // threeIncPercentage
            .startObject("threeIncPercentage").field("type", "double")
            .endObject()
            // threeIncRank
            .startObject("threeIncRank").field("type", "integer")
            .endObject()
            // threeIncChange
            .startObject("threePricePercentage").field("type", "double")
            .endObject()
            // fiveIncPercentage
            .startObject("fiveIncPercentage").field("type", "double")
            .endObject()
            // fiveIncRank
            .startObject("fiveIncRank").field("type", "integer")
            .endObject()
            // fiveIncChange
            .startObject("fivePricePercentage").field("type", "double")
            .endObject()
            // tenIncPercentage
            .startObject("tenIncPercentage").field("type", "double")
            .endObject()
            // tenIncRank
            .startObject("tenIncRank").field("type", "integer")
            .endObject()
            // tenIncChange
            .startObject("tenPricePercentage").field("type", "double")
            .endObject()
            .endObject().endObject()
            .endObject();

    return builder;
  }

  private static void importStocks(Client client, List<StockSymbol> stocks) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();

    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    stocks.forEach(stock -> {
      String json;
      try {
        json = mapper.writeValueAsString(stock);
        bulkRequest.add(client.prepareIndex(ElasticsearchConsts.INDEX_STOCK,
            ElasticsearchConsts.TYPE_SYMBOL, stock.getSymbol()).setSource(json));
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
