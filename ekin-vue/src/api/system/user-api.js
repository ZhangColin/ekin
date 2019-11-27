import request from '@/utils/request'
import qs from 'qs'

export function searchUsers(currentPage, pageSize, params) {
  return request({
    url: `/system/users/search/${currentPage}/${pageSize}`,
    method: 'post',
    data: params
  })
}

export function getUser(id) {
  return request({
    url: `/system/users/${id}`,
    method: 'get'
  })
}

export function addUser(params) {
  return request({
    url: '/system/users',
    method: 'post',
    data: params
  })
}

export function editUser(id, params) {
  return request({
    url: `/system/users/${id}`,
    method: 'put',
    data: params
  })
}

export function removeUser(id) {
  return request({
    url: `/system/users/${id}`,
    method: 'delete'
  })
}

export function frozenUser(id) {
  return request({
    url: `/system/users/${id}/frozen`,
    method: 'put'
  })
}

export function unFrozenUser(id) {
  return request({
    url: `/system/users/${id}/unFrozen`,
    method: 'put'
  })
}

export function changePassword(id, password) {
  return request({
    url: `/system/users/${id}/password`,
    method: 'put',
    headers: { 'content-type': 'application/x-www-form-urlencoded' },
    data: qs.stringify({ password })
  })
}

