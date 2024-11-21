package org.example;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;
/**
 Michael Ramirez, Software Development I, CEN 3024C, November 24, 2024

 Class: Library

 This Class has been replaced with BookCollection Class.
 This class represents the Library Management System (LMS).
 It manages a collection of books by adding, removing, listing, checking in/out books
 saving, and loading books from a file. The class uses a
 TreeMap to store the collection of books, where each book has a
 unique ID/barcode, and books can be manipulated based on title or ID/barcode.
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

     @return toString messages
     */
    public String addBooksFromFile(String filename) {
        StringBuilder messages = new StringBuilder();
        File file = new File(filename);

        if (!file.exists()) {
            messages.append("File not found: ").append(filename).append("\n");
            return messages.toString();
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
                        messages.append("Invalid line in file: ").append(line).append("\n");
                        continue;
                    }
                    String id = tokens[0];
                    String title = tokens[1];
                    String author = tokens[2];

                    // Validate that the ID is numeric
                    if (!id.matches("\\d+")) {
                        messages.append("Invalid book barcode (not numeric): ").append(line).append("\n");
                        continue;
                    }

                    // Create a book and add it to the collection if it doesn't exist
                    Book book = new Book(id, title, author);
                    if (!collection.containsKey(book.getId())) {
                        collection.put(book.getId(), book);
                        messages.append("Added book: ").append(id).append(", ").append(title).append(", ").append(author).append("\n");
                    } else {
                        messages.append("Duplicate book barcode: ").append(book.getId()).append("\n");
                    }
                } catch (Exception e) {
                    messages.append("Error processing line: ").append(line).append(" ").append(e.getMessage()).append("\n");
                }
            }
        } catch (IOException e) {
            messages.append("Error reading file: ").append(filename).append(" ").append(e.getMessage()).append("\n");
        }
        return messages.toString();
    }
    /**
     Removes a book from the collection by its book barcode.

     @param id the barcode of the book to remove
     @return messages.toString()
     */
    public String removeBook(String id) {
        StringBuilder messages = new StringBuilder();
        if (collection.containsKey(id)) {
            collection.remove(id);
            messages.append("Removed book: ").append(id).append("\n");
        } else {
            messages.append("Book not found: ").append(id).append("\n");
        }
        return messages.toString();
    }
    /**
     Removes a book from the collection by its Title.

     @param title the name of the book to remove
     @return messages.toString()
     */
    public String removeBookByTitle(String title) {
        StringBuilder messages = new StringBuilder();
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
            messages.append("Removed book: ").append(title).append("\n");
        } else {
            messages.append("Book not found: ").append(title).append("\n");
        }
        return messages.toString();
    }

    // Check out a book
    public String checkOut(String title) {
        StringBuilder messages = new StringBuilder();
        boolean bookFound = false;  // Track if a book is found
        String bookIdToCheckOut = null;

        title=title.trim();

        // Iterate over the collection to find a book by title
        for (Map.Entry<String, Book> entry : collection.entrySet()) {
            Book book = entry.getValue();

            if (book.getTitle().equalsIgnoreCase(title)) {
                bookIdToCheckOut = entry.getKey();
                bookFound = true;
                break;
            }
        }

        // Check out the book if found
        if (bookFound && bookIdToCheckOut != null) {
            Book book = collection.get(bookIdToCheckOut);
            if (book.getStatus().equals("Checked in")) {
                book.checkOut();
                messages.append("Check out: ").append(book).append("\n");
            } else {
                messages.append("Book already checked out. ").append("\n");
            }
        } else {
            messages.append("Book not found: ").append(title).append("\n");
        }
        return messages.toString();
    }

    // Check in a book
    public String checkIn(String title) {
        StringBuilder messages = new StringBuilder();
        boolean bookFound = false;  // Track if a book is found
        String bookIdToCheckIn = null;

        title=title.trim();

        // Iterate over the collection to find a book by title
        for (Map.Entry<String, Book> entry : collection.entrySet()) {
            Book book = entry.getValue();

            if (book.getTitle().equalsIgnoreCase(title)) {
                bookIdToCheckIn = entry.getKey();
                bookFound = true;
                break;
            }
        }

        // Check in the book if found
        if (bookFound && bookIdToCheckIn != null) {
            Book book = collection.get(bookIdToCheckIn);
            if (book.getStatus().equals("Checked out")) {
                book.checkIn();
                messages.append("Check in: ").append(book).append("\n");
            } else {
                messages.append("Book already checked in. ").append("\n");
            }
        } else {
            messages.append("Book not found: ").append(title).append("\n");
        }
        return messages.toString();
    }


    /**
     * Lists all books in the collection.
     * Displays the book ID along with the book's details.
     *
     * @return toString bookList
     */
    public String listBooks() {
        StringBuilder bookList = new StringBuilder();
        if (collection.isEmpty()) {
            bookList.append("No books found in library");
        } else {
            bookList.append("Books Collection:\n");
            for (Map.Entry<String, Book> entry : collection.entrySet()) {
                bookList.append(entry.getKey()).append(", ").append(entry.getValue()).append("\n");
            }
        }
        return bookList.toString();
    }
    /**
     Saves the current collection of books to a file.
     Each book is saved in CSV format (ID, title, author).

     @param filename the file path where the collection will be saved
     @return messages.toString()
     */
    public String saveCollection(String filename) {
        StringBuilder messages = new StringBuilder();
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Map.Entry<String, Book> entry : collection.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
            messages.append("Saved collection to ").append(filename).append("\n");
        } catch (IOException e) {
            messages.append("Error saving to file: ").append(e.getMessage()).append("\n");
        }
        return messages.toString();
    }
    /**
     Loads the book collection from a file.
     The file must contain lines in the format: ID,title,author.

     @param filename the file path from which to load the collection
     @return messages.toString()
     */
    public String loadCollection(String filename) {
        StringBuilder messages = new StringBuilder();
        File file = new File(filename);

        if (!file.exists()) {
            messages.append("Collection file not found: ").append(filename).append("\n");
            return messages.toString();
        }

        // Try reading the file and loading the collection
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] tokens = line.split(",");

                    // Ensure each line has exactly 5 fields: id, title, author, status, dueDate
                    if (tokens.length != 5) {
                        messages.append("Invalid line in file: ").append(line).append(" : file : ").append(filename).append("\n");
                        continue;
                    }
                    String id = tokens[0];
                    String title = tokens[1];
                    String author = tokens[2];
                    String status = tokens[3];
                    String dueDate = tokens[4];

                    // Validate if ID is numeric
                    if (!id.matches("\\d+")) {
                        messages.append("Invalid book barcode (not numeric): ").append(id).append("\n");
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
                    messages.append("Error processing line: ").append(line).append(e.getMessage()).append("\n");
                }
            }
            messages.append("Loaded collection from ").append(filename).append("\n");
        } catch (IOException e) {
            messages.append("Error reading file: ").append(filename).append(e.getMessage()).append("\n");
        }
        return messages.toString();
    }
}
