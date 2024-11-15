package src.DBHelper;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * Michael Ramirez, Software Development I, CEN 3024C, November 17, 2024
 *
 * Class: DBHelper
 *
 * This class provides the foundational methods to manage and interact with
 * a SQLite database for the Library Management System (LMS). It includes
 * methods for executing SQL commands, retrieving data in various formats,
 * and ensuring the database connection is properly managed by opening and
 * closing the connection as needed.
 *
 * DBHelper serves as a utility for handling SQL operations and forms
 * the backbone for BookCollection that interact with
 * the database.
 */
public class DBHelper {
	private final String DATABASE_NAME = "C:\\Users\\mikef\\sqlite\\DataBases\\LMSDataBases.db";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	// Constructor initializes the DBHelper object
	public DBHelper() {
		connection = null;
		statement = null;
		resultSet = null;
	}

	/* connect
	 * Purpose: Establishes a connection to the SQLite database specified
	 * by DATABASE_NAME. Initializes the connection and statement objects.
	 * Arguments: None
	 * Return Value: None
	 */
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* close
	 * Purpose: Closes the database connection and any active statement
	 * or resultSet objects to prevent memory leaks.
	 * Arguments: None
	 * Return Value: None
	 */
	private void close() {
		try {
			connection.close();
			statement.close();
			if (resultSet != null)
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* arrayListTo2DArray
	 * Purpose: Converts an ArrayList of ArrayLists into a 2D array.
	 * Useful for displaying query results in tabular form.
	 * Arguments:
	 * - list: ArrayList<ArrayList<Object>> - the data to be converted
	 * Return Value: Object[][] - the converted 2D array
	 */
	private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
		Object[][] array = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Object> row = list.get(i);
			array[i] = row.toArray(new Object[row.size()]);
		}
		return array;
	}

	/* execute
	 * Purpose: Executes a SQL command that does not return any result, such as
	 * an INSERT, UPDATE, or DELETE operation. Ensures connection management.
	 * Arguments:
	 * - sql: String - the SQL command to execute
	 * Return Value: None
	 */
	protected void execute(String sql) {
		try {
			connect();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}

	/* executeQueryToTable
	 * Purpose: Executes a SELECT query and formats the result as a
	 * DefaultTableModel for use in GUI components.
	 * Arguments:
	 * - sql: String - the SQL query to execute
	 * Return Value: DefaultTableModel - the result formatted as a table model
	 */
	protected DefaultTableModel executeQueryToTable(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> columns = new ArrayList<Object>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++)
			columns.add(resultSet.getMetaData().getColumnName(i));
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++)
				subresult.add(resultSet.getObject(i));
				result.add(subresult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
	}

	/* executeQuery
	 * Purpose: Executes a SELECT query and returns the result as an ArrayList
	 * of ArrayLists. Each row in the result set is stored as an ArrayList of
	 * Objects, making it flexible for various data types.
	 * Arguments:
	 * - sql: String - the SQL query to execute
	 * Return Value: ArrayList<ArrayList<Object>> - the query result
	 */
	protected ArrayList<ArrayList<Object>> executeQuery(String sql) {
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
		connect();
		try {
			resultSet = statement.executeQuery(sql);
			int columnCount = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				ArrayList<Object> subresult = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					subresult.add(resultSet.getObject(i));
				}
				result.add(subresult);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		close();
		return result;
	}

	/* closeDatabase
	 *
	 * Purpose: Public method to close the database connection. Can be called
	 * externally to ensure the database is closed when the application exits.
	 * Arguments: None
	 * Return Value: None
	 */
	public void closeDatabase() {
		close();
	}
}