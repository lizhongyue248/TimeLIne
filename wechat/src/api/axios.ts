import axios from 'uniapp-axios-adapter'
const request = axios.create({
  baseURL: 'http://127.0.0.1:8080',
  timeout: 10000
})

request.interceptors.request.use((config) => {
  return config
})

request.interceptors.response.use(async (response) => {
  if (response.status >= 200 && response.status < 300) {
    return await Promise.resolve(response.data)
  }
  return await Promise.reject(response)
})

export default request
