package com.example.interview_practice.ai.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientAttributes;
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VacationPlan {

    private final ChatClient chatClient;

    public VacationPlan(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/vacation/unstructured")
    public String vacationUnstructured() {
        return chatClient.prompt()
                .user("What's a good vacation plan while I'm in Montreal CA for 4 days?")
                .call()
                .content();
    }

    // http :8080/vacation/structured destination=="shiraz"
    @GetMapping("/vacation/structured")
    public Itinerary vacationStructured(@RequestParam(value = "destination", defaultValue = "Tehran") String destination) {

        var validationAdvisor = StructuredOutputValidationAdvisor.builder()
                .outputType(Itinerary.class)
                .maxRepeatAttempts(3)
                .build();

        return chatClient.prompt()
                .advisors(a-> a.advisors(validationAdvisor)
                        // Some LLM has native structured output feature we can use it
                        .param(ChatClientAttributes.STRUCTURED_OUTPUT_NATIVE.getKey(), true)
                )
                .user(u -> {
                    u.text("What's a good vacation plan while I'm in {destination} for 3 days?");
                    u.param("destination", destination);
                })
                .call()
                // instead of content() we used entity method to get DTO from AI
                .entity(Itinerary.class);
    }
}
