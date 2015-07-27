package com.destiny1020.stock.ths.xls;

import java.util.Date;
import java.util.List;

/**
 * Overall reader for invoking all other readers.
 * 
 * @author Administrator
 *
 */
public class THSReader {

  public static void main(String[] args) throws Exception {
    String date = "2015-07-27";
    load(date);
  }

  /**
   * Load all related data from exported XLS.
   * 
   * 1. STOCK/daily-yyyyMMdd
   * 2. INDEX_COMPOSITE/daily-yyyyMMdd
   * 3. INDEX_BLOCK/daily-yyyyMMdd
   * 
   * @param targetDate
   * @throws Exception 
   */
  public static void load(String targetDate) throws Exception {
    Date parsedDate = THSReaderUtils.SDF.parse(targetDate);

    // 1. STOCK/daily-yyyyMMdd
    DailyReader.load(parsedDate);

    // 2. INDEX_COMPOSITE/daily-yyyyMMdd
    IndexReader.load(targetDate);

    // 3. INDEX_BLOCK/daily-yyyyMMdd
    BlockIndexReader.load(targetDate);

  }

  /**
   * Load all related data from exported XLS for a various of dates.
   * 
   * @param targetDates
   * @throws Exception
   */
  public static void load(List<String> targetDates) throws Exception {
    targetDates.forEach(targetDate -> {
      try {
        load(targetDate);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

}
