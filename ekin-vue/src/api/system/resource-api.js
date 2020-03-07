import request from '@/utils/request'

export function getAllResources() {
  return request.get(`/system/resources`)
}

export function getAllResourceCategoryCategories() {
  return request.get(`/system/resources/categories`)
}
