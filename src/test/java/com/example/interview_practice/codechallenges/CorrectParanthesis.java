package com.example.interview_practice.codechallenges;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Stack;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CorrectParanthesis {

    /*
     * Create a method in Java that checks if parenthesis in a given string are paired correctly. The method should:
        Return true for correct and false for incorrect strings.
        Accept parenthesis (), [], {} and <>.
        Accept inner parenthesis.
        Filter out any other character.

        Correct examples:
        ()()
        (())<>
        ({[]})
        ((()a)())
        (y)

        Incorrect examples:
        ())()
        (()
        ))( (
        (
        (]
        (()<)>
     */
    private boolean solution(String s) {
        if (s.trim().isEmpty()) {
            return true;
        }

        char[] chars = s.toCharArray();
        var pMap = Map.of(')', '(', ']', '[', '}', '{', '>', '<');
        var stack = new Stack<Character>();
        for (Character ch : chars) {
            if (pMap.containsKey(ch)) {
                if (stack.isEmpty()) {
                    return false;
                }

                if (!stack.isEmpty() && pMap.get(ch) == stack.peek()) {
                    stack.pop();
                }

            } else if (!Character.isLetter(ch)) {
                stack.push(ch);
            }
        }

        return stack.isEmpty();
    }

    @ParameterizedTest(name = "{index} => Testing with input: {0}")
    @ValueSource(strings = {
        "()()",
        "(())<>",
        "({[]})",
        "((()a)())",
        "(y)",
        "",
        " "
    })
    public void testWithCorrectStrings(String input) {
        var result = solution(input);

        assertTrue(result);
    }

    @ParameterizedTest(name = "{index} => Testing with input: {0}")
    @ValueSource(strings = {
        "())()",
        "(()",
        "))(( ",
        "(",
        "(]",
        "(()<)>"
    })
    public void testWithIncorrectValues(String input) {
        var result = solution(input);

        assertFalse(result);
    }
}
