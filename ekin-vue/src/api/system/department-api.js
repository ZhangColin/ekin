import request from '@/utils/request'

export function getDepartmentList() {
  return request({
    url: '/system/departments',
    method: 'get'
  })
}

export function getDepartmentTree() {
  return request({
    url: '/system/departments/tree',
    method: 'get'
  })
}

export function addDepartment(params) {
  return request({
    url: '/system/departments',
    method: 'post',
    data: params
  })
}

export function editDepartment(id, params) {
  return request({
    url: `/system/departments/${id}`,
    method: 'put',
    data: params
  })
}

export function removeDepartment(id) {
  return request({
    url: `/system/departments/${id}`,
    method: 'delete'
  })
}
