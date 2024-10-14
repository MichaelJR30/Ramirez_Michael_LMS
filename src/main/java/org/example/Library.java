package org.example;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
/**
 Michael Ramirez, Software Development I, CEN 3024C, October 14, 2024

 Class: Library

 This class represents the Library Management System (LMS).
 It manages a collection of books by adding, removing, listing, checking in/out books
 saving, and loading books from a file. The class uses a
 TreeMap to store the collection of books, where each book has a
 unique ID/barcode, and books can be manipulated based on that ID/barcode.
 */
public class Library {
    // Collection of books, stored in a TreeMap with book ID as the key
    private TreeMap<String, Book> collection;

    // Constructor initializes the collection
    public Library() {
        collection = new TreeMap<>();
    }
    /**
     Loads books from a specified text file.
     Each line in the file must contain a book's ID, title, and author,
     separated by commas.

     @param filename the file path to load the books from
     */
    public void addBooksFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.err.println("File not found: " + filename);
            return;
        }

        // Try reading the file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Process each line of the file
            while ((line = br.readLine()) != null) {
                try {
                    String[] tokens = line.split(",");

                    // Validate line format
                    if (tokens.length != 3) {
                        System.err.println("Invalid line in file: " + line +"\n");
                        continue;
                    }
                    String id = tokens[0];
                    String title = tokens[1];
                    String author = tokens[2];

                    // Validate that the ID is numeric
                    if (!id.matches("\\d+")) {
                        System.err.println("Invalid book barcode (not numeric): " + line + "\n");
                        continue;
                    }

                    // Create a book and add it to the collection if it doesn't exist
                    Book book = new Book(id, title, author);
                    if (!collection.containsKey(book.getId())) {
                        collection.put(book.getId(), book);
                        System.out.println("Added book: " + id + ", " + title + ", " + author);
                    } else {
                        System.err.println("Duplicate book barcode: " + book.getId());
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename + e.getMessage());
        }
    }
    /**
     Removes a book from the collection by its book barcode.

     @param id the barcode of the book to remove
     */
    public void removeBook(String id) {
        if (collection.containsKey(id)) {
            collection.remove(id);
            System.out.println("Removed book: " + id);
        } else {
            System.out.println("Book not found: " + id);
        }
    }

    public void removeBookByTitle(String title) {
        boolean bookFound = false;  // Track if a book is found
        String bookIdToRemove = null;

        title=title.trim();

        // Iterate over the collection to find a book by title
        for (Map.Entry<String, Book> entry : collection.entrySet()) {
            Book book = entry.getValue();

            if (book.getTitle().equalsIgnoreCase(title)) {
                bookIdToRemove = entry.getKey();
                bookFound = true;
                break;
            }
        }

        // Remove the book if found
        if (bookFound && bookIdToRemove != null) {
            collection.remove(bookIdToRemove);
            System.out.println("Removed book: " + title);
        } else {
            System.out.println("Book not found: " + title);
        }
    }

    // Check out a book
    public void checkOut(String id) {
        if (collection.containsKey(id)) {
            Book book = collection.get(id);
            if (book.getStatus().equals("Checked in")) {
                book.checkOut();
                System.out.println("Check out: " + book);
            } else {
                System.out.println("Book already checked out. ");
            }
        } else {
            System.out.println("Book not found: " + id);
        }
    }

    // Check in a book
    public void checkIn(String id) {
        if (collection.containsKey(id)) {
            Book book = collection.get(id);
            if (book.getStatus().equals("Checked out")) {
                book.checkIn();
                System.out.println("Check in: " + book);
            } else {
                System.out.println("Book already checked in. ");
            }
        } else {
            System.out.println("Book not found: " + id);
        }
    }

    /**
     Lists all books in the collection.
     Displays the book ID along with the book's details.
     */
    public void listBooks() {
        if (collection.isEmpty()) {
            System.out.println("No books found in library");
        } else {
            for (Map.Entry<String, Book> entry : collection.entrySet()) {
                System.out.println(entry.getKey() + ", " + entry.getValue());
            }
        }
    }
    /**
     Saves the current collection of books to a file.
     Each book is saved in CSV format (ID, title, author).

     @param filename the file path where the collection will be saved
     */
    public void saveCollection(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Map.Entry<String, Book> entry : collection.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
            System.out.println("Saved collection to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
    /**
     Loads the book collection from a file.
     The file must contain lines in the format: ID,title,author.

     @param filename the file path from which to load the collection
     */
    public void loadCollection(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("Collection file not found: " + filename);
            return;
        }

        // Try reading the file and loading the collection
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] tokens = line.split(",");

                    // Ensure each line has exactly 5 fields: id, title, author, status, dueDate
                    if (tokens.length != 5) {
                        System.err.println("Invalid line in file: " + line + " : file : " + filename + "\n");
                        continue;
                    }
                    String id = tokens[0];
                    String title = tokens[1];
                    String author = tokens[2];
                    String status = tokens[3];
                    String dueDate = tokens[4];

                    // Validate if ID is numeric
                    if (!id.matches("\\d+")) {
                        System.err.println("Invalid book barcode (not numeric): " + id);
                        continue;
                    }

                    // Create a new book and update its status and due date
                    Book book = new Book(id, title, author);
                    if (status.equals(" Checked in")) {
                        book.checkIn();
                    } else {
                        book.checkOut();
                    }

                    // Add the book to the collection
                    collection.put(id, book);
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line + e.getMessage());
                }
            }
            System.out.println("Loaded collection from " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename + e.getMessage());
        }
    }
}
