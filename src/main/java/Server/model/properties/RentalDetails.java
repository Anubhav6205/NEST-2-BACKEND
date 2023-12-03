package Server.model.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalDetails {

    private String expectedRent;
    private String furnishing;
    private String parking;

    // Getter and Setter methods for each variable
    public String getExpectedRent() {
        return expectedRent;
    }

    public void setExpectedRent(String expectedRent) {
        this.expectedRent = expectedRent;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }
}
