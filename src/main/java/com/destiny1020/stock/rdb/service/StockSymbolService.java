package com.destiny1020.stock.rdb.service;

import javax.persistence.EntityManager;

import com.destiny1020.stock.model.StockSymbol;
import com.destiny1020.stock.rdb.EntityManagerContainer;

public class StockSymbolService {

  public static StockSymbolService INSTANCE = new StockSymbolService();

  private static EntityManager em;

  private StockSymbolService() {
    em = EntityManagerContainer.EM;
  }

  /**
   * Get symbol instance by symbol string.
   * 
   * @param symbol
   * @return
   */
  public StockSymbol getSymbol(String symbol) {
    return em.createNamedQuery(StockSymbol.FIND_BY_SYMBOL, StockSymbol.class)
        .setParameter("symbol", symbol).getSingleResult();
  }

}
