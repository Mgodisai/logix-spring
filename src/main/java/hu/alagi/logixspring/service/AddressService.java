package hu.alagi.logixspring.service;

import hu.alagi.logixspring.dto.AddressSearchDto;
import hu.alagi.logixspring.exception.InvalidCountryCodeException;
import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final CountryService countryService;

    public AddressService(AddressRepository addressRepository, CountryService countryService) {
        this.addressRepository = addressRepository;
        this.countryService = countryService;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public boolean addressExistsById(Long id) {
        return addressRepository.existsById(id);
    }

    @Transactional
    public Optional<Address> saveAddress(Address address) {
        checkCountryCode(address.getCountryCode());
        return Optional.of(addressRepository.save(address));
    }

    public void saveAddressList(List<Address> addresses) {
        addressRepository.saveAll(addresses);
    }

    public void checkCountryCode(String countryCode) {
        if (!countryService.isValidISOCountryCode(countryCode)) {
            throw new InvalidCountryCodeException(countryCode);
        }
    }

    @Transactional
    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        addressRepository.deleteAll();
    }

    public Page<Address> findAddressesByExample(AddressSearchDto exampleDto, Pageable pageable) {
        Specification<Address> spec = (root, query, cb) -> cb.conjunction();

        if (exampleDto.getCity()!=null) {
            spec = spec.and(AddressSpecification.likeCity(exampleDto.getCity()));
        }

        if (exampleDto.getStreetName()!=null) {
            spec = spec.and(AddressSpecification.likeStreetName(exampleDto.getStreetName()));
        }

        if (exampleDto.getPostalCode()!=null) {
            spec = spec.and(AddressSpecification.equalPostalCode(exampleDto.getPostalCode()));
        }

        if (exampleDto.getCountryCode()!=null) {
            spec = spec.and(AddressSpecification.equalCountryCode(exampleDto.getCountryCode()));
        }

        return addressRepository.findAll(spec, pageable);
    }
}
