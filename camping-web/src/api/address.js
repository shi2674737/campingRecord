import request from '@/utils/axios'


// 增
export function addAddress(data) {
  return request({
    url: '/address/addAddress',
    method: 'post',
    data: data
  })
}

// 改
export function updateAddress(data) {
  return request({
    url: '/address/updateAddress',
    method: 'post',
    data: data
  })
}

// 列表
export function getAddressList(params) {
  return request({
    url: '/address/getAddressList',
    method: 'get',
    params: params
  })
}
// 详情
export function getAddressDetail(params) {
  return request({
    url: '/address/getAddressDetail',
    method: 'get',
    params: params
  })
}

// 填充下拉菜单
export function getAllAddressList() {
  return request({
    url: '/address/getAllAddressList',
    method: 'get'
  })
}
