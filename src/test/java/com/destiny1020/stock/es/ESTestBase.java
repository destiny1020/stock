package com.destiny1020.stock.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class ESTestBase {

  // es context
  protected static Node node;
  protected static Client client;

  @BeforeClass
  public static void start() {
    node = NodeBuilder.nodeBuilder().client(true).node();
    client = node.client();
  }

  @AfterClass
  public static void close() {
    node.close();
  }

}
