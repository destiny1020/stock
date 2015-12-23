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
 * The persistent class for the data_week database table.
 * 
 */
@Entity
@Table(name = "data_week_calc")
@NamedQueries({
    @NamedQuery(name = StockDataWeek.FIND_ALL_BY_CODE, query = StockDataWeek.FIND_ALL_BY_CODE_SQL),
    @NamedQuery(name = StockDataWeek.FIND_MAX_DATE_BY_CODE,
        query = StockDataWeek.FIND_MAX_DATE_BY_CODE_SQL)})
public class StockDataWeek implements Serializable {
  private static final long serialVersionUID = 1L;

  public static final String FIND_ALL_BY_CODE = "StockDataWeek.findAllByCode";
  public static final String FIND_ALL_BY_CODE_SQL =
      "SELECT s FROM StockDataWeek s WHERE s.code = :code";

  public static final String FIND_MAX_DATE_BY_CODE = "StockDataWeek.findMaxDateByCode";
  public static final String FIND_MAX_DATE_BY_CODE_SQL =
      "SELECT max(date) FROM StockDataWeek s WHERE s.code = :code";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double bl25;

  private Double bl55;

  private Double bl99;

  private Double bu25;

  private Double bu55;

  private Double bu99;

  private Double close;

  @Column(name = "code", length = 20, nullable = false)
  private String code;

  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "`dea-10-20-5`")
  private Double dea_10_20_5;

  @Column(name = "`dea-12-26-9`")
  private Double dea_12_26_9;

  @Column(name = "`dea-9-12-6`")
  private Double dea_9_12_6;

  @Column(name = "`deviate-bl25`")
  private Double deviate_bl25;

  @Column(name = "`deviate-bl55`")
  private Double deviate_bl55;

  @Column(name = "`deviate-bl99`")
  private Double deviate_bl99;

  @Column(name = "`deviate-bu25`")
  private Double deviate_bu25;

  @Column(name = "`deviate-bu55`")
  private Double deviate_bu55;

  @Column(name = "`deviate-bu99`")
  private Double deviate_bu99;

  @Column(name = "`deviate-ema17`")
  private Double deviate_ema17;

  @Column(name = "`deviate-ema34`")
  private Double deviate_ema34;

  @Column(name = "`deviate-ema55`")
  private Double deviate_ema55;

  @Column(name = "`deviate-ma10`")
  private Double deviate_ma10;

  @Column(name = "`deviate-ma15`")
  private Double deviate_ma15;

  @Column(name = "`deviate-ma20`")
  private Double deviate_ma20;

  @Column(name = "`deviate-ma25`")
  private Double deviate_ma25;

  @Column(name = "`deviate-ma30`")
  private Double deviate_ma30;

  @Column(name = "`deviate-ma5`")
  private Double deviate_ma5;

  @Column(name = "`dif-10-20-5`")
  private Double dif_10_20_5;

  @Column(name = "`dif-12-26-9`")
  private Double dif_12_26_9;

  @Column(name = "`dif-9-12-6`")
  private Double dif_9_12_6;

  private Double ema10;

  private Double ema12;

  private Double ema17;

  private Double ema20;

  private Double ema26;

  private Double ema34;

  private Double ema55;

  private Double ema9;

  private Double high;

  private Double low;

  private Double ma10;

  private Double ma120;

  private Double ma15;

  private Double ma20;

  private Double ma25;

  private Double ma250;

  private Double ma30;

  private Double ma5;

  private Double ma55;

  private Double ma60;

  private Double ma99;

  @Column(name = "`macd-10-20-5`")
  private Double macd_10_20_5;

  @Column(name = "`macd-12-26-9`")
  private Double macd_12_26_9;

  @Column(name = "`macd-9-12-6`")
  private Double macd_9_12_6;

  private Double max10;

  private Double max120;

  private Double max20;

  private Double max25;

  private Double max250;

  private Double max30;

  private Double max5;

  private Double max55;

  private Double max60;

  private Double max99;

  private Double min10;

  private Double min120;

  private Double min20;

  private Double min25;

  private Double min250;

  private Double min30;

  private Double min5;

  private Double min55;

  private Double min60;

  private Double min99;

  private Double open;

  @Column(name = "p_change")
  private Double pChange;

  @Column(name = "p_change10")
  private Double pChange10;

  @Column(name = "p_change15")
  private Double pChange15;

  @Column(name = "p_change2")
  private Double pChange2;

  @Column(name = "p_change20")
  private Double pChange20;

  @Column(name = "p_change3")
  private Double pChange3;

  @Column(name = "p_change30")
  private Double pChange30;

  @Column(name = "p_change5")
  private Double pChange5;

  @Enumerated(EnumType.STRING)
  @Column(name = "period_type", length = 5)
  private PeriodType periodType;

  @Column(name = "price_change")
  private Double priceChange;

  private Long sequence;

  @Column(name = "v_ma10")
  private Double vMa10;

  @Column(name = "v_ma20")
  private Double vMa20;

  @Column(name = "v_ma5")
  private Double vMa5;

  private Double volume;

  public StockDataWeek() {}

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getBl25() {
    return this.bl25;
  }

  public void setBl25(Double bl25) {
    this.bl25 = bl25;
  }

  public Double getBl55() {
    return this.bl55;
  }

  public void setBl55(Double bl55) {
    this.bl55 = bl55;
  }

  public Double getBl99() {
    return this.bl99;
  }

  public void setBl99(Double bl99) {
    this.bl99 = bl99;
  }

  public Double getBu25() {
    return this.bu25;
  }

  public void setBu25(Double bu25) {
    this.bu25 = bu25;
  }

  public Double getBu55() {
    return this.bu55;
  }

  public void setBu55(Double bu55) {
    this.bu55 = bu55;
  }

  public Double getBu99() {
    return this.bu99;
  }

  public void setBu99(Double bu99) {
    this.bu99 = bu99;
  }

  public Double getClose() {
    return this.close;
  }

  public void setClose(Double close) {
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

  public Double getDea_10_20_5() {
    return this.dea_10_20_5;
  }

  public void setDea_10_20_5(Double dea_10_20_5) {
    this.dea_10_20_5 = dea_10_20_5;
  }

  public Double getDea_12_26_9() {
    return this.dea_12_26_9;
  }

  public void setDea_12_26_9(Double dea_12_26_9) {
    this.dea_12_26_9 = dea_12_26_9;
  }

  public Double getDea_9_12_6() {
    return this.dea_9_12_6;
  }

  public void setDea_9_12_6(Double dea_9_12_6) {
    this.dea_9_12_6 = dea_9_12_6;
  }

  public Double getDeviate_bl25() {
    return this.deviate_bl25;
  }

  public void setDeviate_bl25(Double deviate_bl25) {
    this.deviate_bl25 = deviate_bl25;
  }

  public Double getDeviate_bl55() {
    return this.deviate_bl55;
  }

  public void setDeviate_bl55(Double deviate_bl55) {
    this.deviate_bl55 = deviate_bl55;
  }

  public Double getDeviate_bl99() {
    return this.deviate_bl99;
  }

  public void setDeviate_bl99(Double deviate_bl99) {
    this.deviate_bl99 = deviate_bl99;
  }

  public Double getDeviate_bu25() {
    return this.deviate_bu25;
  }

  public void setDeviate_bu25(Double deviate_bu25) {
    this.deviate_bu25 = deviate_bu25;
  }

  public Double getDeviate_bu55() {
    return this.deviate_bu55;
  }

  public void setDeviate_bu55(Double deviate_bu55) {
    this.deviate_bu55 = deviate_bu55;
  }

  public Double getDeviate_bu99() {
    return this.deviate_bu99;
  }

  public void setDeviate_bu99(Double deviate_bu99) {
    this.deviate_bu99 = deviate_bu99;
  }

  public Double getDeviate_ema17() {
    return this.deviate_ema17;
  }

  public void setDeviate_ema17(Double deviate_ema17) {
    this.deviate_ema17 = deviate_ema17;
  }

  public Double getDeviate_ema34() {
    return this.deviate_ema34;
  }

  public void setDeviate_ema34(Double deviate_ema34) {
    this.deviate_ema34 = deviate_ema34;
  }

  public Double getDeviate_ema55() {
    return this.deviate_ema55;
  }

  public void setDeviate_ema55(Double deviate_ema55) {
    this.deviate_ema55 = deviate_ema55;
  }

  public Double getDeviate_ma10() {
    return this.deviate_ma10;
  }

  public void setDeviate_ma10(Double deviate_ma10) {
    this.deviate_ma10 = deviate_ma10;
  }

  public Double getDeviate_ma15() {
    return this.deviate_ma15;
  }

  public void setDeviate_ma15(Double deviate_ma15) {
    this.deviate_ma15 = deviate_ma15;
  }

  public Double getDeviate_ma20() {
    return this.deviate_ma20;
  }

  public void setDeviate_ma20(Double deviate_ma20) {
    this.deviate_ma20 = deviate_ma20;
  }

  public Double getDeviate_ma25() {
    return this.deviate_ma25;
  }

  public void setDeviate_ma25(Double deviate_ma25) {
    this.deviate_ma25 = deviate_ma25;
  }

  public Double getDeviate_ma30() {
    return this.deviate_ma30;
  }

  public void setDeviate_ma30(Double deviate_ma30) {
    this.deviate_ma30 = deviate_ma30;
  }

  public Double getDeviate_ma5() {
    return this.deviate_ma5;
  }

  public void setDeviate_ma5(Double deviate_ma5) {
    this.deviate_ma5 = deviate_ma5;
  }

  public Double getDif_10_20_5() {
    return this.dif_10_20_5;
  }

  public void setDif_10_20_5(Double dif_10_20_5) {
    this.dif_10_20_5 = dif_10_20_5;
  }

  public Double getDif_12_26_9() {
    return this.dif_12_26_9;
  }

  public void setDif_12_26_9(Double dif_12_26_9) {
    this.dif_12_26_9 = dif_12_26_9;
  }

  public Double getDif_9_12_6() {
    return this.dif_9_12_6;
  }

  public void setDif_9_12_6(Double dif_9_12_6) {
    this.dif_9_12_6 = dif_9_12_6;
  }

  public Double getEma10() {
    return this.ema10;
  }

  public void setEma10(Double ema10) {
    this.ema10 = ema10;
  }

  public Double getEma12() {
    return this.ema12;
  }

  public void setEma12(Double ema12) {
    this.ema12 = ema12;
  }

  public Double getEma17() {
    return this.ema17;
  }

  public void setEma17(Double ema17) {
    this.ema17 = ema17;
  }

  public Double getEma20() {
    return this.ema20;
  }

  public void setEma20(Double ema20) {
    this.ema20 = ema20;
  }

  public Double getEma26() {
    return this.ema26;
  }

  public void setEma26(Double ema26) {
    this.ema26 = ema26;
  }

  public Double getEma34() {
    return this.ema34;
  }

  public void setEma34(Double ema34) {
    this.ema34 = ema34;
  }

  public Double getEma55() {
    return this.ema55;
  }

  public void setEma55(Double ema55) {
    this.ema55 = ema55;
  }

  public Double getEma9() {
    return this.ema9;
  }

  public void setEma9(Double ema9) {
    this.ema9 = ema9;
  }

  public Double getHigh() {
    return this.high;
  }

  public void setHigh(Double high) {
    this.high = high;
  }

  public Double getLow() {
    return this.low;
  }

  public void setLow(Double low) {
    this.low = low;
  }

  public Double getMa10() {
    return this.ma10;
  }

  public void setMa10(Double ma10) {
    this.ma10 = ma10;
  }

  public Double getMa120() {
    return this.ma120;
  }

  public void setMa120(Double ma120) {
    this.ma120 = ma120;
  }

  public Double getMa15() {
    return this.ma15;
  }

  public void setMa15(Double ma15) {
    this.ma15 = ma15;
  }

  public Double getMa20() {
    return this.ma20;
  }

  public void setMa20(Double ma20) {
    this.ma20 = ma20;
  }

  public Double getMa25() {
    return this.ma25;
  }

  public void setMa25(Double ma25) {
    this.ma25 = ma25;
  }

  public Double getMa250() {
    return this.ma250;
  }

  public void setMa250(Double ma250) {
    this.ma250 = ma250;
  }

  public Double getMa30() {
    return this.ma30;
  }

  public void setMa30(Double ma30) {
    this.ma30 = ma30;
  }

  public Double getMa5() {
    return this.ma5;
  }

  public void setMa5(Double ma5) {
    this.ma5 = ma5;
  }

  public Double getMa55() {
    return this.ma55;
  }

  public void setMa55(Double ma55) {
    this.ma55 = ma55;
  }

  public Double getMa60() {
    return this.ma60;
  }

  public void setMa60(Double ma60) {
    this.ma60 = ma60;
  }

  public Double getMa99() {
    return this.ma99;
  }

  public void setMa99(Double ma99) {
    this.ma99 = ma99;
  }

  public Double getMacd_10_20_5() {
    return this.macd_10_20_5;
  }

  public void setMacd_10_20_5(Double macd_10_20_5) {
    this.macd_10_20_5 = macd_10_20_5;
  }

  public Double getMacd_12_26_9() {
    return this.macd_12_26_9;
  }

  public void setMacd_12_26_9(Double macd_12_26_9) {
    this.macd_12_26_9 = macd_12_26_9;
  }

  public Double getMacd_9_12_6() {
    return this.macd_9_12_6;
  }

  public void setMacd_9_12_6(Double macd_9_12_6) {
    this.macd_9_12_6 = macd_9_12_6;
  }

  public Double getMax10() {
    return this.max10;
  }

  public void setMax10(Double max10) {
    this.max10 = max10;
  }

  public Double getMax120() {
    return this.max120;
  }

  public void setMax120(Double max120) {
    this.max120 = max120;
  }

  public Double getMax20() {
    return this.max20;
  }

  public void setMax20(Double max20) {
    this.max20 = max20;
  }

  public Double getMax25() {
    return this.max25;
  }

  public void setMax25(Double max25) {
    this.max25 = max25;
  }

  public Double getMax250() {
    return this.max250;
  }

  public void setMax250(Double max250) {
    this.max250 = max250;
  }

  public Double getMax30() {
    return this.max30;
  }

  public void setMax30(Double max30) {
    this.max30 = max30;
  }

  public Double getMax5() {
    return this.max5;
  }

  public void setMax5(Double max5) {
    this.max5 = max5;
  }

  public Double getMax55() {
    return this.max55;
  }

  public void setMax55(Double max55) {
    this.max55 = max55;
  }

  public Double getMax60() {
    return this.max60;
  }

  public void setMax60(Double max60) {
    this.max60 = max60;
  }

  public Double getMax99() {
    return this.max99;
  }

  public void setMax99(Double max99) {
    this.max99 = max99;
  }

  public Double getMin10() {
    return this.min10;
  }

  public void setMin10(Double min10) {
    this.min10 = min10;
  }

  public Double getMin120() {
    return this.min120;
  }

  public void setMin120(Double min120) {
    this.min120 = min120;
  }

  public Double getMin20() {
    return this.min20;
  }

  public void setMin20(Double min20) {
    this.min20 = min20;
  }

  public Double getMin25() {
    return this.min25;
  }

  public void setMin25(Double min25) {
    this.min25 = min25;
  }

  public Double getMin250() {
    return this.min250;
  }

  public void setMin250(Double min250) {
    this.min250 = min250;
  }

  public Double getMin30() {
    return this.min30;
  }

  public void setMin30(Double min30) {
    this.min30 = min30;
  }

  public Double getMin5() {
    return this.min5;
  }

  public void setMin5(Double min5) {
    this.min5 = min5;
  }

  public Double getMin55() {
    return this.min55;
  }

  public void setMin55(Double min55) {
    this.min55 = min55;
  }

  public Double getMin60() {
    return this.min60;
  }

  public void setMin60(Double min60) {
    this.min60 = min60;
  }

  public Double getMin99() {
    return this.min99;
  }

  public void setMin99(Double min99) {
    this.min99 = min99;
  }

  public Double getOpen() {
    return this.open;
  }

  public void setOpen(Double open) {
    this.open = open;
  }

  public Double getPChange() {
    return this.pChange;
  }

  public void setPChange(Double pChange) {
    this.pChange = pChange;
  }

  public Double getPChange10() {
    return this.pChange10;
  }

  public void setPChange10(Double pChange10) {
    this.pChange10 = pChange10;
  }

  public Double getPChange15() {
    return this.pChange15;
  }

  public void setPChange15(Double pChange15) {
    this.pChange15 = pChange15;
  }

  public Double getPChange2() {
    return this.pChange2;
  }

  public void setPChange2(Double pChange2) {
    this.pChange2 = pChange2;
  }

  public Double getPChange20() {
    return this.pChange20;
  }

  public void setPChange20(Double pChange20) {
    this.pChange20 = pChange20;
  }

  public Double getPChange3() {
    return this.pChange3;
  }

  public void setPChange3(Double pChange3) {
    this.pChange3 = pChange3;
  }

  public Double getPChange30() {
    return this.pChange30;
  }

  public void setPChange30(Double pChange30) {
    this.pChange30 = pChange30;
  }

  public Double getPChange5() {
    return this.pChange5;
  }

  public void setPChange5(Double pChange5) {
    this.pChange5 = pChange5;
  }

  public PeriodType getPeriodType() {
    return this.periodType;
  }

  public void setPeriodType(PeriodType periodType) {
    this.periodType = periodType;
  }

  public Double getPriceChange() {
    return this.priceChange;
  }

  public void setPriceChange(Double priceChange) {
    this.priceChange = priceChange;
  }

  public Long getSequence() {
    return this.sequence;
  }

  public void setSequence(Long sequence) {
    this.sequence = sequence;
  }

  public Double getVMa10() {
    return this.vMa10;
  }

  public void setVMa10(Double vMa10) {
    this.vMa10 = vMa10;
  }

  public Double getVMa20() {
    return this.vMa20;
  }

  public void setVMa20(Double vMa20) {
    this.vMa20 = vMa20;
  }

  public Double getVMa5() {
    return this.vMa5;
  }

  public void setVMa5(Double vMa5) {
    this.vMa5 = vMa5;
  }

  public Double getVolume() {
    return this.volume;
  }

  public void setVolume(Double volume) {
    this.volume = volume;
  }

}
