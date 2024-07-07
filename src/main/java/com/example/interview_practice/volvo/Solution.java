package com.example.interview_practice.volvo;

import java.util.HashSet;
import java.util.Set;

class Solution {
    public int solution(String[] A) {
        String finalResult = "";
        // first for
        for (String word : A) {
            String concatString = "";
            Set<Character> charSet = new HashSet<>();

            boolean repeatedLetter = false;
            for (char c : word.toCharArray()) {
                if (charSet.contains(c))
                    repeatedLetter = true;
                charSet.add(c);
            }

            if (repeatedLetter)
                continue;

            // second for
            for (String concatWord : A) {

                for (char c : concatWord.toCharArray()) {
                    if (charSet.contains(c))
                        repeatedLetter = true;
                    charSet.add(c);
                }

                if (repeatedLetter)
                    continue;

                // if two word don't have common word
                boolean flag = true;
                for (char c : concatWord.toCharArray()) {
                    if (charSet.contains(c)) {
                        flag = false;
                        break;
                    }
                }

                //then concatinate and add word to the Set
                if (flag) {
                    if (!concatString.isEmpty())
                        concatString = concatString + concatWord;
                    else
                        concatString = word + concatWord;

                    for (char d : concatWord.toCharArray()) {
                        charSet.add(d);
                    }
                }
            }

            if (concatString.length()> finalResult.length()){
                finalResult = concatString;
            }
        }

        System.out.println(finalResult);
        return finalResult.length();
    }

    public static void main(String[] args) {
        System.out.println(new Solution().solution(new String[]{"yyy", "csv"}));
    }
}


