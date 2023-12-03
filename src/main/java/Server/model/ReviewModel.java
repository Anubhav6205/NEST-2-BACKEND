package Server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.qos.logback.core.model.PropertyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModel {
	private String review;

	private UserModel user;
	private String userId;

	private String property;
	public String getReview() {
		System.out.println("getting review");
		return review;
	}
	public void setReview(String review) {
		System.out.println("setting review");
		this.review = review;
	}
	public UserModel getUser() {
		System.out.println("getting user");
		return user;
	}
	public void setUser(UserModel user) {
		System.out.println("setting user");
		this.user = user;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


}
