package Server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Server.model.UserModel;
import Server.model.appointment.AppointmentWithId;
import Server.service.user.UserInterface;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	@Autowired
	UserInterface userInterface;

	// SIGN UP USER
	@PostMapping("/user/signup")
	@ResponseBody
	public ResponseEntity<?> signUpUser(@RequestBody UserModel user) {
	

		return userInterface.addUser(user);
	}

	// LOG IN USER
	@PostMapping("/user/login")
	@ResponseBody
	public ResponseEntity<?>logInUser(@RequestBody UserModel user)
	{
		return userInterface.checkUser(user);
	}
	
	//UPDATE USER
	@PostMapping("/user/update")
	@ResponseBody
	public ResponseEntity<?>updateUser(@RequestBody UserModel user)
	{
		return userInterface.updateUser(user);
	}
	
	@GetMapping("/user/get")
	@ResponseBody
	public ResponseEntity<?>getUser(@RequestHeader(name = "Authorization") String authorizationHeader) 

	
	{
		return userInterface.getUser(authorizationHeader);
	}
	
	
	@PostMapping("/user/appointment/get")
	@ResponseBody
	public ResponseEntity<?>convertAppointmentIds(@RequestBody AppointmentWithId appointmentDataId)
	{
		return userInterface.convertAppointmentIds(appointmentDataId);
	}
	
	

	@PostMapping("/user/get/id")
	@ResponseBody
	public ResponseEntity<?>getUserById(@RequestBody Map<String,String> currentUserId){
		return userInterface.getUserById(currentUserId);
		
	}

}
