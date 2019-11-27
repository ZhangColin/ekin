import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/system/login',
    // url: '/management/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/system/user/info',
    // url: '/management/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/system/logout',
    // url: '/management/user/logout',
    method: 'post'
  })
}

