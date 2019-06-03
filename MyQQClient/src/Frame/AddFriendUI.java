package Frame;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entity.User;
import UserSocket.Client;
import _Util.CommandTranser;

/**��Ӻ��Ѵ���
* 
*/
public class AddFriendUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private User owner;											// ��ǰ�û�
	private Client client;										// ��ǰ�ͻ���
	private JButton submit_bt;									//�ύ��ť
	private JLabel txt_input_name, txt_reinput_name;			//��ǩ��ʾ
	private JTextField new_name, re_new_name;					//
	private JPanel center, souTh;								//
	
	
	
	public AddFriendUI(User owner, Client client) {		//
		this.owner = owner;
		this.client = client;
		
		init();
		
		add(center, BorderLayout.CENTER);
		add(souTh, BorderLayout.SOUTH);
		
		setBounds(200, 230, 360, 150); 					//�趨��С��λ��
		setResizable(false); 							//��¼���С�̶���������ͨ���ϡ����ı��С
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //���ô������ϽǵĲ�ţ������Ŵ��ڹر� ע�ⲻ��EXIT_ON_CLOSE�������ģ�����ʱ��ʹ�õ���System.exit�����˳�Ӧ�ó��򡣹ʻ�ر����д��ڡ�
		setTitle("��Ӻ���");
		setVisible(true);
				
	}
	
	
	
	public void init() {								//���ÿ�����ֶ�ֵ
		txt_input_name = new JLabel();
		txt_input_name.setText("�������˺�");
		
		txt_reinput_name = new JLabel();
		txt_reinput_name.setText("���ظ�����");
		
		new_name = new JTextField();
		new_name.setName("new_pwd");
		
		re_new_name = new JTextField();
		re_new_name.setName("new_pwd");
		
		submit_bt = new JButton();
		submit_bt.setText("ȷ�����");
		submit_bt.setBorderPainted(false);
		submit_bt.setBorder(BorderFactory.createRaisedBevelBorder());
		submit_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
		submit_bt.addActionListener(this);
		
		center = new JPanel();
		center.setLayout(new GridLayout(2, 2, 3, 15)); 
		center.add(txt_input_name);
		center.add(new_name);
		center.add(txt_reinput_name);
		center.add(re_new_name);
		
		souTh = new JPanel();
		souTh.setLayout(new GridLayout(1, 3, 3, 15)); 
		JLabel empty_1 = new JLabel("");
		JLabel empty_2 = new JLabel("");
		souTh.add(empty_1);
		souTh.add(submit_bt);
		souTh.add(empty_2);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e){									//��ť�ĵ���¼���actionPerformed
		if(e.getSource() == submit_bt){
			String name = new_name.getText().trim();							//JTextField��ȡ����
			String re_name = re_new_name.getText().trim();
			if("".equals(name) || name == null) {
				JOptionPane.showMessageDialog(null, "�������˺ţ���");			//����ʾ��
				return;
			}
			if("".equals(re_name) || re_name == null) {
				JOptionPane.showMessageDialog(null, "���ظ��˺ţ���");
				return;
			}
			
			if(!name.equals(re_name)) {
				JOptionPane.showMessageDialog(null, "�����˺Ų�һ�£���");
				re_new_name.setText("");
				return;
			}
			
			
			CommandTranser cmd = new CommandTranser();
			cmd.setCmd("requeste_add_friend");
			cmd.setData(name);
			cmd.setReceiver(name);
			cmd.setSender(owner.getUsername());
			client.sendData(cmd); 						//�������װ��������л�������
			
			this.dispose();
		}	
	}
}
