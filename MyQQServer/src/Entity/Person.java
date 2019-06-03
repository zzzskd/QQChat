package Entity;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
*	等待user继承的父类
*		|---名字
*		|---昵称
*		|---性别
*		|---头像
*/
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String user_name;					//用户名
	public String user_nickname = "jia";		//用户昵称
	public int user_sex = -1; 					//-1保密， 0为男， 1为女
	public ImageIcon user_avata; 				//用户头像
	
	
	
	
	//构造函数
	public Person() {
		super();
	}
	public Person(String user_name, String user_nickname) {
		super();
		this.user_name = user_name;
		this.user_nickname = user_nickname;
	}
	//获取成员变量值的方法
	public String getUsername() {
		return user_name;
	}	
	public String getUserNickname() {
		return user_nickname;
	}	
	public int getUserSex() {
		return user_sex;
	}
	public ImageIcon getUserAvata() {
		return user_avata;
	}

	
}

