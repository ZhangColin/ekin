import request from '@/utils/request'

export function search(baseUrl, params) {
  return request.get(`${baseUrl}/search`, { params })
}

export function getAll(baseUrl) {
  return request.get(baseUrl)
}

export function add(baseUrl, params) {
  return request.post(baseUrl, params)
}

export function edit(baseUrl, id, params) {
  return request.put(`${baseUrl}/${id}`, params)
}

export function remove(baseUrl, id) {
  return request.delete(`${baseUrl}/${id}`)
}
