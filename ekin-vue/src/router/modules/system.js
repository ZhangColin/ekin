/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'
import nested from '@/layout/nested'

const system = {
  path: '/system',
  component: Layout,
  redirect: '/system/departments',
  name: 'system',
  meta: {
    title: '系统管理',
    icon: 'component',
    permissions: ['system']
  },
  children: [{
    path: 'organization',
    name: 'organization',
    component: () => import('@/views/system/organization/organization'),
    meta: { title: '组织管理', icon: 'nested', permissions: ['system:permission'] }
  }, {
    path: 'user',
    name: 'user',
    component: () => import('@/views/system/user/user'),
    meta: { title: '用户管理', icon: 'nested', permissions: ['system:user'] }
  }, {
    path: 'role',
    name: 'role',
    component: () => import('@/views/system/role/role'),
    meta: { title: '角色管理', icon: 'nested', permissions: ['system:permission'] }
  }, {
    path: 'resource',
    name: 'resource',
    component: nested,
    redirect: '/system/resource/resource',
    meta: { title: '资源管理', icon: 'nested', permissions: ['system:role'] },
    children: [{
      path: 'resource-list',
      name: 'resource-list',
      component: () => import('@/views/system/resource/resource'),
      meta: { title: '资源管理', breadcrumb: false },
      hidden: false
    }, {
      path: 'resource-category',
      name: 'resource-category',
      component: () => import('@/views/system/resource/resource-category'),
      meta: { title: '资源分类' },
      hidden: true
    }]
  }, {
    path: 'menu',
    name: 'menu',
    component: () => import('@/views/system/menu/menu'),
    meta: { title: '菜单管理', icon: 'nested', permissions: ['system:permission'] }
  }, {
    path: 'dictionaries',
    name: 'dictionaries',
    component: nested,
    redirect: '/system/dictionaries/dictionaryList',
    meta: { title: '字典管理', icon: 'nested', permission: ['system:dictionary'] },
    children: [{
      path: 'dictionaryList',
      name: 'dictionaryList',
      component: () => import('@/views/system/dictionary/dictionaries'),
      meta: { title: '字典管理', breadcrumb: false },
      hidden: false
    }, {
      path: 'dictionaryItems',
      name: 'dictionaryItems',
      component: () => import('@/views/system/dictionary/dictionaryItems'),
      meta: { title: '字典项查看' },
      hidden: true
    }]
  }]
}

export default system
