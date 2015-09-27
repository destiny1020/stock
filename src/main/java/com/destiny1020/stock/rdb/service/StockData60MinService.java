package com.destiny1020.stock.rdb.service;

import java.util.Date;

import javax.persistence.EntityManager;

import com.destiny1020.stock.rdb.EntityManagerContainer;
import com.destiny1020.stock.rdb.model.StockData60Min;
import com.destiny1020.stock.util.DateUtil;

/**
 * DAO layer for the service toward table data_60min.
 * 
 * @author destiny1020
 *
 */
public class StockData60MinService {

  public static StockData60MinService INSTANCE = new StockData60MinService();

  private static EntityManager em;

  private StockData60MinService() {
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
        em.createNamedQuery(StockData60Min.FIND_MAX_DATE_BY_CODE, Date.class)
            .setParameter("code", code).getSingleResult();


    return DateUtil.getNextDate(maxDate);
  }

  public static void main(String[] args) {
    System.out.println(StockData60MinService.INSTANCE.latestDate("600886"));
  }

}
