package com.example.interview_practice.tools;

import com.example.interview_practice.tools.date.DateTimeTools;
import com.example.interview_practice.tools.taskmanager.TaskManagementTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToolsController {

    private final ChatClient chatClient;
    private final TaskManagementTools taskManagementTools;

    public ToolsController(ChatClient.Builder builder, TaskManagementTools taskManagementTools) {
        this.chatClient = builder.build();
        this.taskManagementTools = taskManagementTools;
    }

    // http :8080/tools p=="make a task for Anna about develop new banking application prototype"
    @GetMapping("/tools")
    public String dateTime(@RequestParam("p") String prompt) {
        return chatClient.prompt()
                .tools(new DateTimeTools(), taskManagementTools)
                .system("After using any tool, always confirm the action to the user in a friendly, concise message.")
                .user(prompt)
                .call()
                .content();
    }
}
