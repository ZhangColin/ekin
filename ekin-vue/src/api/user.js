import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/system/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/system/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    method: 'post'
  })
}
