package com.example.interview_practice.mockmvc.config;


import com.example.interview_practice.mockmvc.model.Transaction;
import com.example.interview_practice.mockmvc.model.TrxRepo;
import com.example.interview_practice.mockmvc.model.User;
import com.example.interview_practice.mockmvc.model.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private UserRepo userRepo;
    private TrxRepo trxRepo;


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

        Set<Transaction> trxs = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            var trx = Transaction.builder()
                    .sender(user1)
                    .rec(user2)
                    .amount(new BigDecimal("1000"))
                    .version("1")
                    .build();
            trxs.add(trx);
        }

        userRepo.saveAll(List.of(user1, user2));
        trxRepo.saveAll(trxs);
    }
}
