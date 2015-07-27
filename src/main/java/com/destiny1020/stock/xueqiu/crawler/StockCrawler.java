package com.destiny1020.stock.xueqiu.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.destiny1020.stock.common.Market;
import com.destiny1020.stock.xueqiu.model.StockFollowersInfo;
import com.destiny1020.stock.xueqiu.model.StockHistoryWrapper;
import com.destiny1020.stock.xueqiu.model.StockInfoWrapper;
import com.destiny1020.stock.xueqiu.model.StockPeriod;
import com.destiny1020.stock.xueqiu.model.StockQuoteInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Crawl the stock information at page like http://xueqiu.com/S/SH600886
 * 
 * @author Administrator
 *
 */
public class StockCrawler {

  private static final String API_FOLLOWS_COUNT =
      "http://xueqiu.com/recommend/pofriends.json?type=1&code=%s&start=0&count=0";
  private static final String API_QUOTE = "http://xueqiu.com/v4/stock/quote.json?code=%s";
  // Sample: http://xueqiu.com/stock/forchartk/stocklist.json?symbol=SH600886&period=1week&type=before&begin=1391212800000&end=1393632000000
  // 1: symbol, 2: 1day/1week/1month, 3: start millis, 4: end millis
  private static final String API_HISTORY =
      "http://xueqiu.com/stock/forchartk/stocklist.json?symbol=%s&period=%s&type=before";

  public static void main(String[] args) {
    // System.out.println(getFollowersInfo(Market.SH, "600886"));
    //    System.out.println(getQuoteInfo(Arrays.asList(Pair.of(Market.SH, "600886"),
    //        Pair.of(Market.SZ, "000656"))));

    System.out.println(getHistoryWrapper("SH600886"));
  }

  /**
   * Get history K-lines by symbol.
   * Use sensible defaults:
   * 
   *     period = 1day
   *     begin = undefined
   *     end = undefined
   * 
   * @param symbol
   * @return
   */
  public static StockHistoryWrapper getHistoryWrapper(String symbol) {
    String url = String.format(API_HISTORY, symbol.toUpperCase(), StockPeriod.ONE_DAY.getOption());
    String result = getJsonResponse(url);

    if (result != null) {
      return getStockInfoCore(result, StockHistoryWrapper.class);
    }

    return null;
  }

  /**
   * Get Followers Count.
   * 
   * @param symbol
   * @return
   */
  public static StockFollowersInfo getFollowersInfo(String symbol) {
    if (StringUtils.isEmpty(symbol)) {
      return null;
    }

    String url = String.format(API_FOLLOWS_COUNT, symbol);
    String result = getJsonResponse(url);

    if (result != null) {
      return getStockInfoCore(result, StockFollowersInfo.class);
    }

    return null;
  }

  /**
   * Get Followers Count.
   * 
   * @param market
   * @param code
   * @return
   */
  public static StockFollowersInfo getFollowersInfo(Market market, String code) {
    if (market == null || StringUtils.isEmpty(code)) {
      return null;
    }

    String symbol = market.name() + code;
    return getFollowersInfo(symbol);
  }

  /**
   * Get Stock Quote Info.
   * 
   * @param market
   * @param code
   * @return
   */
  public static StockInfoWrapper<StockQuoteInfo> getQuoteInfo(List<Pair<Market, String>> symbols) {
    if (symbols == null || symbols.size() == 0) {
      return null;
    }

    String codes =
        symbols.stream().map(symbol -> symbol.getKey().name() + symbol.getValue())
            .collect(Collectors.joining(","));
    String url = String.format(API_QUOTE, codes);
    String result = getJsonResponse(url);

    if (result != null) {
      return getStockInfoWrapper(result, StockQuoteInfo.class);
    }

    return null;
  }

  private static <T> T getStockInfoCore(String jsonString, Class<T> prototype) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      return (T) objectMapper.readValue(jsonString, prototype);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  private static <T> StockInfoWrapper<T> getStockInfoWrapper(String jsonString, Class<T> prototype) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    StockInfoWrapper<T> wrapper = new StockInfoWrapper<T>();
    try {
      JsonNode root = objectMapper.readTree(jsonString);
      Iterator<Entry<String, JsonNode>> elements = root.fields();

      while (elements.hasNext()) {
        Entry<String, JsonNode> node = elements.next();
        String symbol = node.getKey();
        T element = objectMapper.readValue(node.getValue().toString(), prototype);
        wrapper.addEntry(symbol, element);
      }

      return wrapper;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  private static String getJsonResponse(String url) {
    HttpClient client =
        HttpClientBuilder.create().setDefaultCookieStore(StockCookieStore.DEFAULT_COOKIE_STORE)
            .build();
    HttpGet request = new HttpGet(url);

    // add request header
    request.addHeader("User-Agent",
        "Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0");
    request.addHeader("Host", "xueqiu.com");
    request.addHeader("Origin", "http://xueqiu.com");
    HttpResponse response;
    try {
      response = client.execute(request);
      System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

      BufferedReader rd =
          new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

      StringBuilder result = new StringBuilder();
      String line = "";
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }

      return result.toString();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

}
