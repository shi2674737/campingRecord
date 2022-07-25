package com.example.campingrecord.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.campingrecord.constant.CampingVisibleEnum;
import com.example.campingrecord.constant.DeleteFlagEnum;
import com.example.campingrecord.dto.CampingRecordDto;
import com.example.campingrecord.entity.*;
import com.example.campingrecord.exception.BaseException;
import com.example.campingrecord.mapper.CampingMapper;
import com.example.campingrecord.service.*;
import com.example.campingrecord.utils.UserUtil;
import com.example.campingrecord.vo.CampingDetailVo;
import com.example.campingrecord.vo.CampingImageVo;
import com.example.campingrecord.vo.CampingListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CampingServiceImpl extends ServiceImpl<CampingMapper, Camping>
        implements CampingService {

    @Autowired
    CampingImageService campingImageService;

    @Autowired
    CampingUserRelationService campingUserRelationService;

    @Autowired
    CampingAddressCommentService campingAddressCommentService;

    @Autowired
    AddressService addressService;

    @Autowired
    OssService ossService;

    @Override
    @Transactional
    public void addCampingRecord(CampingRecordDto.AddCampingRecordDto addCampingRecordDto) {
        Long userId = UserUtil.getCurrentUser().getId();
        // 存露营记录
        Camping camping = new Camping();
        BeanUtils.copyProperties(addCampingRecordDto, camping);
        camping.setCreateBy(userId);
        camping.setCreateTime(LocalDateTime.now());
        baseMapper.insert(camping);
        // 存关联人
        List<Long> relationUserIds = addCampingRecordDto.getRelationUserId();
        relationUserIds.add(userId);
        if (CollectionUtils.isNotEmpty(relationUserIds)) {
            List<CampingUserRelation> campingUserRelations = relationUserIds.stream().map(relationUserId -> {
                CampingUserRelation campingUserRelation = new CampingUserRelation();
                campingUserRelation.setCampingId(camping.getId());
                campingUserRelation.setUserId(relationUserId);
                return campingUserRelation;
            }).collect(Collectors.toList());
            campingUserRelationService.saveBatch(campingUserRelations);
        }
        // 存图片
        List<String> images = addCampingRecordDto.getImages();
        if (CollectionUtils.isNotEmpty(images)) {
            List<CampingImage> campingImages = images.stream().map(image -> {
                CampingImage campingImage = new CampingImage();
                campingImage.setCampingId(camping.getId());
                campingImage.setImageUrl(image);
                return campingImage;
            }).collect(Collectors.toList());
            campingImageService.saveBatch(campingImages);
        }
        // 存评价评论
        CampingAddressComment campingAddressComment = new CampingAddressComment();
        campingAddressComment.setScore(addCampingRecordDto.getScore());
        campingAddressComment.setComment(addCampingRecordDto.getComment());
        campingAddressComment.setAddressId(addCampingRecordDto.getAddressId());
        campingAddressComment.setCampingId(camping.getId());
        campingAddressComment.setCreateBy(userId);
        campingAddressComment.setCreateTime(LocalDateTime.now());
        campingAddressCommentService.save(campingAddressComment);
    }

    @Override
    @Transactional
    public void updateCampingRecord(CampingRecordDto.UpdateCampingRecordDto updateCampingRecordDto) {
        // 检查有没有权限修改
        Long userId = UserUtil.getCurrentUser().getId();
        LambdaQueryWrapper<CampingUserRelation> campingUserRelationQuery = new LambdaQueryWrapper<>();
        campingUserRelationQuery.eq(CampingUserRelation::getCampingId, updateCampingRecordDto.getId());
        List<CampingUserRelation> campingUserRelationList = campingUserRelationService.list(campingUserRelationQuery);
        List<Long> userIdList = campingUserRelationList.stream().map(CampingUserRelation::getUserId).collect(Collectors.toList());
        if (!userIdList.contains(userId)) {
            throw new BaseException("没有修改权限");
        }
        // 更新露营记录
        Camping camping = new Camping();
        BeanUtils.copyProperties(updateCampingRecordDto, camping);
        camping.setUpdateBy(userId);
        camping.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(camping);

        // 更新关联人
        List<Long> relationUserIds = updateCampingRecordDto.getRelationUserId();
        relationUserIds.add(userId);
        if (CollectionUtils.isNotEmpty(relationUserIds)) {
            List<CampingUserRelation> campingUserRelations = relationUserIds.stream().map(relationUserId -> {
                CampingUserRelation campingUserRelation = new CampingUserRelation();
                campingUserRelation.setCampingId(camping.getId());
                campingUserRelation.setUserId(relationUserId);
                return campingUserRelation;
            }).collect(Collectors.toList());
            campingUserRelationService.updateBatchById(campingUserRelations);
        }

        // 更新图片
        List<String> images = updateCampingRecordDto.getImages();
        if (CollectionUtils.isNotEmpty(images)) {
            List<CampingImage> campingImages = images.stream().map(image -> {
                CampingImage campingImage = new CampingImage();
                campingImage.setCampingId(camping.getId());
                campingImage.setImageUrl(image);
                return campingImage;
            }).collect(Collectors.toList());
            campingImageService.updateBatchById(campingImages);
        }

        // 更新评价评论
        CampingAddressComment campingAddressComment = new CampingAddressComment();
        campingAddressComment.setScore(updateCampingRecordDto.getScore());
        campingAddressComment.setComment(updateCampingRecordDto.getComment());
        campingAddressComment.setAddressId(updateCampingRecordDto.getAddressId());
        campingAddressComment.setCampingId(camping.getId());
        campingAddressComment.setUpdateBy(userId);
        campingAddressComment.setUpdateTime(LocalDateTime.now());
        LambdaQueryWrapper<CampingAddressComment> campingAddressCommentQuery = new LambdaQueryWrapper<>();
        campingAddressCommentQuery.eq(CampingAddressComment::getAddressId, campingAddressComment.getAddressId());
        campingAddressCommentQuery.eq(CampingAddressComment::getCampingId, campingAddressComment.getCampingId());
        campingAddressCommentService.update(campingAddressComment, campingAddressCommentQuery);
    }

    @Override
    public Page<CampingListVo> getMyCampingRecordList(CampingRecordDto.GetCampingRecordListDto getCampingRecordListDto) {
        Long userId = UserUtil.getCurrentUser().getId();
        getCampingRecordListDto.setUserId(userId);
        Page<CampingListVo> campingListVoPage = baseMapper.myCampingRecordPage(new Page<>(getCampingRecordListDto.getPageNo(), getCampingRecordListDto.getPageSize()), getCampingRecordListDto);
        List<CampingListVo> records = campingListVoPage.getRecords();
        // 自己创建的标识(只能删除自己创建的)
        records.forEach(record -> record.setIsOwn(Objects.equals(record.getCreateBy(), userId)));
        campingListVoPage.setRecords(records);
        return campingListVoPage;
    }

    @Override
    public CampingDetailVo getCampingRecordDetail(CampingRecordDto.GetCampingRecordDetailDto getCampingRecordDetailDto) {
        // 露营记录基础数据
        CampingDetailVo campingDetailVo = new CampingDetailVo();
        Long campingId = getCampingRecordDetailDto.getCampingId();
        Camping camping = baseMapper.selectById(campingId);
        BeanUtils.copyProperties(camping, campingDetailVo);
        campingDetailVo.setVisibleStatusStr(CampingVisibleEnum.of(campingDetailVo.getVisibleStatus()).getName());

        // 获取所有图片
        LambdaQueryWrapper<CampingImage> campingImageQuery = new LambdaQueryWrapper<>();
        campingImageQuery.eq(CampingImage::getCampingId, campingId);
        List<CampingImage> campingImageList = campingImageService.list(campingImageQuery);
        List<CampingImageVo> campingImageVoList = campingImageList.stream().map(campingImage -> {
            CampingImageVo campingImageVo = new CampingImageVo();
            BeanUtils.copyProperties(campingImage, campingImageVo);
            String imageFullUrl = ossService.getImageUrl(campingImage.getImageUrl());
            campingImageVo.setImageFullUrl(imageFullUrl);
            return campingImageVo;
        }).collect(Collectors.toList());
        campingDetailVo.setImages(campingImageVoList);

        // 获取关联的用户
        LambdaQueryWrapper<CampingUserRelation> campingUserRelationQuery = new LambdaQueryWrapper<>();
        campingUserRelationQuery.eq(CampingUserRelation::getCampingId, campingId);
        List<CampingUserRelation> campingUserRelationList = campingUserRelationService.list(campingUserRelationQuery);
        campingDetailVo.setUserRelations(campingUserRelationList);

        // 获取评论评价
        LambdaQueryWrapper<CampingAddressComment> campingAddressCommentQuery = new LambdaQueryWrapper<>();
        campingAddressCommentQuery.eq(CampingAddressComment::getCampingId, campingId);
        CampingAddressComment campingAddressComment = campingAddressCommentService.getOne(campingAddressCommentQuery);
        campingDetailVo.setComment(campingAddressComment.getComment());
        campingDetailVo.setScore(campingAddressComment.getScore());

        // 获取地点名称
        Address address = addressService.getById(camping.getAddressId());
        if (address != null) {
            campingDetailVo.setAddressName(address.getName());
        }

        return campingDetailVo;
    }

    @Override
    public void deleteMyCampingRecord(Long campingId) {
        Long currentUserId = UserUtil.getCurrentUser().getId();
        Camping camping = baseMapper.selectById(campingId);
        if (!Objects.equals(currentUserId, camping.getCreateBy())) {
            throw new BaseException("只有创建人自己可以删除");
        }
        camping.setDeleteFlag(DeleteFlagEnum.DELETED.getCode());
        baseMapper.updateById(camping);
    }
}
