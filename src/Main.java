
/* 
 * Programmer: 	Mitchell Foote
 * Course: 	   	COSC 311, F'23
 * Project:    	5
 * Due date:   	12-5-23
 * Project Description: A menu driven terminal program that can read in text files and create random access files to serve as a
 * database for student records using a fixed byte length file size. Users can create, read, edit and delete student records.
 * The program allows for the creation of a database index organized by student ID to facilitate the creation, reading editing and deletion
 * of student record file by student ID. 
 * Class Description: The following main class handles all functionality other than the data structures and organization handled 
 * by the provided Student, Pair, HashTable, BST and QueueSLL classes.
 * */
import java.util.*;
import java.io.*;

public class Main {
	
	//shared variable declarations.
	private static Scanner scanner;
	private static int option;
	
	private static Scanner fileIn;
	private static RandomAccessFile raFile;
	private static File file;
	
	private static final int studentRecordSize = 92;
	
	//Index Data Structure using BST 
	private static HashTable index = new HashTable();
	

	//Main prints attribution to terminal then calls menu method.
	public static void main(String[] args) {
		
		
		System.out.println("Programmer:		Mitchell Foote");
		System.out.println("Course:			COSC 311, F'23");
		System.out.println("Project:		5");
		System.out.println("Due date:		12-5-23\n");
		
		//Open Scanner object to take keyboard input.
		scanner = new Scanner(System.in);
		
		menu();


	}
	
	//Builds random access file from provided text file.
	public static void build() throws IOException {
	    Student student = new Student();
	    int recordCount = 0;

	    while (fileIn.hasNext()) {
	        student.readFromTextFile(fileIn);

	        // Check if specific fields are empty or null
	        if (student.getFirst() != null && !student.getFirst().isEmpty()
	            && student.getLast() != null && !student.getLast().isEmpty()
	            && student.getID() != 0 && student.getGPA() != 0.0) {

	            student.writeToFile(raFile);
	            recordCount++;
	            
	        } else {
	            System.out.println("Skipping empty or null record.");
	        }
	    }

	    System.out.println("Random access file is built successfully!");
	    System.out.println("Total valid records written: " + recordCount + "\n");
	}
	
	//Main menu provides interface to the user. Handles user choices
	private static void menu() {
		
		System.out.println();
		
		menuHelper();

		//check for valid input.
		try {
			option = scanner.nextInt();
		}
		catch (InputMismatchException ex){
			System.out.println("Non-numeric input\n");
			scanner.nextLine();
			option = 0;
		}
		
		//Menu control switch.
		switch (option) {
		
		case 1:
			makeRandomAccess();
			break;
		case 2:
			displayRandomAccess();
			break;
		case 3:
			buildIndex();
			break;
		case 4:
			displayIndex();
			break;
		case 5:
			retrieveRecord();
			break;
		case 6:
			modifyRecord();
			break;
		case 7:
			addRecord();
			break;
		case 8:
			deleteRecord();
			break;
		case 9:
			exit();
			break;
		default:
			System.out.println("Invalid Option\n");
			menu();
			break;
		}
		
	}
	
	//builds index using a BST
	private static void buildIndex() {
		
	    Student student = new Student();
	    String raFileName;
	    
	    System.out.print("Enter the random access file name: ");
	    raFileName = scanner.next();
	    File raFileCheck = new File(raFileName);
	    
	    if (!raFileCheck.exists()) {
	        System.out.println("File not found.");
	        menu();
	        return; 
	    }

	    try {
	        index = new HashTable();
	        
	        raFile.seek(0); // Reset the file pointer to the beginning of the file

	        System.out.println("Total file length: " + raFile.length());

	        while (raFile.getFilePointer() < raFile.length()) {
	        	
	            student.readFromFile(raFile);

	            if (!isRecordDeleted(student)) {
	                int studentID = student.getID();
	                index.insert(studentID, (int) (raFile.getFilePointer()/studentRecordSize) - 1);

	            }
	        }

	        System.out.println("Index has been built successfully!\n");
	        
	    } catch (IOException ex) {
	        System.out.println("Error while building the index: " + ex.getMessage());
	        ex.printStackTrace();
	    } catch (Exception e) {
	        System.out.println("An unexpected error occurred while building the index: " + e.getMessage());
	        e.printStackTrace();
	    }

	    menu();
	    
	}
	
