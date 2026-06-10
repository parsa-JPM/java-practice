package com.example.interview_practice.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // to test endpoint: http :8080/ai/joke
    @GetMapping("/joke")
    public String getJoke() {
        return chatClient.prompt()
                .user("Tell me a funny programming joke.")
                // blocking call
                .call()
                .content();
    }

    // to test endpoint: http --stream :8080/ai/stream
    @GetMapping("/stream")
    public Flux<String> stream(){
        return chatClient.prompt()
                .user("I'm visiting London soon, can you give me 10 places I must visit?")
                // non-blocking call which returns Flux from project reactor (spring web-flux)
                .stream()
                .content();
    }

    @GetMapping("/detail")
    public ChatResponse detail(){
        return chatClient.prompt()
                .user("Tell me a funny programming joke.")
                // blocking call
                .call()
                .chatResponse();
    }
}
