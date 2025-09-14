package com.pooju.quizlet.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class EmbeddingService {
    private final EmbeddingModel embeddingClient;

    public EmbeddingService(OpenAiEmbeddingModel embeddingClient) {
        this.embeddingClient = embeddingClient;
    }

    public List<Double> getEmbedding(String input) {
        float[] embed = embeddingClient.embed(input);
        List<Double> embeddingList = new ArrayList<>(embed.length);
        for (float v : embed) {
            embeddingList.add((double) v);
        }
        return embeddingList;

    }
}
