import request from '@/utils/request'

export function getAllRoles() {
  return request.get('/system/roles')
}

export function assignMenus(id, menuIds) {
  return request.put(`/system/roles/${id}/menus`, { menuIds })
}

export function assignResources(id, resourceIds) {
  return request.put(`/system/roles/${id}/resources`, { resourceIds })
}

export function enableRole(id) {
  return request.put(`/system/roles/${id}/enable`)
}

export function disableRole(id) {
  return request.put(`/system/roles/${id}/disable`)
}
