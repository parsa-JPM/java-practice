package com.example.interview_practice.codechallenges;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestUniqueConcatenated {

    /*
    Examples:
        1.Given A = ['co", "dil", "ity", your function should return 5. The resulting string S could be "codil", "dilco", "coity" or "Ityco".
        2.Given A = l'abc", "yyy", "def", "csv"], your function should return 6. The resulting string S could be "abcdef", "defabc", "defesv" or
        "csdef".
        3. Given A = ['potato", "kayak", "banana", "racecar"], your function should return 0. It is impossible to choose any of these strings as each
        of them contains repeating letters.
        4. Given A =[ "eva", "jqw", "tyn", "jan], your function should return 9. One of the possible strings of this length is "evajqwtyn".
     */
    @Test
    void runSolution() {
        String[] A1 = {"co", "dil", "ity"};
        String[] A2 = {"abc", "yyy", "def", "csv"};
        String[] A3 = {"potato", "kayak", "banana", "racecar"};
        String[] A4 = {"eva", "jqw", "tyn", "jan"};
        String[] A5 = {"a", "b", "c"};
        String[] A6 = {};
        String[] A7 = {" "};

        assertEquals(5, solution(A1));
        assertEquals(6, solution(A2));
        assertEquals(0, solution(A3));
        assertEquals(9, solution(A4));
        assertEquals(3, solution(A5));
        assertEquals(0, solution(A6));
        assertEquals(1, solution(A7));
    }


    @Test
    void runSolution2() {
        String[] A1 = {"co", "dil", "ity"};
        String[] A2 = {"abc", "yyy", "def", "csv"};
        String[] A3 = {"potato", "kayak", "banana", "racecar"};
        String[] A4 = {"eva", "jqw", "tyn", "jan"};
        String[] A5 = {"a", "b", "c"};
        String[] A6 = {};
        String[] A7 = {""};

        assertEquals(5, solution2(A1));
        assertEquals(6, solution2(A2));
        assertEquals(0, solution2(A3));
        assertEquals(9, solution2(A4));
        assertEquals(3, solution2(A5));
        assertEquals(0, solution2(A6));
        assertEquals(0, solution2(A7));
    }

    int solution(String[] A) {
        List<String> allPossibleStrs = findSubStrings(A, 0, "", new LinkedList<>());
        System.out.println(allPossibleStrs);
        int maxLen = 0;
        for (String s : allPossibleStrs) {
            if (isUnique(s) && s.length() > maxLen) {
                maxLen = s.length();
            }
        }

        return maxLen;
    }

    List<String> findSubStrings(String[] items, int i, String substring, List<String> allFoundStrs) {
        if (i == items.length) {
            allFoundStrs.add(substring);
            return allFoundStrs;
        }

        // Skip the current string (items[i])
        findSubStrings(items, i + 1, substring, allFoundStrs);

        // Include the current string (items[i])
        findSubStrings(items, i + 1, substring + items[i], allFoundStrs);

        return allFoundStrs;
    }

     boolean isUnique(String s) {
        HashSet<Character> charSet = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (charSet.contains(c)) {
                return false;
            }
            charSet.add(c);
        }
        return true;
    }

    int solution2(String[] A) {
        return findMaxLenString(A, 0, "", 0);
    }

    int findMaxLenString(String[] strs, int i, String str, int maxLen) {
        if (i == strs.length) {
            if (isUnique(str) && str.length() > maxLen) {
                // if we use mutable AtomicInteger to keep max len we don't need get and pass in recursion methods
                maxLen = str.length();
            }

            return maxLen;
        }

        int maxLenString = findMaxLenString(strs, i + 1, str, maxLen);

        return findMaxLenString(strs, i + 1, str + strs[i].trim(), maxLenString);
    }
}
