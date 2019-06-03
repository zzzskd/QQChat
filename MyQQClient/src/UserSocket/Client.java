package UserSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import _Util.CommandTranser;

/*
*	Client��
*		|----socket
*		|----�����socket���ͺͽ���CommandTranser ����ĺ���
*/

public class Client {
	private int port = 2222; 
	private String Sever_address = "127.0.0.1"; //����������ip
	private Socket socket;
	
	
	
	
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	} 
	
	
	
	
	
	
	public void sendData(CommandTranser cmd) {						//�����˷�������
		//��Ҫ������������д�������Ϣ���ȡ������Ϣ�� 
		//������Ϣһ��д���ļ�����ô�������Ϣ�Ϳ��������־û���
		ObjectOutputStream oos = null; 
		try {
			if(socket == null) {
				return;
			}
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cmd);
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
		}
	}
	public CommandTranser getData() {								//���ܷ���˷��͵���Ϣ
		ObjectInputStream ois = null;
		CommandTranser cmd = null;
		if(socket == null) {
			return null;
		}
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			cmd = (CommandTranser) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		return cmd;
	}
	public Client(){												//���캯��ʵ������ ��������
		try {
			socket = new Socket(Sever_address, port);
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "��������δ����");
		}
	}
}
