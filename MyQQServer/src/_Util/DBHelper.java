package _Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* @author zzz
* @version 创建时间：2018年7月5日 上午9:21:37
*/
public class DBHelper {
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/myqquser?&useSSL=false&serverTimezone=UTC";
	private static final String username = "root";
	private static final String password = "zzzz";
	private static Connection con = null;
	
	//静态代码负责加载驱动
	static {
		try {
			Class.forName(driver); //Class.forName(xxx.xx.xx)的作用是要求JVM查找并加载指定的类，也就是说JVM会执行该类的静态代码段
			
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
