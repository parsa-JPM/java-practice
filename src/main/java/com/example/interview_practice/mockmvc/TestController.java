package com.example.interview_practice.mockmvc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    record User(String name, int age) {
    }

    List<User> users = List.of(
            new User("Parsa", 27),
            new User("Fati", 29));

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }
}
