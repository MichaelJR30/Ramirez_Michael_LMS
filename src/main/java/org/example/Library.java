package org.example;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Library {
    private TreeMap<String, Book> collection;

    public Library() {
        collection = new TreeMap<>();
    }
    // loads the books from the text file
    public void addBooksFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.err.println("File not found: " + filename);
            return;
        }
        // reads the text in the file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                try {
                    String[] tokens = line.split(",");
                    if (tokens.length != 3) {
                        // If the text is not format right it will display the line in the file
                        System.err.println("Invalid line in file: " + line +"\n");
                        continue;
                    }
                    String id = tokens[0];
                    String title = tokens[1];
                    String author = tokens[2];

                    // Validate if id is numeric
                    if (!id.matches("\\d+")) {
                        System.err.println("Invalid book ID (not numeric): " + line + "\n");
                        continue;
                    }

                    Book book = new Book(id, title, author);
                    // checks if the book id is already in the collection than adds it if not
                    if (!collection.containsKey(book.getId())) {
                        collection.put(book.getId(), book);
                        System.out.println("Added book: " + id + ", " + title + ", " + author);
                    } else {
                        // if the id is already in the collection gives this message
                        System.err.println("Duplicate book id: " + book.getId());
                    }
                } catch (Exception e) {
                    System.err.println("Error processing line: " + line + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename + e.getMessage());
        }
    }
    // remove a book from the collection by id
    public void removeBook(String id) {
        if (collection.containsKey(id)) {
            collection.remove(id);
            System.out.println("Removed book: " + id);
        } else {
            System.out.println("Book not found: " + id);
        }
    }
    // list the books in the collection
    public void listBooks() {
        // shows if no books are in the collection
        if (collection.isEmpty()) {
            System.out.println("No books found in library");
        } else {
            for (Map.Entry<String, Book> entry : collection.entrySet()) {
                System.out.println(entry.getKey() + ", " + entry.getValue());
            }
        }
    }
    // saves collection to a file
    public void saveCollection(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Map.Entry<String, Book> entry : collection.entrySet()) {
                Book book = entry.getValue();
                writer.println(entry.getKey() + "," + entry.getValue());
            }
            System.out.println("Saved collection to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }
    // loads the collection from a file
    public void loadCollection(String filename) {
        File file = new File(filename);

        // checking if file exists
        if (!file.exists()) {
            System.out.println("Collection file not found: " + filename);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] tokens = line.split(",");
                    // Ensure each line has exactly 3 fields: id, title, author
                    if (tokens.length != 3) {
                        System.err.println("Invalid line in file: " + line + " : file : " + filename + "\n");
                        continue;
                    }
                    String id = tokens[0];
                    String title = tokens[1];
                    String author = tokens[2];

                    // Validate if id is numeric
                    if (!id.matches("\\d+")) {
                        System.err.println("Invalid ID (not numeric): " + id);
                        continue;
                    }

                    collection.put(id, new Book(id, title, author));
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
