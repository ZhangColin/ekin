/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/views/layout/Layout'

const base = {
  path: '/base',
  component: Layout,
  redirect: '/base/continent',
  name: 'Base',
  meta: {
    title: '基础信息',
    icon: 'clipboard'
  },
  children: [
    {
      path: 'continent',
      name: 'Continent',
      component: () => import('@/views/base/continent'),
      meta: { title: '大洲' }
    },
    {
      path: 'country',
      name: 'Country',
      component: () => import('@/views/base/country'),
      meta: { title: '国家' }
    },
    {
      path: 'city',
      name: 'City',
      component: () => import('@/views/base/city'),
      meta: { title: '城市' }
    },
    {
      path: 'airport',
      name: 'Airport',
      component: () => import('@/views/base/airport'),
      meta: { title: '机场' }
    },
    {
      path: 'vechile',
      name: 'Vechile',
      component: () => import('@/views/base/vechile'),
      meta: { title: '车型' }
    }
  ]
}

export default base
