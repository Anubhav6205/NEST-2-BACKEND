package Server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import Server.model.ChatMessage;
import Server.model.ChatsModel;
import Server.service.chat.ChatInterface;
import ch.qos.logback.core.net.ObjectWriter;

class ChatControllerTest {

	@Test
	void getChatsTest() {
		ChatInterface chatInterface = mock(ChatInterface.class);
		ChatController chatController = new ChatController();
		chatController.setChatInterface(chatInterface);

		ChatsModel chatsModel = new ChatsModel();
		chatsModel.setPersonAId("personA123");
		chatsModel.setPersonBId("personB987");
		when(chatInterface.getChats(chatsModel)).thenReturn(ResponseEntity.ok().build());

		// CALLING THE CHAT CONTROLLER FUNCTION
		ResponseEntity<?> chatsResponse = chatController.getChats(chatsModel);

		// ASSERTION
		assertEquals(200, chatsResponse.getStatusCode().value());

		// verifying that it was called
		verify(chatInterface).getChats(chatsModel);
	}

	@Test
	void addChatTest() {
		ChatInterface chatInterface = mock(ChatInterface.class);
		ChatController chatController = new ChatController();
		chatController.setChatInterface(chatInterface);
		ChatsModel chatsModel = new ChatsModel();
		List<ChatMessage> messageDetail = new ArrayList<>();
		ChatMessage currentMessage = new ChatMessage();
		currentMessage.setId("123");
		currentMessage.setMessage("Mock message");
		currentMessage.setSenderId("123ABC");
		currentMessage.setisChat(true);
		messageDetail.add(currentMessage);
		when(chatInterface.addChat(chatsModel)).thenReturn(ResponseEntity.ok().build());

		ResponseEntity<?> chatResponse = chatController.addChat(chatsModel);

		assertEquals(200, chatResponse.getStatusCode().value());

		verify(chatInterface).getChats(chatsModel);

	}
//
//	@Test
//	void testGetConversations() {
//
//		ChatInterface chatInterfaceMock = mock(ChatInterface.class);
//
//		ChatController chatController = new ChatController();
//		chatController.setChatInterface(chatInterfaceMock);
//
//		Map<String, String> currentUserId = new HashMap<>();
//		currentUserId.put("currentUserId", "randomUserId");
//
//		Map<String, Object> responseData = new HashMap<>();
//
//		when(chatInterfaceMock.getConversations(currentUserId)).thenReturn(ResponseEntity.ok().build());
//
//		ResponseEntity<?> response = chatController.getConversations(currentUserId);
//
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals("Mock data", response.getBody());
//
//		verify(chatInterfaceMock).getConversations(currentUserId);
//	}

}
