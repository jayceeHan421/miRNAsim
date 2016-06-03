import java.sql.*;



public class DatabaseConnection {
	public static Connection connect(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return conn;
	}
	
	public static void closeDatabase(PreparedStatement ps1, ResultSet rs1, PreparedStatement ps2, ResultSet rs2,Connection conn)
	{
		try {
			if(rs1 != null && !rs1.isClosed())
				rs1.close();
			if(ps1 != null && !ps1.isClosed())
				ps1.close();
			if(rs2 != null && !rs2.isClosed())
				rs2.close();
			if(ps2 != null && !ps2.isClosed())
				ps2.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
