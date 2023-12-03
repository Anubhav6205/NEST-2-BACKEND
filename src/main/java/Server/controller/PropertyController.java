package Server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Server.model.PropertiesModel;
import Server.model.ReviewModel;
import Server.model.appointment.AppointmentWithId;
import Server.service.property.PropertyInterface;
import ch.qos.logback.core.model.PropertyModel;

@RestController
public class PropertyController {

	@Autowired
	PropertyInterface propertyInterface;

	// ADD PROPERTY
	@PostMapping("/property/add")
	@ResponseBody
	public ResponseEntity<?> addProperty(@RequestBody PropertiesModel property) {
	
		return propertyInterface.addProperty(property);
	}
	
	//GET PROPERTIES
	
	@GetMapping("/property/get/all")
	@ResponseBody
	public ResponseEntity<?>getProperties()
	{
		return propertyInterface.getProperties();
		
		
	}
	
	
	//ADD REVIEW
	@PostMapping("/property/review/add")
	@ResponseBody
	public ResponseEntity<?>addReview(@RequestBody ReviewModel review){
		System.out.println("review adding in controller");

		return propertyInterface.addReview(review);
	}
	
	
	@PostMapping("/property/appointment/add")
	@ResponseBody
	public ResponseEntity<?>addAppointment(@RequestBody AppointmentWithId appointment)
	{
		return propertyInterface.addAppointment(appointment);
		
	}
	
	
	
	@PostMapping("/property/get/id")
	public ResponseEntity<?>getPropertiesById(@RequestHeader(name = "Authorization") String authorizationHeader){
		return propertyInterface.getPropertiesById(authorizationHeader);
	}

}
