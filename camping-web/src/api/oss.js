import request from '@/utils/axios'


// 导入
export function uploadImage(data) {
  return request({
    url: '/oss/uploadImage',
    method: 'post',
    data: data
  })
}
