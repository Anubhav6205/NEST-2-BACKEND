package Server.service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;

import Server.model.ChatMessage;
import Server.model.ChatsModel;
import Server.repository.ChatRepository;

import org.springframework.data.mongodb.core.query.Query;

@Service
public class ChatImpl implements ChatInterface {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> getChats(@RequestBody ChatsModel chat) {
		Map<String, Object> responseData = new HashMap<>();
		try {
			String personAId = chat.getPersonAId();
			String personBId = chat.getPersonBId();

			// Create consistent chat ID based on the order of user IDs
			String chatId;
			if (personAId.compareTo(personBId) < 0) {
				chatId = (personAId + personBId).hashCode() + "";
			} else {
				chatId = (personBId + personAId).hashCode() + "";
			}

			Query query = new Query(Criteria.where("id").is(chatId));

			ChatsModel existingChats = mongoTemplate.findOne(query, ChatsModel.class);
			if (existingChats == null) {
				existingChats = new ChatsModel();
				existingChats.setMessageDetail(new ArrayList<>());
				existingChats.setPersonAId(personAId);
				existingChats.setPersonBId(personBId);
				existingChats.setId(chatId);

				chatRepository.save(existingChats);
			}

			responseData.put("chat", existingChats);
			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		} catch (Exception e) {
			responseData.put("Error", "Error while searching for chats: " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
		}
	}

	@Override
	public ResponseEntity<?> addChat(@RequestBody ChatsModel chat) {
		Map<String, Object> responseData = new HashMap<>();
		try {
			String personAId = chat.getPersonAId();
			String personBId = chat.getPersonBId();

			Query query = new Query(
					new Criteria().orOperator(Criteria.where("personAId").is(personAId).and("personBId").is(personBId),
							Criteria.where("personAId").is(personBId).and("personBId").is(personAId)));

			ChatsModel existingChats = mongoTemplate.findOne(query, ChatsModel.class);

			if (existingChats == null) {
				existingChats = new ChatsModel();
				existingChats.setMessageDetail(new ArrayList<>());
				existingChats.setPersonAId(personAId);
				existingChats.setPersonBId(personBId);
			}

			String chatId;
			if (personAId.compareTo(personBId) < 0) {
				chatId = (personAId + personBId).hashCode() + "";
			} else {
				chatId = (personBId + personAId).hashCode() + "";
			}

			existingChats.setId(chatId);

			List<ChatMessage> currentMessageList = chat.getMessageDetail();
			ChatMessage currentMessage = currentMessageList.get(0);
			currentMessage.setId(UUID.randomUUID().toString());
			existingChats.getMessageDetail().add(currentMessage);

			chatRepository.save(existingChats);

			responseData.put("chat", existingChats);
			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		} catch (Exception e) {
			responseData.put("Error", "Error while adding chat: " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
		}
	}

	@Override
    public ResponseEntity<?> getConversations(@RequestBody Map<String,String> currentUserMap){
		
		Map<String, Object> responseData = new HashMap<>();
		try {
	
			String currentUserId=currentUserMap.get("currentUserId");

		
			System.out.println(currentUserId);
			List<ChatsModel> conversations = new ArrayList<>();


			Query query = new Query(new Criteria().orOperator(Criteria.where("personAId").is(currentUserId),
					Criteria.where("personBId").is(currentUserId)));

			conversations = mongoTemplate.find(query, ChatsModel.class);
	
			System.out.println(conversations);
			System.out.println("are convos");
			responseData.put("conversations", conversations);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		}
	}

}
