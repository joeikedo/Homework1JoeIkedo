import java.util.Stack;

/**
 * Class for a sorted BinarySearchTree that holds data of type E
 * @param <E> The type of data to hold in the tree
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E>
        implements SearchTree<E> {
    /** Return from the public add method that indicates if it was inserted */
    protected boolean addReturn;
    /** Return from the public delete method that references the item that was stored */
    protected E deleteReturn;

    /**
     * Add starter
     * pre: the item to insert implements Comparable
     * @param item The item being inserted
     * @return true if the object is inserted, false if it already exists
     */
    @Override
    public boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }

    /**
     * Recursive add method.
     * post: The data field addReturn is set true if the item is added to the tree, false
     * if the item is already in the tree.
     * @param localRoot the local root of the subtree
     * @param item the object to be inserted
     * @return the local root that no contains the inserted item
     */
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree - insert it
            addReturn = true;
            return new Node<E>(item);
        }

        // Search for item
        int compResult = item.compareTo(localRoot.data);
        if (compResult == 0) {
            // item is equal to localRoot.data, already exists
            addReturn = false;
            return localRoot;
        }
        else if (compResult < 0) {
            // item is less than localRoot.data, try to add to left
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        }
        else {
            // item is greater than localRoot.data, try to add to right
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }

    @Override
    public boolean contains(E target) {
        // If find returns null, the target does not exist
        return find(root, target) != null;
    }

    @Override
    public E find(E target) {
        return find(root, target);
    }

    /**
     * Recursive find method
     * @param localRoot The local subtree's root
     * @param target the object being sought
     * @return The object if found, otherwise null
     */
    private E find(Node<E> localRoot, E target) {
        if (localRoot == null)
            return null;

        // Compare target with data field at root
        int compResult = target.compareTo(localRoot.data);
        if (compResult == 0) {
            return localRoot.data;
        }
        else if (compResult < 0)
            return find(localRoot.left, target);
        else
            return find(localRoot.right, target);
    }

    /**
     * Delete starter
     * post: The object is not in the tree
     * @param target The object to be deleted
     * @return The object deleted from the tree or null if the object was not in the tree
     */
    @Override
    public E delete(E target) {
        root = delete(root, target);
        return deleteReturn;
    }

    /**
     * Recursive delete method
     * post: The item is not in the tree, deleteReturn is equal to the delete item as it
     *          was stored in the tree or null if it was not found
     * @param localRoot the root of the current subtree
     * @param item item to be deleted
     * @return The modified local root that does not contain the item any more
     */
    private Node<E> delete(Node<E> localRoot, E item) {
        if (localRoot == null) {
            // item is not in the tree
            deleteReturn = null;
            return localRoot;
        }

        // Search for item to delete
        int compResult = item.compareTo(localRoot.data);
        if (compResult < 0) {
            // item is smaller than localRoot.data, search left
            localRoot.left = delete(localRoot.left, item);
            return localRoot;
        }
        else if (compResult > 0) {
            // item is larger than localRoot.data, search right
            localRoot.right = delete(localRoot.right, item);
            return localRoot;
        }
        else {
            // item is at local root
            deleteReturn = localRoot.data;
            if (localRoot.left == null) {
                // if no left child, return right child which can also be null
                return localRoot.right;
            }
            else if (localRoot.right == null) {
                // If there is no right child, return left child that does exist
                return localRoot.left;
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
                    localRoot.data = findLargestChild(localRoot.left);
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
    private E findLargestChild(Node<E> parent) {
        // If the right child has no right child, it is the inorder predecessor
        if (parent.right.right == null) {
            E returnValue = parent.right.data;
            parent.right = parent.right.left;
            return returnValue;
        }
        else {
            return findLargestChild(parent.right);
        }
    }

    @Override
    public boolean remove(E target) {
        // Attempt to delete target. If removed, deleteReturn will not be null
        delete(root, target);
        return deleteReturn != null;
    }





}