package com.destiny1020.stock.rdb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the data_60min database table.
 * 
 */
@Entity
@Table(name = "data_30min")
@NamedQueries({
    @NamedQuery(name = StockData15Min.FIND_ALL_BY_CODE, query = StockData15Min.FIND_ALL_BY_CODE_SQL),
    @NamedQuery(name = StockData15Min.FIND_MAX_DATE_BY_CODE,
        query = StockData15Min.FIND_MAX_DATE_BY_CODE_SQL)})
public class StockData15Min implements Serializable {
  private static final long serialVersionUID = 1L;

  public static final String FIND_ALL_BY_CODE = "StockData15Min.findAllByCode";
  public static final String FIND_ALL_BY_CODE_SQL =
      "SELECT s FROM StockData15Min s WHERE s.code = :code";

  public static final String FIND_MAX_DATE_BY_CODE = "StockData15Min.findMaxDateByCode";
  public static final String FIND_MAX_DATE_BY_CODE_SQL =
      "SELECT max(dateOnly) FROM StockData15Min s WHERE s.code = :code";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private double bl25;

  private double bl55;

  private double bl99;

  private double bu25;

  private double bu55;

  private double bu99;

  private double close;

  private String code;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  @Temporal(TemporalType.DATE)
  @Column(name = "date_only")
  private Date dateOnly;

  @Column(name = "`dea-10-20-5`")
  private double dea_10_20_5;

  @Column(name = "`dea-12-26-9`")
  private double dea_12_26_9;

  @Column(name = "`dea-9-12-6`")
  private double dea_9_12_6;

  @Column(name = "`deviate-bl25`")
  private double deviate_bl25;

  @Column(name = "`deviate-bl55`")
  private double deviate_bl55;

  @Column(name = "`deviate-bl99`")
  private double deviate_bl99;

  @Column(name = "`deviate-bu25`")
  private double deviate_bu25;

  @Column(name = "`deviate-bu55`")
  private double deviate_bu55;

  @Column(name = "`deviate-bu99`")
  private double deviate_bu99;

  @Column(name = "`deviate-ema17`")
  private double deviate_ema17;

  @Column(name = "`deviate-ema34`")
  private double deviate_ema34;

  @Column(name = "`deviate-ema55`")
  private double deviate_ema55;

  @Column(name = "`deviate-ma10`")
  private double deviate_ma10;

  @Column(name = "`deviate-ma15`")
  private double deviate_ma15;

  @Column(name = "`deviate-ma20`")
  private double deviate_ma20;

  @Column(name = "`deviate-ma25`")
  private double deviate_ma25;

  @Column(name = "`deviate-ma30`")
  private double deviate_ma30;

  @Column(name = "`deviate-ma5`")
  private double deviate_ma5;

  @Column(name = "`dif-10-20-5`")
  private double dif_10_20_5;

  @Column(name = "`dif-12-26-9`")
  private double dif_12_26_9;

  @Column(name = "`dif-9-12-6`")
  private double dif_9_12_6;

  private double ema10;

  private double ema12;

  private double ema17;

  private double ema20;

  private double ema26;

  private double ema34;

  private double ema55;

  private double ema9;

  private double high;

  private double low;

  private double ma10;

  private double ma120;

  private double ma15;

  private double ma20;

  private double ma25;

  private double ma250;

  private double ma30;

  private double ma5;

  private double ma55;

  private double ma60;

  private double ma99;

  @Column(name = "`macd-10-20-5`")
  private double macd_10_20_5;

  @Column(name = "`macd-12-26-9`")
  private double macd_12_26_9;

  @Column(name = "`macd-9-12-6`")
  private double macd_9_12_6;

  private double max10;

  private double max120;

  private double max20;

  private double max25;

  private double max250;

  private double max30;

  private double max5;

  private double max55;

  private double max60;

  private double max99;

  private double min10;

  private double min120;

  private double min20;

  private double min25;

  private double min250;

  private double min30;

  private double min5;

  private double min55;

  private double min60;

  private double min99;

  private double open;

  @Column(name = "p_change")
  private double pChange;

  @Column(name = "p_change10")
  private double pChange10;

  @Column(name = "p_change15")
  private double pChange15;

  @Column(name = "p_change2")
  private double pChange2;

  @Column(name = "p_change20")
  private double pChange20;

  @Column(name = "p_change3")
  private double pChange3;

  @Column(name = "p_change30")
  private double pChange30;

  @Column(name = "p_change5")
  private double pChange5;

