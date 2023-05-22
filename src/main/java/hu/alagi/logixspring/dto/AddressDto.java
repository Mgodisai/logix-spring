package hu.alagi.logixspring.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;
public class AddressDto {

    @JsonView(Views.ExtendedView.class)
    private Long id;

    @NotEmpty
    @Size(min=2, max = 2)
    @JsonView(Views.BaseView.class)
    private String countryCode;
    @NotEmpty
    @Size(max = 20)
    @JsonView(Views.BaseView.class)
    private String postalCode;
    @NotEmpty
    @Size(max = 100)
    @JsonView(Views.BaseView.class)
    private String city;
    @NotEmpty
    @Size(max = 200)
    @JsonView(Views.BaseView.class)
    private String streetName;
    @NotEmpty
    @Size(max = 20)
    @JsonView(Views.BaseView.class)
    private String houseNumber;
    @Range(min=-90, max = 90)
    @JsonView(Views.ExtendedView.class)
    private Double latitude;
    @Range(min=-180, max = 180)
    @JsonView(Views.ExtendedView.class)
    private Double longitude;

    public AddressDto() {
    }

    public AddressDto(Long id, String countryCode, String postalCode, String city, String streetName, String houseNumber, Double latitude, Double longitude) {
        this.id = id;
        this.countryCode = countryCode;
        this.postalCode = postalCode;
        this.city = city;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        AddressDto address = (AddressDto) o;
        return Objects.equals(houseNumber, address.houseNumber) && Objects.equals(streetName, address.streetName) && Objects.equals(city, address.city) && Objects.equals(postalCode, address.postalCode) && Objects.equals(countryCode, address.countryCode) && Objects.equals(latitude, address.latitude) && Objects.equals(longitude, address.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNumber, streetName, city, postalCode, countryCode, latitude, longitude);
    }
}