	//Prints out index to terminal
	private static void displayIndex() {
		
	    if (index.isEmpty()) { // Replace checkIndex() with appropriate method
	        System.out.println("No index available. Use option 3 to first build the index.");
	        menu();
	        return;
	    }

	    // Display hash table
	    index.display();
	    
	    menu();
	    
	}
	
	//same as display index minus menu loop
	private static void displayIndexOnExit() {
		
	    if (index.isEmpty()) { // Replace checkIndex() with appropriate method
	        System.out.println("No index available. Use option 3 to first build the index.");
	        menu();
	        return;
	    }

	    // Display hash table
	    index.display();
	    
	}

	//Breaks Menu loop, closes scanner and exits terminal application.
	private static void exit() {
		
		displayIndexOnExit();
		System.out.println("Bye!");
		scanner.close();
		System.exit(0);
		
	}

	//Marks a record as deleted using a "lazy" deletion method.
	private static void deleteRecord() {
		
	    if (index.isEmpty()) {
	        System.out.println("No index available. Use option 3 to first build the index.");
	        menu();
	        return;
	    }

	    System.out.print("Enter student ID: ");
	    while (!scanner.hasNextInt()) {
	        System.out.println("Please enter a valid student ID (integer): ");
	        scanner.next(); 
	    }
	    
	    int studentID = scanner.nextInt();

	    Integer address = index.find(studentID);
	    if (address != null) {
	        try {
	            raFile.seek(address * studentRecordSize);
	            raFile.writeChars("DELETED");
	            raFile.seek(0);
	            index.delete(studentID);
	            System.out.println("Record successfully deleted.");
	        } catch (IOException ex) {
	            System.out.println("Error while deleting the record: " + ex.getMessage());
	        }
	    } else {
	        System.out.println("Record not found.");
	    }

	    menu();
	    
	}

	//Adds a record to the random access file.
	private static void addRecord() {
		
	    if (!checkRAF()) {
	        menu();
	        return;
	    }
	    
	    if (checkIndex()) {
	    	System.out.println("No index available. Use option 3 to first build the index.");
	        menu();
	        return;
	    }

	    try {
	        System.out.println("Enter details for the new record:");
	        Student student = new Student();

	        // Validate then set student ID
	        while (true) {
	            System.out.print("Enter Student ID: ");
	            
	            while (!scanner.hasNextInt()) {
	                System.out.println("Invalid input. Please enter a valid integer for student ID.");
	                scanner.next();
	            }
	            
	            int studentID = scanner.nextInt();
	            scanner.nextLine();
	            
	            if (findPairByStudentID(studentID) == null) {
	                student.setID(studentID);
	                break;
	            } else {
	                System.out.println("Duplicate ID - TRY AGAIN");
	            }
	        }

	        // Validate then set first name
	        System.out.print("Enter First Name: ");
	        String firstName = scanner.nextLine().trim();
	        while (firstName.isEmpty()) {
	            System.out.println("First Name cannot be empty. Enter First Name: ");
	            firstName = scanner.nextLine().trim();
	        }
	        student.setFirst(firstName);

	        // Validate then set last name
	        System.out.print("Enter Last Name: ");
	        String lastName = scanner.nextLine().trim();
	        while (lastName.isEmpty()) {
	            System.out.println("Last Name cannot be empty. Enter Last Name: ");
	            lastName = scanner.nextLine().trim();
	        }
	        student.setLast(lastName);

	        // Validate and set GPA
	        System.out.print("Enter GPA: ");
	        while (!scanner.hasNextDouble()) {
	            System.out.println("Invalid input. Please enter a valid GPA.");
	            scanner.next();
	        }
	        
	        double gpa = scanner.nextDouble();
	        while (gpa < 0.0 || gpa > 4.0) {
	            System.out.println("GPA should be between 0.0 and 4.0. Enter GPA: ");
	            while (!scanner.hasNextDouble()) {
	                System.out.println("Invalid input. Please enter a valid GPA.");
	                scanner.next();
	            }
	            gpa = scanner.nextDouble();
	        }
	        student.setGPA(gpa);

	        // After taking all inputs and validations
	        raFile.seek(raFile.length());
	        student.writeToFile(raFile);
	        int address = (int) (raFile.getFilePointer()/studentRecordSize) - 1;

	        // Add the ID and address to the index.
	        index.insert(student.getID(), address);

	    } catch (IOException ex) {
	        System.out.println("Error while adding a record: " + ex.getMessage());
	    }

	    menu();
	}

