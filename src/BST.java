
/**
 *  A linked data structure implementation of a binary search tree
 *
 *  @author COSC 311, Fall '23
 *  @version (10-19-23)
 *  
 */

public class BST<E extends Comparable<E>> {
	
	// Class Node is defined as an inner class
	public static class Node <E> {
		
		// data stored in the node
		private E data;
		
		// reference to the root of the left and right subtrees
		private Node<E> left;
		private Node<E> right;
		
		/**
         * Construct a node with the given data value
         * @param item - The data value 
         */
		public Node(E item) {
			data = item;
			left = right = null;
		}
		
		//getter methods for Node object
	    public E getData() {
	        return data;
	    }

	    //getter for left subtree
	    public Node<E> getLeft() {
	        return left;
	    }

	    //getter for right subtree
	    public Node<E> getRight() {
	        return right;
	    }
		
		/** 
		 * Return a string representing the node
		 * @param  None  
		 * @return a string representing the data stored in the node  	
		 */
		public String toString () {
			return data.toString();
		}
	}
	
	//data member
	private Node<E> root;
	
	//bool check for empty BST
	public boolean isEmpty() {
	    return root == null;
	}
	
	//getter returns node root
	public Node<E> getRoot() {
	    return root;
	}
	
	//breadth first traversal of BST
	public void levelOrder() {
	    if (root == null) return;

	    QueueSLL<Node<E>> queue = new QueueSLL<>();
	    queue.offer(root);

	    while (!queue.empty()) {
	        int levelSize = queue.size();

	        for (int i = 0; i < levelSize; i++) {
	            Node<E> current = queue.poll();
	            System.out.print(current.getData() + " ");
	            
	            if (current.getLeft() != null) {
	                queue.offer(current.getLeft());
	            }
	            if (current.getRight() != null) {
	                queue.offer(current.getRight());
	            }
	        }
	        System.out.println(); // Move to the next line after each level
	    }
	}
	
	//breadth first traversal of BST from starting object
	public void levelOrderFrom(E item) {
	    Node<E> startNode = findNode(item);
	    if (startNode == null) {
	        System.out.println("Key not found in the tree.");
	        return;
	    }

	    QueueSLL<Node<E>> queue = new QueueSLL<>();
	    queue.offer(startNode);

	    while (!queue.empty()) {
	        int levelSize = queue.size();

	        for (int i = 0; i < levelSize; i++) {
	            Node<E> current = queue.poll();
	            System.out.print(current.getData() + " ");
	            
	            if (current.getLeft() != null) {
	                queue.offer(current.getLeft());
	            }
	            if (current.getRight() != null) {
	                queue.offer(current.getRight());
	            }
	        }
	        System.out.println(); // Move to the next line after each level
	    }
	}
	
	/**
     * Construct an empty binary search tree 
     * @param none
     */
	public BST () {
		root = null;
	}
	

	/** 
	 * Search for an item in the tree
	 * @param  item  the target value
	 * @return true if item is found, and false otherwise
	 */
	public boolean find (E item) {
		return find (root, item);
	}
	
	/** 
	 * Search for an item in the tree rooted at current
	 * @param  current  the current root
	 * @param  item  the target value
	 * @return true 
	 */
	private boolean find (Node<E> current , E item) {
		if (current == null)
			return false;
		int result = current.data.compareTo(item);
		if (result == 0)
			return true;
		else if (result < 0)
			return find (current.right, item);
		else
			return find (current.left, item);
	}
	
	//expansion of find returns node object
	public Node<E> findNode(E item) {
	    return findNode(root, item);
	}

	//inner recursive method finds and returns node object
	private Node<E> findNode(Node<E> current, E item) {
	    if (current == null) {
	        return null;
	    }
	    int result = current.data.compareTo(item);
	    if (result == 0) {
	        return current;
	    } else if (result < 0) {
	        return findNode(current.right, item);
	    } else {
	        return findNode(current.left, item);
	    }
	}
	
	/** 
	 * Insert an item to the tree
	 * @param  item  the value to be inserted 
	 * @return none 
	 */
	public void add(E item) {
		
			root =  add (root, item);
	}
	
	/** 
	 * Insert an item to the tree rooted at current
	 * @param  current  the current root
	 * @param  item  the value to be inserted 
	 * @return reference to the node that was inserted 
	 */
	private Node<E> add (Node <E>current , E item) {
		if (current == null) 
			current = new Node<>(item);
		else {
            int result = current.data.compareTo(item);
            if (result < 0)
                current.right =  add (current.right, item);
            else if (result > 0)
                current.left =  add (current.left, item);
		}
		return current;
	}
	
	/** 
	 * Traverse the tree using preorder traversal 
	 * @param  none
	 * @return none 
	 */
	public void preorder() {
		preorder (root, 1);
	}
	
	/** 
	 * Traverse the tree using preorder traversal 
	 * @param  current the current root
	 * @param  level the level of the current node
	 * @return none 
	 */
	private void preorder (Node<E> current, int level) {
		if (current != null) {
			for (int i = 1; i  < level; i++ )
				System.out.print("\t");
			System.out.println(current);
			preorder(current.left, level+1);
			preorder(current.right, level+1);
		}
	}
	 
	/** 
	 * Return the smallest value in the tree 
	 * @param  none
	 * @return the smallest value 
	 */
	public E min () {
		return min(root);
	}

	/** 
	 * Return the smallest value in the tree  
	 * @param  current the current root
	 * @return the smallest value
	 */
	private E min (Node<E> current) {
		if (current.left == null)
			return current.data;
		else 
			return min(current.left);
	}
	
	/** 
	 * Delete a given item from the tree 
	 * @param  item the item to be deleted
	 * @return none
	 */
	public void delete (E item) {
		root = delete (root,item);
	}
	
	/** 
	 * Delete a given item from the tree 
	 * @param  current the current root
	 * @param  item the item to be deleted
	 * @return a reference to a node 
	 */
	private Node<E> delete(Node<E> current , E item) {
		if (current != null) {
			int result = current.data.compareTo(item);
			if (result < 0)
				current.right =  delete (current.right, item);
			else if (result > 0)
				current.left =  delete (current.left, item);	
			else {    // find it
				if (current.left == null)    // current has 1 child
					current = current.right;
				else if (current.right == null)  
					current = current.left;
				else {   // current has two children
					E replace = min(current.right);
					current.data = replace;
					current.right = delete(current.right, replace);	
				}
			}
		}
		return current;
	}	
	
	/** 
	 * Find and return the height of the tree 
	 * @param  none
	 * @return height of the tree
	 */
	public int height() {
		return height (root);
	}
	
	/** 
	 * Find and return the height of the tree 
	 * @param  root  the root of the tree
	 * @return height of the tree
	 */
	private int height (Node<E> current) {
		if (current == null) return 0;
		//int left = height(current.left);
		//int right = height(current.right);
		//System.out.println (left + "   " + right);
		
		//return 1 + (left > right ? left : right);
		return 1 + Math.max(height(current.left), height(current.right));
	}
}

