package com.niels.referall.service.impl;

import com.niels.referall.dto.address.CreateAddressDto;
import com.niels.referall.dto.address.UpdateAddressDto;
import com.niels.referall.entity.Address;
import com.niels.referall.factory.AddressFactory;
import com.niels.referall.repository.AddressRepository;
import com.niels.referall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address getOrCreateAddress(CreateAddressDto dto) {
        Optional<Address> optionalAddress = addressRepository.findIfExist(dto.getAddress(), dto.getCity(), dto.getZipCode(), dto.getProvinceCode(), dto.getCountryCode());
        return optionalAddress.orElseGet(() -> AddressFactory.createAddress(dto, addressRepository));
    }

    @Override
    public Address getOrUpdateAddress(UpdateAddressDto dto) {
        Optional<Address> optionalAddress = addressRepository.findIfExist(dto.getAddress(), dto.getCity(), dto.getZipCode(), dto.getProvinceCode(), dto.getCountryCode());
        return optionalAddress.orElseGet(() -> AddressFactory.updateAddress(dto, addressRepository));
    }

}
