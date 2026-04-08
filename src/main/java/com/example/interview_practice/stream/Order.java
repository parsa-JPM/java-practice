package com.example.interview_practice.stream;

import java.util.List;

public record Order(Long id, String customer, List<OrderItem> items) {
}
