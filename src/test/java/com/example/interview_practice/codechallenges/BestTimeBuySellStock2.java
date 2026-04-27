package com.example.interview_practice.codechallenges;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BestTimeBuySellStock2 {


    /*
        You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
        On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time.
        However, you can sell and buy the stock multiple times on the same day, ensuring you never hold more than one share of the stock.
        Find and return the maximum profit you can achieve.

        Example 1:
        Input: prices = [7,1,5,3,6,4]
        Output: 7
        Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
        Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
        Total profit is 4 + 3 = 7.

        Example 2:
        Input: prices = [1,2,3,4,5]
        Output: 4
        Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
        Total profit is 4.

        Example 3:
        Input: prices = [7,6,4,3,1]
        Output: 0
        Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
     */
    static Stream<Arguments> maxProfitCases() {
        return Stream.of(
                // LeetCode examples
                Arguments.of("leetcode example 1 - two transactions",          new int[]{7, 1, 5, 3, 6, 4}, 7),
                Arguments.of("leetcode example 2 - continuously rising",       new int[]{1, 2, 3, 4, 5},    4),
                Arguments.of("leetcode example 3 - descending prices",         new int[]{7, 6, 4, 3, 1},    0),
                // Edge cases
                Arguments.of("single element - no sell day",                   new int[]{5},                0),
                Arguments.of("two elements - profit possible",                 new int[]{1, 5},             4),
                Arguments.of("two elements - no profit",                       new int[]{5, 1},             0),
                Arguments.of("all same prices - zero spread",                  new int[]{3, 3, 3, 3},       0),
                Arguments.of("all zeros",                                      new int[]{0, 0, 0, 0},       0),
                // Multiple transactions
                Arguments.of("valley-peak-valley-peak - capture both swings",  new int[]{1, 4, 2, 7},       8),
                Arguments.of("zigzag - buy and sell every other day",          new int[]{1, 3, 1, 3, 1, 3}, 6),
                Arguments.of("mixed gains and losses - only count gains",      new int[]{6, 1, 3, 2, 4, 7}, 7),
                // Profit only from subset of days
                Arguments.of("drop then rise - one transaction suffices",      new int[]{5, 1, 6},          5),
                Arguments.of("rise then drop - sell before it falls",          new int[]{1, 9, 1},          8)
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("maxProfitCases")
    void testMaxProfit(String description, int[] prices, int expected) {
        assertEquals(expected, maxProfit(prices));
    }

    public int maxProfit(int[] prices) {
        int max = 0;
        int l = 0;
        int r = 1;
        while (r < prices.length) {
            var profit = prices[r] - prices[l];
            if (profit > 0) {
                max += profit;
            }

            l++;
            r++;
        }

        return max;
    }
}
