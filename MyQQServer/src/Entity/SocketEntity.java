package Entity;

import java.net.Socket;

/**
* @author zzz
* @version 创建时间：2018年7月5日 下午2:35:00
*/
//将每一个socket取个名字便于寻找，这就是SocketEntity的作用
public class SocketEntity {
	private Socket socket;
	private String name;
	
	public SocketEntity() {
		super();
	}
	
	public SocketEntity(Socket socket, String name) {
		super();
		this.socket = socket;
		this.name = name;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
