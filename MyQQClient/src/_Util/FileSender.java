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
	private Socket currentUserSocket;						//���͵�socket
	private File file;												//���͵��ļ�����
	private static final int BUFSIZE = 8192;						//���ͻ������Ĵ�С
	private DataOutputStream out;
	private DataInputStream in;
	JProgressBar progressBar;
	public FileSender(Socket currentUserSocket,JProgressBar progressBar,File file) throws IOException {				//���캯����ʼ��
		this.currentUserSocket = currentUserSocket;
		this.progressBar=progressBar;
		progressBar.setVisible(true);
		this.file=file;
		out = new DataOutputStream(new BufferedOutputStream(currentUserSocket.getOutputStream()));			//��socket�������������
		in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		//file = new File(fileLabel.getText());
	}
	
	@Override
	protected List<String> doInBackground() throws Exception {
		
		long fileLen = file.length();																						//����ļ���С		
		int numRead = 0;
		long numFinished = 0;
		byte[]buffer = new byte[BUFSIZE];																					//����һ�����ͻ�������
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
			//System.out.println("����Ƿ����ļ�");
		}
		System.out.println("�鿴���Ƿ�ر�");
//		in.close();
//		out.close();
//		currentUserSocket.close();
		return null;
	}
	@Override
	protected void process(List<String> middleResults) {
		for(String str:middleResults) {
			//progressLabel.setText(str);   ���ðٷֱ�
		}
	}
	@Override
	protected void done() {
		progressBar.setValue(0);			//����UI�ؼ�
		JOptionPane.showMessageDialog(null,  "�ļ��������");
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
