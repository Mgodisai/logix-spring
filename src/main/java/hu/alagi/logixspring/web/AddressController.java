package hu.alagi.logixspring.web;

import com.fasterxml.jackson.annotation.JsonView;
import hu.alagi.logixspring.dto.AddressSearchDto;
import hu.alagi.logixspring.dto.Views;
import hu.alagi.logixspring.exception.EntityIdMismatchException;
import hu.alagi.logixspring.exception.EntityNotExistsWithGivenIdException;
import hu.alagi.logixspring.mapper.AddressMapper;
import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.dto.AddressDto;
import hu.alagi.logixspring.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/addresses")
@JsonView(Views.ExtendedView.class)
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
        return addressMapper.toDtoList(addressList);
    }

    @GetMapping("/{id}")
    public AddressDto getAddressById(@PathVariable Long id) {
        Address requestedAddress = addressService.getAddressById(id)
                .orElseThrow(()->new EntityNotExistsWithGivenIdException(id, Address.class));
        return addressMapper.toDto(requestedAddress);
    }


    @PostMapping
    public AddressDto createNewAddress(@RequestBody @Valid AddressDto addressDto) {
        if (addressDto.getId()!=null) {
            throw new IllegalArgumentException("Do not use id in case of posting new address!");
        }
        Address savedAddress = addressService.saveAddress(addressMapper.toAddress(addressDto))
                .orElseThrow(()->new IllegalArgumentException("Entity cannot be saved!"));
        return addressMapper.toDto(savedAddress);
    }

    @PostMapping("/search")
    public ResponseEntity<List<AddressDto>> searchEmployees(
            @RequestBody AddressSearchDto exampleDto,
            @PageableDefault(size = Integer.MAX_VALUE, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        if (exampleDto == null) {
            throw new IllegalArgumentException("In case of search the request body cannot be empty!");
        }
        Page<Address> resultPage = addressService.findAddressesByExample(exampleDto, pageable);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count", String.valueOf(resultPage.getTotalElements()));
        return new ResponseEntity<>(resultPage.map(addressMapper::toDto).getContent(), responseHeaders, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public AddressDto updateExistingAddress(@PathVariable Long id, @RequestBody @Valid AddressDto addressDto) {
        Long idFromBody = addressDto.getId();
        if (idFromBody!=null && !Objects.equals(addressDto.getId(), id)) {
            throw new EntityIdMismatchException("The id from path ("+id+") and from the body ("+addressDto.getId()+") is mismatched");
        }
        Address updatingAddress = addressMapper.toAddress(addressDto);
        updatingAddress.setId(id);

        Address savedAddress = addressService.saveAddress(updatingAddress)
                .orElseThrow(()->new IllegalArgumentException("Entity cannot be updated!"));
        return addressMapper.toDto(savedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.ok().build();
    }
}
