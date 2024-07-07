package com.example.interview_practice.mockmvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
// keep in mind Data is not good idea for entity because it uses all fields to generate equals and hashCode and would be inefficient
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private BigDecimal balance;
    /*
     Using FetchType.EAGER either implicitly or explicitly for your JPA associations is a bad idea
     because you are going to fetch way more data that you need.
     More, the FetchType.EAGER strategy is also prone to N+1 query issues if you forget to
     specify that an EAGER association needs to be JOIN FETCH-ed by a JPQL query,
     Hibernate is going to issue a secondary select for every uninitialized association
     First Query (1 Query):
        sql
        SELECT * FROM users;

        N Queries:
        For each user loaded in the first query, additional queries are executed to load the sendTransactions and recTransactions because of FetchType.EAGER.
        sql
        SELECT * FROM transactions WHERE sender_id = ?;
        SELECT * FROM transactions WHERE rec_id = ?;
        If there are 10 users, this results in 1 query to fetch users and 20 queries to fetch transactions (10 for sendTransactions and 10 for recTransactions),
        resulting in 21 queries in total.
    */
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<Transaction> sendTransactions;

    @OneToMany(mappedBy = "rec", fetch = FetchType.LAZY)
    private Set<Transaction> recTransactions;
}
