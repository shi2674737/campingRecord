package com.example.campingrecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.campingrecord.dto.CampingRecordDto;
import com.example.campingrecord.entity.Camping;
import com.example.campingrecord.vo.CampingDetailVo;
import com.example.campingrecord.vo.CampingListVo;

public interface CampingService extends IService<Camping> {
    void addCampingRecord(CampingRecordDto.AddCampingRecordDto addCampingRecordDto);

    void updateCampingRecord(CampingRecordDto.UpdateCampingRecordDto updateCampingRecordDto);

    Page<CampingListVo> getMyCampingRecordList(CampingRecordDto.GetCampingRecordListDto getCampingRecordListDto);

    CampingDetailVo getCampingRecordDetail(CampingRecordDto.GetCampingRecordDetailDto getCampingRecordDetailDto);

    void deleteMyCampingRecord(Long campingId);
}
