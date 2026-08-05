package com.example.interview_practice.capital.service;

import com.example.interview_practice.capital.model.Reservation;
import com.example.interview_practice.capital.model.Table;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService {


    private List<Table> tables;

    private List<Reservation> reservations;

    public ReservationService(List<Table> tables, List<Reservation> reservations) {
        this.tables = tables;
        this.reservations = reservations;
    }

    public int reserve(int partySize, LocalTime startTime){

        // check party size with table size
        // check if time slot with reseravtion list
        // assign smallest availabe table

        List<Table> availableTables = tables.stream()
                .filter(table -> table.size() >= partySize)
                .toList();

        Set<Integer> busyTables = reservations.stream()
                .filter(reservation -> startTime.equals(reservation.startTime()))
                .map(reservation -> reservation.tableId())
                .collect(Collectors.toSet());


        // finding right table
        var tableId = availableTables.stream()
                .filter(table -> !busyTables.contains(table.id()))
                .sorted(Comparator.comparingInt(Table::size))
                .map(table -> table.id())
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Don't have available table"));


        reservations.add(new Reservation(tableId, partySize, startTime));

        return tableId;
    }


    public int cancelation(int tableId, LocalTime startTime){

        var res = reservations.stream()
                .filter(reservation -> reservation.tableId() == tableId && startTime.equals(reservation.startTime()))
                .findFirst()
                .orElseThrow();

        reservations.remove(res);

        return res.tableId();
    }


}
