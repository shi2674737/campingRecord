package com.example.campingrecord.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campingrecord.dto.AddressDto;
import com.example.campingrecord.entity.Address;
import com.example.campingrecord.service.AddressService;
import com.example.campingrecord.vo.AddressDetailVo;
import com.example.campingrecord.vo.result.UResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/addAddress")
    public UResult<String> addAddress(@RequestBody AddressDto.AddAddressDto addAddressDto) {
        log.info("addAddress start addAddressDto = {}", addAddressDto);
        addressService.addAddress(addAddressDto);
        log.info("addAddress success.");
        return UResult.ok();
    }

    @PostMapping("/updateAddress")
    public UResult<String> updateAddress(@RequestBody AddressDto.UpdateAddressDto updateAddressDto) {
        log.info("updateAddress start updateAddressDto = {}", updateAddressDto);
        addressService.updateAddress(updateAddressDto);
        log.info("updateAddress success.");
        return UResult.ok();
    }

    @GetMapping("/getAddressList")
    public UResult<Page<Address>> getAddressList(AddressDto.GetAddressListDto getAddressListDto) {
        log.info("getAddressList start getAddressListDto = {}", getAddressListDto);
        Page<Address> addressList = addressService.getAddressList(getAddressListDto);
        log.info("getAddressList success. addressList = {}", addressList);
        return UResult.ok().data(addressList);
    }

    @GetMapping("/getAddressDetail")
    public UResult<AddressDetailVo> getAddressDetail(AddressDto.GetAddressDetailDto getAddressDetailDto) {
        log.info("getAddressDetail start getAddressListDto = {}", getAddressDetailDto);
        AddressDetailVo addressDetail = addressService.getAddressDetail(getAddressDetailDto);
        log.info("getAddressDetail success. addressDetail = {}", addressDetail);
        return UResult.ok().data(addressDetail);
    }

    /**
     * 填充下拉菜单
     * @return
     */
    @GetMapping("/getAllAddressList")
    public UResult<List<Address>> getAllAddressList() {
        log.info("getAddressDetail start.");
        List<Address> address = addressService.getAllAddressList();
        log.info("getAddressDetail success.");
        return UResult.ok().data(address);
    }
}
