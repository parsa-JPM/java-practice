package com.example.interview_practice.concurrency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ExecutersService {

    @Test
    void createFixedThreadPool() throws InterruptedException, ExecutionException {
//        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        try (ExecutorService executorService = Executors.newFixedThreadPool(19)) {

            List<Future<String>> futures = new ArrayList<>();
            Set<Long> ids = ConcurrentHashMap.newKeySet();
//            Set<Long> ids = new HashSet<>();

            for (int i = 1; i <= 20; i++) {
                int taskId = i;
                Callable<String> task = () -> {
                    Thread.sleep(1000);
                    var tid = Thread.currentThread().threadId();
                    if (ids.contains(tid)) {
                        System.out.printf("run in second hand thread: %s \n", tid);
                    } else {
                        ids.add(tid);
                    }
                    return "Task " + taskId + " executed by " + tid;
                };

                futures.add(executorService.submit(task));
            }

            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        }
    }

}
