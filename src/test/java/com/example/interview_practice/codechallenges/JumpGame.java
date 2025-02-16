package com.example.interview_practice.codechallenges;


// You are given an integer array nums.
// You are initially positioned at the array's first index,
// and each element in the array represents your maximum jump length at that position (note: you don't have to jump in max).
//
// Return true if you can reach the last index, or false otherwise.
//
//
//
//     Example 1:
// Input: nums = [2,3,1,1,4]
// Output: true
// Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
//     Example 2:
//
// Input: nums = [3,2,1,0,4]
// Output: false
// Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JumpGame {

    @Test
    public void test_DP_jump() {
        int[] input = {2, 3, 1, 1, 4};
        assertTrue(DPJump.canJump(input));
        assertTrue(canJumpGreedy(input));

        int[] input2 = {3, 2, 1, 0, 4};
        assertFalse(DPJump.canJump(input2));
        assertFalse(canJumpGreedy(input2));
    }

    /*
      this is Greedy and also used pointer algo with O(n) time complexity and O(1) space complexity
     */
    private boolean canJumpGreedy(int[] nums) {
        var goal = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            var jumpNeeded = goal - i;
            if (nums[i] >= jumpNeeded) {
                goal = i;
            }
        }

        return goal == 0;
    }

    /*
      this is Solution with DP (dynamic programming) O(n^n) without caching but with caching it will be O(n)
      it's tested with Leetcode's test cases
    */
    static class DPJump {

        public static boolean canJump(int[] nums) {
            Map<Integer, Boolean> cache = new HashMap<>();
            return jumpTo(nums, 0, cache);
        }


        private static boolean jumpTo(int[] nums, int index, Map<Integer, Boolean> cache) {
            if (index >= nums.length - 1)
                return true;

            if (nums[index] == 0)
                return false;

            int maxJump = nums[index];
            while (maxJump > 0) {
                var canJump = false;
                var jumpToIndex = index + maxJump;

                if (cache.containsKey(jumpToIndex)) {
                    canJump = cache.get(jumpToIndex);
                } else {
                    canJump = jumpTo(nums, jumpToIndex, cache);
                    cache.put(jumpToIndex, canJump);
                }

                if (canJump) {
                    return true;
                }
                maxJump--;
            }

            return false;
        }
    }
}
