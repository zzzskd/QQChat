package UserSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.server.ServerCloneException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;

import Entity.ChatUIEntity;
import Entity.User;
import Frame.ChatUI;
import Frame.FriendsUI;
import _Util.*;

/**客户端的信息处理中心，处理来自服务器的各种消息并作出相应
*	run()方法是不需要用户来调用的，当通过start方法启动一个线程之后，当线程获得了CPU执行时间，
*	便进入run方法体去执行具体的任务。注意，继承Thread类必须重写run方法，在run方法中定义具体要执行的任务
*/
public class ChatTread extends Thread{
	private Client client;
	private boolean isOnline = true; 						
	private User user; 										//如果同意好友请求， 则刷新好友列表
	private FriendsUI friendsUI; 							//刷新好友列表用
	private String username; 								//如果创建新的聊天窗口（chatUI)那么必须将username传进去 用来发送消息
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		 this.isOnline = true; 
	}
	public ChatTread(Client client, User user, FriendsUI friendsUI) {
		this.client = client;
		this.user = user;
		this.friendsUI = friendsUI;
		this.username = user.getUsername();
		//this.chat_windows = chat_windows;
	}
	

	
/*
 *	每个用户登录进来后会得到一个封装了socket 的client对象，在run方法里不断通过client提供的getdata方法获得主机发来的CommandTranser对象，
 *用execute()方法解析这个命令
 *		|---message，往ChatUI 里的聊天框里追加Commandtranser携带的消息
 *		|---worldchat,往ChatUI 里的聊天框里追加Commandtranser携带的消息
 *		|---requeste_add_friend，选择接受或拒接添加好友的请求，通过client返回去一个Commandtranser
 *		|---request_send_file ，接受或拒绝发送文件的请求。
 *		|---accept_add_friend，对方接受了天加好友请求，服务器自自动更新相关操作，弹框提示一下即可，直接return就行（这里也可以优化做到刷新好友列表，但没做）
 *		|---refuse_to_add：被拒绝了也要弹框提示一下。
 *		|---changepwd 修改密码是否成功也要弹框提示一下
 */
	@Override
	public void run() {
		if(!isOnline) {
			JOptionPane.showMessageDialog(null,  "unbelievable ！！！");
			return;
		}
		while(isOnline) {
			
			CommandTranser cmd = client.getData();
			//与服务器端相同处理接收到的消息(命令)
			//这里处理来自服务器的消息(命令)
			if(cmd != null) {
			 execute(cmd);
			}
		}
	}
	private void execute(CommandTranser cmd) {				//处理消息(命令)
		//登录、忘记密码、注册消息未在此处处理
		System.out.println(cmd.getCmd());
		
		//聊天消息请求 
		if("message".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			//查询是否有与该好友的窗口该窗口
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI(friendname);
			if(chatUI == null) {
				chatUI = new ChatUI(username, friendname, username, client);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName(friendname);
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); 							//如果以前创建过仅被别的窗口掩盖了 就重新显示
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yy-MM-dd hh:mm:ss a");
			String message = friendname + "说："
					+ (String) cmd.getData() + "\t" + sdf.format(date)
					+ "\n";
			chatUI.getChatWin().append(message); 		//往聊天框里追加消息
			return;
		}
		
		if("WorldChat".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult()); 
//				return;
//			}
			//查询是否有与该好友的窗口该窗口
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				chatUI = new ChatUI("WorldChat", "WorldChat", user.getUsername(), client);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //如果以前创建过仅被别的窗口掩盖了 就重新显示
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yy-MM-dd hh:mm:ss a");
			String message = friendname + "说："
					+ (String) cmd.getData() + "\t" + sdf.format(date)
					+ "\n";
			chatUI.getChatWin().append(message); //追加消息
			return;
		}
		
		if("requeste_add_friend".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			String sendername = cmd.getSender();
			int flag = JOptionPane.showConfirmDialog(null, "是否同意" + sendername + "的好友请求", "好友请求", JOptionPane.YES_NO_OPTION);
			System.out.println(flag);
			if(flag == 0) {
				cmd.setCmd("accept_add_friend");
			} else {
				cmd.setCmd("refuse_add_friend");			
			}
			cmd.setSender(username);
			cmd.setReceiver(sendername); 
			client.sendData(cmd);
			return;
		}
		
		if("request_send_file".equals(cmd.getCmd())){
			String sendername = cmd.getSender();
			 
			int flag = JOptionPane.showConfirmDialog(null,cmd.getData(), "文件传输请求", JOptionPane.YES_NO_OPTION);
			System.out.println(flag);
			if(flag == 0) {
				cmd.setCmd("accept_send_file");
				cmd.setSender(username);
				cmd.setReceiver(sendername);
				client.sendData(cmd);
				String str=cmd.getData().toString();
				int strStartIndex = str.indexOf("{");
		        int strEndIndex = str.indexOf("}");
		        String filename=str.substring(strStartIndex+1, strEndIndex);
		        
		        strStartIndex = str.indexOf("[");
		        strEndIndex = str.indexOf("]");
		        str=str.substring(strStartIndex+1, strEndIndex);
		        long fileLen=Long.valueOf(str);
		        ServerSocket ss;
				try {
					ss = new ServerSocket(4444);
					Socket s = ss.accept();
					final ChatUI chatUI = ChatUIList.getChatUI(sendername);
			        FileReciver fr=new FileReciver(s, filename, fileLen, chatUI.progressBar);
					fr.addPropertyChangeListener(new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							if("progress".equals(evt.getPropertyName())) {
								chatUI.progressBar.setValue((Integer)evt.getNewValue());
							}
						}
					});
			        fr.execute();
				} catch (IOException e1) {
				}
				return;
			} else {
				cmd.setCmd("refuse_send_file");	
				cmd.setSender(username);
				cmd.setReceiver(sendername);
				client.sendData(cmd);
				return;
			}
			
		}
		if("accept_send_file".equals(cmd.getCmd())){
			String sendername = cmd.getSender();						
			final ChatUI chatUI = ChatUIList.getChatUI(sendername);
			Socket socket;
			try {
				socket = new Socket("localhost",4444);
				FileSender ss=new FileSender(socket, chatUI.progressBar,chatUI.file);
				//System.out.println("这条语句输出说明文件socket已连接");
				ss.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if("progress".equals(evt.getPropertyName())) {
							chatUI.progressBar.setValue((Integer)evt.getNewValue());
						}
					}
				});
				ss.execute();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if("refuse_send_file".equals(cmd.getCmd())){
			JOptionPane.showMessageDialog(null,  "对方拒绝接受文件");
			return ;
		}
//		if("successful".equals(cmd.getCmd())) {
//			JOptionPane.showMessageDialog(null, cmd.getResult()); 
//			return;
//		}
		
		if("accept_add_friend".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			
//			CommandTranser newcmd = new CommandTranser();
//			newcmd.setCmd("updatefriendlist");
//			newcmd.setReceiver(username);
//			newcmd.setSender(username);
//			newcmd.setData(user);
//			client.sendData(cmd);
			return;
			
		}
		
//		if("updatefriendlist".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult()); 
//				return;
//			}
//			
//			User tmp = (User)cmd.getData();
//			user.setFriendsList(tmp.getFriend());
//			friendsUI.validate();
//			friendsUI.repaint();
//			friendsUI.setVisible(true);
//			
//			return;
//		}
		
		if("refuse_to_add".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			return;
		}
		
		if("changepwd".equals(cmd.getCmd())) {
			JOptionPane.showMessageDialog(null, cmd.getResult());
			return;
		}
		return;
	}
	
}

