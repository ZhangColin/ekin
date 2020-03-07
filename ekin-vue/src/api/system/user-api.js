import request from '@/utils/request'

export function getUser(id) {
  return request.get(`/system/users/${id}`)
}

export function createUser(params) {
  return request.post('/system/users', params)
}

export function disableUser(id) {
  return request.put(`/system/users/${id}/disable`)
}

export function enableUser(id) {
  return request.put(`/system/users/${id}/enable`)
}

export function resetPassword(id) {
  return request.put(`/system/users/${id}/resetPassword`)
}

export function assignRoles(id, roleIds) {
  return request.put(`/system/users/${id}/assignRoles`, { roleIds })
}

export function assignOrganization(id, organizationId) {
  return request.put(`/system/users/${id}/assignOrganization`, { organizationId })
}
