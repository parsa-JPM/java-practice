package com.example.interview_practice.codechallenges;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class FindNthLargerstNumber {

    /*
     * Given list of unordered non-distinct int numbers, create a method 
     * that finds the n-th largest number
     * 
     * Example: 
     * input: {1,3,2,6,7,6,2,5,2,10}, n=2
     * output: 7
     */
    public int solution(int[] nums, int nth) {
        Arrays.sort(nums);

        return nums[nums.length - nth];
    }

    @Test
    public void test() {
        System.out.println(solution(new int[]{1, 3, 2, 6, 7, 6, 2, 5, 2, 10}, 2));
    }
}
