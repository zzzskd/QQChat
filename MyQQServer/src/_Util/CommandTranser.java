package _Util;

import java.io.Serializable;

/**
*
*/
public class CommandTranser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sender = null;// ������
	private String receiver = null;// ������
	private Object data = null; // ���ݵ�����
	private boolean flag = false; // ָ��Ĵ�����
	private String cmd = null; // �����Ҫ����ָ��
	private String result = null; //������

	public String getSender() {
		return sender;
	}

	public String setSender(String sender) {
		return this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String setReceiver(String receiver) {
		return this.receiver = receiver;
	}

	public Object getData() {
		return data;
	}

	public Object setData(Object data) {
		return this.data = data;
	}

	public boolean isFlag() {
		return flag;
	}

	public boolean setFlag(boolean flag) {
		return this.flag = flag;
	}

	public String getResult() {
		return result;
	}

	public String setResult(String result) {
		return this.result = result;
	}

	public String getCmd() {
		return cmd;
	}

	public String setCmd(String cmd) {
		return this.cmd = cmd;
	}

}
