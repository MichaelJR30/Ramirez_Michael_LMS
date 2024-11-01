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

    public MainFrame () {

        library = new Library();

        setContentPane(BasePanel);
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        library.loadCollection(collectionFile);
        textArea1.setText(library.listBooks());  // Display loaded books in textArea1

        AddFileTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(AddBookPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

        MainLMSTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardPanel.removeAll();
                cardPanel.add(MainPanel);
                cardPanel.repaint();
                cardPanel.revalidate();
            }
        });

       addBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = tfFileName.getText().trim();
                String addBookMessage = library.addBooksFromFile(filename);
                textArea1.setText(addBookMessage);
            }
       });

        ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfFileName.setText("");
            }
        });
        CheckInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfCheckInOut.getText().trim();
                textArea1.setText(library.checkIn(BookTitle));
            }
        });

        CheckOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfCheckInOut.getText().trim();
                textArea1.setText(library.checkOut(BookTitle));
            }
        });

        DisplayBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(library.listBooks());
            }
        });
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String saveMessage = library.saveCollection(collectionFile);
                textArea1.setText(saveMessage);  // Display save status message in textArea1
                System.exit(0);  // Exit the application
            }
        });

        RMBookT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookTitle = tfRemoveBook.getText().trim();
                textArea1.setText(library.removeBookByTitle(BookTitle));
            }
        });

        RMBookNum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String BookBarcode = tfRemoveBook.getText().trim();
                textArea1.setText(library.removeBook(BookBarcode));
            }
        });
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
