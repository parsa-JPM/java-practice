package com.example.interview_practice.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class BakeryTest {

    @Test
    void bakeryWorkingSuccessfully() throws InterruptedException {
        Bakery bakery = new Bakery();

        Thread baker = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    bakery.addBread();
                    System.out.println("Baked " + i );
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                 /*
                     We catch InterruptedException because we have to, but we don't want to silently swallow it —
                     so we put the interrupt flag back so the rest of the application still knows
                     something requested this thread to stop.
                 */
                Thread.currentThread().interrupt();
            }
        });

        Thread customer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    bakery.takeBread();
                    System.out.println("Took " + i );
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        baker.start();
        customer.start();
        customer.join();
        baker.join();

        Assertions.assertThat(bakery.shelf)
                .hasSize(0);
    }
}
