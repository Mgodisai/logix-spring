package hu.alagi.logixspring.web;

import hu.alagi.logixspring.mapper.AddressMapper;
import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.dto.AddressDto;
import hu.alagi.logixspring.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    private final AddressMapper addressMapper;

    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping
    public List<AddressDto> getAllAddresses() {
        List<Address> addressList = addressService.getAllAddresses();
        return addressMapper.addressesToDtoList(addressList);
    }

}
