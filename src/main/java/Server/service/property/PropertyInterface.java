package Server.service.property;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import Server.model.PropertiesModel;
import Server.model.ReviewModel;
import Server.model.appointment.AppointmentWithId;
import ch.qos.logback.core.model.PropertyModel;

public interface PropertyInterface {
	
	public ResponseEntity<?> addProperty(@RequestBody PropertiesModel property);
	
	public ResponseEntity<?> getProperties();
	
	public ResponseEntity<?>addReview(@RequestBody ReviewModel review);
	
	public ResponseEntity<?>addAppointment(@RequestBody AppointmentWithId appointment);
	
	public ResponseEntity<?>getPropertiesById(String authorizationHeader);

}
