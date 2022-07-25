import Vue from 'vue'
import axios from 'axios'

export const BASE_URL_CONFIG = {
  URL: 'http://localhost:8081/api/v1',
  // URL: 'http://localhost:8181',
};

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  // 配置请求的根目录
  baseURL: BASE_URL_CONFIG.URL,
  // 超时
  timeout: 10000
})
service.interceptors.request.use(
    req => {
      // 在发送请求前要做的事儿
      req.headers.Authorization = localStorage.getItem('access_token')
      return req
    }
)

// 响应拦截器
service.interceptors.response.use(
    response => {
  let code = response.data.code||200
  let message = response.data.msg||'服务器异常,请查看控制台'
  if(code===200){
    return response.data
  }else if(code===500){
    Vue.prototype.$message.error("服务器异常,请查看控制台")
    return Promise.reject(new Error(message))
  }else if(code===400){
    Vue.prototype.$message.error(response.data.message)
    return Promise.reject(new Error(message))
  }else if(code===401){
    Vue.prototype.$message.error("请先登录")
    return Promise.reject(new Error(message))
  }else{
    Vue.prototype.$message.error(message)
    return Promise.reject('error')
  }
}, (error) => {
  console.log('err'+error)
  Vue.prototype.$message({
    message:error,
    type:'error'
  })
  return Promise.reject(error)
})

export default service