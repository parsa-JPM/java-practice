package com.example.interview_practice.codechallenges;

import org.junit.jupiter.api.Test;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Hindex {

    @Test
    void test() {
        int[] input = {3, 0, 6, 1, 5};
        assertEquals(3, hIndex(input));

        int[] input2 = {1, 2, 3, 4, 5};
        assertEquals(3, hIndexMap(input2));

    }

    private int hIndex(int[] citations) {
        var hindex = 0;
        for (int h = 1; h <= citations.length; h++) {
            var matchH = 0;
            for (int i = 0; i < citations.length; i++) {
                if (citations[i] >= h) {
                    matchH++;
                }

                if (matchH == h) {
                    hindex = max(hindex, h);
                    break;
                }
            }
        }

        return hindex;
    }

    private int hIndexMap(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];

        for (int c : citations) {
            buckets[min(c, n)]++;
        }

        int count = 0;
        /*
          bucket array would be like this:
          value: 1 1 1 1 1
          index: 1 2 3 4 5
         */
        for (int i = n; i >= 0; i--) {
            count += buckets[i];
            if (count >= i) {
                return i;
            }
        }

        return 0;
    }
}
