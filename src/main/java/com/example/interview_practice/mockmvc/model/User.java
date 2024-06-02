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
     todo ask chat gpt to explain n+1 query problem
    */
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<Transaction> sendTransactions;

    @OneToMany(mappedBy = "rec", fetch = FetchType.LAZY)
    private Set<Transaction> recTransactions;
}
