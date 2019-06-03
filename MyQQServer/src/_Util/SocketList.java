package _Util;
import java.net.Socket;
import java.util.HashMap;

import Entity.SocketEntity;

/**
*	全局存在的一张hashmap表，记录着一个个socketEntity（包含名字，socket）
*/
public class SocketList {
	private static HashMap<String, Socket> map = new HashMap<String, Socket>();			//静态全局变量
	
	
	
	//增删查改这张管理着socket的hashmap表
	public static Socket getSocket(String name) {				//通过昵称返回socket 类比socklist在客户端创建 ChatUIList
		return map.get(name);
	}
	public static HashMap<String, Socket> getMap(){
		return map;
	}
	public static void deleteSocket(String name) {
		if(map.get(name) != null) {
			map.remove(name);
		}
		return;
	}
	public static void addSocket(SocketEntity socketEntity) {
		map.put(socketEntity.getName(), socketEntity.getSocket());	
	}
}
