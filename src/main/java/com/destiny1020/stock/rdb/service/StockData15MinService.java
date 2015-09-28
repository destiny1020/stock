package com.destiny1020.stock.rdb.service;

import java.util.Date;

import javax.persistence.EntityManager;

import com.destiny1020.stock.rdb.EntityManagerContainer;
import com.destiny1020.stock.rdb.model.StockData15Min;
import com.destiny1020.stock.util.DateUtil;

/**
 * DAO layer for the service toward table data_15min.
 * 
 * @author destiny1020
 *
 */
public class StockData15MinService {

  public static StockData15MinService INSTANCE = new StockData15MinService();

  private static EntityManager em;

  private StockData15MinService() {
    em = EntityManagerContainer.EM;
  }

  /**
   * Get the lastest available date from the data_daily table.
   * 
   * @param code
   * @return
   */
  public String latestDate(String code) {
    Date maxDate =
        em.createNamedQuery(StockData15Min.FIND_MAX_DATE_BY_CODE, Date.class)
            .setParameter("code", code).getSingleResult();


    return DateUtil.getNextDate(maxDate);
  }

  public static void main(String[] args) {
    System.out.println(StockData15MinService.INSTANCE.latestDate("600886"));
  }

}
