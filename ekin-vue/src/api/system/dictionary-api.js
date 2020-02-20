import request from '@/utils/request'

export function searchDictionarys(currentPage, pageSize, searchParam) {
  return request({
    url: `/system/dicts?page=${currentPage}&size=${pageSize}&blurry=${searchParam}`,
    method: 'get'
  })
}

export function searchItemsByDictCode(dictCode) {
  return request({
    url: `/system/dicts/${dictCode}/items`,
    method: 'get'
  })
}
