package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class controller{

	private static String url="jdbc:postgresql://localhost:5432/jforce-task?user=postgres&password=root";
	private static String driver = "org.postgresql.Driver";
	private static final int pool_size = 4;
	private static List<Connection> ConnectionPool = new ArrayList();
	
	static {
		for(int i=0; i<=pool_size; i++) {
			ConnectionPool.add(createConnection());
		}
	}

	public static Connection createConnection() {
		Connection connection = null;
		
		try {
			Class.forName(driver);
			
			connection = DriverManager.getConnection(url);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
	
	public static Connection giveConnection() {
		if(!ConnectionPool.isEmpty()) {
			return ConnectionPool.remove(0);
		}else {
			return createConnection();
		}
	}
	
	public static void submitConnection(Connection connection) {
		if(ConnectionPool.size() < pool_size) {
			ConnectionPool.add(connection);
		}else {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	
}
