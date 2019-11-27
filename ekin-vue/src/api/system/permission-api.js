import request from '@/utils/request'

export function getPermissionList() {
  return request({
    url: '/system/permissions',
    method: 'get'
  })
}

export function getPermissionTree() {
  return request({
    url: '/system/permissions/tree',
    method: 'get'
  })
}

export function addPermission(params) {
  return request({
    url: '/system/permissions',
    method: 'post',
    data: params
  })
}

export function editPermission(id, params) {
  return request({
    url: `/system/permissions/${id}`,
    method: 'put',
    data: params
  })
}

export function removePermission(id) {
  return request({
    url: `/system/permissions/${id}`,
    method: 'delete'
  })
}
