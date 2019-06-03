﻿package _Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entity.User;
import _Util.DBHelper;

/**serService类负责处理具体的和数据库交互的内容，
 * 如查询用户账号和密码是否匹配、
 * 修改密码、
 * 注册用户、
 * 忘记密码、
 * 添加好友等操作。
*/
public class UserService {
	
	//login验证账号密码
	public boolean checkUser(User user) {
		PreparedStatement stmt = null; 	//PreparedStatement是用来执行SQL查询语句的API之一
		Connection conn = null; 		//与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		ResultSet rs = null; 			//是数据中查询结果返回的一种对象，可以说结果集是一个存储查询结果的对象，但是结果集并不仅仅具有存储的功能，他同时还具有操纵数据的功能，可能完成对数据的更新等
		conn = DBHelper.getConnection();
		String sql = "select * from tb_user where user_name =? and user_pwd =?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getUserpwd());
			rs = stmt.executeQuery();
			if(rs.next()) {
				//能查到说明用户已经注册
				return true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//登陆后向客户端发送其好友列表,装在一个ArrayList里面进行传输
	public User getFriendsList(User user) {
		PreparedStatement stmt = null; 
		Connection conn = null; 
		ResultSet rs = null; 
		conn = DBHelper.getConnection();
		String sql = "select * from " + user.getUsername() + "_friends";
		ArrayList<String> friendslist = new ArrayList<String>(); //这里假设好友不超过20个
		try {
			
			stmt = conn.prepareStatement(sql);
			//stmt.setString(1, user.getUsername() + "_friends"); 这样的话会报错
			rs = stmt.executeQuery();
			int count = 0;
			while(rs.next()) {
				friendslist.add(rs.getString(2));	//获取好友name
				count++;
			}
			user.setFriendsNum(count);
			user.setFriendsList(friendslist);
			return user;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	//用户注册
	public boolean registerUser(User user) {
		PreparedStatement stmt1 = null; //PreparedStatement是用来执行SQL查询语句的API之一
		PreparedStatement stmt2 = null;
		PreparedStatement stmt3 = null;
		Connection conn = null; 
		ResultSet rs = null; 
		int insertFlag = 0;
		int creatFlag = 0;
		conn = DBHelper.getConnection();
		String sql = "select * from tb_user where user_name =?";
		String insertusersql = "insert into tb_user (user_name, user_pwd, user_question, user_ans) values(?, ?, ?, ?)";
		String creatfriendstabsql = "CREATE TABLE " + user.getUsername() + "_friends " + "(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, PRIMARY KEY (id))";
		try {
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, user.getUsername());
			rs = stmt1.executeQuery();
			if(rs.next()) {
				System.out.println("该用户已存在" + user.getUsername() + "***");
				//用户已被注册
				return false;
			}
			else {
				System.out.println("该用户不存在" + user.getUsername() + "***");
				//向用户表插入数据
				stmt2 = conn.prepareStatement(insertusersql);
				stmt2.setString(1, user.getUsername());
				stmt2.setString(2, user.getUserpwd());
				stmt2.setString(3, user.getUserQuestion());
				stmt2.setString(4, user.getUserAnswer());
				insertFlag = stmt2.executeUpdate();
				System.out.println("向表中插入数据" + user.getUsername() + "***" + insertFlag);
				//创建好友表
				stmt3 = conn.prepareStatement(creatfriendstabsql);
				creatFlag = stmt3.executeUpdate();
				
				System.out.println("创建表" + user.getUsername() + "***" + creatFlag);
				if(insertFlag == 1) {
					return true;
				}
				
				//System.out.println("不高兴" + user.getUsername() + "***");
				//return true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
				if(stmt3 != null) {
					stmt3.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//添加好友
	public boolean addFriend(String sender, String receiver) {
		PreparedStatement stmt1 = null; 
		PreparedStatement stmt2 = null; 
		Connection conn = null; 
		int updateResult1 = 0;
		int updateResult2 = 0;
		conn = DBHelper.getConnection();
		String sql1 = "insert into " + sender + "_friends (name) values(?)";
		//String sql1 = "insert into ? (name) values(?)";
		String sql2 = "insert into " + receiver + "_friends (name) values(?)";
		//String sql2 = "insert into ? (name) values(?)";
		try {
			stmt1 = conn.prepareStatement(sql1);
			stmt2 = conn.prepareStatement(sql2);
			stmt1.setString(1, receiver);
			stmt2.setString(1, sender);
			updateResult1 = stmt1.executeUpdate();
			updateResult2 = stmt2.executeUpdate();
			if(updateResult1 == 1 && updateResult2 == 1) {
				return true;
			}
			else {
				//希望能插入， 如果插入不成功的话，应该将插入成功的删除....这里不做处理了
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(stmt1 != null) {
					stmt1.close();
				}
				if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	//修改信息
	public boolean changeInfo(User user) {
		return false;
	}
	
	//修改密码 忘记密码
	public boolean changePassword(User user) {
		PreparedStatement stmt1 = null; //PreparedStatement是用来执行SQL查询语句的API之一
		PreparedStatement stmt2 = null; //PreparedStatement是用来执行SQL查询语句的API之一
		Connection conn = null; //与特定数据库的连接（会话）。在连接上下文中执行 SQL 语句并返回结果
		ResultSet rs = null; //是数据中查询结果返回的一种对象，可以说结果集是一个存储查询结果的对象，但是结果集并不仅仅具有存储的功能，他同时还具有操纵数据的功能，可能完成对数据的更新等
		int updateFlag = 0;
		conn = DBHelper.getConnection();
		//String sql = "select * from tb_user where user_question =? and user_ans =?";
		String updatesql = "update tb_user set user_pwd =? where user_name = ?";
		
		try {
			//stmt1 = conn.prepareStatement(sql);
			//stmt1.setString(1, user.getUserQuestion());
			//stmt1.setString(2, user.getUserAnswer());
			//rs = stmt1.executeQuery();
			
			//if(rs.next()) {
				
				stmt2 = conn.prepareStatement(updatesql);
				stmt2.setString(1, user.getUserpwd());
				stmt2.setString(2, user.getUsername());
				updateFlag = stmt2.executeUpdate();
				if(updateFlag == 1)
				  return true;
			//}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}if(stmt1 != null) {
					stmt1.close();
				}if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	public User getUser(User user) {								//获得用户的相关信息
		PreparedStatement stmt1 = null; 
		PreparedStatement stmt2 = null; 
		Connection conn = null; 
		ResultSet rs = null; 
		conn = DBHelper.getConnection();
		String sql = "select * from tb_user where user_name =?";
		try {
			stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, user.getUsername());
			rs = stmt1.executeQuery();
			if(rs.next()) {
				user.setUsername(rs.getString("user_name"));
				user.setUserAnswer(rs.getString("user_ans"));
				user.setUserQuestion(rs.getString("user_question"));
				return user; 
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if(rs != null) {
					rs.close();
				}if(stmt1 != null) {
					stmt1.close();
				}if(stmt2 != null) {
					stmt2.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}

