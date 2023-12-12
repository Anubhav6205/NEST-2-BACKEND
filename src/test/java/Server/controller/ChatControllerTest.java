package Server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
//import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
import jakarta.mail.internet.ContentType;

class ChatControllerTest {
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Mock
	private ChatInterface chatInterface;

	@InjectMocks
	private ChatController chatController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();

	}

	@Test
	void testGetChats() throws Exception {
		// Arrange
		ChatsModel chats = new ChatsModel();
		chats.setId("123");
		List<ChatMessage> messages = new ArrayList<>();
		messages.add(new ChatMessage("id1", "MockMessag1", "mockSenderId", true));
		chats.setMessageDetail(messages);
		chats.setPersonAId("657848767851d80bfd4b5e2d");
		chats.setPersonBId("657846277851d80bfd4b5e21");
		ResponseEntity<ChatsModel> expectedResponse = ResponseEntity.status(HttpStatus.OK).body(chats);
		Mockito.doReturn(expectedResponse).when(chatInterface).getChats(chats);

		// Act
		// Mock HTTP request
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/chat/get").contentType(MediaType.APPLICATION_JSON)

						.content(objectWriter.writeValueAsString(chats)))
				.andReturn();

		// Assert
		int expectedStatusCode = expectedResponse.getStatusCode().value();
		int actualStatusCode = mvcResult.getResponse().getStatus();
		assertEquals(expectedStatusCode, actualStatusCode);

//        String expectedResponseBody = new ObjectMapper().writeValueAsString(expectedResponse.getBody());
//        String actualResponseBody = mvcResult.getResponse().getContentAsString();
//        System.out.println(expectedResponseBody + " expected");
//        System.out.println(actualResponseBody + "actual");
//        assertEquals(expectedResponseBody, actualResponseBody);
	}

}
