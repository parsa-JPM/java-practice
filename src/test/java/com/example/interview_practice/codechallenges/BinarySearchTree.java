package com.example.interview_practice.codechallenges;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
   Write java function to find some element in binary search tree
 */
public class BinarySearchTree {

    @Test
    void findTargetNode() {
        TreeNode tree = TreeNode.arrayToTree(new int[]{1, 19, 10, 20, 90});

        Assertions.assertTrue(TreeNode.find(tree, 90));
        Assertions.assertFalse(TreeNode.find(tree, 0));
    }

    @Test
    void find_shouldReturnFalse_whenTreeIsEmpty() {
        Assertions.assertFalse(TreeNode.find(null, 5));
    }

    @Test
    void find_shouldReturnTrue_whenValueIsRoot() {
        TreeNode tree = TreeNode.arrayToTree(new int[]{10, 5, 14});
        Assertions.assertTrue(TreeNode.find(tree, 10));
    }

    @Test
    void find_shouldReturnTrue_whenSingleNodeTree() {
        TreeNode tree = TreeNode.arrayToTree(new int[]{42});
        Assertions.assertTrue(TreeNode.find(tree, 42));
    }

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }

        /*
         we always get same node we pass it i.e add(root, val) returns root always. and it tries to go down the tree to find
         correct place to put val.
         Complexity is o(log n) for balanced tree but for sorted array it is o(n) cause it will be basically Linked list
         */
        static TreeNode add(TreeNode rootNode, int val) {
            if (rootNode == null) return new TreeNode(val);

            if (val > rootNode.val) {
                rootNode.right = add(rootNode.right, val);
            } else if (val < rootNode.val) {
                rootNode.left = add(rootNode.left, val);
            }

            return rootNode;
        }

        static TreeNode arrayToTree(int[] arr) {
            TreeNode root = null;
            for (int val : arr) {
                root = add(root, val);
            }

            return root;
        }

        static boolean find(TreeNode rootNode, int val) {
            TreeNode current = rootNode;
            while (current != null) {
                if (current.val == val) {
                    return true;
                }

                if (current.val < val) {
                    current = current.right;
                } else {
                    current = current.left;
                }
            }
            return false;
        }
    }
}
