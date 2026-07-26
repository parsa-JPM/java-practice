package com.example.interview_practice;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        var t = Map.of("test", "test", "test2", "test");
        Map<String,String> map = new HashMap<>();
        map.put("test", "test1");
        map.put(null, "test2");

        for (Map.Entry<String,String> entry: map.entrySet()){
            System.out.println(entry.getKey());
        }
        System.out.println(map);
    }
}
