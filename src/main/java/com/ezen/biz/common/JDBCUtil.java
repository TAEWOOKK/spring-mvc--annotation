package com.ezen.biz.common;

import java.sql.*;

public class JDBCUtil {

	// 오라클 데이터베이스 연결 수행
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String uid = "spring_user";
		String pass = "ora123";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uid, pass);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 오라클 데이터베이스 연결 해지(조회 구문에서도 사용)
	public static void close(Connection conn, Statement stmt, ResultSet rs) {

		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//오라클 데이터베이스 연결해지(Insert Update Delete 구분에 대해 사용)
	public static void close(Connection conn, Statement stmt) {

		try {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
