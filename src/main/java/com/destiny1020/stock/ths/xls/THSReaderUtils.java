package com.destiny1020.stock.ths.xls;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common utilities used during the reading phase.
 * 
 * @author destiny
 *
 */
public class THSReaderUtils {

  public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  public static final String NON_EXISTENCE = "--";
  public static final String NON_EXISTENCE_LONG = "----";

  public static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+", Pattern.DOTALL);

  /**
   * Extract the number from the cell content.
   * 
   * @param target
   * @return
   */
  public static BigDecimal extractNumber(String target) {
    BigDecimal result = BigDecimal.ZERO;
    if (target != null && !target.equals(NON_EXISTENCE_LONG)) {
      final Matcher matcher = NUMBER_PATTERN.matcher(target);
      while (matcher.find()) {
        result = new BigDecimal(matcher.group());
      }
    }
    return result;
  }
}
