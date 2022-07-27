package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.dto.AddressDto;
import com.example.campingrecord.entity.Address;
import com.example.campingrecord.entity.CampingAddressComment;
import com.example.campingrecord.mapper.AddressMapper;
import com.example.campingrecord.service.AddressService;
import com.example.campingrecord.service.CampingAddressCommentService;
import com.example.campingrecord.utils.UserUtil;
import com.example.campingrecord.vo.AddressDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
        implements AddressService {

    @Autowired
    CampingAddressCommentService commentService;

    @Override
    public void addAddress(AddressDto.AddAddressDto addAddressDto) {
        Long userId = UserUtil.getCurrentUser().getId();
        // 新增露营地点
        Address address = new Address();
        BeanUtils.copyProperties(addAddressDto, address);
        address.setCreateBy(userId);
        address.setCreateTime(LocalDateTime.now());
        baseMapper.insert(address);
    }

    @Override
    public void updateAddress(AddressDto.UpdateAddressDto updateAddressDto) {
        Long userId = UserUtil.getCurrentUser().getId();
        // 新增露营地点
        Address address = new Address();
        BeanUtils.copyProperties(updateAddressDto, address);
        address.setUpdateBy(userId);
        address.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(address);
    }

    @Override
    public Page<Address> getAddressList(AddressDto.GetAddressListDto getAddressListDto) {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        Page<Address> addressPage = baseMapper.selectPage(new Page<>(getAddressListDto.getPageNo(), getAddressListDto.getPageSize()), queryWrapper);
        return addressPage;
    }

    @Override
    public AddressDetailVo getAddressDetail(AddressDto.GetAddressDetailDto getAddressDetailDto) {
        Long addressId = getAddressDetailDto.getId();
        AddressDetailVo addressDetailVo = new AddressDetailVo();
        Address address = baseMapper.selectById(addressId);
        BeanUtils.copyProperties(address, addressDetailVo);
        LambdaQueryWrapper<CampingAddressComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CampingAddressComment::getAddressId, addressId);
        queryWrapper.orderByDesc(CampingAddressComment::getCreateTime);
        // 获取关于这个地点的评论评分
        List<CampingAddressComment> commentList = commentService.list(queryWrapper);
        commentList = commentList.stream().limit(10).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(commentList)) {
            addressDetailVo.setComments(commentList);
            // 求平均分数
            BigDecimal avg = commentList.stream().map(CampingAddressComment::getScore)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(commentList.size()), 2, RoundingMode.HALF_UP);
            addressDetailVo.setAvgScore(avg);
        }
        return addressDetailVo;
    }

    @Override
    public List<Address> getAllAddressList() {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectList(queryWrapper);
    }
}
