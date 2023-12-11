package Server.service.user;

import org.apache.tomcat.util.bcel.Const;
import org.bson.Document;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import Server.jwt.JwtService;
import Server.model.PropertiesModel;
import Server.model.UserModel;
import Server.model.appointment.AppointmentWithId;
import Server.model.appointment.AppointmentWithValue;
import Server.repository.PropertyRepository;
import Server.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Service
public class UserImpl implements UserInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PropertyRepository propertyRepository;
	@Autowired
	JwtService jwtService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> addUser(@RequestBody UserModel user) {
		try {
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(hashedPassword);

			this.userRepository.save(user);

			String token = jwtService.generateToken(user);

			// creating a repose map to send to frontend
			Map<String, Object> responseData = new HashMap<>();
			responseData.put("token", token);
			responseData.put("status", "true");

			responseData.put("userData", user);

			// return the response object with a 201 status code
			return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unable to add user in backend: " + e.getMessage());
		}
	}

	private UserModel findUserByEmail(String email) {
		Document query = new Document("email", email);

		// Use the explain command to get query execution details
		Document explainCommand = new Document("find", "your_collection_name").append("filter", query);
		Document explainResult = mongoTemplate.getDb().runCommand(new Document("explain", explainCommand));

		// Log or print the explain result
		System.out.println("Explain Result: " + explainResult.toJson());

		// Now, you can proceed with the regular find operation
		List<UserModel> existingUsers = userRepository.findAll();

		for (UserModel existingUser : existingUsers) {
			if (existingUser.getEmail().equals(email)) {
				return existingUser;
			}
		}

		return null;
	}
