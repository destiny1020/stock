package com.destiny1020.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.destiny1020.stock.es.IEsIDEntity;

@Entity
@Table(name = "symbol")
@NamedQueries({@NamedQuery(name = StockSymbol.FIND_ALL, query = StockSymbol.FIND_ALL_SQL),
    @NamedQuery(name = StockSymbol.FIND_BY_SYMBOL, query = StockSymbol.FIND_BY_SYMBOL_SQL),
    @NamedQuery(name = StockSymbol.FIND_BY_SYMBOLS, query = StockSymbol.FIND_BY_SYMBOLS_SQL),
    @NamedQuery(name = StockSymbol.FIND_BY_IDX, query = StockSymbol.FIND_BY_IDX_SQL)})
public class StockSymbol implements IEsIDEntity {

  public static final String FIND_ALL = "StockSymbol.findAll";
  public static final String FIND_ALL_SQL = "select s from StockSymbol s";

  public static final String FIND_BY_SYMBOL = "StockSymbol.findBySymbol";
  public static final String FIND_BY_SYMBOL_SQL =
      "select s from StockSymbol s where s.symbol = :symbol";

  public static final String FIND_BY_SYMBOLS = "StockSymbol.findBySymbols";
  public static final String FIND_BY_SYMBOLS_SQL =
      "select s from StockSymbol s where s.symbol in :symbols";

  public static final String FIND_BY_IDX = "StockSymbol.findByIdx";
  public static final String FIND_BY_IDX_SQL = "select s from StockSymbol s where s.idx = 1";

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

  /**
   * 是否是指数
   */
  @Column(name = "IDX")
  private Boolean idx;

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

  public boolean isNotCYB() {
    return !code.startsWith("30");
  }

  public Boolean getIdx() {
    return idx;
  }

  public void setIdx(Boolean idx) {
    this.idx = idx;
  }

}
