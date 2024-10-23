package com.example.interview_practice.codechallenges;

import java.util.Map;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
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
    @SuppressWarnings({"BoxedValueEquality", "NumberEquality", "unlikely-arg-type"})
    private boolean solution(String s) {
        // todo check empty string 
        // todo check string with only closed p
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

    @ParameterizedTest
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

    @ParameterizedTest
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
