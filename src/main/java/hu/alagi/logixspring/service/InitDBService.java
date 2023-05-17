package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InitDBService {
    private final AddressRepository addressRepository;

    public InitDBService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public void clearDB() {
        addressRepository.deleteAll();
    }

    @Transactional
    public void insertTestData() {
        Address address1 = new Address("HU", "7400", "Kaposvár", "Virág u.", "17.TT.12", 46.35998, 17.80346);
        addressRepository.save(address1);
    }
}
