package com.example.interview_practice.volvo;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

class CongestionTaxCalculatorTest {


    @Test
    void calculateTax() throws ParseException {
        CongestionTaxCalculator calculator = new CongestionTaxCalculator();
        List<Date> dates = new LinkedList<>();
        for (String sDate : datesList()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(sDate);
            dates.add(date);
            System.out.println(sDate + " " + calculator.getTollFee(Vehicle.Diplomat, date));
        }

        System.out.println(calculator.getTax(Vehicle.Diplomat, dates.toArray(Date[]::new)));
    }

    String[] datesList() {
        return new String[]{
                "2013-01-14 21:00:00",
                "2013-01-15 21:00:00",
                "2013-02-07 06:23:27",
                "2013-02-07 15:27:00",
                "2013-02-08 06:27:00",
                "2013-02-08 06:20:27",
                "2013-02-08 14:35:00",
                "2013-02-08 15:29:00",
                "2013-02-08 15:47:00",
                "2013-02-08 16:01:00",
                "2013-02-08 16:48:00",
                "2013-02-08 17:49:00",
                "2013-02-08 18:29:00",
                "2013-02-08 18:35:00",
                "2013-03-26 14:25:00",
                "2013-03-28 14:07:27"
        };
    }
}