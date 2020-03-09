/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'
import nested from '@/layout/nested'

const system = {
  path: '/system',
  component: Layout,
  redirect: '/system/user',
  name: 'system',
  meta: { title: '系统管理', icon: 'component' },
  children: [{
    path: 'organization',
    name: 'organization',
    component: () => import('@/views/system/organization/organization'),
    meta: { title: '组织管理', icon: 'nested' }
  }, {
    path: 'user',
    name: 'user',
    component: () => import('@/views/system/user/user'),
    meta: { title: '用户管理', icon: 'nested' }
  }, {
    path: 'role',
    name: 'role',
    component: () => import('@/views/system/role/role'),
    meta: { title: '角色管理', icon: 'nested' }
  }, {
    path: 'resource',
    name: 'resource',
    component: nested,
    redirect: '/system/resource/resource-list',
    meta: { title: '资源管理', icon: 'nested' },
    children: [{
      path: 'resource-list',
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
    meta: { title: '菜单管理', icon: 'nested' }
  }, {
    path: 'dictionary',
    name: 'dictionary',
    component: nested,
    redirect: '/system/dictionary/dictionary-list',
    meta: { title: '字典管理', icon: 'nested' },
    children: [{
      path: 'dictionary-list',
      component: () => import('@/views/system/dictionary/dictionary'),
      meta: { title: '字典管理', breadcrumb: false },
      hidden: false
    }, {
      path: 'dictionary-item',
      name: 'dictionary-item',
      component: () => import('@/views/system/dictionary/dictionary-item'),
      meta: { title: '字典项' },
      hidden: true
    }]
  }]
}

export default system
