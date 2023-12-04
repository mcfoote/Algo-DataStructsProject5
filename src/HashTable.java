
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

    // Hash function as specified in your rubric
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
            System.out.print(i + "- ");
            table[i].levelOrder();  // or any other order you prefer
            System.out.println();
        }
    }

}