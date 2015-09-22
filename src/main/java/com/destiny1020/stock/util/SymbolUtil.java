package com.destiny1020.stock.util;

/**
 * Helpers for the symbol related business.
 * 
 * @author destiny1020
 *
 */
public class SymbolUtil {

  public static boolean isCYB(String symbol) {
    return symbol.toLowerCase().startsWith("sz3");
  }

}
