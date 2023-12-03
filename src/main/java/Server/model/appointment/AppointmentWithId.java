package Server.model.appointment;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "appointment")
public class AppointmentWithId {
	private Boolean isUser;
	private String propertyDetails;
 
	private String userDetails;
	
	private String landlordDetails;
	private String[] time;
	public Boolean getIsUser() {
		return isUser;
	}
	public void setIsUser(Boolean isUser) {
		this.isUser = isUser;
	}
	
	public String[] getTime() {
		return time;
	}
	public void setTime(String[] time) {
		this.time = time;
	}
	public String getPropertyDetails() {
		return propertyDetails;
	}
	public void setPropertyDetails(String propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	public String getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails;
	}
	public String getLandlordDetails() {
		return landlordDetails;
	}
	public void setLandlordDetails(String landlordDetails) {
		this.landlordDetails = landlordDetails;
	}

	

}
