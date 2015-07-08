package com.destiny1020.stock.xueqiu.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.destiny1020.stock.common.Market;
import com.destiny1020.stock.xueqiu.model.StockFollowersInfo;
import com.destiny1020.stock.xueqiu.model.StockInfoWrapper;
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

  private static final CookieStore DEFAULT_COOKIE_STORE;

  static {
    DEFAULT_COOKIE_STORE = new BasicCookieStore();
    BasicClientCookie cookie = new BasicClientCookie("u", "9944872366");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xq_a_token", "4091ed7f665a8f549b5a632531c3dab9ca0cf285");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xq_is_login", "1");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xq_r_token", "a58f2a7203e7621175464931bbffcb782ef3669c");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie =
        new BasicClientCookie("xq_token_expire",
            "Thu%20Jul%2030%202015%2011%3A40%3A34%20GMT%2B0800%20(CST)");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xqat", "4091ed7f665a8f549b5a632531c3dab9ca0cf285");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);
  }

  private static final String API_FOLLOWS_COUNT =
      "http://xueqiu.com/recommend/pofriends.json?type=1&code=%s%s&start=0&count=0";
  private static final String API_QUOTE = "http://xueqiu.com/v4/stock/quote.json?code=%s";
  private static final String API_HISTORY = "";

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

    String url = String.format(API_FOLLOWS_COUNT, market.name(), code);
    String result = getJsonResponse(url);

    if (result != null) {
      return getStockInfoCore(result, StockFollowersInfo.class);
    }

    return null;
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

  public static void main(String[] args) {
    // System.out.println(getFollowersInfo(Market.SH, "600886"));
    System.out.println(getQuoteInfo(Arrays.asList(Pair.of(Market.SH, "600886"),
        Pair.of(Market.SZ, "000656"))));
  }

  private static <T> T getStockInfoCore(String jsonString, Class<T> prototype) {

    ObjectMapper objectMapper = new ObjectMapper();
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
        HttpClientBuilder.create().setDefaultCookieStore(DEFAULT_COOKIE_STORE).build();
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
