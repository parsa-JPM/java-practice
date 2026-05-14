package com.example.interview_practice.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureTest {

    private Future<Integer> sum(int a, int b) {
        try (var pool = Executors.newSingleThreadExecutor()) {
            return pool.submit(() -> a + b);
        }
    }

    private String sayHell(boolean f){
        if (f)
            throw new RuntimeException("This method has error");

        return "hello";
    }

    @Test
    public void testFuture(){
        Assertions.assertThat(sum(1,2)).isDone();
    }
}
