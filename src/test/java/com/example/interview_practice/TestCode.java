package com.example.interview_practice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

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
                .filter(u -> u.age > 10)
                .toList();
        CopyOnWriteArrayList<User> asyncList = new CopyOnWriteArrayList<>(userCollect);

        for (User user: asyncList){
            System.out.println(user);
        }

        System.out.println(userCollect);
    }

    @Test
    void howReduceMethodWorks() {
        int[] input = {2, 3, 4};
        var r = Arrays.stream(input)
                .reduce(0, (a, b) -> a + b);
        System.out.println(r);
    }
}
