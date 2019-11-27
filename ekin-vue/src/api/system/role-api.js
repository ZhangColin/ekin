import request from '@/utils/request'

export function searchRoles(currentPage, pageSize, params) {
  // return request({
  //   url: `/system/roles/search/${currentPage}/${pageSize}`,
  //   method: 'get',
  //   params: params
  // })
  return request.get(`/system/roles/search/${currentPage}/${pageSize}`, { params })
}

export function getAllRoles() {
  return request.get('/system/roles')
}

export function getRole(id) {
  return request.get(`/system/roles/${id}`)
}

export function addRole(params) {
  return request({
    url: '/system/roles',
    method: 'post',
    data: params
  })
}

export function editRole(id, params) {
  return request({
    url: `/system/roles/${id}`,
    method: 'put',
    data: params
  })
}

export function removeRole(id) {
  return request({
    url: `/system/roles/${id}`,
    method: 'delete'
  })
}

export function getRolePermissions(id) {
  return request.get(`/system/roles/${id}/permissions`)
}

export function assignPermissions(id, permissionIds) {
  return request({
    url: `/system/roles/${id}/permissions`,
    method: 'post',
    data: permissionIds
  })
}
