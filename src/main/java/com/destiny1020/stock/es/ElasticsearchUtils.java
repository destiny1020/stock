package com.destiny1020.stock.es;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common functions for ES.
 * 
 * @author Administrator
 *
 */
public class ElasticsearchUtils {

  private static final Logger LOGGER = LogManager.getLogger(ElasticsearchUtils.class);

  /**
   * Get an instance of ES node.
   * 
   * @return
   */
  public static Node getNode() {
    return NodeBuilder.nodeBuilder().client(true).node();
  }

  /**
   * Get an instance of ES client. Ignore the node creation process.
   * 
   * @return
   */
  public static Client getClient() {
    return NodeBuilder.nodeBuilder().client(true).node().client();
  }

  /**
   * To remove the index in the ES instance.
   * 
   * @param client
   * @param index
   * @return
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static boolean deleteIndex(Client client, String index) throws InterruptedException,
      ExecutionException {
    if (isIndexExisting(client, index)) {
      return client.admin().indices().prepareDelete(index).execute().get().isAcknowledged();
    }

    return false;
  }

  /**
   * Whether index exists in the ES instance.
   * 
   * @param client
   * @param index
   * @return
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static boolean isIndexExisting(Client client, String index) throws InterruptedException,
      ExecutionException {
    return client.admin().indices().prepareExists(index).execute().get().isExists();
  }

  /**
   * Whether index/type exists in the ES instance.
   * 
   * @param client
   * @param index
   * @param type
   * @return
   */
  public static boolean isTypeExisting(Client client, String index, String type) {
    GetMappingsResponse getResponse =
        client.admin().indices().prepareGetMappings(index).setTypes(type).execute().actionGet();

    return getResponse.getMappings().size() == 1;
  }

  /**
   * To put the type mapping in the ES instance. Return true when successful.
   * 
   * @param client
   * @param index
   * @param type
   * @param mapping
   * @return
   */
  public static boolean putTypeMapping(Client client, String index, String type,
      XContentBuilder mapping) {
    PutMappingResponse response =
        client.admin().indices().preparePutMapping(index).setType(type).setSource(mapping)
            .execute().actionGet();

    return response.isAcknowledged();
  }

  /**
   * To put index with settings to the ES instance.
   * 
   * @param client
   * @param index
   * @param settings
   * @return
   * @throws ExecutionException 
   * @throws InterruptedException 
   */
  public static boolean putIndexWithSetting(Client client, String index, String settings)
      throws InterruptedException, ExecutionException {
    return client.admin().indices().prepareCreate(index).setSettings(settings).execute().get()
        .isAcknowledged();
  }

  /**
   * Bulk index a bunch of entities. The entity should implement IOwnEsIDEntity interface.
   * 
   * @param client
   * @param index
   * @param type
   * @param entities
   * @return
   */
  public static <T extends IEsIDEntity> boolean bulkIndexing(Client client, String index,
      String type, List<T> entities) {
    BulkRequestBuilder bulkRequest = client.prepareBulk();

    ObjectMapper mapper = new ObjectMapper(); // create once, reuse
    entities.forEach(entity -> {
      String json;
      try {
        json = mapper.writeValueAsString(entity);
        bulkRequest.add(client.prepareIndex(index, type, entity.getEsID()).setSource(json));
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(String.format("Serializing ID:%d has some errors.",
            entity.getEsID()));
      }
    });

    // execute bulk indexing
    BulkResponse bulkResponse = bulkRequest.execute().actionGet();
    if (bulkResponse.hasFailures()) {
      LOGGER.error(bulkResponse.buildFailureMessage());
      return false;
    }

    return true;
  }

  /**
   * To determine whether the block index type for a certain day existed.
   * For example, when the parameter is "2015-08-27_THSZS.xls",
   * it will try to check whether index_block/daily-20150827 exists and
   * return corresponding result.
   * 
   * @param blockIndexFileName
   * @return
   */
  public static boolean isBlockIndexTypeExisted(Client client, String blockIndexFileName) {
    String datePart = blockIndexFileName.substring(0, blockIndexFileName.indexOf('_'));
    String typeName = "daily-" + datePart.replaceAll("-", "");

    return isTypeExisting(client, ElasticsearchConsts.INDEX_BLOCK, typeName);
  }

}
