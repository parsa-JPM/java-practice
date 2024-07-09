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

        assertEquals(5, solution(A1));
        assertEquals(6, solution(A2));
        assertEquals(0, solution(A3));
        assertEquals(9, solution(A4));
        assertEquals(3, solution(A5));
    }


    int solution(String[] A) {
        List<String> allPossibleStrs = findSubStrings(A, 0, "", new LinkedList<>());
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

    private boolean isUnique(String s) {
        HashSet<Character> charSet = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (charSet.contains(c)) {
                return false;
            }
            charSet.add(c);
        }
        return true;
    }
}
