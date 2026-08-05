package com.example.interview_practice.capital.model;

import java.time.LocalTime;

public record Reservation(int tableId, int partySize, LocalTime startTime) {
}
