package com.example.interview_practice;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class TestCode {

    @Test
    void test() {
        System.out.println(Integer.decode("-1337"));
        int[] input = {2, 3, 4};
        var r = Arrays.stream(input)
                .reduce(1, (a, b) -> a * b);
        System.out.println(r);
    }
}
