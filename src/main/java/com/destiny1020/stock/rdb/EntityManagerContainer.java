package com.destiny1020.stock.rdb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerContainer {

  public static EntityManagerFactory EMF;
  public static EntityManager EM;

  static {
    EMF = Persistence.createEntityManagerFactory("stock");
    EM = EMF.createEntityManager();
  }

}
