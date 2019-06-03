package Frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.ChatTread;
import UserSocket.Client;
import _Util.CommandTranser;

/**�������봰��
*
*/
public class ForgetUI extends JFrame implements ActionListener, FocusListener{
	
	
	private static final long serialVersionUID = 1L;
	private JLabel upper_N, user_name_txt, user_pwd_txt, user_ques_txt, user_ans_txt, user_ques; //��ʾ������
	private JButton forget_button_S, submit_button;
	private JTextField user_name, user_pwd, user_ans; //���������ȡ�û�����
	//private JPanel tmp_South, center_Center;
	private MainFrame mainFrame; //���ڹرյ�¼ҳ�� ����޸�����ɹ��򽫸տ�ʼ��ע��ҳ��ر�
	private User user;
	private Client client; //���ڲ�ѯ���ݿ����Ƿ��и��û�
	
	
	
	
	
	public ForgetUI(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		//ҳ���ڲ�Ԫ�ع���
		init();
		//�ϳ�����
//		add(upper_N);
		add(user_name_txt);
		add(user_name);
		add(submit_button);
		add(user_ques_txt);
		add(user_ques);
		add(user_ans_txt);
		add(user_ans);
		add(user_pwd_txt);
		add(user_pwd);
		add(forget_button_S);
		
		//λ�á�ҳ���С����
		setSize(270, 430);
		setLocation(550, 300);
		setLayout(null); //�ֹ����� ������ҳ�治ͬ 
		ImageIcon logo = new ImageIcon("image/register_image.jpg"); //���Ϸ�Сͼ��
		setIconImage(logo.getImage());
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setVisible(true);		
	}
	public void init() {
		//�Ϸ�ͼƬ���ֹ��� �� ��Ϊ ��
//		ImageIcon upper_image = new ImageIcon("image/forget_background_image.png");
//		upper_image.setImage(upper_image.getImage().getScaledInstance(270, 170,Image.SCALE_DEFAULT));			
//		upper_N = new JLabel(upper_image);		
//		upper_N.setLocation(0,0); //ȷ��λ��
//		upper_N.setSize(270, 170); //���ô�С
		
		
		//�м䲿������ ����λ��
		user_name_txt = new JLabel("�û��˺�", JLabel.CENTER);
		user_name_txt.setSize(60, 20);
		user_name_txt.setLocation(10, 185);
		
		submit_button = new JButton();
		submit_button.setText("��ѯ");
		submit_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		submit_button.setSize(60, 20);
		submit_button.setLocation(190, 185);
		submit_button.addActionListener(this); //�����¼���Ӧ�û���Ϊ
		
		user_ques_txt = new JLabel("��ʾ����", JLabel.CENTER);
		user_ques_txt.setSize(60, 20);
		user_ques_txt.setLocation(10, 220);
		
		user_ans_txt = new JLabel("�����", JLabel.CENTER);
		user_ans_txt.setSize(60, 20);
		user_ans_txt.setLocation(10, 255);
		
		user_pwd_txt = new JLabel("��������", JLabel.CENTER);
		user_pwd_txt.setSize(60, 20);
		user_pwd_txt.setLocation(10, 290);
		
		user_name = new JTextField();
		user_name.setSize(100, 30);
		user_name.setLocation(80, 185);
		
		user_ques = new JLabel("�����ѯ����ʾ", JLabel.CENTER);
		user_ques.setSize(100, 30);
		user_ques.setLocation(80, 220);
		
		user_ans = new JTextField();
		user_ans.setSize(100, 30);
		user_ans.setLocation(80, 255);
		
		user_pwd = new JTextField();
		user_pwd.setSize(100, 30);
		user_pwd.setLocation(80, 290);
		
			
		user_name.addFocusListener(this);
		user_pwd.addFocusListener(this);
		user_ans.addFocusListener(this);
		
		
		//�·�ע��ͼƬ���� �� ��Ϊ ��
		forget_button_S = new JButton();
		ImageIcon conform_register_image = new ImageIcon("image/conform_forget_image.png");
		conform_register_image.setImage(conform_register_image.getImage().getScaledInstance(220, 32, Image.SCALE_DEFAULT));
		forget_button_S.setIcon(conform_register_image);
		forget_button_S.setBorderPainted(false);
		forget_button_S.setBorder(null);
		forget_button_S.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forget_button_S.setSize(220, 32);
		forget_button_S.setLocation(15, 330);
		forget_button_S.addActionListener(this); //�����¼���Ӧ�û���Ϊ
			
	}
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {							//��ť�ĵ���¼���actionPerforme
		
		if(e.getSource() == submit_button) {
			String username = user_name.getText().trim();
			if("".equals(username) || username == null) {
				JOptionPane.showMessageDialog(null, "�������ʺţ���");
				return;
			} else {
				user = new User();
				user.setUsername(username);
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("forgetpwd");
				cmd.setReceiver(username);
				cmd.setSender(username);
				cmd.setData(user);
				client = new Client();
				client.sendData(cmd);
				cmd = client.getData();
				
				if(cmd != null) {
					if(cmd.isFlag()) {
						user = (User)cmd.getData();
						user_ques.setText(user.getUserQuestion());
						JOptionPane.showMessageDialog(null, "��ѯ�ɹ�"); 		//this �� null����
					}else {
						JOptionPane.showMessageDialog(null, cmd.getResult());
						//�˺�������ÿ�
						user_name.setText("");	
					}
				}else {
					
				}
			}
		}
		if (e.getSource() == forget_button_S) {
			if(user == null || "".equals(user.getUsername()) || user.getUserQuestion() == null || "".equals(user.getUserQuestion())) {
				JOptionPane.showMessageDialog(null, "��������ȷ�˺Ų������ѯ"); 
				return;
			} 
			
			String newpwd = user_pwd.getText();
			if(newpwd == null || "".equals(newpwd)) {
				JOptionPane.showMessageDialog(null, "������������"); 
				return;
			}
			
			if(user.getUserAnswer() == null || "".equals(user.getUserAnswer())) {
				JOptionPane.showMessageDialog(null, "�����˻�δ�������ⲻ���һ�"); 
				return;
			}
			
			String userans = user_ans.getText();
			
			if(userans == null || "".equals(userans)) {
				JOptionPane.showMessageDialog(null, "�������");
				
				return;
			}
			
			String username = user_name.getText().trim();
			//�����ж�username�Ǳ�Ҫ��
			if(!userans.equals(user.getUserAnswer()) || username == null || !username.equals(user.getUsername()) ) {
				JOptionPane.showMessageDialog(null, "�𰸴���"); 
				return;
			}
			
			//��֤�ɹ��� ��ʼ�޸�����
			CommandTranser cmd = new CommandTranser();
			user.setUsername(username);
			user.setUserpwd(newpwd);
			cmd.setCmd("changepwd");
			cmd.setData(user);
			cmd.setReceiver(username);
			cmd.setSender(username);
			if(client == null) {
				JOptionPane.showMessageDialog(null, "�������"); 
				return;
			}
			client.sendData(cmd);
			
			cmd = client.getData();
			
			if(cmd != null) {
				if(cmd.isFlag()) {
					JOptionPane.showMessageDialog(null,  cmd.getResult());
					//��¼
					cmd.setCmd("login");
					if(client == null) {
						JOptionPane.showMessageDialog(null, "�������"); 
						return;
					}
					client.sendData(cmd);
					cmd = client.getData();
					if(cmd != null) {
						if(cmd.isFlag()) {
							this.dispose();
							mainFrame.dispose();
							JOptionPane.showMessageDialog(null,  "��½�ɹ�");
							user = (User)cmd.getData(); 
							FriendsUI friendsUI = new FriendsUI(user, client);
							ChatTread thread = new ChatTread(client, user, friendsUI);
							thread.start();
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(null, cmd.getResult()); 
					return;
				}
			}
			
		}
	}
	
	
	
	
	
	/////////////////////////////////////////////////////////���ĵ�����ƶ�֮�����focuslistener ��֪��Ϊɶû��ɫ
	@Override
	public void focusGained(FocusEvent e) {
		//�����˺������
	    if(e.getSource() == user_name){
	    	user_name.setForeground(Color.BLACK);
	    }
	    	
	    //�������������
	    if(e.getSource() == user_pwd){
	    	user_pwd.setForeground(Color.BLACK);
	    }
	    
	  //�������������
	    if(e.getSource() == user_ques){
	    	//user_ques.setForeground(Color.BLACK);
	    }
	    	
	    //����������
	    if(e.getSource() == user_ans){
	    	user_ans.setForeground(Color.BLACK);
	    }
	    
	}
	@Override
	public void focusLost(FocusEvent e) {
		//�����˺������
	    if(e.getSource() == user_name){
	    	user_name.setForeground(Color.gray);
	    }
	    	
	    //�������������
	    if(e.getSource() == user_pwd){
	    	user_pwd.setForeground(Color.gray);
	    }
	    
	    //�������������
	    if(e.getSource() == user_ques){
	    	//user_ques.setForeground(Color.gray);
	    }
	    	
	    //����������
	    if(e.getSource() == user_ans){
	    	user_ans.setForeground(Color.gray);
	    }
	}

}


