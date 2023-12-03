package Server.service.property;

import java.util.*;

import org.apache.catalina.startup.UserDatabase;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import Server.jwt.JwtService;
import Server.model.PropertiesModel;
import Server.model.ReviewModel;
import Server.model.UserModel;
import Server.model.appointment.AppointmentWithId;
import Server.repository.PropertyRepository;
import Server.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import org.springframework.data.mongodb.core.query.Query;

@Service
public class PropertyImpl implements PropertyInterface {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	JwtService jwtService;
	@Override
	public ResponseEntity<?> addProperty(@RequestBody PropertiesModel property) {

	    try {
	        // Set the landlord details
	        UserModel updateLandlord = property.getLandlordDetails();
	        System.out.println("current Landlord: "+updateLandlord.getFirstName());
	        System.out.println("current Landlord property: "+updateLandlord.getPropertiesDetails());

	        // Save the landlord (UserModel) first
	        UserModel savedLandlord = userRepository.save(updateLandlord);

	        // Set the saved user as the landlord for the property
	        property.setLandlordDetails(savedLandlord);

	        // Add the property to the user's property list
	        if (savedLandlord.getPropertiesDetails() == null) {
	            savedLandlord.setPropertiesDetails(new ArrayList<>());
	       
	        }
	        savedLandlord.getPropertiesDetails().add(property);
	     
	   

	        // Save the property
	        propertyRepository.save(property);
	  

	        // Save the user with the updated properties
	        userRepository.save(savedLandlord);


	        Map<String, String> responseData = new HashMap<>();
	        responseData.put("Response", "This is the response from the property backend");
	        return ResponseEntity.status(HttpStatus.CREATED).body(responseData);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body("Unable to add property in the backend: " + e.getMessage());
	    }
	}

	@Override
	public ResponseEntity<?> getProperties() {
		try {
			List<PropertiesModel> properties = propertyRepository.findAll();
			Map<String, Object> responseData = new HashMap<>();
			List<Map<String, Object>> propertieswithLandlordList = new ArrayList<>();

			for (PropertiesModel property : properties) {
				Map<String, Object> propertyInfo = new HashMap<>();
				propertyInfo.put("id", property.getId());
				propertyInfo.put("propertyDetails", property.getPropertyDetails());
				propertyInfo.put("propertyGallery", property.getPropertyGallery());
				propertyInfo.put("rentalDetails", property.getRentalDetails());
				propertyInfo.put("reviews", property.getReviews());

				// Landlord is a reference to user model so first we need to fetch entire
				// details of landlord from user model

				// query helps to define the condition for searching
				Query query = new Query(Criteria.where("_id").is(property.getLandlordDetails().getId()));

				// executng the query condition
				UserModel landlord = mongoTemplate.findOne(query, UserModel.class);

				propertyInfo.put("landlordDetails", landlord);

				propertieswithLandlordList.add(propertyInfo);

			}

			responseData.put("properties", propertieswithLandlordList);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Unable to get properties in backend " + e.getMessage());

		}

	}

	
	@Override

