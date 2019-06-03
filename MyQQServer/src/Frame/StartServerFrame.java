package Frame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import _Socket.Service;

/**�������ڣ�������Ψһ�Ĵ���
*
*/
public class StartServerFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton startServer_btn;
	private JButton endServer_btn;
	
	public StartServerFrame() {
		//��������
		setLayout(new FlowLayout());
		startServer_btn = new JButton("��������");
		endServer_btn = new JButton("�رշ���");
		add(startServer_btn);
		add(endServer_btn);
		//ImageIcon logo = new ImageIcon("image/server_image.jpg"); 
		//setIconImage(logo.getImage());
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		startServer_btn.addActionListener(this);
		endServer_btn.addActionListener(this);
	}
	public static void main(String[] args) {
		StartServerFrame startServer = new StartServerFrame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() == startServer_btn) {
			new startServerThread().start();			//������˿�����������ťһ��Ҫ�¿���һ���߳� ������������ ������main�̻߳����I/O����~ ������˹رշ�����
			JOptionPane.showMessageDialog(null, "�����������ɹ���������...");	
		}
		if(e.getSource() == endServer_btn) {
			System.exit(0);
		}
	}
}

class startServerThread extends Thread{
	@Override
	public void run() {
		Service s = new Service();			//�ѹ�������Service.java���������ᴴ��socket
		s.startService();
	}
}
