package _Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class FileReciver extends SwingWorker<List<String>,String> {
	private Socket currentUserSocket; 
	private static final int BUFSIZE = 8192;
	private String fileName;
	private long fileLen;
	public JProgressBar progressBar;
	private DataInputStream in ;
	private BufferedOutputStream out;
	public FileReciver(Socket currentUserSocket,String fileName,long fileLen,JProgressBar progressBar)  throws IOException{
		this.currentUserSocket = currentUserSocket;
		this.fileName = fileName;
		this.fileLen = fileLen;
		File file = new File( "D:/Users/win/workspace/MyQQClient/" + fileName);
		this.progressBar=progressBar;
		in = new DataInputStream(new BufferedInputStream(currentUserSocket.getInputStream()));
		out = new BufferedOutputStream(new FileOutputStream(file));
		
		//progressBar.setVisible(true);
	}
	@Override
	protected List<String> doInBackground() throws Exception{
		try {
			
			byte[] buffer = new byte[BUFSIZE];
			int numRead = 0;
			long numFinished = 0;
			float per=0 ;
			int p=0 ;
			while(numFinished < fileLen && (numRead = in.read(buffer))!= -1) {
				out.write(buffer, 0, numRead);
				numFinished += numRead;
				publish(numFinished + "/" + fileLen + "bytes");
				per = numFinished/fileLen;
				p = (int)per*100;
				setProgress(p);
				//System.out.println("检查是否接受文件");
			}
			in.close();
			out.close();
			currentUserSocket.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void process(List<String> middleResults) {
		for(String str:middleResults) {
			//progressLabel.setText(str);    百分比
		}
	}
	@Override
	protected void done() {
		progressBar.setValue(0);
		JOptionPane.showMessageDialog(null,  "文件接受完毕");
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