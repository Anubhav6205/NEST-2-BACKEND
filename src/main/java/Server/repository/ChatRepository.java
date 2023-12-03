package Server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import Server.model.ChatsModel;

public interface ChatRepository extends MongoRepository<ChatsModel, String> {
	
	

}
