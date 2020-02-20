import request from '@/utils/request'

export function getPermissionList(params) {
  return request({
    url: '/system/permissions',
    method: 'get',
    params: params
  })
}

export function getPermissionTree() {
  return request({
    url: '/system/permissions/tree',
    method: 'get'
  })
}
