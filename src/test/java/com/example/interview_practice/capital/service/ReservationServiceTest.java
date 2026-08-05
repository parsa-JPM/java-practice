package com.example.interview_practice.capital.service;

import com.example.interview_practice.capital.model.Table;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


class ReservationServiceTest {


    private ReservationService reservationService = new ReservationService(getTable(), new ArrayList<>());


    @Test
    public void shouldReturnTableId() {
        Assertions
                .assertThat(reservationService.reserve(5, LocalTime.of(4, 0)))
                .isEqualTo(4);
    }

    @Test
    void shouldReturnCorrectID_whenWeHaveBusyTable() {
        reservationService.reserve(8, LocalTime.of(4, 0));

        Assertions
                .assertThat(reservationService.reserve(8, LocalTime.of(4, 0)))
                .isEqualTo(9);
    }


    private List<Table> getTable() {
        return List.of(
                new Table(1, 2),
                new Table(2, 3),
                new Table(3, 4),
                new Table(4, 5),
                new Table(5, 6),
                new Table(6, 7),
                new Table(7, 2),
                new Table(8, 4),
                new Table(9, 9),
                new Table(10, 8)
        );
    }
}