	public ResponseEntity<?> addReview(@RequestBody ReviewModel review) {
	    System.out.println("in review ");
	    Map<String, Object> responseData = new HashMap<>();

	    try {
	        String propertyId = review.getProperty();
	        PropertiesModel property = propertyRepository.findById(propertyId).orElse(null);

	        String userId = review.getUserId();
	        UserModel userModel = userRepository.findById(userId).orElse(null);

	        if (property != null && userModel != null) {
	            // Creating new review
	            ReviewModel newReview = new ReviewModel();
	            newReview.setReview(review.getReview());
	            newReview.setUser(userModel);

	            // Retrieving reviews from the property
	            ReviewModel[] reviews = property.getReviews();

	            if (reviews == null) {
	                reviews = new ReviewModel[0];
	            }

	            // Add the new review to the list
	            List<ReviewModel> updatedReviewsList = new ArrayList<>(Arrays.asList(reviews));
	            updatedReviewsList.add(newReview);

	            // Set the updated reviews array back to the property
	            ReviewModel[] updatedReviewsArray = updatedReviewsList.toArray(new ReviewModel[0]);
	            property.setReviews(updatedReviewsArray);

	            // Save the updated property with the new review
	            propertyRepository.save(property);

	            // Refresh the property from the database to get the updated reviews
	            property = propertyRepository.findById(propertyId).orElse(null);

	            responseData.put("property", property);
	            return ResponseEntity.status(HttpStatus.OK).body(responseData);
	        } else {
	            responseData.put("Error", "Property or User not found");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        responseData.put("Error", "Unable to add review in backend " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseData);
	    }
	}


	
	@Override
	public ResponseEntity<?> addAppointment(@RequestBody AppointmentWithId appointment) {
		Map<String, Object> responseData = new HashMap<>();

		try {

			UserModel currentUser = userRepository.findById(appointment.getUserDetails()).orElse(null);
			AppointmentWithId[] currentUserAppointments = currentUser.getAppointmentDetails();
			if (currentUserAppointments == null) {
				currentUserAppointments = new AppointmentWithId[0];
			}

			List<AppointmentWithId> updatedUserAppointments = new ArrayList<>(Arrays.asList(currentUserAppointments));

			AppointmentWithId userNewAppointment = new AppointmentWithId();
			userNewAppointment.setIsUser(true);
			userNewAppointment.setLandlordDetails(appointment.getLandlordDetails());
			userNewAppointment.setPropertyDetails(appointment.getPropertyDetails());
			userNewAppointment.setTime(appointment.getTime());
			userNewAppointment.setUserDetails(appointment.getUserDetails());
			updatedUserAppointments.add(userNewAppointment);

			AppointmentWithId[] newAppointmentsArray = updatedUserAppointments
					.toArray(new AppointmentWithId[updatedUserAppointments.size()]);

			currentUser.setAppointmentDetails(newAppointmentsArray);
			userRepository.save(currentUser);

			// Updating landlord appointments

			UserModel currentLandlord = userRepository.findById(appointment.getLandlordDetails()).orElse(null);
			AppointmentWithId[] currentLandlordAppointments = currentLandlord.getAppointmentDetails();
			if (currentLandlordAppointments == null) {
				currentLandlordAppointments = new AppointmentWithId[0];
			}
			List<AppointmentWithId> updatedLandlordAppointments = new ArrayList<>(Arrays.asList(currentLandlordAppointments));

			AppointmentWithId landlordNewAppointment = new AppointmentWithId();
			landlordNewAppointment.setIsUser(false);
			landlordNewAppointment.setLandlordDetails(appointment.getLandlordDetails());
			landlordNewAppointment.setPropertyDetails(appointment.getPropertyDetails());
			landlordNewAppointment.setTime(appointment.getTime());
			landlordNewAppointment.setUserDetails(appointment.getUserDetails());
			updatedLandlordAppointments.add(landlordNewAppointment);

			AppointmentWithId[] newLandlordAppointmentsArray = updatedLandlordAppointments.toArray(new AppointmentWithId[0]);

			currentLandlord.setAppointmentDetails(newLandlordAppointmentsArray);

			userRepository.save(currentLandlord);
			
			responseData.put("appointment", userNewAppointment);
			return ResponseEntity.status(HttpStatus.OK).body(responseData);
		} catch (Exception e) {
			e.printStackTrace();
			responseData.put("Error", "Unable to add appointment in backend " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
		}

	}
	
	
	@Override
	public ResponseEntity<?>getPropertiesById(String authorizationHeader){
		Map<String, Object> responseData = new HashMap<>();

		try {
			if (jwtService.verifyTokenInHeader(authorizationHeader)) {
//				String token = authorizationHeader.substring(7);
//				Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtService.SECRET_KEY).parseClaimsJws(token);
//				Claims claims = claimsJws.getBody();
//				String userId = (String) claims.get("id");
//				System.out.println(userId);
//				UserModel userData = userRepository.findById(userId).orElse(null);
//				List<PropertiesModel> propertiesDetails = userData.getPropertiesDetails();
//				responseData.put("propertiesDetails", propertiesDetails);
//				System.out.println(userData);
				responseData.put("Status", true);

				return ResponseEntity.status(HttpStatus.OK).body(responseData);

			}
			responseData.put("Status", false);

			return ResponseEntity.status(HttpStatus.OK).body(responseData);
			
			
		}
		catch (Exception e) {
			responseData.put("status",false);
			responseData.put("Error", "Unable to get properties by id in backend " + e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);

		}
	}

}