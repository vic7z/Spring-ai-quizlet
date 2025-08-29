package com.pooju.quizlet.controller;

import com.pooju.quizlet.model.Quiz;
import com.pooju.quizlet.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{topic}/{difficulty}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable String topic,
                                        @PathVariable int difficulty){
        return ResponseEntity.ok(quizService.generateQuiz(topic,difficulty));

    }
}
