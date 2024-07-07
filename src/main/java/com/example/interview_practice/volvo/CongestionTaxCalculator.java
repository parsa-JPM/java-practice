package com.example.interview_practice.volvo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CongestionTaxCalculator {

    // Date array in asc order
    // todo use java 8 DateTime class
    public int getTax(Vehicle vehicle, Date[] dates) {
        Arrays.sort(dates);
        Date intervalStart = dates[0];
        int totalFee = 0;
        int tempFee = getTollFee(vehicle, intervalStart);

        for (Date date : dates) {
            int nextFee = getTollFee(vehicle, date);
            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies / 1000 / 60;

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) {
                    tempFee = nextFee;
                }
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
                // interval restart
                intervalStart = date;
                tempFee = getTollFee(vehicle, intervalStart);
            }
        }

        if (totalFee > 60)
            return 60;

        return totalFee;
    }


    public int getTollFee(Vehicle vehicle, Date date) {
        if (isTollFreeDate(date) || isTollFreeVehicle(vehicle)) return 0;

        int hour = date.getHours();
        int minute = date.getMinutes();

        //todo use switch case
        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        else if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        else if (hour == 7 && minute >= 0 && minute <= 59) return 18;
        else if (hour == 8 && minute >= 0 && minute <= 29) return 13;
            // 08:30–14:59 bug in 14:25:00
        else if (hour >= 8 && hour <= 14) return 8;
        else if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        else if (hour == 15 && minute >= 30 || hour == 16 && minute <= 59) return 18;
        else if (hour == 17 && minute >= 0 && minute <= 59) return 13;
        else if (hour == 18 && minute >= 0 && minute <= 29) return 8;

        else return 0;
    }

    private Boolean isTollFreeDate(Date date) {
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDay() + 1;
        int dayOfMonth = date.getDate();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 113)
            return (month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31));


        return false;
    }


    private boolean isTollFreeVehicle(Vehicle vehicle) {
        if (vehicle == null)
            return false;

        return vehicle.isExempted();
    }
}
