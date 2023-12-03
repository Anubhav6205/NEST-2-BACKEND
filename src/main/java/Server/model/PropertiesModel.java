package Server.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import Server.model.properties.PropertyDetails;
import Server.model.properties.RentalDetails;


import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "property")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PropertiesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@DBRef
	private UserModel landlordDetails;
	private String[] propertyGallery;
	private PropertyDetails propertyDetails;
	private RentalDetails rentalDetails;

	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
	private ReviewModel[] reviews;

	public UserModel getLandlordDetails() {
		return landlordDetails;
	}

	public void setLandlordDetails(UserModel landlordDetails) {
		this.landlordDetails = landlordDetails;
	}

	public String[] getPropertyGallery() {
		return propertyGallery;
	}

	public void setPropertyGallery(String[] propertyGallery) {
		this.propertyGallery = propertyGallery;
	}

	public PropertyDetails getPropertyDetails() {
		return propertyDetails;
	}

	public void setPropertyDetails(PropertyDetails propertyDetails) {
		this.propertyDetails = propertyDetails;
	}

	public RentalDetails getRentalDetails() {
		return rentalDetails;
	}

	public void setRentalDetails(RentalDetails rentalDetails) {
		this.rentalDetails = rentalDetails;
	}

	public void printAll() {
		System.out.println("Landlord details:");
		System.out.println(landlordDetails);

		System.out.println("Property details:");
		System.out.println(propertyDetails);

		System.out.println("Rental details:");
		System.out.println(rentalDetails);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ReviewModel[] getReviews() {

		return reviews;
	}

	public void setReviews(ReviewModel[] reviews) {
		this.reviews = reviews;
	}

}
