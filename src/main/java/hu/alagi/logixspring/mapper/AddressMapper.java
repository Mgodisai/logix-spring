package hu.alagi.logixspring.mapper;

import hu.alagi.logixspring.dto.AddressDto;
import hu.alagi.logixspring.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    List<AddressDto> addressesToDtoList(List<Address> addressList);

    AddressDto addressToDto(Address address);
}
