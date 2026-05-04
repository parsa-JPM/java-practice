package com.example.interview_practice.mockmvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.List;
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
    /*
     What size actually controls:
    Say you loaded 120 orders. When Hibernate needs to fetch their orderItems, it needs to query with those 120 order IDs. size = 50 means:
    sql
    -- batch 1
    SELECT * FROM order_items WHERE order_id IN (1,2,3,...,50)
    -- batch 2
    SELECT * FROM order_items WHERE order_id IN (51,52,...,100)
    -- batch 3
    SELECT * FROM order_items WHERE order_id IN (101,...,120)
     */
    @BatchSize(size = 50)
    private List<Transaction> sendTransactions;

    @OneToMany(mappedBy = "rec", fetch = FetchType.LAZY)
    private Set<Transaction> recTransactions;
}
