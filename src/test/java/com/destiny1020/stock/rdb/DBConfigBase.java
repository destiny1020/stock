package com.destiny1020.stock.rdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class DBConfigBase {
  private static final Logger LOGGER = LogManager.getLogger(DBConfigBase.class);

  protected static EntityManagerFactory emf;
  protected static EntityManager em;

  protected EntityTransaction tx;

  @BeforeClass
  public static void init() {
    emf = Persistence.createEntityManagerFactory("stock");
    em = emf.createEntityManager();
  }

  @Before
  public void beginTransaction() {
    tx = em.getTransaction();
    tx.begin();

    LOGGER.info("Tx starts....");
  }

  @After
  public void rollbackTransaction() {
    if (tx.isActive()) {
      LOGGER.info("Tx commits....");
      tx.commit();
    }
  }

  @AfterClass
  public static void clear() {
    if (em != null) {
      em.close();
    }
    if (emf != null) {
      emf.close();
    }
  }
}
