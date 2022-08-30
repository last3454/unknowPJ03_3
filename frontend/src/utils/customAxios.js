import { axios } from "@bundled-es-modules/axios"
import { store } from "@/store/index.js"

const timeout = 30 * 1000

export const instance = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: timeout,
  headers:{
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*'
  }
})

instance.interceptors.request.use(config => {
  const accessToken = store.getters['accessToken']
  if(accessToken){
    config.headers['authorization'] = accessToken
  }
  console.log(config)
  return config
}, error => {
  instance.defaults.timeout = timeout
  console.log(error)
})

instance.interceptors.response.use(response => {
  instance.defaults.timeout = timeout
  console.log(response.config)
  return response
}, error => {
  instance.defaults.timeout = timeout

  const errorCode = error.response.status
  alert(errorCode)
  return Promise.reject(error);
})

export default instance