package com.destiny1020.stock.tushare;

import java.io.IOException;

import org.junit.Test;

public class ExporterTest {

  @Test
  public void testExportExcel() throws IOException {
    Exporter.exportToExcel("600886", "2015-01-01", "2015-12-31");
  }

  @Test
  public void testExportMySQL() throws IOException {
    Exporter.exportToMySQL("600886", "2015-01-01", "2015-12-31");
  }

}
