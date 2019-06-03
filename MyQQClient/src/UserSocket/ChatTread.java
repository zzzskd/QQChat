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

/**�ͻ��˵���Ϣ�������ģ��������Է������ĸ�����Ϣ��������Ӧ
*	run()�����ǲ���Ҫ�û������õģ���ͨ��start��������һ���߳�֮�󣬵��̻߳����CPUִ��ʱ�䣬
*	�����run������ȥִ�о��������ע�⣬�̳�Thread�������дrun��������run�����ж������Ҫִ�е�����
*/
public class ChatTread extends Thread{
	private Client client;
	private boolean isOnline = true; 						
	private User user; 										//���ͬ��������� ��ˢ�º����б�
	private FriendsUI friendsUI; 							//ˢ�º����б���
	private String username; 								//��������µ����촰�ڣ�chatUI)��ô���뽫username����ȥ ����������Ϣ
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
 *	ÿ���û���¼�������õ�һ����װ��socket ��client������run�����ﲻ��ͨ��client�ṩ��getdata�����������������CommandTranser����
 *��execute()���������������
 *		|---message����ChatUI ����������׷��CommandtranserЯ������Ϣ
 *		|---worldchat,��ChatUI ����������׷��CommandtranserЯ������Ϣ
 *		|---requeste_add_friend��ѡ����ܻ�ܽ���Ӻ��ѵ�����ͨ��client����ȥһ��Commandtranser
 *		|---request_send_file �����ܻ�ܾ������ļ�������
 *		|---accept_add_friend���Է���������Ӻ������󣬷��������Զ�������ز�����������ʾһ�¼��ɣ�ֱ��return���У�����Ҳ�����Ż�����ˢ�º����б���û����
 *		|---refuse_to_add�����ܾ���ҲҪ������ʾһ�¡�
 *		|---changepwd �޸������Ƿ�ɹ�ҲҪ������ʾһ��
 */
	@Override
	public void run() {
		if(!isOnline) {
			JOptionPane.showMessageDialog(null,  "unbelievable ������");
			return;
		}
		while(isOnline) {
			
			CommandTranser cmd = client.getData();
			//�����������ͬ������յ�����Ϣ(����)
			//���ﴦ�����Է���������Ϣ(����)
			if(cmd != null) {
			 execute(cmd);
			}
		}
	}
	private void execute(CommandTranser cmd) {				//������Ϣ(����)
		//��¼���������롢ע����Ϣδ�ڴ˴�����
		System.out.println(cmd.getCmd());
		
		//������Ϣ���� 
		if("message".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			//��ѯ�Ƿ�����ú��ѵĴ��ڸô���
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI(friendname);
			if(chatUI == null) {
				chatUI = new ChatUI(username, friendname, username, client);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName(friendname);
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); 							//�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yy-MM-dd hh:mm:ss a");
			String message = friendname + "˵��"
					+ (String) cmd.getData() + "\t" + sdf.format(date)
					+ "\n";
			chatUI.getChatWin().append(message); 		//���������׷����Ϣ
			return;
		}
		
		if("WorldChat".equals(cmd.getCmd())) {
//			if(cmd.isFlag() == false) {
//				JOptionPane.showMessageDialog(null, cmd.getResult()); 
//				return;
//			}
			//��ѯ�Ƿ�����ú��ѵĴ��ڸô���
			String friendname = cmd.getSender();
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				chatUI = new ChatUI("WorldChat", "WorldChat", user.getUsername(), client);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yy-MM-dd hh:mm:ss a");
			String message = friendname + "˵��"
					+ (String) cmd.getData() + "\t" + sdf.format(date)
					+ "\n";
			chatUI.getChatWin().append(message); //׷����Ϣ
			return;
		}
		
		if("requeste_add_friend".equals(cmd.getCmd())) {
			if(cmd.isFlag() == false) {
				JOptionPane.showMessageDialog(null, cmd.getResult()); 
				return;
			}
			String sendername = cmd.getSender();
			int flag = JOptionPane.showConfirmDialog(null, "�Ƿ�ͬ��" + sendername + "�ĺ�������", "��������", JOptionPane.YES_NO_OPTION);
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
			 
			int flag = JOptionPane.showConfirmDialog(null,cmd.getData(), "�ļ���������", JOptionPane.YES_NO_OPTION);
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
				//System.out.println("����������˵���ļ�socket������");
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
			JOptionPane.showMessageDialog(null,  "�Է��ܾ������ļ�");
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

