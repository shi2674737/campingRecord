import request from '@/utils/axios'

//增
export function addCampingRecord(data) {
  return request({
    url: '/camping/addCampingRecord',
    method: 'post',
    data: data,
  })
}

// 改
export function updateCampingRecord(data) {
  return request({
    url: '/camping/updateCampingRecord',
    method: 'post',
    data: data,
  })
}

// 列表
export function getMyCampingRecordList(params) {
  return request({
    url: '/camping/getMyCampingRecordList',
    method: 'get',
    params: params,
  })
}

// 详情
export function getCampingRecordDetail(params) {
  return request({
    url: '/camping/getCampingRecordDetail',
    method: 'get',
    params: params,
  })
}

// 删
export function deleteMyCampingRecord(campingId) {
  return request({
    url: '/camping/deleteMyCampingRecord',
    method: 'delete',
    params: {"campingId": campingId},
  })
}
