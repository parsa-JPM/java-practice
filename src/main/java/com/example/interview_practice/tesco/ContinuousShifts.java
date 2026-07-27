package com.example.interview_practice.tesco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ContinuousShifts {


    // O(n log n)
    public List<int[]> mergeShifts(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));
        List<int[]> result = new ArrayList<>();

        //{9,11},{10,13},{12,15}
        for (int[] interval : intervals) {
            if (result.isEmpty()) {
                result.add(new int[]{interval[0], interval[1]});
            } else {
                int[] lastInterval = result.getLast();
                if (interval[0] <= lastInterval[1]) {
                    lastInterval[1] = Math.max(interval[1], lastInterval[1]);
                } else {
                    result.add(new int[]{interval[0], interval[1]});
                }
            }
        }

        return result;
    }


    public List<int[]> bucketMerge(int[][] intervals) {
        boolean[] timeBucket = new boolean[25];
        for (int[] interval : intervals) {
            // < is important e.g in {10, 15} we don't count 15 [10,15)
            for (int i = interval[0]; i < interval[1]; i++) {
                timeBucket[i] = true;
            }
        }

        List<int[]> result = new ArrayList<>();
        for (int i = 1; i < timeBucket.length; i++) {
            if (!timeBucket[i]) {
                continue;
            }

            int startTime = i;
            int endTime = 0;
            while (timeBucket[i]) {
                endTime = i;
                i++;
            }

            result.add(new int[]{startTime, endTime + 1});
        }

        return result;
    }


}
