package _Util;
import java.util.HashMap;

import Entity.ChatUIEntity;
import Frame.ChatUI;

/* 记录客户端打开的聊天页面，处理与好友的消息弹窗
*  窗口控制器 每个聊天界面都在这里"注册"
*/
public class ChatUIList {
	private static HashMap<String, ChatUI> map = new HashMap<String, ChatUI>();		//容器
	
	
	
	
	
	public static void addChatUI(ChatUIEntity chatUIEntity) {						//向map里面“注册”
		map.put(chatUIEntity.getName(), chatUIEntity.getChatUI());	
	}
	public static void deletChatUI(String chatUIName) {								//关闭窗口后要从map里删除这个窗口
		//删除之前查看是否有这个窗口, 防止出错
		if(map.get(chatUIName) != null) {
			map.remove(chatUIName);
		}
	}
	public static ChatUI getChatUI(String name) {									//通过昵称返回窗口封装体
		return map.get(name);
	}
}

