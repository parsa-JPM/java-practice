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
         
    */
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private Set<Transaction> sendTransactions;

    @OneToMany(mappedBy = "rec", fetch = FetchType.LAZY)
    private Set<Transaction> recTransactions;
}
