package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        String collectionFile = "collection.txt";

        // Loads the existing collection file
        library.loadCollection(collectionFile);

        Scanner scanner = new Scanner(System.in);
        // Program loop for the menu
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add new books from file");
            System.out.println("2. Remove a book by ID");
            System.out.println("3. List all books");
            System.out.println("4. Save and Exit");
            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            // calling the methods for the menu choice
            switch (choice) {
                case "1":
                    System.out.print("Enter the filename to load books from: ");
                    String filename = scanner.nextLine();
                    library.addBooksFromFile(filename);
                    break;
                case "2":
                    System.out.print("Enter the ID of the book to remove: ");
                    String bookId = scanner.nextLine();
                    library.removeBook(bookId);
                    break;
                case "3":
                    library.listBooks();
                    break;
                case "4":
                    library.saveCollection(collectionFile);
                    System.out.println("Saving and Exiting the application.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}