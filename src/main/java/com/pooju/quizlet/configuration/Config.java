package com.pooju.quizlet.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ChatClient getChatClient(ChatModel chatModel){
        return ChatClient.builder(chatModel).build();
    }
}
