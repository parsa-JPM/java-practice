package com.example.interview_practice.codechallenges;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MaxNumber {

    @Test
    public void maxTest() {
        int[] ints = {10, 15, 8, 49, 25, 98, 98, 32, 15};
        assertEquals(98, findMax(ints));
        assertEquals(98, findMaxWithStream(ints));
        assertEquals(98, findMaxWithPureStream(ints));

    }

    private int findMax(int[] ints) {
        int max = Integer.MIN_VALUE;
        for (int n : ints) {
            if (n > max) {
                max = n;
            }
        }

        return max;
    }

    private int findMaxWithStream(int[] ints) {
        return Arrays.stream(ints).max().orElse(0);
    }


    // int[] numbers = {3, 7, 2, 9, 5, 10, 6};
    //First call: a = 3, b = 7
    //     7 is greater → Keep 7
    // Second call
    //     : a  = 7, b = 2
    //     7 is greater → Keep 7
    // Third call
    //     : a  = 7, b = 9
    //     9 is greater → Keep 9
    private int findMaxWithPureStream(int[] ints) {
        return Arrays.stream(ints)
                .reduce((a, b) -> {
                    return a > b ? a : b;
                })
                .orElse(0);
    }
}
