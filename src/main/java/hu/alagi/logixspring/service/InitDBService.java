package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Address;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InitDBService {
    private final AddressService addressService;

    public InitDBService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Transactional
    public void clearDB() {
        addressService.deleteAll();
    }

    @Transactional
    public void insertTestData() {
        Address address1 = new Address("HU", "7400", "Kaposvár", "Virág u.", "17.TT.12", 46.35998, 17.80346);
        addressService.saveAddress(address1);
    }
}