  @Enumerated(EnumType.STRING)
  @Column(name = "period_type", length = 5)
  private PeriodType periodType;

  @Column(name = "price_change")
  private double priceChange;

  private Long sequence;

  private double turnover;

  @Column(name = "v_ma10")
  private double vMa10;

  @Column(name = "v_ma20")
  private double vMa20;

  @Column(name = "v_ma5")
  private double vMa5;

  private double volume;

  public StockData15Min() {}

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double getBl25() {
    return this.bl25;
  }

  public void setBl25(double bl25) {
    this.bl25 = bl25;
  }

  public double getBl55() {
    return this.bl55;
  }

  public void setBl55(double bl55) {
    this.bl55 = bl55;
  }

  public double getBl99() {
    return this.bl99;
  }

  public void setBl99(double bl99) {
    this.bl99 = bl99;
  }

  public double getBu25() {
    return this.bu25;
  }

  public void setBu25(double bu25) {
    this.bu25 = bu25;
  }

  public double getBu55() {
    return this.bu55;
  }

  public void setBu55(double bu55) {
    this.bu55 = bu55;
  }

  public double getBu99() {
    return this.bu99;
  }

  public void setBu99(double bu99) {
    this.bu99 = bu99;
  }

  public double getClose() {
    return this.close;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Date getDateOnly() {
    return this.dateOnly;
  }

  public void setDateOnly(Date dateOnly) {
    this.dateOnly = dateOnly;
  }

  public double getDea_10_20_5() {
    return this.dea_10_20_5;
  }

  public void setDea_10_20_5(double dea_10_20_5) {
    this.dea_10_20_5 = dea_10_20_5;
  }

  public double getDea_12_26_9() {
    return this.dea_12_26_9;
  }

  public void setDea_12_26_9(double dea_12_26_9) {
    this.dea_12_26_9 = dea_12_26_9;
  }

  public double getDea_9_12_6() {
    return this.dea_9_12_6;
  }

  public void setDea_9_12_6(double dea_9_12_6) {
    this.dea_9_12_6 = dea_9_12_6;
  }

  public double getDeviate_bl25() {
    return this.deviate_bl25;
  }

  public void setDeviate_bl25(double deviate_bl25) {
    this.deviate_bl25 = deviate_bl25;
  }

  public double getDeviate_bl55() {
    return this.deviate_bl55;
  }

  public void setDeviate_bl55(double deviate_bl55) {
    this.deviate_bl55 = deviate_bl55;
  }

  public double getDeviate_bl99() {
    return this.deviate_bl99;
  }

  public void setDeviate_bl99(double deviate_bl99) {
    this.deviate_bl99 = deviate_bl99;
  }

  public double getDeviate_bu25() {
    return this.deviate_bu25;
  }

  public void setDeviate_bu25(double deviate_bu25) {
    this.deviate_bu25 = deviate_bu25;
  }

  public double getDeviate_bu55() {
    return this.deviate_bu55;
  }

  public void setDeviate_bu55(double deviate_bu55) {
    this.deviate_bu55 = deviate_bu55;
  }

  public double getDeviate_bu99() {
    return this.deviate_bu99;
  }

  public void setDeviate_bu99(double deviate_bu99) {
    this.deviate_bu99 = deviate_bu99;
  }

  public double getDeviate_ema17() {
    return this.deviate_ema17;
  }

  public void setDeviate_ema17(double deviate_ema17) {
    this.deviate_ema17 = deviate_ema17;
  }

  public double getDeviate_ema34() {
    return this.deviate_ema34;
  }

  public void setDeviate_ema34(double deviate_ema34) {
    this.deviate_ema34 = deviate_ema34;
  }

  public double getDeviate_ema55() {
    return this.deviate_ema55;
  }

  public void setDeviate_ema55(double deviate_ema55) {
    this.deviate_ema55 = deviate_ema55;
  }

  public double getDeviate_ma10() {
    return this.deviate_ma10;
  }

  public void setDeviate_ma10(double deviate_ma10) {
    this.deviate_ma10 = deviate_ma10;
  }

  public double getDeviate_ma15() {
    return this.deviate_ma15;
  }

  public void setDeviate_ma15(double deviate_ma15) {
    this.deviate_ma15 = deviate_ma15;
  }

  public double getDeviate_ma20() {
    return this.deviate_ma20;
  }

  public void setDeviate_ma20(double deviate_ma20) {
    this.deviate_ma20 = deviate_ma20;
  }

  public double getDeviate_ma25() {
    return this.deviate_ma25;
  }

  public void setDeviate_ma25(double deviate_ma25) {
    this.deviate_ma25 = deviate_ma25;
  }

  public double getDeviate_ma30() {
    return this.deviate_ma30;
  }

  public void setDeviate_ma30(double deviate_ma30) {
    this.deviate_ma30 = deviate_ma30;
  }

  public double getDeviate_ma5() {
    return this.deviate_ma5;
  }

  public void setDeviate_ma5(double deviate_ma5) {
    this.deviate_ma5 = deviate_ma5;
  }

  public double getDif_10_20_5() {
    return this.dif_10_20_5;
  }

  public void setDif_10_20_5(double dif_10_20_5) {
    this.dif_10_20_5 = dif_10_20_5;
  }

  public double getDif_12_26_9() {
    return this.dif_12_26_9;
  }

  public void setDif_12_26_9(double dif_12_26_9) {
    this.dif_12_26_9 = dif_12_26_9;
  }

  public double getDif_9_12_6() {
    return this.dif_9_12_6;
  }

  public void setDif_9_12_6(double dif_9_12_6) {
    this.dif_9_12_6 = dif_9_12_6;
  }

  public double getEma10() {
    return this.ema10;
  }

  public void setEma10(double ema10) {
    this.ema10 = ema10;
  }

  public double getEma12() {
    return this.ema12;
  }

  public void setEma12(double ema12) {
    this.ema12 = ema12;
  }

  public double getEma17() {
    return this.ema17;
  }

  public void setEma17(double ema17) {
    this.ema17 = ema17;
  }

  public double getEma20() {
    return this.ema20;
  }

  public void setEma20(double ema20) {
    this.ema20 = ema20;
  }

  public double getEma26() {
    return this.ema26;
  }

  public void setEma26(double ema26) {
    this.ema26 = ema26;
  }

  public double getEma34() {
    return this.ema34;
  }

  public void setEma34(double ema34) {
    this.ema34 = ema34;
  }

  public double getEma55() {
    return this.ema55;
  }

  public void setEma55(double ema55) {
    this.ema55 = ema55;
  }

  public double getEma9() {
    return this.ema9;
  }

  public void setEma9(double ema9) {
    this.ema9 = ema9;
  }

  public double getHigh() {
    return this.high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return this.low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getMa10() {
    return this.ma10;
  }

  public void setMa10(double ma10) {
    this.ma10 = ma10;
  }

  public double getMa120() {
    return this.ma120;
  }

  public void setMa120(double ma120) {
    this.ma120 = ma120;
  }

  public double getMa15() {
    return this.ma15;
  }

  public void setMa15(double ma15) {
    this.ma15 = ma15;
  }

  public double getMa20() {
    return this.ma20;
  }

  public void setMa20(double ma20) {
    this.ma20 = ma20;
  }

  public double getMa25() {
    return this.ma25;
  }

  public void setMa25(double ma25) {
    this.ma25 = ma25;
  }

  public double getMa250() {
    return this.ma250;
  }

  public void setMa250(double ma250) {
    this.ma250 = ma250;
  }

  public double getMa30() {
    return this.ma30;
  }

  public void setMa30(double ma30) {
    this.ma30 = ma30;
  }

  public double getMa5() {
    return this.ma5;
  }

  public void setMa5(double ma5) {
    this.ma5 = ma5;
  }

  public double getMa55() {
    return this.ma55;
  }

  public void setMa55(double ma55) {
    this.ma55 = ma55;
  }

  public double getMa60() {
    return this.ma60;
  }

  public void setMa60(double ma60) {
    this.ma60 = ma60;
  }

  public double getMa99() {
    return this.ma99;
  }

  public void setMa99(double ma99) {
    this.ma99 = ma99;
  }

  public double getMacd_10_20_5() {
    return this.macd_10_20_5;
  }

  public void setMacd_10_20_5(double macd_10_20_5) {
    this.macd_10_20_5 = macd_10_20_5;
  }

  public double getMacd_12_26_9() {
    return this.macd_12_26_9;
  }

  public void setMacd_12_26_9(double macd_12_26_9) {
    this.macd_12_26_9 = macd_12_26_9;
  }

  public double getMacd_9_12_6() {
    return this.macd_9_12_6;
  }

  public void setMacd_9_12_6(double macd_9_12_6) {
    this.macd_9_12_6 = macd_9_12_6;
  }

  public double getMax10() {
    return this.max10;
  }

  public void setMax10(double max10) {
    this.max10 = max10;
  }

  public double getMax120() {
    return this.max120;
  }

  public void setMax120(double max120) {
    this.max120 = max120;
  }

  public double getMax20() {
    return this.max20;
  }

  public void setMax20(double max20) {
    this.max20 = max20;
  }

  public double getMax25() {
    return this.max25;
  }

  public void setMax25(double max25) {
    this.max25 = max25;
  }

  public double getMax250() {
    return this.max250;
  }

  public void setMax250(double max250) {
    this.max250 = max250;
  }

  public double getMax30() {
    return this.max30;
  }

  public void setMax30(double max30) {
    this.max30 = max30;
  }

  public double getMax5() {
    return this.max5;
  }

  public void setMax5(double max5) {
    this.max5 = max5;
  }

  public double getMax55() {
    return this.max55;
  }

  public void setMax55(double max55) {
    this.max55 = max55;
  }

  public double getMax60() {
    return this.max60;
  }

  public void setMax60(double max60) {
    this.max60 = max60;
  }

  public double getMax99() {
    return this.max99;
  }

  public void setMax99(double max99) {
    this.max99 = max99;
  }

  public double getMin10() {
    return this.min10;
  }

  public void setMin10(double min10) {
    this.min10 = min10;
  }

  public double getMin120() {
    return this.min120;
  }

  public void setMin120(double min120) {
    this.min120 = min120;
  }

  public double getMin20() {
    return this.min20;
  }

  public void setMin20(double min20) {
    this.min20 = min20;
  }

  public double getMin25() {
    return this.min25;
  }

  public void setMin25(double min25) {
    this.min25 = min25;
  }

  public double getMin250() {
    return this.min250;
  }

  public void setMin250(double min250) {
    this.min250 = min250;
  }

  public double getMin30() {
    return this.min30;
  }

  public void setMin30(double min30) {
    this.min30 = min30;
  }

  public double getMin5() {
    return this.min5;
  }

  public void setMin5(double min5) {
    this.min5 = min5;
  }

  public double getMin55() {
    return this.min55;
  }

  public void setMin55(double min55) {
    this.min55 = min55;
  }

  public double getMin60() {
    return this.min60;
  }

  public void setMin60(double min60) {
    this.min60 = min60;
  }

  public double getMin99() {
    return this.min99;
  }

  public void setMin99(double min99) {
    this.min99 = min99;
  }

  public double getOpen() {
    return this.open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getPChange() {
    return this.pChange;
  }

  public void setPChange(double pChange) {
    this.pChange = pChange;
  }

  public double getPChange10() {
    return this.pChange10;
  }

  public void setPChange10(double pChange10) {
    this.pChange10 = pChange10;
  }

  public double getPChange15() {
    return this.pChange15;
  }

  public void setPChange15(double pChange15) {
    this.pChange15 = pChange15;
  }

  public double getPChange2() {
    return this.pChange2;
  }

  public void setPChange2(double pChange2) {
    this.pChange2 = pChange2;
  }

  public double getPChange20() {
    return this.pChange20;
  }

  public void setPChange20(double pChange20) {
    this.pChange20 = pChange20;
  }

  public double getPChange3() {
    return this.pChange3;
  }

  public void setPChange3(double pChange3) {
    this.pChange3 = pChange3;
  }

  public double getPChange30() {
    return this.pChange30;
  }

  public void setPChange30(double pChange30) {
    this.pChange30 = pChange30;
  }

  public double getPChange5() {
    return this.pChange5;
  }

  public void setPChange5(double pChange5) {
    this.pChange5 = pChange5;
  }

  public PeriodType getPeriodType() {
    return this.periodType;
  }

  public void setPeriodType(PeriodType periodType) {
    this.periodType = periodType;
  }

  public double getPriceChange() {
    return this.priceChange;
  }

  public void setPriceChange(double priceChange) {
    this.priceChange = priceChange;
  }

  public Long getSequence() {
    return this.sequence;
  }

  public void setSequence(Long sequence) {
    this.sequence = sequence;
  }

  public double getTurnover() {
    return this.turnover;
  }

  public void setTurnover(double turnover) {
    this.turnover = turnover;
  }

  public double getVMa10() {
    return this.vMa10;
  }

  public void setVMa10(double vMa10) {
    this.vMa10 = vMa10;
  }

  public double getVMa20() {
    return this.vMa20;
  }

  public void setVMa20(double vMa20) {
    this.vMa20 = vMa20;
  }

  public double getVMa5() {
    return this.vMa5;
  }

  public void setVMa5(double vMa5) {
    this.vMa5 = vMa5;
  }

  public double getVolume() {
    return this.volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }

}
