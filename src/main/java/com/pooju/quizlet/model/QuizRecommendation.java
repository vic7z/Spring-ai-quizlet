package com.pooju.quizlet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizRecommendation {
    private String topic;
    private List<String> recommendedQuizIds;
}
