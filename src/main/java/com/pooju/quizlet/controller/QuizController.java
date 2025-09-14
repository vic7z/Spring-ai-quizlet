package com.pooju.quizlet.controller;

import com.pooju.quizlet.dto.Request;
import com.pooju.quizlet.model.Quiz;
import com.pooju.quizlet.service.EmbeddingService;
import com.pooju.quizlet.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuizController {


    private final QuizService quizService;
    @Autowired
    private EmbeddingService embeddingService;;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/quiz")
    public ResponseEntity<Quiz> getQuiz(@RequestBody Request request) {
        return ResponseEntity.ok(quizService.getQuiz(request.getTopic(),
                request.getDifficulty(),
                request.getSessionId(),
                request.getRefresh()));

    }

    @GetMapping("/getQuiz")
    public ResponseEntity<Quiz> getQuizById(@RequestParam(name = "quizId") String id) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(quiz);
        }
    }

    @GetMapping("/embedding")
    public ResponseEntity<?> getEmbedding(@RequestParam(name = "input") String input) {
        quizService.embedAllQuiz();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getRecommendation")
    public ResponseEntity<?> getRecommendation(@RequestParam(name = "quizTopic") String topic){
        return ResponseEntity.ok(quizService.getSimilarQuizzes(topic));
    }




}
