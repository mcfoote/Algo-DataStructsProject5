/**
 * Description: A class that represents a student 
 *
 * @author (COSC 311, F'23)
 * @version (8-31-23)
 */

//package demo083123;

import java.io.*;
import java.util.*;
public class Student {
	/** A student has a first name (fName), a last name (lName),
	 * an id (ID), and a gpa (GPA).
	 */
	private String fName;
	private String lName;
	private int ID;
	private double GPA;
	private final int LENGTH = 20;
	
	
	/**
	 * Set the student info
	 * @param first the first name
	 * @param last the last name
	 * @param id the ID
	 * @param gpa the GPA
	 */
	public void setData (String first, String last, int id, double gpa){
		fName = pad (first,LENGTH); lName = pad (last, LENGTH); ID = id; GPA = gpa;
	}

	/**
	 * set the student first name
	 * @param first the first name
	 * @return none
	 */
	public void setFirst(String first){
		fName = pad (first, LENGTH);
	}
	
	/**
	 * set the student last name
	 * @param last the last name
	 * @return none
	 */
	public void setLast(String last){
		lName = pad (last, LENGTH);
	}
	
	/**
	 * set the student ID 
	 * @param id the ID
	 * @return none
	 */
	public void setID(int id){
		ID = id;
	}
	
	/**
	 * set the student GPA 
	 * @param gpa the GPA
	 * @return none
	 */
	public void setGPA(double gpa){
		GPA = gpa;
	}
	
	/**
	 * get the student first name
	 * @return a string representing the first name
	 */
	public String getFirst(){
		return fName;
	}
	
	/**
	 * get the student' last name
	 * @return a string representing the last name
	 */
	public String getLast(){
		return lName;
	}
	
	/**
	 * get the student ID
	 * @return an int representing the ID
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * get the student GPA
	 * @return a string representing the GPA
	 */
	public double getGPA(){
		return GPA;
	}
	
	public int length(){
		return LENGTH;
	}
	
	/**
	 * Determine if two students have the same ID
	 * @return true if same ID, otherwise return false
	 */
	public boolean equals(Object other){
		if (other == null)
			return false;
		else if (getClass() != other.getClass())
			return false;
		else
			return ID == ((Student)other).ID;
	}

	/**
	 * Write a student object (or record) to the random access file
	 * @param out the random access file
	 * @throws IOException
	 */
	public void writeToFile(RandomAccessFile out) throws IOException {
		out.writeChars(fName);
		out.writeChars(lName);
		out.writeInt(ID);
		out.writeDouble(GPA);
	}
	
	/**
	 * Read a student object (or record) from the random access file
	 * @param in the random access file
	 * @throws IOException
	 */
	public void readFromFile(RandomAccessFile in)throws IOException {
		fName = readString(in); 
		lName = readString(in); 
		ID = in.readInt();
		GPA = in.readDouble();
	}
	
	/**
	 * Read LENGTH characters from the random access file
	 * @param in the random access file
	 * @return a string representing the characters read
	 * @throws IOException
	 */
	private String readString(RandomAccessFile in)throws IOException{
		String str = "";
		for (int i =0; i<LENGTH; i++)
			str += in.readChar();
		return str;
		
	}
	
	/**
	 * Read a student info from a text file and pad the first and last names with a blank(s) if needed
	 * @param in the text file
	 * @throws IOException
	 */
	public void readFromTextFile(Scanner in) throws IOException {
		fName = in.next();
		lName = in.next();
		ID = in.nextInt();
		GPA = in.nextDouble();
		fName = pad (fName, LENGTH);
		lName = pad (lName, LENGTH);
	}
	
	/**
	 * Padding a string with trailing blank(s) 
	 * @param s the string
	 * @param size the length of the resulting string
	 * @return a string of length size
	 */
	public static String pad (String s, int size){
		if (s.length() > size) s = s.substring(0,size);
		else {
			for (int i = s.length(); i <size; i++)
				s += ' ';
		}
		return s;
	}
	
	/**
	 * Create and return a string that represents a student 
	 * @return a string representing a student
	 */
	public String toString(){
		return String.format("%-20s",fName) + String.format("%-20s",lName) +  
				   String.format("%-10s",ID) + String.format("%-10s",GPA);
	}	
}




