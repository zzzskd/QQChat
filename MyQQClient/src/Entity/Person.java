package Entity;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
*
*/
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String user_name;
	public String user_nickname = "zzz";
	public int user_sex = -1; 							//-1���ܣ� 0Ϊ�У� 1ΪŮ
	public ImageIcon user_avata; 						//�û�ͷ��
	
	
	
	
	
	
	
	
	public Person() {
		super();
	}
	public Person(String user_name, String user_nickname) {
		super();
		this.user_name = user_name;
		this.user_nickname = user_nickname;
	}
	
	
	
	
	
	
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
