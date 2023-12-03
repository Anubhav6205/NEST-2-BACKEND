package Server.service.chat;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import Server.model.ChatsModel;

public interface ChatInterface {
	public ResponseEntity<?> getChats(@RequestBody ChatsModel chat);

	
	public ResponseEntity<?>addChat(@RequestBody ChatsModel chat);
	
	
    public ResponseEntity<?> getConversations(@RequestBody Map<String,String> currentUserMap) ;
	
	

}
