package com.destiny1020.stock.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtil {

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Get the next date based on current passed date.
   * 
   * @param date
   * @return
   */
  public static String getNextDate(Date date) {
    String latestDate;
    if (date != null) {
      latestDate = SDF.format(date);
    } else {
      return "2000-01-01";
    }

    LocalDate ld = LocalDate.parse(latestDate);
    return ld.plusDays(1).toString();
  }

}
