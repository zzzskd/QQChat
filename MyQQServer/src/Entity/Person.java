package Entity;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
*	�ȴ�user�̳еĸ���
*		|---����
*		|---�ǳ�
*		|---�Ա�
*		|---ͷ��
*/
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public String user_name;					//�û���
	public String user_nickname = "jia";		//�û��ǳ�
	public int user_sex = -1; 					//-1���ܣ� 0Ϊ�У� 1ΪŮ
	public ImageIcon user_avata; 				//�û�ͷ��
	
	
	
	
	//���캯��
	public Person() {
		super();
	}
	public Person(String user_name, String user_nickname) {
		super();
		this.user_name = user_name;
		this.user_nickname = user_nickname;
	}
	//��ȡ��Ա����ֵ�ķ���
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

