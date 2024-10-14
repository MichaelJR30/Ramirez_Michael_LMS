package org.example;

import java.util.Scanner;

/**
 Michael Ramirez, Software Development I, CEN 3024C, October 14, 2024

 Class: Main

 This is the entry point for the Library Management System (LMS).
 It provides an interactive console-based menu that allows users to:
 1. Add new books from a file to the library's collection.
 2. Remove a book from the collection by its unique barcode number.
 3. Remove a book from the collection by its title.
 4. List all books currently in the collection.
 5. Check out a book by barcode number.
 6. Check in a book by barcode number.
 7. Save the collection and exit the application.

 The Main class works in conjunction with the Library class, which handles
 the underlying functionality of managing the library's collection.
 */
public class Main {
    public static void main(String[] args) {

        Library library = new Library();

        // File to save and load the book collection
        String collectionFile = "collection.txt";

        // Attempt to load the existing collection from a file
        library.loadCollection(collectionFile);

        Scanner scanner = new Scanner(System.in);

        // Main program loop to interact with the user through a menu
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add new books from file");
            System.out.println("2. Remove a book by the barcode number");
            System.out.println("3. Remove a book by the title");
            System.out.println("4. List all books");
            System.out.println("5. Check out a book by barcode number");
            System.out.println("6. Check in a book by barcode number");
            System.out.println("7. Save and Exit");
            System.out.print("Enter your choice (1-7): ");
            String choice = scanner.nextLine();

            // Handle the user's menu choice by calling appropriate methods
            switch (choice) {
                case "1":
                    System.out.print("Enter the filename to load books from: ");
                    String filename = scanner.nextLine();
                    library.addBooksFromFile(filename);
                    System.out.println("Book list");
                    library.listBooks();
                    break;
                case "2":
                    System.out.print("Enter the barcode number of the book to be remove: ");
                    String bookId = scanner.nextLine();
                    library.removeBook(bookId);
                    library.listBooks();
                    break;
                case "3":
                    System.out.print("Enter the title of the book to be remove: ");
                    String bookTitle = scanner.nextLine();
                    library.removeBookByTitle(bookTitle);
                    library.listBooks();
                    break;
                case "4":
                    library.listBooks();
                    break;
                case "5":
                    System.out.print("Enter the barcode number of the book to check out: ");
                    String checkOutId = scanner.nextLine();
                    library.checkOut(checkOutId);
                    library.listBooks();
                    break;
                case "6":
                    System.out.print("Enter the barcode number of the book to check in: ");
                    String checkInId = scanner.nextLine();
                    library.checkIn(checkInId);
                    library.listBooks();
                    break;
                case "7":
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