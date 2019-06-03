﻿package _Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**负责和数据库建立连接
 * 并且返回一个连接对象

*/
public class DBHelper {
	private static final String driver = "com.mysql.jdbc.Driver";
	//private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/myqquser?&useSSL=false&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "621226";
	private static Connection con = null;
	
	
	static {							//static静态代码负责加载驱动
		try {
			Class.forName(driver); 		//Class.forName(xxx.xx.xx)的作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if(con == null) {
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return con;
	}
}
