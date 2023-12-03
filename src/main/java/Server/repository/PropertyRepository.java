package Server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import Server.model.PropertiesModel;
import ch.qos.logback.core.model.PropertyModel;

@Repository
public interface PropertyRepository  extends MongoRepository<PropertiesModel, String>{
	  List<PropertiesModel> findByLandlordDetailsId(String landlordId);
}
