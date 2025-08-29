package com.pooju.quizlet.service;

import com.pooju.quizlet.model.Quiz;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final ChatClient chatClient;
    @Value("classpath:prompts/UserMessage.st")
    private Resource userMessage;
    @Value("classpath:prompts/SystemMessage.st")
    private Resource systemMessage;

    public QuizService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Quiz generateQuiz(String topic,int difficulty){

        SystemMessage systemMessage1= new SystemMessage(systemMessage);
        PromptTemplate promptTemplate= new PromptTemplate(userMessage);
        Prompt prompt=promptTemplate.create(Map.of(
                "topic", topic,
                "difficulty",difficulty
        ));
        UserMessage userMessage1=new UserMessage(prompt.getContents());
        return chatClient
                .prompt(new Prompt(
                        List.of(systemMessage1,userMessage1)
                ))
                .call()
                .entity(Quiz.class);



    }
}
