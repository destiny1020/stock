package com.destiny1020.stock.common.crawler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class CrawlerUtils {

  private static final Logger LOGGER = LogManager.getLogger(CrawlerUtils.class);

  public synchronized static Connection getConnection(String url) {
    // rest for 3s to avoid ip-banning
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      LOGGER.error(e.getMessage());
    }

    return Jsoup.connect(url).timeout(60000)
        .userAgent("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
        .header("accept", "application/json");
  }

}
