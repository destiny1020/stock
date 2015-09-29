package com.destiny1020.stock.rdb.service;

import java.util.Date;

import javax.persistence.EntityManager;

import com.destiny1020.stock.rdb.EntityManagerContainer;
import com.destiny1020.stock.rdb.model.StockDataWeek;
import com.destiny1020.stock.util.DateUtil;

/**
 * DAO layer for the service toward table data_week.
 * 
 * @author destiny1020
 *
 */
public class StockDataWeekService {

  public static StockDataWeekService INSTANCE = new StockDataWeekService();

  private static EntityManager em;

  private StockDataWeekService() {
    em = EntityManagerContainer.EM;
  }

  /**
   * Get the lastest available date from the data_week table.
   * 
   * @param code
   * @return
   */
  public String latestDate(String code) {
    Date maxDate =
        em.createNamedQuery(StockDataWeek.FIND_MAX_DATE_BY_CODE, Date.class)
            .setParameter("code", code).getSingleResult();


    return DateUtil.getNextDate(maxDate);
  }

  public static void main(String[] args) {
    System.out.println(StockDataWeekService.INSTANCE.latestDate("600886"));
  }

}
