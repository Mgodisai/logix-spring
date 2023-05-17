package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
