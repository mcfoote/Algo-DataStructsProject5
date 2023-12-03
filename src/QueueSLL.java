/**
 * A class that represents a queue using a singly linked list
 * 
 * @author  COSC 311, F '23
 * @version (9-21-23)
 */

import java.util.*;
public class QueueSLL<E> {
	
	// Class Node is defined as an inner class
    /** A Node is the building block for the SinglyLinkedList */
    private static class Node<E> {

        /** Data members */
        private E data;
        /** The link */
        private Node<E> next;

        /**
         * Construct a node with the given data value
         * @param data - The data value 
         */
        public Node(E data) {
            this(data, null);
        }
        
        /**
         * Construct a node with the given data value and link
         * @param data - The data value 
         * @param next - The link
         */
        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
 
	//data members
	private Node<E> front = null;
	private Node<E> rear = null;
	private int size = 0;
	
	/** 
	 * Insert an item at the rear of the queue
	 * @param  item  The element to add
	 * @return true 
	 */
	public boolean offer (E item) {
		if (size == 0)
			front = rear = new Node<> (item);
		else 
			rear = rear.next = new Node<>(item);
		size ++;
		return true;
	}
	
	/** 
	 * Remove the entry at the front of the queue
	 * @param  None  
	 * @return the item removed or null if queue is empty
	 */
	public E poll() {
		E result = peek();
		if (result != null) {
			if (front == rear)   // has 1 element
				front = rear = null;
			else                 // has more than 1 element
				front = front.next;
			size --;
		}
		return result;
	}
	
	/** 
	 * Remove the entry at the front of the queue
	 * @param  None  
	 * @return the item removed or NoSuchElementException if queue is empty
	 */
	public E remove() {
		if (size == 0) 
			throw new NoSuchElementException("Queue is empty");
		E result = front.data;
		front = front.next;
		if (front == null)
			rear = null;
		size --;
		return result;
	}
	
	/** 
	 * Return the entry at the front of the queue
	 * @param  None  
	 * @return the item at the front of the queue or null if queue is empty
	 */
	public E peek() {
		if (size == 0) 
			return null;
		else
			return front.data;
	}
	
	/** 
	 * Return the entry at the front of the queue
	 * @param  None  
	 * @return the item at the front of the queue or throw NoSuchElementException if queue is empty
	 */
	public E element() {
		if (size == 0) 
			throw new NoSuchElementException("Queue is empty");
		else
			return front.data;
	}
	
	/** 
	 * Return a string representing the queue
	 * @param  None  
	 * @return a string representing the queue  	
	 */
	public String toString() {
		String result = "[";
		Node <E> current = front;
		while (current.next != null) {
			result = result + current.data + " ";
			current = current.next;
		}
		return result + current.data + "]";
	}
	
	
	/** 
	 * Determine whether or not queue is empty
	 * @param  None  
	 * @return true if the queue is empty, and false otherwise 	
	 */
	public boolean empty(){
		/*
		if (size == 0)
			return true;
		else
			return false;
		*/
		return (size ==0) ? true : false;
	}
	
	/** 
	 * Determine the number of elements stored in the queue
	 * @param  None  
	 * @return the number of elements in the queue
	 */
	public int size() {
		return size;
	}

}


