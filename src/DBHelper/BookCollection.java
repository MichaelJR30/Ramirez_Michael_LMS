package src.DBHelper;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 Michael Ramirez, Software Development I, CEN 3024C, November 17, 2024

 Class: BookCollection

 This class has represents the Library Management System (LMS).
 It manages the methods for adding, removing, listing, checking in/out books
 and displaying books from the database.
 */
public class BookCollection extends DBHelper {
	private final String TABLE_NAME = "BookCollection";
	public static final String title = "title";
	public static final String author = "author";
	public static final String genre = "genre";
	public static final String barcode = "barcode";
	public static final String status = "status";
	public static final String due_date = "due_date";

	/* prepareSQL
	 * Purpose: prepares the text of a SQL "select" command.
	 * Return Type: String
	 * Arguments:
	 * 	fields: the fields to be displayed in the output
	 * 	whatField: field to search for
	 * 	whatValue: value to search for within whatField
	 * 	sort: use ASC or DESC to specify the sorting order
	 * 	softField: the field that the data will be sorted by
	 */
	private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
		String query = "SELECT ";
		query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
		query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
		query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
		return query;
	}

	/* insert
	 * Purpose: insert a new record into the database
	 * Return Type: void
	 * Arguments: each field listed in the table from the database, in order
	 * Notes: Due to inheritance, this executes the execute method found in the parent class
	 */
	public void insert(String title, String author, String genre, String barcode, String status, String due_date) {
		title = title != null ? "\"" + title + "\"" : null;
		author = author != null ? "\"" + author + "\"" : null;
		genre = genre != null ? "\"" + genre + "\"" : null;
		barcode = barcode != null ? "\"" + barcode + "\"" : null;
		status = status != null ? "\"" + status + "\"" : null;
		due_date = due_date != null ? "\"" + due_date + "\"" : null;
		
		Object[] values_ar = {title, author, genre, barcode, status, due_date};
		String[] fields_ar = {BookCollection.title, BookCollection.author, BookCollection.genre, BookCollection.barcode, BookCollection.status, BookCollection.due_date};
		String values = "", fields = "";
		for (int i = 0; i < values_ar.length; i++) {
			if (values_ar[i] != null) {
				values += values_ar[i] + ", ";
				fields += fields_ar[i] + ", ";
			}
		}
		if (!values.isEmpty()) {
			values = values.substring(0, values.length() - 2);
			fields = fields.substring(0, fields.length() - 2);
			super.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
		}
	}

	/* delete
	 * Purpose: remove a record from the database
	 * Return Type: void
	 * Arguments: the field (key) used to determine if a row should be deleted, and value
	 * that should be removed
	 * Notes: Due to inheritance, this executes the execute method found in the parent class
	 */
	public void delete(String whatField, String whatValue) {
		super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
	}

	/* update
	 * Purpose: update a record in the database
	 * Return Type: void
	 * Arguments: the field (key) used to determine if a row should be updated, and value
	 * that should be updated
	 * Notes: Due to inheritance, this executes the execute method found in the parent class
	 */
	public void update(String whatField, String whatValue, String whereField, String whereValue) {
		super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
	}

	/* select
	 * Purpose: completes a SQL "select" command.
	 * Return Type: ArrayList<ArrayList<Object>> - this means it returns a 2D array of objects, so the data returned
	 * can be any type.
	 * Arguments:
	 * 	fields: the fields to be displayed in the output
	 * 	whatField: field to search for
	 * 	whatValue: value to search for within whatField
	 * 	sort: use ASC or DESC to specify the sorting order
	 * 	softField: the field that the data will be sorted by
	 * Notes: this method calls the private method "prepareSQL" within this class.
	 */
	public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	/* getExecuteResult
	 * Purpose: Executes a given SQL query and returns the result.
	 * Return Type: ArrayList<ArrayList<Object>> - this means it returns a 2D array of objects, so the data returned
	 * can be any type.
	 * Arguments: query - this is the SQL command that would be entered at the command line for a SQL query
	 * Notes: Due to inheritance, this executes the execute method found in the parent class.
	 */
	public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
		return super.executeQuery(query);
	}

	/* execute
	 * Purpose: Executes a SQL command that does not return any result.
	 * Return Type: void
	 * Arguments: query - this is the SQL command that would be entered at the command line for a SQL query
	 * Notes: Due to inheritance, this executes the execute method found in the parent class.
	 */
	public void execute(String query) {
		super.execute(query);
	}

	/* selectToTable
	 * Purpose:  Executes a SELECT query and formats the result as a table model for use in GUI components.
	 * Return Type: DefaultTableModel - uses a vector of vectors (i.e. a table) to store data
	 * Arguments:
	 * 	fields: the fields to be displayed in the output
	 * 	whatField: field to search for
	 * 	whatValue: value to search for within whatField
	 * 	sort: use ASC or DESC to specify the sorting order
	 * 	softField: the field that the data will be sorted by
	 * Notes: Due to inheritance, this executes the execute method found in the parent class.
	 */
	public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
		return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
	}

	/* DisplayBooks
	 * Purpose:  display the books in the database.
	 * Return Type: toString
	 */
	public String DisplayBooks( ){
		ArrayList<ArrayList<Object>> books = getExecuteResult("select * from BookCollection");
		StringBuilder displayText = new StringBuilder();
		for (ArrayList<Object> row : books) {
			displayText.append(row).append("\n");
		}
		return displayText.toString();
	}

	/* checkBookIn
	 * Purpose: update the status of the book to Check in and sets the due date to null.
	 * Return Type: toString
	 */
	public String checkBookIn(String title) {
		String BookTitle = title.trim();
		StringBuilder bookStatus = new StringBuilder();

		// Query the database to get the current status of the book by title
		ArrayList<ArrayList<Object>> data = select(BookCollection.status, BookCollection.title, BookTitle, null, null);
		if (data.isEmpty()){
			bookStatus.append("Book Not Found: ").append(BookTitle);
		} else {
			String currentStatus = data.get(0).get(0).toString(); // Get the status from the first row
			if(currentStatus.equals("Check in")){
				bookStatus.append("Book already checked in: ").append(BookTitle);
			} else {
				update(BookCollection.status, "Check in", BookCollection.title, BookTitle);
				update(BookCollection.due_date, "null", BookCollection.title, BookTitle);
				bookStatus.append("Book checked in: ").append(BookTitle).append(", Due Date: null");
			}
		}
		return bookStatus.toString();
	}

	/* checkBookOut
	 * Purpose: update the status of the book to Check out and sets the due date.
	 * Return Type: toString
	 */
	public String checkBookOut(String title) {
		String BookTitle = title.trim();
		StringBuilder bookStatus = new StringBuilder();

		// Query the database to get the current status of the book by title
		ArrayList<ArrayList<Object>> bookData = select(BookCollection.status, BookCollection.title, BookTitle, null, null);
		if (bookData.isEmpty()){
			bookStatus.append("Book Not Found: ").append(BookTitle);
		} else {
			String currentStatus = bookData.get(0).get(0).toString(); // Get the status from the first row
			if(currentStatus.equals("Check out")){
				bookStatus.append("Book already checked out: ").append(BookTitle);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.WEEK_OF_YEAR, 4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "Due Date: " + sdf.format(cal.getTime());
				update(BookCollection.status, "Check out", BookCollection.title, BookTitle);
				update(BookCollection.due_date, date, BookCollection.title, BookTitle);
				bookStatus.append("Book checked in: ").append(BookTitle).append(date);
			}
		}
		return bookStatus.toString();
	}

	// Close the database connection
	public void closeDatabase() {
		super.closeDatabase();
	}
}