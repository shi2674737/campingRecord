package com.example.campingrecord.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.campingrecord.dto.CampingRecordDto;
import com.example.campingrecord.entity.Camping;
import com.example.campingrecord.vo.CampingListVo;
import org.apache.ibatis.annotations.Param;

public interface CampingMapper extends BaseMapper<Camping> {

    Page<CampingListVo> myCampingRecordPage(Page<CampingListVo> page, @Param("item") CampingRecordDto.GetCampingRecordListDto item);
}