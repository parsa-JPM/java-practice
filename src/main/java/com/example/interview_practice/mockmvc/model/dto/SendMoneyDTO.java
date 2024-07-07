package com.example.interview_practice.mockmvc.model.dto;

import java.math.BigDecimal;

public record SendMoneyDTO(String sender,
                           String rec,
                           BigDecimal amount,
                           String version) {
}
