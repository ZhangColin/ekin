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
    path: 'departments',
    name: 'departments',
    component: () => import('@/views/system/department/departments'),
    meta: { title: '部门管理', icon: 'nested', permissions: ['system:department'] }
  }, {
    path: 'users',
    name: 'users',
    component: nested,
    redirect: '/system/users/userList',
    meta: { title: '用户管理', icon: 'nested', permissions: ['system:user'] },
    children: [{
      path: 'userList',
      name: 'userList',
      component: () => import('@/views/system/user/users'),
      meta: { title: '用户管理', breadcrumb: false },
      hidden: false
    }, {
      path: 'userAdd',
      name: 'userAdd',
      component: () => import('@/views/system/user/userAdd'),
      meta: { title: '添加用户' },
      hidden: true
    }, {
      path: 'userEdit',
      name: 'userEdit',
      component: () => import('@/views/system/user/userEdit'),
      meta: { title: '编辑用户' },
      hidden: true
    }]
  }, {
    path: 'roles',
    name: 'roles',
    component: nested,
    redirect: '/system/roles/roleList',
    meta: { title: '角色管理', icon: 'nested', permissions: ['system:role'] },
    children: [{
      path: 'roleList',
      name: 'roleList',
      component: () => import('@/views/system/role/roles'),
      meta: { title: '角色列表', breadcrumb: false },
      hidden: false
    }, {
      path: 'permissionAssign',
      name: 'permissionAssign',
      component: () => import('@/views/system/role/permissionAssign'),
      meta: { title: '权限分配' },
      hidden: true
    }]
  }, {
    path: 'permissions',
    name: 'permissions',
    component: () => import('@/views/system/permission/permissions'),
    meta: { title: '权限管理', icon: 'nested', permissions: ['system:permission'] }
  }]
}

export default system
