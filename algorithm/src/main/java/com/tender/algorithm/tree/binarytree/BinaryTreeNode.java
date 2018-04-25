package com.tender.algorithm.tree.binarytree;

/**
 * Created by boyu on 2018/4/25.
 * 二叉树结点
 */

public class BinaryTreeNode {
    private int value;
    private BinaryTreeNode leftNode;
    private BinaryTreeNode rightNode;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinaryTreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(BinaryTreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public BinaryTreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(BinaryTreeNode rightNode) {
        this.rightNode = rightNode;
    }
}
