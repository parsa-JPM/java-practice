package com.example.interview_practice.ai.rag;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    private final ChatClient chatClient;

    public RagController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                // this is how we pass vectorstor to run similarity query against our prompts
                // so if prompts was similar to data we have in VectorStore it provide additional context to chat
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }

    //  http :8080/rag q=="do you have any info regarding parsa mihandoost work experinces?"
    @GetMapping("/rag")
    public String faq(@RequestParam("q") String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    @GetMapping("/rag/record")
    public CandidateCV workRecord() {
        return chatClient.prompt()
                .user("Give me list of Parsa Mihandoost work experiences")
                .call()
                .entity(CandidateCV.class);
    }
}
