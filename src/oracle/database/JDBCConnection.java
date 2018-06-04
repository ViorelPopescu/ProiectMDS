package oracle.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JDBCConnection {
	private static JDBCConnection instance = null;
	private Connection databaseConnection = null;
	private final String DB_URL = "jdbc:oracle:thin:@193.226.51.37:1521:o11g";
	private final String DB_USER = "grupa241";
	private final String DB_PASS = "bazededate";

	public static JDBCConnection getInstance()
	{
		if(instance == null)
			instance = new JDBCConnection();
		return instance;
	}

	private JDBCConnection() {
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		databaseConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void closeResource() {
		if (databaseConnection != null) {
			try {
				databaseConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	public Connection getDatabaseConnection() {
		return databaseConnection;
	}
}
