package Server.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import Server.repository.UserRepository;
import ch.qos.logback.core.net.ObjectWriter;

class UserControllerTest {
	
	private MockMvc mockMvc;
	ObjectMapper objectMapper=new ObjectMapper();
//	ObjectWriter objectWriter=objectMapper.writer();
	
	@Mock
	private UserRepository userRepository;
	
	
	
	
	


}
