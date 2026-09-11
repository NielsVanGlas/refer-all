package com.niels.referall.factory;

import com.niels.referall.dto.address.CreateAddressDto;
import com.niels.referall.dto.address.ShowAddressDto;
import com.niels.referall.dto.address.UpdateAddressDto;
import com.niels.referall.entity.Address;
import com.niels.referall.repository.AddressRepository;

public class AddressFactory {

    public static Address createAddress(CreateAddressDto dto, AddressRepository addressRepository) {
        return addressRepository.saveAndFlush(new Address(
                dto.getAddress(),
                dto.getCity(),
                dto.getZipCode(),
                dto.getProvinceCode(),
                dto.getCountryCode()
        ));
    }

    public static Address updateAddress(UpdateAddressDto dto, AddressRepository addressRepository) {
        return addressRepository.saveAndFlush(new Address(
                dto.getAddress(),
                dto.getCity(),
                dto.getZipCode(),
                dto.getProvinceCode(),
                dto.getCountryCode()
        ));
    }

    public static ShowAddressDto showAddressDto(Address entity) {
        return new ShowAddressDto(
                entity.getId(),
                entity.getAddress(),
                entity.getCity(),
                entity.getZipCode(),
                entity.getProvinceCode(),
                entity.getCountryCode()
        );
    }

}
