package com.example.interview_practice;

import com.example.interview_practice.mockmvc.model.User;
import com.example.interview_practice.mockmvc.model.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class InterviewPracticeApplicationTests {

    @Autowired
    private UserRepo userRepo;

    @Test
    void contextLoads() {

        List<User> all = userRepo.findAll();
        System.out.println(all.get(0).getSendTransactions().size());
        System.out.println(all.get(1).getSendTransactions().size());
    }

}
