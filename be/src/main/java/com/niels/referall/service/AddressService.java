package com.niels.referall.service;

import com.niels.referall.dto.address.CreateAddressDto;
import com.niels.referall.dto.address.UpdateAddressDto;
import com.niels.referall.entity.Address;

public interface AddressService {
    Address getOrCreateAddress(CreateAddressDto residence);

    Address getOrUpdateAddress(UpdateAddressDto dto);
}
