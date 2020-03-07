import request from '@/utils/request'

export function getMenuList(params) {
  return request.get('/system/menus')
}
