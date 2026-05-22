package com.example.interview_practice.codechallenges;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LinkedListReversal {

    @Test
    void reverse_shouldReverseMultipleElements() {
        Node list = Node.arrayToList(new int[]{1, 2, 3, 4, 5});
        Node reversed = Node.reverse(list);
        assertThat(Node.toArray(reversed)).containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    void reverse_shouldReturnSameNode_whenSingleElement() {
        Node list = Node.arrayToList(new int[]{42});
        Node reversed = Node.reverse(list);
        assertThat(Node.toArray(reversed)).containsExactly(42);
    }

    @Test
    void reverse_shouldReturnNull_whenListIsEmpty() {
        Node reversed = Node.reverse(null);
        Assertions.assertNull(reversed);
    }

    @Test
    void reverse_shouldWork_whenTwoElements() {
        Node list = Node.arrayToList(new int[]{1, 2});
        Node reversed = Node.reverse(list);
        assertThat(Node.toArray(reversed)).containsExactly(2, 1);
    }


    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }

        /*
         * Reverses arrows one by one using three pointers.
         * prev starts as null (new tail points to null).
         * At each step: save next, flip arrow, advance both pointers.
         * Returns the new head (last node we visited before current hit null).
         * Complexity: O(n) time, O(1) space — no extra memory needed.
         */
        static Node reverse(Node head) {
            // 1 > 2 > 3 > null
            Node pre = null;
            Node current = head;

            while (current != null){
                Node next = current.next;
                current.next = pre;
                pre = current;
                current = next;
            }

            return pre;
        }

        /*
         * Helper: build a linked list from an array.
         * arrayToList({1,2,3}) → [1] → [2] → [3] → null
         */
        static Node arrayToList(int[] arr) {
            if (arr.length == 0) return null;
            Node head = new Node(arr[0]);
            Node current = head;
            for (int i = 1; i < arr.length; i++) {
                current.next = new Node(arr[i]);
                current = current.next;
            }
            return head;
        }

        /*
         * Helper: collect list values into an array for easy assertion.
         * [1] → [2] → [3] → null  becomes  {1, 2, 3}
         */
        static int[] toArray(Node head) {
            List<Integer> result = new ArrayList<>();
            Node current = head;
            while (current != null) {
                result.add(current.val);
                current = current.next;
            }
            return result.stream().mapToInt(Integer::intValue).toArray();
        }
    }
}