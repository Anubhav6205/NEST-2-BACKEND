package Server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Server.model.ChatsModel;
import Server.service.chat.ChatInterface;

@RestController
public class ChatController {
	
	@Autowired
	ChatInterface chatInterface;   

	@PostMapping("/chat/get")
	@ResponseBody
	public ResponseEntity<?>getChats(@RequestBody ChatsModel chat)
	{
		return chatInterface.getChats(chat);
	}
	
	
	@PostMapping("/chat/add")
	@ResponseBody
	public ResponseEntity<?>addChat(@RequestBody ChatsModel chat)
	{
		return chatInterface.addChat(chat);
	}
	
	@PostMapping("/chat/get/all")
	@ResponseBody
    public ResponseEntity<?> getConversations(@RequestBody Map<String,String> currentUserId)
	{
		return chatInterface.getConversations(currentUserId);
	}
	
	
	
}
