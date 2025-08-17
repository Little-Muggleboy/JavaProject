import axios from 'axios'
import {message} from "ant-design-vue";

// 区分开发和生产环境
//const DEV_BASE_URL = "http://localhost:8145";
const PROD_BASE_URL = "http://118.24.80.45";
// 创建 Axios 实例
const myAxios = axios.create({
  baseURL: PROD_BASE_URL,
  timeout: 60000,
  withCredentials: true,
});

// 全局请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 全局响应拦截器
axios.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  const { data } = response
  // 未登录
  if (data.code === 40100) {
    // 不是获取用户信息的请求，并且用户目前不是已经在用户登录页面，则跳转到登录页面
    if (
      !response.request.responseURL.includes('user/get/login') &&
      !window.location.pathname.includes('/user/login')
    ) {
      message.warning('请先登录')
      window.location.href = `/user/login?redirect=${window.location.href}`
    }
  }
  return response;
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error);
});

export default myAxios
