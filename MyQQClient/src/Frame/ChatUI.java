package Frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import UserSocket.Client;
import _Util.ChatUIList;
import _Util.CommandTranser;

/**聊天窗口
*
*/
public class ChatUI extends JFrame implements ActionListener{
	private JProgressBar progressBar_2;
	private static final long serialVersionUID = 1L;
	private JTextArea chat_windows; 					//双方交流信息的文本框
	private JTextField message_txt; 					//写信息的文本框
	private JButton send_btn; 							//发送按钮
	public JButton send_file;							//发送文件按钮
	private JPanel panel;
	private String owner_name;							//这个聊天框体的主人
	private String friend_name;							//这个聊天框体的客人
	private String who;									//这个聊天框体属于哪个Client(里面包含了socket)
	private Client client; 								//这个socket只用来发送消息， 收消息不用这个处理
	public JProgressBar progressBar;
	public JFileChooser chooser;
	public String fileName;
	public File file;
	//private ChatTread thread; //接受信息线程
	
	
	
	
	
	public ChatUI(String ower_name, String friend_name, String who, Client client){
		this.owner_name = ower_name;
		this.friend_name = friend_name;
		this.client = client;
		this.who = who;
		
		//生成聊天页面
		init();
		
		setTitle(ower_name + "正在和" + friend_name + "聊天");
		setSize(596, 398);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		// 开启客户端接收信息线程,将收到的消息反馈到 chat_windows
		//thread = new ChatTread(client); //将唯一client传入
		//thread.start();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		panel = new JPanel();

		progressBar = new JProgressBar();
		message_txt = new JTextField(20);
		send_btn = new JButton("发送消息");
		send_file=new JButton("发送文件");
		panel.add(message_txt);
		panel.add(send_btn);
		panel.add(send_file);
		panel.add(progressBar);
		chat_windows = new JTextArea();
		chat_windows.setEditable(false);
		chat_windows.add(new JScrollBar(JScrollBar.VERTICAL)); //滚动条
		add(chat_windows, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		send_btn.addActionListener(this);
		send_file.addActionListener(this);
		
		//窗口关闭事件
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ChatUIList.deletChatUI(friend_name);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				ChatUIList.deletChatUI(friend_name);
			}
		});
	}
	//发送方消息
	public JTextArea getChatWin() {
		return chat_windows;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {					//按钮的点击事件用actionPerforme
		//点击发送消息按钮
		if(e.getSource() == send_btn) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
			
			String message = "你说 : " + message_txt.getText() + "\t"
					+ sdf.format(date) + "\n";
			//聊天框添加消息
			chat_windows.append(message);					//添加信息进对话框
			
			//数据
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("message");
			if("WorldChat".equals(owner_name)) {
				cmd.setCmd("WorldChat");
			}
			cmd.setSender(who);
			cmd.setReceiver(friend_name);
			cmd.setData(message_txt.getText());
			
			//发送
			client.sendData(cmd);
			
			//发送完消息后清除输入框的内容
			message_txt.setText(null);
		}
		
		//点击发送文件按钮
		if(e.getSource() == send_file) {
			String filePath = null;											//文件路径
			fileName = null;											//文件名
			chooser = new JFileChooser("D:");					//文件选择框空间
			long fileLen = 0;												//文件长度
			int returnVal = chooser.showOpenDialog(null);					
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {					//如果文件选择框中选中了对应的文件
				filePath = chooser.getSelectedFile().getAbsolutePath();		//文件路径有值	
				file = new File(filePath);								//文件有值
				fileName = file.getName();									//文件名有值
				fileLen = file.length();									//文件长度有值
				
				CommandTranser cmd=new CommandTranser();					//封装命令并发出
				cmd.setCmd("request_send_file");
				cmd.setSender(who);
				cmd.setReceiver(friend_name);
				cmd.setData("用户"+who+"向你发送文件:{"+fileName+"}"+"文件大小为：["+fileLen+"]");
				client.sendData(cmd);
			}
				
//				String dst = listOnlineUsers.getSelectedValue().trim();
//				FileMessage userFileMessage = new FileMessage(localUserName, dst, fileName, fileLen, true,"",0,false);
//				try {
//					synchronized (dos) {
//						fileMessage = TransJson.objectToJson(userFileMessage, userFileMessage.getNames());
//						dos.writeUTF(fileMessage);
//						dos.flush();
//					}
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				String msgRecord = dateFormat.format(new Date()) + "等待目标用户确认...\r\n";
//				addMsgRecord(msgRecord, Color.YELLOW, 12, false, false);
//				fileLabel.setText(filePath);
		}
	}
	
	
}
