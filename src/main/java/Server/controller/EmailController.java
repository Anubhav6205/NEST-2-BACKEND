package Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Server.model.EmailModel;
import Server.service.email.emailInterface;

//EmailController.java
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

	@Autowired
	private emailInterface emailService;

	@PostMapping("/email")
	@ResponseBody
	public ResponseEntity<String> sendEmail(@RequestBody EmailModel emailRequest) {
		System.out.println("Received email request in backend");

		try {

			emailService.sendEmailWithImage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody(),
					emailRequest.getImageData());

			System.out.println("Email sent successfully");
			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
		}
	}
}
