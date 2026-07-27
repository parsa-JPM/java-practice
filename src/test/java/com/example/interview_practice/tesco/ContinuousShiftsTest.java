package com.example.interview_practice.tesco;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ContinuousShiftsTest {

    private final ContinuousShifts continuousShifts = new ContinuousShifts();

    @ParameterizedTest
    @MethodSource("testData")
    void shouldReturnContinuousIntervals_whenUseSorting(int[][] input, int[][] results) {
        Assertions
                .assertThat(continuousShifts.mergeShifts(input))
                .containsExactly(results);
    }

    @ParameterizedTest
    @MethodSource("testData")
    void shouldReturnContinuousIntervals_whenUseBucketMerge(int[][] input, int[][] results) {
        Assertions
                .assertThat(continuousShifts.bucketMerge(input))
                .containsExactly(results);
    }



    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(new int[][]{{10, 12}, {12, 14}, {15, 19}}, new int[][]{{10, 14}, new int[]{15, 19}}),
                Arguments.of(new int[][]{{9, 11}, new int[]{10, 13}, new int[]{12, 15}}, new int[][]{{9, 15}})
        );
    }


}