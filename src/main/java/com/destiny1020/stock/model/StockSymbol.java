package com.destiny1020.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.destiny1020.stock.es.IEsIDEntity;

@Entity
@Table(name = "symbol")
public class StockSymbol implements IEsIDEntity {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private int id;

  /**
   * 代码
   */
  @Column(name = "SYMBOL")
  private String symbol;

  /**
   * 略去SH或者SZ前缀，并且将指数的SYMBOL转换为sh这种tushare识别的格式
   */
  @Column(name = "CODE")
  private String code;

  /**
   * 名称
   */
  @Column(name = "NAME")
  private String name;

  public StockSymbol() {

  }

  public StockSymbol(String symbol, String name) {
    super();
    this.symbol = symbol;
    this.name = name;
  }

  public StockSymbol(String symbol, String name, String code) {
    this(symbol, name);
    this.code = code;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getEsID() {
    return symbol.toUpperCase();
  }

  public int getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
