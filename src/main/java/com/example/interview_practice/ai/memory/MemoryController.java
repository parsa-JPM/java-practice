package com.example.interview_practice.ai.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryController {

    private final ChatClient chatClient;

    public MemoryController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    //  http :8080/ai/chat msg=="Hey, I'm Parsa" id=="1"
    //  http :8080/ai/chat msg=="what's my name?" id=="1"
    @GetMapping("/ai/memory")
    public String chat(@RequestParam("msg") String msg,
                       @RequestParam("id") String conversationId) {
        return chatClient.prompt()
                .user(msg)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }
}
