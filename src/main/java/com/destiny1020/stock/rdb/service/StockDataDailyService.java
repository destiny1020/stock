package com.destiny1020.stock.rdb.service;

import java.util.Date;

import javax.persistence.EntityManager;

import com.destiny1020.stock.rdb.EntityManagerContainer;
import com.destiny1020.stock.rdb.model.StockDataDaily;
import com.destiny1020.stock.util.DateUtil;

/**
 * DAO layer for the service toward table data_daily.
 * 
 * @author destiny1020
 *
 */
public class StockDataDailyService {

  public static StockDataDailyService INSTANCE = new StockDataDailyService();

  private static EntityManager em;

  private StockDataDailyService() {
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
        em.createNamedQuery(StockDataDaily.FIND_MAX_DATE_BY_CODE, Date.class)
            .setParameter("code", code).getSingleResult();


    return DateUtil.getNextDate(maxDate);
  }

  public static void main(String[] args) {
    System.out.println(StockDataDailyService.INSTANCE.latestDate("600886"));
  }

}
