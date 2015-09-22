package com.destiny1020.stock.rdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "concept_data")
@NamedQueries({@NamedQuery(name = StockConceptData.FIND_ALL, query = StockConceptData.FIND_ALL_SQL)})
public class StockConceptData {

  public static final String FIND_ALL = "findConceptDataAll";
  public static final String FIND_ALL_SQL = "select c from StockConceptData c";

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "index", updatable = false)
  private Long index;

  @Column(name = "code", nullable = false, length = 10)
  private String code;

  @Column(name = "name", nullable = false, length = 20)
  private String name;

  @Column(name = "c_name", nullable = false, length = 20)
  private String c_name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getC_name() {
    return c_name;
  }

  public void setC_name(String c_name) {
    this.c_name = c_name;
  }

  public Long getIndex() {
    return index;
  }

}
