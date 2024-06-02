package com.example.interview_practice.jpatest;

import com.example.interview_practice.mockmvc.model.Transaction;
import com.example.interview_practice.mockmvc.model.TrxRepo;
import com.example.interview_practice.mockmvc.model.User;
import com.example.interview_practice.mockmvc.model.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import lombok.extern.jbosslog.JBossLog;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
  the scope of @DataJpaTest is limited to the JPA repository layer of the application.
   It doesn’t load the entire application context,
  which can make testing faster and more focused.
  This annotation also provides a pre-configured EntityManager and TestEntityManager for testing JPA entities.
  it can be configurable:
  @DataJpaTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
  })
   By default, each test method annotated with @DataJpaTest runs within a transactional boundary.
   This ensures that changes made to the database are automatically rolled back at the end of the test,
   leaving a clean slate for the next test.
 */
@DataJpaTest
@JBossLog
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @Autowired
    TrxRepo trxRepo;


    @BeforeEach
    public void setUp() {
        var user1 = User.builder()
                .name("parsa")
                .balance(new BigDecimal("1000"))
                .sendTransactions(new HashSet<>())
                .recTransactions(new HashSet<>())
                .build();
        var user2 = User.builder()
                .name("mahsa")
                .balance(new BigDecimal("1000"))
                .sendTransactions(new HashSet<>())
                .recTransactions(new HashSet<>())
                .build();

        Set<Transaction> trxs = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            var trx = Transaction.builder()
                    .sender(user1)
                    .rec(user2)
                    .amount(new BigDecimal("1000"))
                    .version("1")
                    .build();
            /*
             I have added relations manually in two sides as all methods is executed in one context so
             we will have problem to fetch relations in test cases :
             By default, @DataJpaTest runs tests within a single transaction and session.
             This means changes made to entities within the test might not be automatically flushed to the database.
             */
            user1.getSendTransactions().add(trx);
            user2.getRecTransactions().add(trx);
            trxs.add(trx);
        }

        userRepo.saveAll(List.of(user1, user2));
        trxRepo.saveAll(trxs);
    }

    @Test
    public void addUserTest() {
        assertEquals(userRepo.findAll().size(), 2);
        assertEquals(trxRepo.findAll().size(), 10);
    }

    @Test
    @DisplayName("check fetching user's send transactions")
    public void checkUserTransactions() {
        User parsa = userRepo.findByName("parsa");
        log.info("query for checking how Hibernate retrieve transactions ");
        assertEquals(parsa.getSendTransactions().size(), 10);
    }

}