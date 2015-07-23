package com.destiny1020.stock.es.setting;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * Hold common settings for indices.
 * For example, analysis section --- custom analyzers, etc.
 * 
 * @author Administrator
 *
 */
public class CommonSettings {

  /**
   * First, add this setting into the index.
   * Then, use the custom symbol_analyzer for the symbol related fields.
   * 
   * @return
   * @throws IOException
   */
  public static String getSymbolAnalyzerSettings() throws IOException {
    // @formatter:off
    return XContentFactory.jsonBuilder()
        .startObject()
          .startObject("analysis")
            // tokenizer section
            .startObject("tokenizer")
              .startObject("symbol_t")
                .field("type", "nGram")
                .field("min_gram", "1")
                .field("max_gram", "8")
              .endObject()
            .endObject()
            // analyzer section
            .startObject("analyzer")
              .startObject("symbol_analyzer")
                .field("type", "custom")
                .startArray("char_filter")
                .endArray()
                .field("tokenizer", "symbol_t")
                .startArray("filter")
                  .value("lowercase")
                .endArray()
              .endObject()
            .endObject()
          .endObject()
        .endObject().string();
    // @formatter:on
  }

}
