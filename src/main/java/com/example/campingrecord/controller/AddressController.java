package com.example.campingrecord.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campingrecord.dto.CampingRecordDto;
import com.example.campingrecord.service.CampingService;
import com.example.campingrecord.vo.CampingDetailVo;
import com.example.campingrecord.vo.CampingListVo;
import com.example.campingrecord.vo.result.UResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/camping")
public class AddressController {

    @Autowired
    CampingService campingService;

    @PostMapping("/addCampingRecord")
    public UResult<String> addCampingRecord(@RequestBody CampingRecordDto.AddCampingRecordDto addCampingRecordDto) {
        log.info("addCampingRecord start addCampingRecordDto = {}", addCampingRecordDto);
        campingService.addCampingRecord(addCampingRecordDto);
        log.info("addCampingRecord success.");
        return UResult.ok();
    }

    @PostMapping("/updateCampingRecord")
    public UResult<String> updateCampingRecord(@RequestBody CampingRecordDto.UpdateCampingRecordDto updateCampingRecordDto) {
        log.info("updateCampingRecord start updateCampingRecordDto = {}", updateCampingRecordDto);
        campingService.updateCampingRecord(updateCampingRecordDto);
        log.info("updateCampingRecord success.");
        return UResult.ok();
    }

    @GetMapping("/getMyCampingRecordList")
    public UResult<String> getMyCampingRecordList(CampingRecordDto.GetCampingRecordListDto getCampingRecordListDto) {
        log.info("getMyCampingRecordList start getCampingRecordListDto = {}", getCampingRecordListDto);
        Page<CampingListVo> campingRecordList = campingService.getMyCampingRecordList(getCampingRecordListDto);
        log.info("getMyCampingRecordList success. campingRecordList = {}", campingRecordList);
        return UResult.ok().data(campingRecordList);
    }

    @GetMapping("/getCampingRecordDetail")
    public UResult<String> getCampingRecordDetail(CampingRecordDto.GetCampingRecordDetailDto getCampingRecordDetailDto) {
        log.info("getCampingRecordDetail start getCampingRecordListDto = {}", getCampingRecordDetailDto);
        CampingDetailVo campingRecordDetail = campingService.getCampingRecordDetail(getCampingRecordDetailDto);
        log.info("getCampingRecordDetail success. campingRecordList = {}", campingRecordDetail);
        return UResult.ok().data(campingRecordDetail);
    }

    @DeleteMapping("/deleteMyCampingRecord")
    public UResult<String> deleteMyCampingRecord(Long campingId) {
        log.info("deleteMyCampingRecord start campingId = {}", campingId);
        campingService.deleteMyCampingRecord(campingId);
        log.info("deleteMyCampingRecord success.");
        return UResult.ok();
    }
}
