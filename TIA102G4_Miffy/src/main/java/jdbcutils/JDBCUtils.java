package jdbcutils;

import java.sql.*;

public class JDBCUtils {

	public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String URL = "jdbc:mysql://localhost:3306/chugether?serverTimezone=Asia/Taipei";
		String USER = "root";
		String PASSWORD = "123456";
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
	}

	public static void closeResource(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeResource(Connection conn, Statement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
