package Server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import Server.model.UserModel;

@Repository
public interface UserRepository  extends MongoRepository<UserModel, String>{
	

}
