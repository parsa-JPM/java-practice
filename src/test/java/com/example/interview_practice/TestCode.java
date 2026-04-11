package com.example.interview_practice;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

public class TestCode {

    protected record User(String name, String family, int age) {
    }

    @Test
    void test() {
        var users = List.of(
                new User("mahsa", "mihan", 35),
                new User("parsa", "Mihan", 27),
                new User("mamad", "saadat", 28),
                new User("mamad", "mo", 9)
        );

        List<User> userCollect = users.stream()
                .sorted(Comparator.comparing(User::age).reversed())
                .toList();


        System.out.println(userCollect);
    }

    @Test
    void howReduceMethodWorks() {
        int poolSize = Runtime.getRuntime().availableProcessors();

        System.out.println(poolSize);
    }
}
