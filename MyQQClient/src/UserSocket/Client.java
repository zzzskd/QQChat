package UserSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import _Util.CommandTranser;

/*
*	Client类
*		|----socket
*		|----用这个socket发送和接受CommandTranser 对象的函数
*/

public class Client {
	private int port = 2222; 
	private String Sever_address = "127.0.0.1"; //服务器主机ip
	private Socket socket;
	
	
	
	
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	} 
	
	
	
	
	
	
	public void sendData(CommandTranser cmd) {						//向服务端发送数据
		//主要的作用是用于写入对象信息与读取对象信息。 
		//对象信息一旦写到文件上那么对象的信息就可以做到持久化了
		ObjectOutputStream oos = null; 
		try {
			if(socket == null) {
				return;
			}
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cmd);
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
		}
	}
	public CommandTranser getData() {								//接受服务端发送的消息
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
	public Client(){												//构造函数实例化， 建立连接
		try {
			socket = new Socket(Sever_address, port);
		} catch(UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "服务器端未开启");
		}
	}
}
