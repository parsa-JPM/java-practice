package com.example.interview_practice.codechallenges;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
  You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0].

  Each element nums[i] represents the maximum length of a forward jump from index i.
   In other words, if you are at nums[i], you can jump to any nums[i + j] where:

  0 <= j <= nums[i] and
  i + j < n
  Return the minimum number of jumps to reach nums[n - 1]. The test cases are generated such that you can reach nums[n - 1].
 */
public class JumpGameTwo {

    @Test
    void test() {
        int[] input1 = {2, 1, 1, 4, 1, 1, 4};
        assertEquals(3, jump(input1));
        assertEquals(3, findMinStepsGreedy(input1));

        int[] input2 = {2, 3, 0, 1, 4};
        assertEquals(2, jump(input2));
        assertEquals(2, findMinStepsGreedy(input2));

        int[] input3 = {5, 9, 3, 2, 1, 0, 2, 3, 3, 1, 0, 0};
        assertEquals(3, jump(input3));
        assertEquals(3, findMinStepsGreedy(input3));

        int[] input4 = {1, 1, 0, 1};
        assertEquals(-1, jump(input4));
        assertEquals(-1, findMinStepsGreedy(input4));

        int[] input5 = {7, 0, 9, 6, 9, 6, 1, 7, 9, 0, 1, 2, 9, 0, 3};
        assertEquals(2, jump(input5));
        assertEquals(2, findMinStepsGreedy(input5));
    }

    public int jump(int[] nums) {
        return findMinStepsDynamic(nums, 0, new HashMap<>());
    }


    /*
       BFS (Breadth-First Search) is a graph/tree traversal algorithm that explores nodes level by level — it visits all neighbors of the current node before going deeper.
       Think of it like ripples in water: you explore everything at distance 1 first, then distance 2, then distance 3, etc.
     */
    private int findMinStepsGreedy(int[] nums) {
        int l = 0;
        int r = 0;
        int goal = nums.length - 1;
        int steps = 0;

        while (r < goal) {
            // this loop check furthestIndex index we can go in specified window
            int furthestIndex = 0;
            for (int i = l; i <= r; i++) {
                // if value is 0 furthestIndex will be same index so in next turn l > r so loop will be ignored.
                furthestIndex = Math.max(furthestIndex, nums[i] + i);
            }

            if (furthestIndex == 0)
                return -1;

            l = r + 1;
            r = furthestIndex;
            steps++;
        }

        return steps;
    }


    /*
      it solves problem with dynamic programming and returns -1 if array cant reach end
      which is unnecessary for the this problem but I did to learn more recursion
     */
    private int findMinStepsDynamic(int[] nums, int index, Map<Integer, Integer> cache) {
        // Base case condition
        if (index >= nums.length - 1) {
            // in the end of array our distance to end is 0
            return 0;
        }

        // Base case condition
        if (cache.containsKey(index)) {
            return cache.get(index);
        }

        var jumps = nums[index];
        // Base case condition
        if (jumps == 0) {
            System.out.println("steps: blocked");
            System.out.println("index: " + index);
            System.out.println("+++++++++");
            return -1;
        }

        // it keeps shortest number of steps for each branch
        var branchMinSteps = Integer.MAX_VALUE;
        var isAllWaysBlocked = true;
        for (int j = 1; j <= jumps; j++) {
            var steps = findMinStepsDynamic(nums, index + j, cache);
            // Here is where one line of branch execution has been completed
            if (steps != -1) {
                isAllWaysBlocked = false;
            }

            if (steps == -1) {
                continue;
            }

            // imagine this [i3]<-[i4] in i4 it returns 0 then in i3 our steps become 1 and our
            // branchMinStep become 1 then it returns 1 in i3's branches our steps become 1+1 = 2 and so on...
            branchMinSteps = Math.min(branchMinSteps, steps + 1);
        }


        System.out.println("Steps: " + (branchMinSteps));
        System.out.println("index: " + index);
        System.out.println("+++++++++");

        if (isAllWaysBlocked) {
            // sign that this branch is totally blocked
            branchMinSteps = -1;
        }

        cache.put(index, branchMinSteps);

        return branchMinSteps;
    }
}
