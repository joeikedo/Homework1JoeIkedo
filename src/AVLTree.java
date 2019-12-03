import java.util.ArrayList;
import java.util.Stack;

/**
 * Self-balancing binary search tree using the algorithm defined by Adelson-Velskii and
 * Landis
 * @param <E> The comparable type of data to be contained in tree Nodes
 */
public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    /** Flag to indicate that height of tree has increased */
    private boolean increase;
    /** Flag to indicate that height of tree has decreased */
    private boolean decrease;


    /**
     * Add starter
     * pre: the item to insert implements Comparable
     * @param item The item being inserted
     * @return true if the object is inserted, false if it already exists
     */
    @Override
    public boolean add(E item) {
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    /**
     * Recursive add method. Inserts the given object into the tree.
     * post:    addReturn is set true if the item is inserted, false if the item already
     *          exists in the tree
     * @param localRoot The local root of the subtree
     * @param item The object to be inserted
     * @return The new local root of the rubtree with the item inserted
     */
    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            // item doesn't exist. Insert it
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }

        // Search for item
        int compResult = item.compareTo(localRoot.data);
        if (compResult == 0) {
            // item is already in tree
            increase = false;
            addReturn = false;
            return localRoot;
        }
        else if (compResult < 0) {
            // item is less than localRoot.data, add left
            localRoot.left = add((AVLNode<E>) localRoot.left, item);

            // Check if rebalance needed and perform
            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot; // No rebalance needed
        }
        else {
            // item is greater than localRoot.data, add right
            localRoot.right = add((AVLNode<E>) localRoot.right, item);

            // Check if rebalance needed and perform
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
            return localRoot; // No rebalance needed
        }
    }

    @Override
    public E delete(E target) {
        decrease = false;
        root = delete((AVLNode<E>) root, target);
        return deleteReturn;
    }

    /**
     * Recursive delete method
     * post: The item is not in the tree, deleteReturn is equal to the deleted item as it
     *          was stored in the tree or null if it was not found
     * @param localRoot the root of the current subtree
     * @param item item to be deleted
     * @return The modified local root that does not contain the item any more
     */
    private AVLNode<E> delete(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree
            decrease = false;
            deleteReturn = null;
            return localRoot;
        }

        // Search for item to delete
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data, search left
            localRoot.left = delete((AVLNode<E>) localRoot.left, item);

            // Check if rebalance needed and perform
            if (decrease) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    decrease = false;
                    return rebalanceRight(localRoot);
                }
            }

            return localRoot;
        }
        else if (compResult > 0) {
            // item is larger than localRoot.data, search right
            localRoot.right = delete((AVLNode<E>) localRoot.right, item);

            // Check if rebalance needed and perform
            if (decrease) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    decrease = false;
                    return rebalanceLeft(localRoot);
                }
            }

            return localRoot;
        }
        else {
            // item is at local root
            decrease = true;
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // if no left child, return right child, which can also be null
                return (AVLNode<E>) localRoot.right;
            }
            else if (localRoot.right == null) {
                // If there is no right child, return left child that does exist
                return (AVLNode<E>) localRoot.left;
            }
            else {
                // Node being deleted has 2 children. Replace data with inorder predecessor.
                if (localRoot.left.right == null) {
                    // left child has no right child. Replace the with data in left child
                    localRoot.data = localRoot.left.data;
                    // Replace the left child with its left child
                    localRoot.left = localRoot.left.left;
                    return localRoot;
                }
                else {
                    // Search for the inorder predecessor and replace deleted node's data
                    // with it
                    localRoot.data = findLargestChild((AVLNode<E>) localRoot.left);
                    return localRoot;
                }
            }
        }
    }

    /**
     * Find Node that is the inorder predecessor and replace it with its left child (if any)
     * post: The inorder predecessor is removed from the tree
     * @param parent The parent of possible inorder predecessor
     * @return the data in the inorder predecessor
     */
    private E findLargestChild(AVLNode<E> parent) {
        // If the right child has no right child, it is the inorder predecessor
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        }
        else {
            return findLargestChild((AVLNode<E>) parent.right);
        }
    }

    /**
     * Method to rebalance left
     * pre:     localRoot is the root of an AVL subtree that is critically left-heavy
     * post:    balance is restored
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        // Obtain reference to left child
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        // See whether left-right heavy
        if (leftChild.balance > AVLNode.BALANCED) {
            // Obtain reference to left-right child
            AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;

            // Adjust balances to what they will be after rebalance
            if (leftRightChild.balance < AVLNode.BALANCED) {
                // left-right-left case
                leftChild.balance= AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            }
            else if (leftRightChild.balance > AVLNode.BALANCED) {
                // left-right-right case
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            else {
                // left-right balanced case
                leftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform the left rotation
            localRoot.left = rotateLeft(leftChild);
        }
        else {
            // Left-Left case
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // rotate localroot right
        decrease = true;
        return (AVLNode<E>) rotateRight(localRoot);
    }


    /**
     * Method to rebalance right
     * pre:     localRoot is the root of an AVL subtree that is critically right-heavy
     * post:    balance is restored
     * @param localRoot Root of the AVL subtree that needs rebalancing
     * @return a new localRoot
     */
    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        // Obtain reference to right child
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        // See whether right-left heavy
        if (rightChild.balance < AVLNode.BALANCED) {
            // Obtain reference to right-left child
            AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.left;

            // Adjust balances to what they will be after rebalance
            if (rightLeftChild.balance > AVLNode.BALANCED) {
                // right-left-right case
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            }
            else if (rightLeftChild.balance < AVLNode.BALANCED) {
                // right-left-left case
                rightChild.balance= AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            else {
                // right-left balanced case
                rightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }
            // Perform the right rotation
            localRoot.right = rotateRight(rightChild);
        }
        else {
            // right-right case
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        // rotate localroot left
        decrease = true;
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    /**
     * Decrease balance of local subtree by 1
     * @param localRoot the root of the local subtree
     */
    private void decrementBalance(AVLNode<E> localRoot) {
        localRoot.balance--;
        if (localRoot.balance == AVLNode.BALANCED) {
            // If adding and now balanced, overall height has not increased
            increase = false;
            // If deleting and now balanced, overall height has decreased
            decrease = true;
        }
        else {
            decrease = false;
        }
    }

    /**
     * Increase balance of local subtree by 1
     * @param localRoot the root of the local subtree
     */
    private void incrementBalance(AVLNode<E> localRoot) {
        localRoot.balance++;
        if (localRoot.balance == AVLNode.BALANCED) {
            // If adding and now balanced, overall height has not increased
            increase = false;
            // If deleting and now balanced, overall height has decreased
            decrease = true;
        }
        else {
            decrease = false;
        }
    }

    /**
     * Nested class to represent an AVL Node. It extends the BinaryTree.Node by adding the balance field.
     *
     * @author Kevin
     * @version 1.0
     */
    private static class AVLNode<E> extends Node<E> {
        public static final int LEFT_HEAVY = -1;

        public static final int BALANCED = 0;

        public static final int RIGHT_HEAVY = 1;

        private int balance;

        public AVLNode(E item) {
            super(item);
            balance = BALANCED;
        }
    }



    /**
     * The searchWithSuggestions method. Searches the tree for the target, and while it does so it adds the
     * nodes it views during its search into the input Stack passed as parameter.
     *
     * @param target The word being searched for
     * @param inputStack The stack that all the nodes viewed during the search will be put into.
     *
     * @return returns true if the word is found in the tree, otherwise returns false.
     */
    public boolean searchWithSuggestions(E target, Stack<Node> inputStack){
        return searchWithSuggestions(root, target, inputStack);
    }

    /**
     * The recursive searchWithSuggestions method.
     *
     * @param localRoot the local root to start at
     * @param target The word being searched for
     * @param inputStack The stack that all the nodes viewed during the search will be put into.
     */
    private boolean searchWithSuggestions(Node<E> localRoot, E target, Stack<Node> inputStack){
        if(localRoot == null){
            return false; // Tree is empty so word can't be there.
        }
        // Compare the target with the data field at the root.
        int compResult = target.compareTo(localRoot.data);
        if (compResult == 0){
            inputStack.push(localRoot);
            return true; // The word was found to be in the dictionary tree.
        }
        else if (compResult < 0){
            inputStack.push(localRoot); // add the node into the stack
            return searchWithSuggestions(localRoot.left, target, inputStack);
        }
        else{
            inputStack.push(localRoot); // add the node into the stack
            return searchWithSuggestions(localRoot.right, target, inputStack);
        }
    }


    /**
     * The searchSubtreeSuggestions method. This method uses the preOrderTraverse method to gather all the
     * node data in a subtree starting at the specified node taken as parameter.
     *
     * @param node The node that will be considered the root of the whole subtree.
     * @param inputArrayList The arraylist that will hold the data of all the nodes in the subtree.
     *
     */
    public void searchSubtreeSuggestions(Node<E> node, ArrayList<String> inputArrayList){
        preOrderTraverse(node, inputArrayList);
    }

    /**
     * The recursive preOrderTraverse method. This method is used by the searchSubtreeSuggestions method. It starts
     * at a specified node to be the root of the whole subtree, and then stores all the nodes' data in the array
     * list passed as parameter during its pre order traversal.
     *
     * @param node The node that will be considered the root of the whole subtree.
     * @param inputArrayList The arraylist that will hold the data of all the nodes in the subtree.
     *
     */
    private void preOrderTraverse(Node<E> node,  ArrayList<String> inputArrayList){
        if(node == null){
            // This is the base case, nothing needs to go here, it's just needed to avoid infinite recursion.
        }
        else{
            String temp = (String)node.data; // Need to typecast the data as a String
            inputArrayList.add(temp); // Add the node to the ArrayList

            preOrderTraverse(node.left,  inputArrayList);
            preOrderTraverse(node.right,  inputArrayList);
        }
    }


}