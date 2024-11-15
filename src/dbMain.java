package src;

import src.DBHelper.BookCollection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 Michael Ramirez, Software Development I, CEN 3024C, November 17, 2024

 Class: dbMain

 This is the GUI for the Library Management System (LMS).
 It provides an interactive window menu that allows users to:
 1. Remove a book from the collection by its unique barcode number.
 2. Remove a book from the collection by its title.
 3. List all books currently in the collection.
 4. Check out a book by Title.
 5. Check in a book by Title.

 The Main class works in conjunction with the database which is access through BookCollection class, which handles
 the underlying functionality of managing the library's collection.
 */
public class dbMain extends JFrame {

    // GUI components
    private JPanel BasePanel;
    private JPanel MainPanel;
    private JTextArea textArea1;
    private JButton CheckInButton;
    private JButton CheckOutButton;
    private JTextField tfCheckInOut;
    private JButton RMBookT;
    private JButton RMBookNum;
    private JTextField tfRemoveBook;
    private JButton ExitButton;
    private JPanel HeadPanel;
    private JButton DisplayBooksButton;

    BookCollection db1 = new BookCollection(); // create an instance of database class

    /**
     Constructor: dbMain
     Initializes the GUI frame, sets up the database instance and
     defines actions for each button in the interface.
     */
    public dbMain () {

        // Set up the GUI frame properties
        setContentPane(BasePanel);
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        /*
         Checks in a book to the library collection by title.
         Triggered by the "CheckInButton" in the GUI.
         */
        CheckInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfCheckInOut.getText();
                textArea1.setText(db1.checkBookIn(BookTitle));
            }
        });

        /*
         Checks out a book from the library collection by title.
         Triggered by the "CheckOutButton" in the GUI.
         */
        CheckOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfCheckInOut.getText();
                textArea1.setText(db1.checkBookOut(BookTitle));
            }
        });

        /*
         Displays the list of all books in the library collection.
         Triggered by the "DisplayBooksButton" in the GUI.
         */
        DisplayBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(db1.DisplayBooks());
            }
        });

        /*
         Saves the current collection to file and exits the application.
         Triggered by the "ExitButton" in the GUI.
         */
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db1.closeDatabase(); // Close the database connection
                System.exit(0);  // Exit the application
            }
        });

        /*
         Removes a book by title from the library collection.
         Triggered by the "RMBookT" button in the GUI.
         */
        RMBookT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfRemoveBook.getText().trim();

                // Query the database to check for the book by title
                ArrayList<ArrayList<Object>> data = db1.select(BookCollection.title, BookCollection.title, BookTitle, null, null);
                if (data.isEmpty()){
                    textArea1.setText("Book Not Found: " + BookTitle);
                } else {
                    db1.delete(BookCollection.title, BookTitle);
                    textArea1.setText("Book deleted: " + BookTitle + "\n" + db1.DisplayBooks());
                }
            }
        });

        /*
         Removes a book by its barcode from the library collection.
         Triggered by the "RMBookNum" button in the GUI.
         */
        RMBookNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookBarcode = tfRemoveBook.getText().trim();

                // Query the database to check for the book by barcode
                ArrayList<ArrayList<Object>> data = db1.select(BookCollection.barcode, BookCollection.barcode, BookBarcode, null, null);
                if (data.isEmpty()){
                    textArea1.setText("Book Barcode Not Found: " + BookBarcode);
                } else {
                    db1.delete(BookCollection.barcode, BookBarcode);
                    textArea1.setText("Book deleted: Barcode " + BookBarcode + "\n" + db1.DisplayBooks());
                }
            }
        });
    }

    /**
     Main method: Starts the GUI application for the Library Management System.
     */
    public static void main(String[] args) {
        new dbMain();
    }
}