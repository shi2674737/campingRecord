package com.example.campingrecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campingrecord.dto.AddressDto;
import com.example.campingrecord.entity.Address;
import com.example.campingrecord.vo.AddressDetailVo;

import java.util.List;

public interface AddressService extends IService<Address> {
    void addAddress(AddressDto.AddAddressDto addAddressDto);

    void updateAddress(AddressDto.UpdateAddressDto updateAddressDto);

    Page<Address> getAddressList(AddressDto.GetAddressListDto getAddressListDto);

    AddressDetailVo getAddressDetail(AddressDto.GetAddressDetailDto getAddressDetailDto);

    List<Address> getAllAddressList();
}
