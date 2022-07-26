import request from '@/utils/axios'


export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}

export function addUser(data) {
  return request({
    url: '/user/addUser',
    method: 'post',
    data: data
  })
}
export function updateUser(data) {
  return request({
    url: '/user/updateUser',
    method: 'post',
    data: data
  })
}
export function getUserByPhone(phone) {
  return request({
    url: '/user/getUserByPhone',
    method: 'get',
    params: {'phone':phone}
  })
}
export function getUserDetail(id) {
  return request({
    url: '/user/getUserDetail',
    method: 'get',
    params: {'id': id}
  })
}
export function getFriendList() {
  return request({
    url: '/user/getFriendList',
    method: 'get'
  })
}
export function getFriendPage(params) {
  return request({
    url: '/user/getFriendPage',
    method: 'get',
    params: params,
  })
}