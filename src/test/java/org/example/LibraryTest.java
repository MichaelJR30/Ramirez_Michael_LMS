package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    Library lib;
    ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        lib = new Library();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Add Books from file")
    void addBooksFromFile() {

        lib.addBooksFromFile("test_books.txt");
        lib.listBooks();
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Book 1"),"Book should be added to the library.");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Book by barcode")
    void removeBook() {
        lib.addBooksFromFile("test_books.txt");
        outputStreamCaptor.reset();
        lib.removeBook("2");
        lib.listBooks();
        String output = outputStreamCaptor.toString();
        assertFalse(output.contains("Book 2"),"Book should be removed from the library.");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Remove Book by title")
    void removeBookByTitle() {
        lib.addBooksFromFile("test_books.txt");
        lib.removeBookByTitle("Book 1");
        outputStreamCaptor.reset();
        lib.listBooks();
        String output = outputStreamCaptor.toString().trim();
        assertFalse(output.contains("Book 1"),"Book should be removed from the library.");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Book check out")
    void checkOut() {
        lib.addBooksFromFile("test_books.txt");
        lib.removeBook("2");
        outputStreamCaptor.reset();
        lib.checkOut("1");
        lib.listBooks();
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Checked out"),"Book should be Checked out of library.");
        assertFalse(output.contains("due Date: null"),"Due date should not be null after checkout.");
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Book check in")
    void checkIn() {
        lib.addBooksFromFile("test_books.txt");
        lib.removeBook("2");
        outputStreamCaptor.reset();
        lib.checkOut("1");
        lib.checkIn("Book 1");
        lib.listBooks();
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Checked in"), "Book should be checked in.");
        assertTrue(output.contains("due Date: null"), "Due date should be null after check-in.");
    }
}