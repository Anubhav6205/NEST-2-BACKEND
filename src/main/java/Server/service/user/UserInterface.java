package Server.service.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;import org.springframework.web.bind.annotation.ResponseBody;

import Server.model.PaymentModel;
import Server.model.UserModel;
import Server.model.appointment.AppointmentWithId;
import jakarta.servlet.http.HttpServletRequest;

public interface UserInterface {
	//ADD USER 
	
	public ResponseEntity<?>addUser(@RequestBody UserModel user);
	
	//CHECK USER
	
	public ResponseEntity<?>checkUser(@RequestBody UserModel user);
	
	
	//CONVERT APPOINTMENT IDS
	
	public ResponseEntity<?>convertAppointmentIds(@RequestBody AppointmentWithId appointmentDataId);
	
	//UPDATE USER
	
	public ResponseEntity<?>updateUser(@RequestBody UserModel user);
	
	
//	public ResponseEntity<?>updateUser(@RequestBody String token );
	
	//ADD PAYMENT RECORD
	
	public ResponseEntity<?>addPaymentRecord(@RequestBody PaymentModel currentPayment);
	
	
	//GET CURRENT USER BY TOKEN
	
	public ResponseEntity<?>getUser(String authorizationHeader);
	
	
	//GET USER BY ID
	public ResponseEntity<?>getUserById(@RequestBody Map<String,String> currentUserId);
	
	
}
