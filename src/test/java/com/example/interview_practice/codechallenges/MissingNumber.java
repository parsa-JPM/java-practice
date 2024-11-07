package com.example.interview_practice.codechallenges;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MissingNumber {

    /*
     * Given an array nums containing n distinct numbers in the range [0, n],
     *  return the only number in the range that is missing from the array. 
 
        Example 1:  
        
        Input: nums = [3,0,1]  
        Output: 2  
        
        Explanation: n = 3 since there are 3 numbers, so all numbers are in the range [0,3].  
        2 is the missing number in the range since it does not appear in nums. 
        
        Example 2: 
        
        Input: nums = [0,1]  
        Output: 2  
        
        Explanation: n = 2 since there are 2 numbers, so all numbers are in the range [0,2].
         2 is the missing number in the range since it does not appear in nums.
     */
    public int solution(int[] range) {
        // cool solution
        return IntStream.range(0, range.length + 1).sum() - Arrays.stream(range).sum();
    }

    @Test
    public void test() {
        int[] input = {3, 0, 1};
        assertEquals(2, solution(input));
    }
}
