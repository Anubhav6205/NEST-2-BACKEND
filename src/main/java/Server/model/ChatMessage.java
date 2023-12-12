package Server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public ChatMessage() {
    	
    }
    
    public ChatMessage(String id,String message,String senderId,boolean isChat)
    {
    	this.id=id;
    	this.message=message;
    	this.senderId=senderId;
    	this.isChat=isChat;
    }
    private String id;
    private String message;
    private String senderId;
    private boolean isChat;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public boolean getisChat() {
		return isChat;
	}
	public void setisChat(boolean isChat) {
		this.isChat = isChat;
	}


}
