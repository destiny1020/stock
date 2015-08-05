package com.destiny1020.stock.ths.xls;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.collect.Lists;

import com.google.common.collect.Sets;

/**
 * Common utilities used during the reading phase.
 * 
 * @author destiny
 *
 */
public class THSReaderUtils {

  private static final Logger LOGGER = LogManager.getLogger(THSReaderUtils.class);

  public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

  public static final String NON_EXISTENCE = "--";
  public static final String NON_EXISTENCE_LONG = "----";

  public static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+", Pattern.DOTALL);

  // File Path Patterns --- maybe various on different environment
  // TODO: make properties file for the environment dependent variables
  // ON WIN7
  public static final String FT_PREFIX = "D:/stock/THS/";
  // ON UBUNTU DEV
  //  public static final String FT_PREFIX = "data/";

  public static final String FT_DAILY = FT_PREFIX + "%s.xls";
  public static final String FT_DAILY_CAPITAL = FT_PREFIX + "%s_ZJLX.xls";
  public static final String FT_DAILY_POSITION = FT_PREFIX + "%s_ZLZC.xls";

  public static final String FT_INDEX_COMPOSITE = FT_PREFIX + "%s_HSZS.xls";
  public static final String FT_INDEX_BLOCK = FT_PREFIX + "%s_THSZS.xls";

  // file name patterns [Date]_BK_[BLOCK_ABBR].xls
  public static final String FT_BLOCK_DAILY = FT_PREFIX + "%s_BK_%s.xls";

  // TODO: make it an external property in prop file.
  /**
   * Selected blocks that are of my interests
   */
  public static final Set<String> SELECTED_BLOCKS = Sets.newHashSet("DFJ", "GFJG", "JSJYY", "YJS",
      "YLGG");

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

  /**
   * Validate whether all needed exported xls are ready.
   * 
   * @param date
   * @return All the missing xls paths.
   */
  public static List<String> validateDataExist(Date date) {
    String dateStr = SDF.format(date);
    List<String> nonExistPaths = Lists.newLinkedList();

    // daily --- normal, capital and position
    String pathDailyNormal = String.format(FT_DAILY, dateStr);
    if (Files.notExists(Paths.get(pathDailyNormal))) {
      nonExistPaths.add(pathDailyNormal);
    }

    String pathDailyCapital = String.format(FT_DAILY_CAPITAL, dateStr);
    if (Files.notExists(Paths.get(pathDailyCapital))) {
      nonExistPaths.add(pathDailyCapital);
    }

    String pathDailyPosition = String.format(FT_DAILY_POSITION, dateStr);
    if (Files.notExists(Paths.get(pathDailyPosition))) {
      nonExistPaths.add(pathDailyPosition);
    }

    // composite index
    String pathCompositeIndex = String.format(FT_INDEX_COMPOSITE, dateStr);
    if (Files.notExists(Paths.get(pathCompositeIndex))) {
      nonExistPaths.add(pathCompositeIndex);
    }

    // block index
    String pathBlockIndex = String.format(FT_INDEX_BLOCK, dateStr);
    if (Files.notExists(Paths.get(pathBlockIndex))) {
      nonExistPaths.add(pathBlockIndex);
    }

    // selected block daily --- only generate warnings since those blocks are changing
    SELECTED_BLOCKS.forEach(block -> {
      String pathBlockDaily = String.format(FT_BLOCK_DAILY, dateStr, block);
      if (Files.notExists(Paths.get(pathBlockDaily))) {
        LOGGER.warn(String.format("Block daiy XLS is not exist for %s on %s !!", block, dateStr));
      }
    });

    return nonExistPaths;
  }
}
