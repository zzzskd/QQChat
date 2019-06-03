package _Util;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import UserSocket.Client;

public class FileSender extends SwingWorker<List<String>,String>{			
	private Socket currentUserSocket;						//发送的socket
	private File file;												//发送的文件对象
	private static final int BUFSIZE = 8192;						//发送缓冲区的大小
	private DataOutputStream out;
	private DataInputStream in;
	JProgressBar progressBar;
	public FileSender(Socket currentUserSocket,JProgressBar progressBar,File file) throws IOException {				//构造函数初始化
		this.currentUserSocket = currentUserSocket;
		this.progressBar=progressBar;
		progressBar.setVisible(true);
		this.file=file;
		out = new DataOutputStream(new BufferedOutputStream(currentUserSocket.getOutputStream()));			//从socket里获得输入输出流
		in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		//file = new File(fileLabel.getText());
	}
	
	@Override
	protected List<String> doInBackground() throws Exception {
		
		long fileLen = file.length();																						//获得文件大小		
		int numRead = 0;
		long numFinished = 0;
		byte[]buffer = new byte[BUFSIZE];																					//创建一个发送缓存数组
		float per=0;
		int p=0;
		while(numFinished < fileLen && (numRead=in.read(buffer)) != -1) {
			out.write(buffer,0,numRead);
			out.flush();
			numFinished += numRead;
			publish(numFinished + "/" + fileLen + "bytes");
			per = numFinished/fileLen;
			p = (int)per*100;
			setProgress(p);
			//System.out.println("检查是否发送文件");
		}
		System.out.println("查看流是否关闭");
//		in.close();
//		out.close();
//		currentUserSocket.close();
		return null;
	}
	@Override
	protected void process(List<String> middleResults) {
		for(String str:middleResults) {
			//progressLabel.setText(str);   设置百分比
		}
	}
	@Override
	protected void done() {
		progressBar.setValue(0);			//更新UI控件
		JOptionPane.showMessageDialog(null,  "文件发送完毕");
		try {
			in.close();
			out.close();
			currentUserSocket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		//progressBar.setVisible(false);
	}
}
