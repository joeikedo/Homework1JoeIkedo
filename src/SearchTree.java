/**
 * Interface for class that implements the binary search tree for efficient insertion,
 * search, and retreival of information.
 *
 * @param <E> Type of data contained in tree Nodes
 */
public interface SearchTree<E> {

    /**
     * Adds Node containing item in its sorted place in the tree if it doesn't exist
     * @param item The data to be held in the Node
     * @return true if the object is inserted, false if it already exists
     */
    boolean add(E item);

    /**
     * Returns true if the target is found in the tree
     * @param target the item to search for
     * @return true if the target exists in the tree
     */
    boolean contains(E target);

    /**
     * Returns a reference to the data in the node that is equal to target if it is found.
     * Otherwise returns null.
     * @param target the item to search for
     * @return reference to the data contained in the tree if it exists, otherwise null
     */
    E find(E target);

    /**
     * Removes target from tree and returns it if found. Otherwise returns null
     * @param target the item to search for
     * @return target if found, otherwise null
     */
    E delete(E target);

    /**
     * Removes target if found and returns true, otherwise false
     * @param target the item to search for
     * @return true if target was found and removed, otherwise false
     */
    boolean remove(E target);

}