import request from '@/utils/axios'
import {BASE_URL_CONFIG} from '@/utils/axios'


// 通过输入的番名查询
export function getMyCampingRecordList(param, userId) {
  return request({
    url: '/camping/getMyCampingRecordList',
    method: 'get',
    headers: {'userId': userId},
    params: param
  })
}

export function getCartoonRelationDetail(cartoonPartId, userId) {
  return request({
    url: '/cartoon/getCartoonRelationDetail',
    method: 'get',
    headers: {'userId': userId},
    params: {'cartoonPartId': cartoonPartId},
  })
}


// 获取番剧名称select列表
export function getAllCartoonInfos() {
  return request({
    url: '/cartoon/getAllCartoonInfos',
    method: 'get'
  })
}
// 获取分季名称select列表
export function getAllCartoonParts(cartoonInfoId) {
  return request({
    url: '/cartoon/getAllCartoonParts',
    method: 'get',
    params: {'cartoonInfoId': cartoonInfoId},
  })
}

// 新增追番记录
export function addCartoonRecord(data, userId) {
  return request({
    url: '/cartoon/addCartoonRecord',
    method: 'post',
    data: data,
    headers: { 'content-type': 'application/json', 'userId': userId}
  })
}

// 导出追番记录 模拟a标签方式
export function exportCartoonRecords(userId) {
  let a = document.createElement('a')
  console.log(BASE_URL_CONFIG.URL);
  a.href = BASE_URL_CONFIG.URL + "/cartoon/exportCartoonRecords/" + userId
  a.click();
}

// 导入
export function uploadCartoonRecords(data, userId) {
  return request({
    url: '/cartoon/uploadCartoonRecords',
    method: 'post',
    data: data,
    headers: { 'userId': userId}
  })
}
