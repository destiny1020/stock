package com.destiny1020.stock.es;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common functions for ES.
 * 
 * @author Administrator
 *
 */
public class ElasticsearchUtils {

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
      return false;
    }

    return true;
  }

}
