package Frame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Entity.ChatUIEntity;
import Entity.User;
import UserSocket.Client;
import _Util.ChatUIList;
import _Util.CommandTranser;
	/*
	 * 	���˽���һ�� ��ʽ���� �� �߿򣨽磩���� �ֱ���ο�
	 * 	https://blog.csdn.net/liujun13579/article/details/7771191
	 *  https://blog.csdn.net/liujun13579/article/details/7772215
	 *  https://www.cnblogs.com/qingyundian/p/8012527.html
	 */
/**�����б�ҳ��
*
*/
public class FriendsUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private User owner;						// ��ǰ�û�(���������Ϣ)
	private Client client;					// �ͻ���
    private JButton changepwd_bt;
    private JButton addfriends_bt;
    private JButton world_bt;
    
    
    
    
    
    
    
	public FriendsUI(User owner, Client client) {
		this.owner = owner;
		this.client = client;
		//��ʼ������
		init();
		
		setTitle(owner.getUsername() + "-����");
		setSize(260, 670);
		setLocation(1050, 50);
		ImageIcon logo = new ImageIcon("image/friendsui/login_successful_image.jpg"); //���Ϸ�Сͼ��
		setIconImage(logo.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
	}
	private void init() {
		// TODO Auto-generated method stub
		//��¼�ɹ����ϲ��֣�����ͷ�� �û����� ����ǩ���� �������ǩ���̶�
		final JPanel upper_N = new JPanel();
		upper_N.setLayout(new BorderLayout()); // ���ñ߽粼��
		add(upper_N, BorderLayout.NORTH);
		
		ImageIcon my_avata = new ImageIcon("image/friendsui/sdust.jpg"); //ͷ�񲿷�
		final JLabel upper_N_W = new JLabel(my_avata);
		upper_N.add(upper_N_W, BorderLayout.WEST);
		upper_N_W.setPreferredSize(new Dimension(79, 79));
		
		final JPanel upper_N_Cen = new JPanel(); 
		upper_N_Cen.setLayout(new BorderLayout());
		upper_N.add(upper_N_Cen, BorderLayout.CENTER);
		
		final JLabel upper_N_Cen_Cen = new JLabel(); //�û�������
		upper_N_Cen_Cen.setText(owner.getUsername());
		upper_N_Cen_Cen.setFont(new Font("����", 1, 16));
		upper_N_Cen.add(upper_N_Cen_Cen, BorderLayout.CENTER);
		
		final JLabel upper_N_Cen_S = new JLabel(); // ����ǩ������
		upper_N_Cen_S.setText("hello world");
		upper_N_Cen.add(upper_N_Cen_S, BorderLayout.SOUTH);
		
		//��½�ɹ����²��� �޸�����, ��Ӻ���
		final JPanel down_S = new JPanel();
		down_S.setLayout(new BorderLayout());
		add(down_S, BorderLayout.SOUTH);
		
		final JPanel down_S_W = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		down_S_W.setLayout(flowLayout);
		down_S.add(down_S_W); //�����EAST��ɾ��,���ñ�ʶλ���� 
		
		
		addfriends_bt = new JButton(); //��������� ȥ�� set...
		down_S_W.add(addfriends_bt);
		addfriends_bt.setHorizontalTextPosition(SwingConstants.LEFT);
		addfriends_bt.setHorizontalAlignment(SwingConstants.LEFT);
		addfriends_bt.setText("��Ӻ���");
		addfriends_bt.addActionListener(this);
		
		changepwd_bt = new JButton();
		down_S_W.add(changepwd_bt);
		changepwd_bt.setText("�޸�����");
		changepwd_bt.addActionListener(this);
		
		final JTabbedPane jtp = new JTabbedPane();
		add(jtp, BorderLayout.CENTER);
		
		final JPanel friend_pal = new JPanel();
		final JPanel world_propaganda = new JPanel();
		
		int friendsnum = owner.getFriendsNum();
		friend_pal.setLayout(new GridLayout(50, 1, 4, 4));
		final JLabel friendsname[];// ������ҵĺ��� = new JLabel[];
		friendsname = new JLabel[friendsnum];
		//���ź���ͷ�� 
		ImageIcon icon[] = new ImageIcon[5];
		for(int i = 0; i < 5; ++i) {
			//System.out.println("image/friendsui/" + Integer.toString(i) + ".jpg");
			icon[i] = new ImageIcon((String)("image/friendsui/" + Integer.toString(i) + ".jpg"));
			icon[i].setImage(icon[i].getImage().getScaledInstance(75, 75,
					Image.SCALE_DEFAULT));
		}	
		String insert = new String();
		ArrayList<String> friendslist = new ArrayList<String>(owner.getFriend());
		for (int i = 0; i < friendsnum; ++i) {
			// ����icon��ʾλ����jlabel�����
			insert = (String)friendslist.get(i);
			while(insert.length() < 38) {
				insert = (String)(insert + " ");
			}
			friendsname[i] = new JLabel(insert, icon[i % 5], JLabel.CENTER);
			friendsname[i].addMouseListener(new MyMouseListener());
			friend_pal.add(friendsname[i]);

		}
		
		//ȫԱȺ�Ĳ���
		ImageIcon world_image = new ImageIcon("image/friendsui/world.jpg");
		world_image.setImage(world_image.getImage().getScaledInstance(245, 493, Image.SCALE_DEFAULT));
		world_bt = new JButton();
		world_bt.setIcon(world_image);
		world_bt.setBackground(Color.white);
		world_bt.setBorderPainted(false); //���������Ӧ�û��Ʊ߿���Ϊ true������Ϊ false
		world_bt.setBorder(null); //���ô�����ı߿� ��
		world_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //�������Ϊ ��С�֡���״
		world_bt.addActionListener(this);
		world_propaganda.add(world_bt);
		
		
		final JScrollPane jsp = new JScrollPane(friend_pal);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jtp.addTab("����", jsp);
		jtp.addTab("Ⱥ��", world_propaganda);
		
		//���ڹر��¼�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("logout");
				cmd.setReceiver("Server");
				cmd.setSender(owner.getUsername());
				client.sendData(cmd);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				CommandTranser cmd = new CommandTranser();
				cmd.setCmd("logout");
				cmd.setReceiver("Server");
				cmd.setSender(owner.getUsername());
				client.sendData(cmd);
			}
		});		
	}
	

	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e){						//�����޸����� �� ��Ӻ��Ѱ�ť ��ȫԱȺ��
		
		if(e.getSource() == changepwd_bt){						//�����޸�����ҳ��
			new ChangePwdUI(owner, client);			
		}

		if(e.getSource() == addfriends_bt){						//�ٻ�����Ӻ���ҳ��
			new AddFriendUI(owner, client);
		}

		if(e.getSource() == world_bt) {
			ChatUI chatUI = ChatUIList.getChatUI("WorldChat");
			if(chatUI == null) {
				chatUI = new ChatUI("WorldChat", "WorldChat", owner.getUsername(), client);
				chatUI.send_file.setEnabled(false);
				ChatUIEntity chatUIEntity = new ChatUIEntity();
				chatUIEntity.setName("WorldChat");
				chatUIEntity.setChatUI(chatUI);
				ChatUIList.addChatUI(chatUIEntity);
			} else {
				chatUI.show(); //�����ǰ������������Ĵ����ڸ��� ��������ʾ
			}
		}
	}
	class MyMouseListener extends MouseAdapter{						//������궯��
		@Override
		public void mouseClicked(MouseEvent e) {
			
			if(e.getClickCount() == 2) {							//˫���ҵĺ��ѵ�����ú��ѵ������
				JLabel label = (JLabel)e.getSource(); 				//getSource()���ص���Object,
				
				String friendname = label.getText().trim();			//ͨ��label�е�getText��ȡ�������
				//System.out.println(friendname + "*");
				
				ChatUI chatUI = ChatUIList.getChatUI(friendname);	//�鿴��ú����Ƿ񴴽�������
				if(chatUI == null) {
					chatUI = new ChatUI(owner.getUsername(), friendname, owner.getUsername(), client);
					ChatUIEntity chatUIEntity = new ChatUIEntity();
					chatUIEntity.setName(friendname);
					chatUIEntity.setChatUI(chatUI);
					ChatUIList.addChatUI(chatUIEntity);
				} else {
					chatUI.show(); 									//�����ǰ������������Ĵ����ڸ��� ��������ʾ
				}
				
			}	
		}
		
		//����ȥ�����б� ����ɫ��ɫ	
		@Override
		public void mouseEntered(MouseEvent e) {
			JLabel label = (JLabel)e.getSource();
			label.setOpaque(true); //���ÿؼ���͸��
			label.setBackground(new Color(255, 240, 230));
		}
		
		// �������˳��ҵĺ����б� ����ɫ��ɫ
		@Override
		public void mouseExited(MouseEvent e) {
			JLabel label = (JLabel) e.getSource();
			label.setOpaque(false);
			label.setBackground(Color.WHITE);
		}
	}

}
