package com.destiny1020.stock.ths.xls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import com.destiny1020.stock.es.ElasticsearchUtils;

/**
 * Used to reindex all THSZS series into ES.
 * 
 * @author Administrator
 *
 */
public class StockBlockReaderEntry {

  /**
   * Will first remove the whole index.
   * Then reindex all the THSZS related data into ES.
   * 
   * @param args
   * @throws IOException
   * @throws ExecutionException 
   * @throws InterruptedException 
   */
  public static void main(String[] args) throws IOException, InterruptedException,
      ExecutionException {
    Node node = ElasticsearchUtils.getNode();
    Client client = node.client();

    // Step 1: remove the whole index --- not necessary
    //    ElasticsearchUtils.deleteIndex(client, ElasticsearchConsts.INDEX_BLOCK);

    // Step 2: iterate all THSZS data and index them into ES
    Files.list(Paths.get(THSReaderUtils.FT_PREFIX)).filter(path -> {
      return path.toString().endsWith(THSReaderUtils.SF_INDEX_BLOCK);
    }).forEach(path -> {
      try {
        // check whether the index has already existed
        if (ElasticsearchUtils.isBlockIndexTypeExisted(client, path.getFileName().toString())) {
          return;
        }

        System.out.println("Try to load: " + path.toString());
        StockBlockReader.load(client, path.toFile());
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    });

    node.close();
  }

}
