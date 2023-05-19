package hu.alagi.logixspring.mapper;

import hu.alagi.logixspring.dto.AddressDto;
import hu.alagi.logixspring.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    List<AddressDto> toDtoList(List<Address> addressList);

    AddressDto toDto (Address address);

    Address toAddress(AddressDto addressDto);

    List<Address> toAddressList(List<AddressDto> addressDtoList);
}
