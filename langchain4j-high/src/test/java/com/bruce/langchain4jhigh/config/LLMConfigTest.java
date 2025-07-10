package com.bruce.langchain4jhigh.config;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

@SpringBootTest
class LLMConfigTest {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private QdrantClient qdrantClient;

    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;

    @Test
    void embeddingModel() {
        Response<Embedding> resp = embeddingModel.embed("文本向量化示例");
        System.out.println(resp);
    }

    @Test
    void createCollection() {
        var vectorParams = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine)
                .setSize(1024)
                .build();
        qdrantClient.createCollectionAsync("test", vectorParams);
    }

    @Test
    void add() {
        TextSegment segment = TextSegment.from("我的名字叫Bruce，我是一个程序员。");
        segment.metadata().put("userName", "Bruce");
        Embedding embedding1 = embeddingModel.embed(segment).content();
        embeddingStore.add(embedding1, segment);
    }

    @Test
    void query() {
        Embedding queryEmbedding = embeddingModel.embed("我是做什么工作的？").content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(metadataKey("userName").isEqualTo("Jay"))
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(embeddingSearchRequest);
        System.out.println(searchResult.matches().get(0).embedded().text());
    }
}