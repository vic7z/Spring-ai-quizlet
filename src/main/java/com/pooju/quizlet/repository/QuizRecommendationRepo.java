package com.pooju.quizlet.repository;

import com.pooju.quizlet.model.Quiz;
import org.bson.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuizRecommendationRepo {
    private final MongoTemplate mongoTemplate;

    public QuizRecommendationRepo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Cacheable("quizRecommendations")
    public List<Quiz> findSimilarQuizzes(List<Double> queryVector, String topic, double minScore) {
        List<Float> queryVectorFloat = queryVector.stream()
                .map(Double::floatValue)
                .toList();

        List<Document> pipeline = List.of(
                new Document("$vectorSearch",
                        new Document("index", "quiz_embeddings")
                                .append("path", "embedding")
                                .append("queryVector", queryVectorFloat)
                                .append("numCandidates", 100)
                                .append("limit", 10)
                                .append("score", true)
                ),
                new Document("$match",
                        new Document("topic", new Document("$ne", topic))
                )
        );



        List<Quiz> quizzes = new ArrayList<>();
        mongoTemplate.getCollection("Quiz")
                .aggregate(pipeline)
                .forEach(doc -> quizzes.add(mongoTemplate.getConverter().read(Quiz.class, doc)));
        return quizzes.stream()
                .peek(q -> {
                    double score = cosineSimilarity(queryVector, q.getEmbedding());
                    q.setScore(score);
                })
                .filter(q -> q.getScore() >= minScore)
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .limit(10)
                .collect(Collectors.toList());


    }

    private double cosineSimilarity(List<Double> v1, List<Double> v2) {
        double dot = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (int i = 0; i < v1.size(); i++) {
            dot += v1.get(i) * v2.get(i);
            norm1 += v1.get(i) * v1.get(i);
            norm2 += v2.get(i) * v2.get(i);
        }
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