	//Edit a record in the random access file.
	private static void modifyRecord() {
		
	    if (checkIndex()) {
	    	System.out.println("No index available. Use option 3 to first build the index.");
	        menu();
	        return;
	    }

	    System.out.print("Enter student ID: ");
	    
	    while (!scanner.hasNextInt()) {
	        System.out.println("Please enter a valid student ID (integer): ");
	        scanner.next(); 
	    }
	    
	    int studentID = scanner.nextInt();

	    Pair<Integer> pair = findPairByStudentID(studentID);  // Use helper method to find pair
	    
	    if (pair != null) {
	    	
	        try {
	            raFile.seek(pair.getSecond()*studentRecordSize);
	            Student student = new Student();
	            student.readFromFile(raFile);

	            int choice;
	            do {
	            	
	                System.out.println(student);

	                System.out.println("\n1- Change the first name");
	                System.out.println("2- Change the last name");
	                System.out.println("3- Change GPA");
	                System.out.println("4- Done");
	                System.out.print("Enter your choice: ");

	                while (!scanner.hasNextInt()) {
	                    System.out.println("Invalid input. Enter a valid choice: ");
	                    scanner.next(); 
	                }
	                
	                choice = scanner.nextInt();
	                scanner.nextLine();

	                //Modify menu switch.
	                switch (choice) {
	                    case 1:
	                        System.out.print("Enter First Name: ");
	                        String newFirstName = scanner.nextLine();
	                        if (!newFirstName.isEmpty()) {
	                            student.setFirst(newFirstName);
	                        }
	                        break;
	                    case 2:
	                        System.out.print("Enter Last Name: ");
	                        String newLastName = scanner.nextLine();
	                        if (!newLastName.isEmpty()) {
	                            student.setLast(newLastName);
	                        }
	                        break;
	                    case 3:
	                        System.out.print("Enter GPA: ");
	                        while (!scanner.hasNextDouble()) {
	                            System.out.println("Input must be in decimal format: ");
	                            scanner.next();
	                        }
	                        double newGPA = scanner.nextDouble();
	                        student.setGPA(newGPA);
	                        break;
	                    case 4:
	                        break;
	                    default:
	                        System.out.println("Invalid choice.");
	                }
	                
	            } while (choice != 4);

	            // After modification, overwrite the record:
	            raFile.seek(pair.getSecond() * studentRecordSize);
	            student.writeToFile(raFile);
	        } catch (IOException ex) {
	            System.out.println("Error while modifying the record: " + ex.getMessage());
	        }
	    } else {
	        System.out.println("Record not found.");
	    }
	    
	    menu();
	    
	}

	//Search for a record and read out to terminal.
	private static void retrieveRecord() {
		
	    if (index.isEmpty()) {
	        System.out.println("No index available. Use option 3 to first build the index.");
	        menu();
	        return;
	    }

	    System.out.print("Enter student ID: ");
	    while (!scanner.hasNextInt()) {
	        System.out.println("Please enter a valid student ID: ");
	        scanner.next(); 
	    }
	    int studentID = scanner.nextInt();

	    Integer address = index.find(studentID);
	    if (address != null) {
	        try {
	            raFile.seek(address * studentRecordSize);
	            Student student = new Student();
	            student.readFromFile(raFile);
	            System.out.println(student);
	        } catch (IOException ex) {
	            System.out.println("Error while retrieving the record: " + ex.getMessage());
	        }
	    } else {
	        System.out.println("Record not found.");
	    }
	    
	    menu();
	    
	}

