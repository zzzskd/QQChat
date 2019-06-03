package Entity;
import Frame.ChatUI;

/*
*
*/
public class ChatUIEntity {
	private ChatUI chatUI;						//聊天框体
	private String name;						//聊天框有一个名字，以便检索
	
	
	
	
	
	
	
	public ChatUIEntity() {
		super();
	}	
	public ChatUIEntity(ChatUI chatUI, String name) {
		super();
		this.chatUI = chatUI;
		this.name = name;
	}
	
	
	
	
	
	
	
	public ChatUI getChatUI() {
		return chatUI;
	}
	public void setChatUI(ChatUI chatUI) {
		this.chatUI = chatUI;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

