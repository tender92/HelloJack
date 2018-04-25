package com.tender.algorithm.tree.binarytree;

import com.tender.algorithm.tools.ArrayUtil;

import java.util.Arrays;

/**
 * Created by boyu on 2018/4/25.
 * 二叉树
 * 每个结点最多有两个孩子，左孩子小于父结点，右孩子大于父结点
 * 前序遍历、中序遍历、后序遍历
 */

public class BinaryTree {

    private BinaryTreeNode root;

    /**
     * 输入二叉树的前序遍历和中序遍历的结果,重建出该二叉树。
     * 前序遍历第一个结点是父结点，中序遍历如果遍历到父结点，那么父结点前面的结点是左子树的结点，后边的结点的右子树的结点
     * @param preOrder
     * @param inOrder
     * @return
     * @throws Exception
     */
    public BinaryTreeNode construct(int[] preOrder, int[] inOrder) throws Exception {
        if (preOrder == null || inOrder == null) {
            return null;
        }
        if (preOrder.length != inOrder.length) {
            throw new Exception("长度不一样，非法输入!!");
        }
        BinaryTreeNode rootNode = new BinaryTreeNode();
        for (int i = 0; i < preOrder.length; i ++) {
            if (preOrder[0] == inOrder[i]) {
                rootNode.setValue(preOrder[0]);
                System.out.println("创建结点：" + preOrder[0]);
                if (i == 0) {
                    rootNode.setLeftNode(null);
                } else {
                    rootNode.setLeftNode(construct(Arrays.copyOfRange(preOrder, 1, i + 1), Arrays.copyOfRange(inOrder, 0, i)));
                }
                if (i == preOrder.length - 1) {
                    rootNode.setRightNode(null);
                } else {
                    rootNode.setRightNode(construct(Arrays.copyOfRange(preOrder, i + 1, preOrder.length), Arrays.copyOfRange(inOrder, i + 1, inOrder.length)));
                }
            }
        }
        return rootNode;
    }

    public void insert(int value) {
        System.out.print(" " + value);
        BinaryTreeNode newNode = new BinaryTreeNode(value);
        if (root == null) {
            root = newNode;
            root.setLeftNode(null);
            root.setRightNode(null);
        } else {
            BinaryTreeNode currentNode = root;
            BinaryTreeNode parentNode;
            while (true) {
                parentNode = currentNode;
                if (newNode.getValue() > currentNode.getValue()) {//往右节点放置
                    currentNode = currentNode.getRightNode();
                    if (currentNode == null) {
                        parentNode.setRightNode(newNode);
                        return;
                    }
                } else {//往左节点放置
                    currentNode = parentNode.getLeftNode();
                    if (currentNode == null) {
                        parentNode.setLeftNode(newNode);
                        return;
                    }
                }

            }
        }
    }

    public BinaryTreeNode query(int value) {
        BinaryTreeNode currentNode = root;
        if (currentNode != null) {
            while (currentNode.getValue() != value) {
                if (currentNode.getValue() > value) {
                    currentNode = currentNode.getLeftNode();
                } else {
                    currentNode = currentNode.getRightNode();
                }
                if (currentNode == null) {
                    return null;
                }
            }
            return currentNode;
        }
        return null;
    }

    /**
     * 二叉树的前序遍历
     * 先根后左再右
     * @param treeNode
     */
    public void preOrder(BinaryTreeNode treeNode) {
        System.out.print(" " + treeNode.getValue());
        if (treeNode.getLeftNode() != null) {
            preOrder(treeNode.getLeftNode());
        }
        if (treeNode.getRightNode() != null) {
            preOrder(treeNode.getRightNode());
        }
    }

    /**
     * 二叉树的中序遍历
     * 先左后根再右
     * @param treeNode
     */
    public void inOrder(BinaryTreeNode treeNode) {
        if (treeNode != null) {
            inOrder(treeNode.getLeftNode());
            System.out.print(" " + treeNode.getValue());
            inOrder(treeNode.getRightNode());
        }
    }

    /**
     * 二叉树的后序遍历
     * 先左后右再根
     * @param treeNode
     */
    public void postOrder(BinaryTreeNode treeNode) {
        if (treeNode.getLeftNode() != null) {
            postOrder(treeNode.getLeftNode());
        }
        if (treeNode.getRightNode() != null) {
            postOrder(treeNode.getRightNode());
        }
        System.out.print(" " + treeNode.getValue());
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        int queryValue = -1;
        System.out.println("二叉树中插入数据：");
        for (int i = 0; i < 10; i ++) {
            queryValue = ArrayUtil.random100();
            binaryTree.insert(queryValue);
        }

        System.out.println();
        if (binaryTree.query(queryValue) != null) {
            System.out.println(queryValue + "在二叉树中找到了^ _ ^");
        } else {
            System.out.println(queryValue + "在二叉树中没有找到- _ -");
        }

        System.out.println("二叉树的前序遍历结果：");
        binaryTree.preOrder(binaryTree.root);
        System.out.println();
        System.out.println("二叉树的中序遍历结果：");
        binaryTree.inOrder(binaryTree.root);
        System.out.println();
        System.out.println("二叉树的后序遍历结果：");
        binaryTree.postOrder(binaryTree.root);
        System.out.println();

        int[] preOrder = new int[] {18, 43, 42, 25, 28, 50, 44, 58, 75, 99};
        int[] inOrder = new int[] {18, 25, 28, 42, 43, 44, 50, 58, 75, 99};
        try {
            BinaryTreeNode rootNode = binaryTree.construct(preOrder, inOrder);
            System.out.println("该二叉树的后序遍历结果：");
            binaryTree.postOrder(rootNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