	//Displays selected random access file to terminal starting with 5 records and prompting the user for continuation.
	private static void displayRandomAccess() {
		
	    Student student = new Student();
	    String raFileName;
	    
	    System.out.print("Enter the random access file name: ");
	    raFileName = scanner.next();
	    File raFileCheck = new File(raFileName);
	    
	    if (!raFileCheck.exists()) {
	        System.out.println("File not found.");
	        menu();
	        return; 
	    }
	    
	    try {
	        raFile = new RandomAccessFile(raFileName, "rw");
	        raFile.seek(0);

	        int count = 0;
	        boolean displayAll = false;

	        while (raFile.getFilePointer() < raFile.length()) {  // Check if we're at the end
	            
	            student.readFromFile(raFile);
	            String record = student.toString();
	            
	            // Check for deleted records.
	            if (record.contains("DELETED")) {
	                continue; 
	            }

	            System.out.println(record);
	            count++;

	            if (!displayAll && count % 5 == 0) {
	                System.out.print("Enter N (for next 5 records), A (for all remaining records), M(for main menu): ");
	                char choice = scanner.next().charAt(0); 

	                if (choice == 'N' || choice == 'n') {
	                    continue; // Display next set.
	                } else if (choice == 'A' || choice == 'a') {
	                    displayAll = true; 
	                } else if (choice == 'M' || choice == 'm') {
	                    break; // Return to menu loop.
	                }
	            }
	        }

	    } catch(InputMismatchException ex) {
	        System.out.println(ex.getMessage());
	        scanner.nextLine();
	    } catch(FileNotFoundException ex) {
	        System.out.println(ex.getMessage());
	    } catch(IOException ex) {
	        System.out.println(ex.getMessage());
	    }
	    
	    menu();
	    
	}

	//Takes input that provides an input text file and creates a random access file with the desired name.
	private static void makeRandomAccess() {
	    try {
	        String inputFile, outputFile;

	        System.out.print("Enter an input file name: ");
	        inputFile = scanner.next();
	        file = new File(inputFile);
	        if (!file.exists()) {
	            System.out.println("Input file does not exist.");
	            return;
	        }
	        fileIn = new Scanner(file);

	        System.out.print("Enter an output file name: ");
	        outputFile = scanner.next();
	        file = new File(outputFile);

	        // Check if file exists and delete it if it does
	        if (file.exists()) {
	            System.out.println("Output file already exists. Deleting existing file...");
	            boolean deleteSuccess = file.delete();
	            if (!deleteSuccess) {
	                System.out.println("Failed to delete existing file. Please try again.");
	                return;
	            }
	        }

	        raFile = new RandomAccessFile(file, "rw");
	        build();

	    } catch (InputMismatchException ex) {
	        System.out.println(ex.getMessage());
	        scanner.nextLine();
	    } catch (FileNotFoundException ex) {
	        System.out.println(ex.getMessage());
	    } catch (IOException ex) {
	        System.out.println(ex.getMessage());
	    }

	    menu();
	}

	
	//Prints the menu options to terminal.
	private static void menuHelper() {
		
		System.out.println("   Menu");
		System.out.println("   ====");
		System.out.println("1: Make a random-access file");
		System.out.println("2: Display a random-access file");
		System.out.println("3: Build the index");
		System.out.println("4: Display the index");
		System.out.println("5: Retrieve a record");
		System.out.println("6: Modify a record");
		System.out.println("7: Add a record");
		System.out.println("8: Delete a record");
		System.out.println("9: Exit");
		System.out.print("\nEnter your choice: ");
		
	}
	
	//Checks for deleted records.
	private static boolean isRecordDeleted(Student student) {
		return student.getFirst().contains("DELETED");
	}
	
	//Checks for random access file
	private static boolean checkRAF() {
		
	    if (raFile == null) {
	        System.out.println("No random access file selected, use option 1 or 2 to begin.");
	        return false;
	    }
	    
	    return true;
		
	}
	
	//Checks for an extant index
	private static boolean checkIndex() {
	    return index.isEmpty();
	}
	
	//Outer method inputs student ID outputs matching pair object
	private static Pair<Integer> findPairByStudentID(int studentID) {
	    Integer address = index.find(studentID);
	    if (address != null) {
	        return new Pair<>(studentID, address);
	    }
	    return null;
	}

}