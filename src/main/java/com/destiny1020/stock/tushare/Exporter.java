package com.destiny1020.stock.tushare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Export data to the XLS.
 * 
 * @author destiny1020
 *
 */
public class Exporter {

  /**
   * Export history data into MYSQL.
   * 
   * @param symbol
   * @param startDay
   * @param endDay
   * @throws IOException
   */
  public static void exportToMySQL(String symbol, String startDay, String endDay)
      throws IOException {
    ProcessBuilder pb = new ProcessBuilder("python", "to_mysql.py", symbol, startDay, endDay);
    System.out.println(pb.command());
    Process p = pb.start();

    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    in.lines().forEach(line -> {
    });
    in.close();
  }

  /**
   * Export history data for symbol, from start day to end day.
   * 
   * @param symbol
   * @param startDay
   * @param endDay
   * @throws IOException 
   */
  public static void exportToExcel(String symbol, String startDay, String endDay)
      throws IOException {
    ProcessBuilder pb = new ProcessBuilder("python", "to_excel.py", symbol, startDay, endDay);
    System.out.println(pb.command());
    Process p = pb.start();

    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    in.lines().forEach(line -> {
    });
    in.close();
  }

  public static void main(String[] args) {
    //    try {
    //      //      String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
    //      //      BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
    //      //      out.write(prg);
    //      //      out.close();
    //      //      int number1 = 10;
    //      //      int number2 = 32;
    //
    //      ProcessBuilder pb = new ProcessBuilder("python", "test1.py");
    //      Process p = pb.start();
    //
    //      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    //      in.lines().forEach(line -> {
    //      });
    //      in.close();
    //
    //      System.out.println(Files.exists(Paths.get("d:/600774.xls")));
    //    } catch (Exception e) {
    //      e.printStackTrace();
    //    }


  }

}
