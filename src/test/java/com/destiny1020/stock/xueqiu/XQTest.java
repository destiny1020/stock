package com.destiny1020.stock.xueqiu;

import org.junit.Test;

import com.destiny1020.stock.common.Market;
import com.destiny1020.stock.xueqiu.crawler.StockCrawler;
import com.destiny1020.stock.xueqiu.model.StockFollowersInfo;

public class XQTest {

  @Test
  public void getFollowersCount() {
    StockFollowersInfo followersInfo = StockCrawler.getFollowersInfo(Market.SH, "600886");
    System.out.println(followersInfo.getTotalcount());
  }

}
