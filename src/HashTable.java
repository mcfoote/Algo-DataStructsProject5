/*
 * Programmer: 	Mitchell Foote
 * Course: 	   	COSC 311, F'23
 * Project:    	5
 * Due date:   	12-5-23
 * Class Description: A hash table built to implement the projects index using BST.
 */
public class HashTable {
    // Number of buckets in the hash table
    private static final int SIZE = 37;
    
    // Array of BSTs for separate chaining
    private BST<Pair<Integer>>[] table;

    public HashTable() {
        table = new BST[SIZE];
        // Initialize each bucket as a new BST
        for (int i = 0; i < SIZE; i++) {
            table[i] = new BST<>();
        }
    }

    // Hash function as specified
    private int hash(int key) {
        return ((key * key) >>> 10) % SIZE;
    }

    // Method to insert a key-address pair into the hash table
    public void insert(int key, int address) {
        int index = hash(key);
        Pair<Integer> pair = new Pair<>(key, address);
        table[index].add(pair);
    }

    // Method to find a record in the hash table
    public Integer find(int key) {
        int index = hash(key);
        Pair<Integer> pair = new Pair<>(key, null);
        BST.Node<Pair<Integer>> node = table[index].findNode(pair);
        return (node != null) ? node.getData().getSecond() : null;
    }

    // Method to delete a record from the hash table
    public void delete(int key) {
        int index = hash(key);
        Pair<Integer> pair = new Pair<>(key, null);
        table[index].delete(pair);
    }

    // Method to display the hash table
    public void display() {
        for (int i = 0; i < SIZE; i++) {
            // Check if the BST at this index is not empty before printing
            if (!table[i].isEmpty()) {
                System.out.print(i + "- ");
                table[i].levelOrder(); 
                System.out.println();
            }
        }
    }
    
    // Method for displaying from a specific range
    public void displayRange(int start, int end) {
        for (int i = start; i <= end; i++) {
            if (!table[i].isEmpty()) {
                System.out.print(i + "- ");
                table[i].levelOrder(); 
                System.out.println();
            }
        }
    }
    
    //Bool method to check for empty table
    public boolean isEmpty() {
        for (BST<Pair<Integer>> bst : table) {
            if (!bst.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}