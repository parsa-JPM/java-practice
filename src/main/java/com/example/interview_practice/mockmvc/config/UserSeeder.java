package com.example.interview_practice.mockmvc.config;


import com.example.interview_practice.mockmvc.model.User;
import com.example.interview_practice.mockmvc.model.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private UserRepo userRepo;


    @Override
    public void run(String... args) throws Exception {
        var user1 = User.builder()
                .name("parsa")
                .balance(new BigDecimal("1000"))
                .build();
        var user2 = User.builder()
                .name("mahsa")
                .balance(new BigDecimal("1000"))
                .build();

        userRepo.saveAll(List.of(user1, user2));
    }
}