//	   public UserModel findUserByEmail(String email) {
//	        try {
//	            // Use the explain command to get query execution details
//	            Document query = new Document("email", email);
//	            Document explainCommand = new Document("find", "your_collection_name")
//	                    .append("filter", query)
//	                    .append("verbosity", "executionStats");  // Specify verbosity for detailed stats
//	            Document explainResult = mongoTemplate.getDb().runCommand(new Document("explain", explainCommand));
//
//	            // Log or print the explain result
//	            System.out.println("Explain Result: " + explainResult.toJson());
//
//	            // Now, you can proceed with the regular find operation using an indexed query
//	            Query indexedQuery = new Query(Criteria.where("email").is(email));
//	            UserModel existingUser = mongoTemplate.findOne(indexedQuery, UserModel.class);
//
//	            return existingUser;
//	        } catch (Exception e) {
//	            // Log or handle the exception
//	            e.printStackTrace();
//	            return null;
//	        }
//	    }
//	
//	

	@Override
	public ResponseEntity<?> checkUser(@RequestBody UserModel user) {
		System.out.println("in check user");
		try {
			System.out.println("before get email");
			System.out.println(user.getEmail());
			UserModel existingUser = findUserByEmail(user.getEmail());
//			System.out.println("User data after login: "+existingUser.getPropertiesDetails().get(0).getPropertyDetails().getName());
			System.out.println("after get email");

			Map<String, Object> responseData = new HashMap<>();

			if (existingUser != null && BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {

				UserModel completeUserData = findUserByEmail(existingUser.getEmail());
				String token = jwtService.generateToken(completeUserData);

				// Verify the token associated with the user
				System.out.println("in login 10 ");
				System.out.println("the user is");
				System.out.println(completeUserData.getFirstName());
				boolean isTokenValid = jwtService.verifyToken(token);
				responseData.put("token", token);
				responseData.put("userData", completeUserData);
				System.out.println("in login 11");

				if (isTokenValid) {
					System.out.println("in login 12 ");
//					System.out.println(completeUserData.getPropertiesDetails().get(0).getPropertyDetails().getName());
					responseData.put("status", "true");
					System.out.println("in login 3");

					return ResponseEntity.status(HttpStatus.OK).body(responseData);
				} else {

					System.out.println("in login 4");
					responseData.put("status", "false");
					return ResponseEntity.status(HttpStatus.OK).body(responseData);

				}
			} else {
				System.out.println("in login 5");

				responseData.put("status", "false");
				return ResponseEntity.status(HttpStatus.OK).body(responseData);
			}
		} catch (Exception e) {
			System.out.println("error in check user " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Unable to check user in backend: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> convertAppointmentIds(@RequestBody AppointmentWithId appointmentDataId) {
		Map<String, Object> responseData = new HashMap<>();
		try {

			UserModel appointmentUser = userRepository.findById(appointmentDataId.getUserDetails()).orElse(null);
			UserModel appointmentLandlord = userRepository.findById(appointmentDataId.getLandlordDetails())
					.orElse(null);
			PropertiesModel appointmentProperty = propertyRepository.findById(appointmentDataId.getPropertyDetails())
					.orElse(null);
			AppointmentWithValue currentAppointment = new AppointmentWithValue();
			currentAppointment.setIsUser(appointmentDataId.getIsUser());
			currentAppointment.setLandlordDetails(appointmentLandlord);
			currentAppointment.setPropertyDetails(appointmentProperty);
			currentAppointment.setUserDetails(appointmentUser);
			currentAppointment.setTime(appointmentDataId.getTime());

			responseData.put("appointmnent", currentAppointment);
			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		} catch (Exception e) {
			e.printStackTrace();
			responseData.put("Error", "Unable to convert appointment in backend " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);

		}
	}

	@Override
	public ResponseEntity<?> updateUser(@RequestBody UserModel updatedUser) {
		try {
			// Find the existing user in the database
			UserModel existingUser = userRepository.findById(updatedUser.getId()).orElse(null);

			if (existingUser != null) {
				// Update specific fields if they are present in the request

				existingUser.setFirstName(updatedUser.getFirstName());

				existingUser.setLastName(updatedUser.getLastName());
				existingUser.setContactNumber(updatedUser.getContactNumber());

				existingUser.setEmail(updatedUser.getEmail());
				existingUser.setRole(updatedUser.getRole());

				// Add other fields as needed

				// Save the updated user to the database
				userRepository.save(existingUser);

				// Optionally, generate a new JWT token after the update
				String newToken = jwtService.generateToken(existingUser);

				// Create a response map to send to the frontend
				Map<String, Object> responseData = new HashMap<>();
				responseData.put("token", newToken);
				responseData.put("status", "true");
				responseData.put("userData", existingUser);

				// Return the response object with a 200 status code
				return ResponseEntity.status(HttpStatus.OK).body(responseData);
			} else {
				// User with the provided ID not found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Unable to update user in backend: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> getUser(String authorizationHeader) {
		Map<String, Object> responseData = new HashMap<>();
		try {

			if (jwtService.verifyTokenInHeader(authorizationHeader)) {
				String token = authorizationHeader.substring(7);
				Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtService.SECRET_KEY).parseClaimsJws(token);
				Claims claims = claimsJws.getBody();
				String userId = (String) claims.get("id");

				UserModel userData = userRepository.findById(userId).orElse(null);
				responseData.put("userData", userData);
				responseData.put("Status", true);

				return ResponseEntity.status(HttpStatus.OK).body(responseData);

			}
			responseData.put("Status", false);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		}

		catch (Exception e) {
			responseData.put("Status", false);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		}

	}

	@Override
	public ResponseEntity<?> getUserById(@RequestBody Map<String, String> currentUserMap) {
		Map<String, Object> responseData = new HashMap<>();
		try {
			String currentUserId = currentUserMap.get("currentUserId");

			UserModel userData = userRepository.findById(currentUserId).orElse(null);
			responseData.put("userData", userData);
			responseData.put("Status", true);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		}

		catch (Exception e) {
			responseData.put("Status", false);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);

		}

	}
}
