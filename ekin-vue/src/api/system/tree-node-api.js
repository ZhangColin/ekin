import request from '@/utils/request'

export function getTreeByClassName(className) {
  return request({
    url: `/system/tree/${className}`,
    method: 'get'
  })
}
