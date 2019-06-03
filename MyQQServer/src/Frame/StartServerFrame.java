package Frame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import _Socket.Service;

/**启动窗口，服务器唯一的窗口
*
*/
public class StartServerFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton startServer_btn;
	private JButton endServer_btn;
	
	public StartServerFrame() {
		//创建窗口
		setLayout(new FlowLayout());
		startServer_btn = new JButton("开启服务");
		endServer_btn = new JButton("关闭服务");
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
			new startServerThread().start();			//当点击了开启服务器按钮一定要新开启一个线程 开启动服务器 ，否则main线程会进入I/O阻塞~ 点击不了关闭服务器
			JOptionPane.showMessageDialog(null, "服务器开启成功，请连接...");	
		}
		if(e.getSource() == endServer_btn) {
			System.exit(0);
		}
	}
}

class startServerThread extends Thread{
	@Override
	public void run() {
		Service s = new Service();			//把工作交给Service.java来处理，它会创建socket
		s.startService();
	}
}
