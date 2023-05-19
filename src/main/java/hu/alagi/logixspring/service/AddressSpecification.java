package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    private AddressSpecification() {
    }

    public static Specification<Address> likeCity(String city) {
        return (root, cq, cb)-> cb.like(cb.upper(root.get(Address_.city)), city.toUpperCase() + "%");
    }

    public static Specification<Address> likeStreetName(String streetName) {
        return (root, cq, cb)-> cb.like(cb.upper(root.get(Address_.streetName)), streetName.toUpperCase() + "%");
    }

    public static Specification<Address> equalPostalCode(String postalCode) {
        return (root, cq, cb)-> cb.equal(root.get(Address_.postalCode), postalCode);
    }

    public static Specification<Address> equalCountryCode(String countryCode) {
        return (root, cq, cb)-> cb.equal(root.get(Address_.countryCode), countryCode);
    }
}
