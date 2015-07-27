package com.destiny1020.stock.xueqiu.crawler;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Stores the cookies for XQ.
 * 
 * @author Administrator
 *
 */
public class StockCookieStore {

  public static final CookieStore DEFAULT_COOKIE_STORE;

  static {
    DEFAULT_COOKIE_STORE = new BasicCookieStore();
    BasicClientCookie cookie = new BasicClientCookie("u", "9944872366");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xq_a_token", "dd3c531416c4f9efd35f3898e33e9f6f572e3757");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xq_is_login", "1");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xq_r_token", "fbba932b8b038bc1c36a358e6d2b414c6fa5b9b6");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie =
        new BasicClientCookie("xq_token_expire",
            "Mon%20Aug%2010%202015%2019%3A11%3A47%20GMT%2B0800%20(CST)");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);

    cookie = new BasicClientCookie("xqat", "dd3c531416c4f9efd35f3898e33e9f6f572e3757");
    cookie.setDomain("xueqiu.com");
    DEFAULT_COOKIE_STORE.addCookie(cookie);
  }

}
