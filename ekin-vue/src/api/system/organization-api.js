import request from '@/utils/request'

export function getOrganizationList() {
  return request.get('/system/organizations')
}
