package Server.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import Server.model.appointment.AppointmentWithId;
//import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String firstName;
	private String lastName;
	private String role;
	private String email;
	private String password;
	private String contactNumber;
	private String profilePicture;
	
	private AppointmentWithId[] appointmentDetails;
	
	
	//To fix infine loop in references

    private List<PropertiesModel> propertiesDetails;

	public String getId()
	{
		return id;
	}
	public String getFirstName() {
		System.out.println("invoked firstname");
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	  public void setId(String id) {
	        this.id = id;
	    }

	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<PropertiesModel> getPropertiesDetails() {
		System.out.println("getting user properties");
		return propertiesDetails;
	}
	public void setPropertiesDetails(List<PropertiesModel> propertiesDetails) {
		System.out.println("setting user properties");
		this.propertiesDetails = propertiesDetails;
	}
	public AppointmentWithId[] getAppointmentDetails() {
		return appointmentDetails;
	}
	public void setAppointmentDetails(AppointmentWithId[] appointmentDetails) {
		this.appointmentDetails = appointmentDetails;
	}

	
	
	

}
