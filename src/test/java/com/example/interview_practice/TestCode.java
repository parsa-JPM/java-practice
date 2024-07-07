package com.example.interview_practice;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TestCode {


    @Test
    void test() {
        var list = Arrays.asList(1, 3, 5, 6, 7, 2, 4, 8, 9);
        Collections.sort(list, Comparator.reverseOrder());
        System.out.println(list);
        System.out.println(Collections.max(list));
        System.out.println(Collections.min(list));
    }
}
