package com.destiny1020.stock.tushare;

import java.io.IOException;

import org.junit.Test;

public class ExporterTest {

  @Test
  public void testExportExcel() throws IOException, InterruptedException {
    Exporter.exportToExcel("600886", "2015-01-01", "2015-12-31");
  }

}
