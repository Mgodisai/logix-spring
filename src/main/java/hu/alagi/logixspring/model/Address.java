package hu.alagi.logixspring.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class Address extends AbstractEntity<Long> {

    @NotEmpty
    @Size(max = 2)
    private String countryCode;

    @NotEmpty
    @Size(max = 20)
    private String postalCode;

    @NotEmpty
    @Size(max = 100)
    private String city;

    @NotEmpty
    @Size(max = 200)
    private String streetName;

    @NotEmpty
    @Size(max = 20)
    private String houseNumber;

    private Double latitude;

    private Double longitude;

    public Address() {
    }

    public Address(String countryCode, String postalCode, String city, String streetName, String houseNumber, Double latitude, Double longitude) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(houseNumber, address.houseNumber) && Objects.equals(streetName, address.streetName) && Objects.equals(city, address.city) && Objects.equals(postalCode, address.postalCode) && Objects.equals(countryCode, address.countryCode) && Objects.equals(latitude, address.latitude) && Objects.equals(longitude, address.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, streetName, city, postalCode, countryCode, latitude, longitude);
    }
}
