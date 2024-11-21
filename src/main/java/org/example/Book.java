package org.example;

import java.util.Calendar;

/**
Michael Ramirez, Software Development I, CEN 3024C, November 24, 2024

Class: Book

 This Class is not used anymore do to the database.
This class represents a Book in the Library Management System (LMS).
It contains information about a book, including its ID/barcode, title, author, status, and due date.
The class provides getter methods to access these details and an override
for the toString method to display the book details in a formatted string.
*/
public class Book {
    private String id;
    private String title;
    private String author;
    private String status; // "checked in" or "checked out"
    private String dueDate;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title.trim();
        this.author = author;
        this.status = "Checked in";
        this.dueDate = null;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void checkOut(){
        this.status = "Checked out";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_YEAR, 4);
        this.dueDate = cal.getTime().toString();
    }

    public void checkIn(){
        this.status = "Checked in";
        this.dueDate = null;
    }

    /**
     Override of the toString method to return a formatted string
     representation of the book. Title and author are displayed as well as status and due date.

     @return A string containing the book's title, author, status, and due date separated by a comma.
     */
    @Override
    public String toString() {
        return title + "," + author + ", " + status + ", due Date: " + dueDate;
    }
}
