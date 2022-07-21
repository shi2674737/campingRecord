import request from '@/utils/axios'


// 通过输入的番名查询
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}

