package com.example.interview_practice.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

public class ExecutersService {

    @Test
    void createFixedThreadPool() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5); // Pool of 5 threads

        // List to store Future objects
        List<Future<String>> futures = new ArrayList<>();

        // Submit 20 tasks to the executor service
        for (int i = 1; i <= 20; i++) {
            int taskId = i; // Each thread gets a unique ID
            
            Callable<String> task = () -> {
                return "Task " + taskId + " executed by " + Thread.currentThread().getName();
            };

            futures.add(executorService.submit(task)); // Submit the task and store the Future
        }

        // Retrieve and print results
        for (Future<String> future : futures) {
            System.out.println(future.get()); // Blocking call to get the result
        }

        // Shut down the executor service
        executorService.shutdown();
    }

}
