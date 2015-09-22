package com.destiny1020.stock.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  public static String convertToString(Date date) {
    return SDF.format(date);
  }

}
