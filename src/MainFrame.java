package src;

import org.example.Library;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 Michael Ramirez, Software Development I, CEN 3024C, November 3, 2024

 Class: MainFrame

 This is the GUI for the Library Management System (LMS).
 It provides an interactive window menu that allows users to:
 1. Add new books from a file to the library's collection.
 2. Remove a book from the collection by its unique barcode number.
 3. Remove a book from the collection by its title.
 4. List all books currently in the collection.
 5. Check out a book by Title.
 6. Check in a book by Title.
 7. Save the collection and exit the application.

 The Main class works in conjunction with the Library class, which handles
 the underlying functionality of managing the library's collection.
 */
public class MainFrame extends JFrame {

    private static final String collectionFile = "collection.txt";

    // GUI components
    private Library library;
    private JPanel BasePanel;
    private JPanel ButtonPanel;
    private JButton AddFileTab;
    private JButton MainLMSTab;
    private JPanel AddBookPanel;
    private JPanel MainPanel;
    private JPanel cardPanel;
    private JTextField tfFileName;
    private JButton addBooksButton;
    private JButton ClearButton;
    private JTextArea textArea1;
    private JButton CheckInButton;
    private JButton CheckOutButton;
    private JTextField tfCheckInOut;
    private JButton RMBookT;
    private JButton RMBookNum;
    private JTextField tfRemoveBook;
    private JButton ExitButton;
    private JButton DisplayBooksButton;
    private JButton button1Clear;
    private JButton button2Clear;

    /**
     Constructor: MainFrame
     Initializes the GUI frame, sets up the Library instance, loads the book collection from file, and
     defines actions for each button in the interface.
     */
    public MainFrame () {

        library = new Library();  // Initialize library collection

        // Set up the GUI frame properties
        setContentPane(BasePanel);
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        // Load the library collection from a file and display in textArea1
        library.loadCollection(collectionFile);
        textArea1.setText(library.listBooks());  // Display loaded books in textArea1

        // Switch to Add Book panel
        AddFileTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(AddBookPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        // Switch to Main LMS panel
        MainLMSTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(MainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        /*
         Adds books to the library collection from a specified file.
         Triggered by the "addBooksButton" in the GUI.
         */
       addBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = tfFileName.getText().trim();
                String addBookMessage = library.addBooksFromFile(filename);
                textArea1.setText(addBookMessage);
            }
       });

        // Clears the file input field (tfFileName)
        ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfFileName.setText("");
            }
        });

        /*
         Checks in a book to the library collection by title.
         Triggered by the "CheckInButton" in the GUI.
         */
        CheckInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfCheckInOut.getText().trim();
                textArea1.setText(library.checkIn(BookTitle));
            }
        });

        /*
         Checks out a book from the library collection by title.
         Triggered by the "CheckOutButton" in the GUI.
         */
        CheckOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfCheckInOut.getText().trim();
                textArea1.setText(library.checkOut(BookTitle));
            }
        });

        /*
         Displays the list of all books in the library collection.
         Triggered by the "DisplayBooksButton" in the GUI.
         */
        DisplayBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(library.listBooks());
            }
        });

        /*
         Saves the current collection to file and exits the application.
         Triggered by the "ExitButton" in the GUI.
         */
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saveMessage = library.saveCollection(collectionFile);
                textArea1.setText(saveMessage);  // Display save status message in textArea1
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
                textArea1.setText(library.removeBookByTitle(BookTitle));
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
                textArea1.setText(library.removeBook(BookBarcode));
            }
        });

        // Clears the check-in/out input field (tfCheckInOut)
        button1Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfCheckInOut.setText("");
            }
        });

        // Clears the remove book input field (tfRemoveBook)
        button2Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfRemoveBook.setText("");
            }
        });
    }

    /**
     Main method: Starts the GUI application for the Library Management System.
     */
    public static void main(String[] args) {
        new MainFrame();
    }
}
