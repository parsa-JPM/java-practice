package com.example.interview_practice.tools.taskmanager;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TaskManagementTools {

    private static final String TASKS_FILE = "tasks.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public enum TaskStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong taskIdGenerator = new AtomicLong(1);

    public record Task(Long id, String title, String assignee, TaskStatus status) {}

    @Tool(description = "Create a new task with title and assignee")
    public Task createTask(String title, String assignee) {
        Long taskId = taskIdGenerator.getAndIncrement();
        Task task = new Task(taskId, title, assignee, TaskStatus.PENDING);
        tasks.put(taskId, task);
        persistToFile();
        return task;
    }

    @Tool(description = "Update task status by task ID")
    public Task updateStatus(Long taskId, TaskStatus status) {
        Task existing = tasks.get(taskId);
        if (existing == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        Task updated = new Task(existing.id(), existing.title(), existing.assignee(), status);
        tasks.put(taskId, updated);
        persistToFile();
        return updated;
    }

    @Tool(description = "Assign or reassign a task to a different person")
    public Task assignTask(Long taskId, String newAssignee) {
        Task existing = tasks.get(taskId);
        if (existing == null) {
            throw new IllegalArgumentException("Task not found: " + taskId);
        }
        Task updated = new Task(existing.id(), existing.title(), newAssignee, existing.status());
        tasks.put(taskId, updated);
        persistToFile();
        return updated;
    }

    private void persistToFile() {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(TASKS_FILE), tasks);
    }
}
