package Server.model.appointment;

import Server.model.PropertiesModel;
import Server.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWithValue {
	private Boolean isUser;
	private PropertiesModel propertyDetails;
 
	private UserModel userDetails;
	
	private UserModel landlordDetails;
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
	public PropertiesModel getPropertyDetails() {
		return propertyDetails;
	}
	public void setPropertyDetails(PropertiesModel propertyDetails) {
		this.propertyDetails = propertyDetails;
	}
	public UserModel getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserModel userDetails) {
		this.userDetails = userDetails;
	}
	public UserModel getLandlordDetails() {
		return landlordDetails;
	}
	public void setLandlordDetails(UserModel landlordDetails) {
		this.landlordDetails = landlordDetails;
	}

}
