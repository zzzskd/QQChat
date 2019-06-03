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

/**���촰��
*
*/
public class ChatUI extends JFrame implements ActionListener{
	private JProgressBar progressBar_2;
	private static final long serialVersionUID = 1L;
	private JTextArea chat_windows; 					//˫��������Ϣ���ı���
	private JTextField message_txt; 					//д��Ϣ���ı���
	private JButton send_btn; 							//���Ͱ�ť
	public JButton send_file;							//�����ļ���ť
	private JPanel panel;
	private String owner_name;							//���������������
	private String friend_name;							//����������Ŀ���
	private String who;									//���������������ĸ�Client(���������socket)
	private Client client; 								//���socketֻ����������Ϣ�� ����Ϣ�����������
	public JProgressBar progressBar;
	public JFileChooser chooser;
	public String fileName;
	public File file;
	//private ChatTread thread; //������Ϣ�߳�
	
	
	
	
	
	public ChatUI(String ower_name, String friend_name, String who, Client client){
		this.owner_name = ower_name;
		this.friend_name = friend_name;
		this.client = client;
		this.who = who;
		
		//��������ҳ��
		init();
		
		setTitle(ower_name + "���ں�" + friend_name + "����");
		setSize(596, 398);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		// �����ͻ��˽�����Ϣ�߳�,���յ�����Ϣ������ chat_windows
		//thread = new ChatTread(client); //��Ψһclient����
		//thread.start();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		panel = new JPanel();

		progressBar = new JProgressBar();
		message_txt = new JTextField(20);
		send_btn = new JButton("������Ϣ");
		send_file=new JButton("�����ļ�");
		panel.add(message_txt);
		panel.add(send_btn);
		panel.add(send_file);
		panel.add(progressBar);
		chat_windows = new JTextArea();
		chat_windows.setEditable(false);
		chat_windows.add(new JScrollBar(JScrollBar.VERTICAL)); //������
		add(chat_windows, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		send_btn.addActionListener(this);
		send_file.addActionListener(this);
		
		//���ڹر��¼�
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
	//���ͷ���Ϣ
	public JTextArea getChatWin() {
		return chat_windows;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {					//��ť�ĵ���¼���actionPerforme
		//���������Ϣ��ť
		if(e.getSource() == send_btn) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss a");
			
			String message = "��˵ : " + message_txt.getText() + "\t"
					+ sdf.format(date) + "\n";
			//����������Ϣ
			chat_windows.append(message);					//�����Ϣ���Ի���
			
			//����
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("message");
			if("WorldChat".equals(owner_name)) {
				cmd.setCmd("WorldChat");
			}
			cmd.setSender(who);
			cmd.setReceiver(friend_name);
			cmd.setData(message_txt.getText());
			
			//����
			client.sendData(cmd);
			
			//��������Ϣ���������������
			message_txt.setText(null);
		}
		
		//��������ļ���ť
		if(e.getSource() == send_file) {
			String filePath = null;											//�ļ�·��
			fileName = null;											//�ļ���
			chooser = new JFileChooser("D:");					//�ļ�ѡ���ռ�
			long fileLen = 0;												//�ļ�����
			int returnVal = chooser.showOpenDialog(null);					
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {					//����ļ�ѡ�����ѡ���˶�Ӧ���ļ�
				filePath = chooser.getSelectedFile().getAbsolutePath();		//�ļ�·����ֵ	
				file = new File(filePath);								//�ļ���ֵ
				fileName = file.getName();									//�ļ�����ֵ
				fileLen = file.length();									//�ļ�������ֵ
				
				CommandTranser cmd=new CommandTranser();					//��װ�������
				cmd.setCmd("request_send_file");
				cmd.setSender(who);
				cmd.setReceiver(friend_name);
				cmd.setData("�û�"+who+"���㷢���ļ�:{"+fileName+"}"+"�ļ���СΪ��["+fileLen+"]");
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
//				String msgRecord = dateFormat.format(new Date()) + "�ȴ�Ŀ���û�ȷ��...\r\n";
//				addMsgRecord(msgRecord, Color.YELLOW, 12, false, false);
//				fileLabel.setText(filePath);
		}
	}
	
	
}
