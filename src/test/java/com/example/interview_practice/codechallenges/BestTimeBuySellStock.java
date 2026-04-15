package com.example.interview_practice.codechallenges;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BestTimeBuySellStock {

    /*
    You are given an array prices where prices[i] is the price of a given stock on the ith day.
    You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
    Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

    Example 1:

    Input: prices = [7,1,5,3,6,4]
    Output: 5
    Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
    Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
    Example 2:

    Input: prices = [7,6,4,3,1]
    Output: 0
    Explanation: In this case, no transactions are done and the max profit = 0.

    Constraints:

        1 <= prices.length <= 105
        0 <= prices[i] <= 104
     */
    static Stream<Arguments> maxProfitCases() {
        return Stream.of(
                Arguments.of("leetcode example 1 - buy low sell high",       new int[]{7, 1, 5, 3, 6, 4}, 5),
                Arguments.of("leetcode example 2 - descending prices",       new int[]{7, 6, 4, 3, 1},    0),
                Arguments.of("single element - no sell day available",       new int[]{5},                0),
                Arguments.of("two elements - profit possible",               new int[]{1, 5},             4),
                Arguments.of("two elements - no profit",                     new int[]{5, 1},             0),
                Arguments.of("all same prices - zero spread",                new int[]{3, 3, 3, 3},       0),
                Arguments.of("all zeros - zero spread",                      new int[]{0, 0, 0, 0},       0),
                Arguments.of("strictly ascending - sell at last day",        new int[]{1, 2, 3, 4, 5},    4),
                Arguments.of("valley then peak at the very end",             new int[]{5, 3, 8, 2, 0, 9}, 9),
                Arguments.of("multiple valleys - pick global max",           new int[]{3, 1, 5, 2, 8, 4}, 7),
                Arguments.of("peak in the middle - ignore later dip",        new int[]{5, 2, 10, 7, 1},   8)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("maxProfitCases")
    void testMaxProfit(String description, int[] prices, int expected) {
        assertEquals(expected, maxProfit(prices));
    }

    private int maxProfit(int[] prices) {
        var maxProfit = 0;
        int l = 0;
        int r = 1;
        while (r < prices.length) {
            maxProfit = Math.max(maxProfit, prices[r] - prices[l]);
            if (prices[l] > prices[r]) {
                l = r;
                r++;
            } else {
                r++;
            }
        }

        return maxProfit;
    }
}
