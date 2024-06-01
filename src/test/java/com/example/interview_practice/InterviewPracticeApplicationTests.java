package com.example.interview_practice;

import com.example.interview_practice.mockmvc.model.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class InterviewPracticeApplicationTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    void contextLoads() {
        userRepo.findAll();
    }

}
