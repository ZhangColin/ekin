/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout'
import nested from '@/layout/nested'

const goods = {
  path: '/goods',
  component: Layout,
  redirect: '/goods/brands/brandList',
  name: 'Goods',
  meta: {
    title: '商品',
    icon: 'component'
  },
  children: [{
    path: 'productCategories',
    name: 'ProductCategories',
    component: nested,
    redirect: '/goods/productCategories/ProductCategoryList',
    meta: { title: '商品分类', icon: 'nested' },
    children: [{
      path: 'productCategoryList',
      name: 'ProductCategoryList',
      component: () => import('@/views/goods/productCategory/productCategories'),
      meta: { title: '商品分类', breadcrumb: false },
      hidden: false
    }, {
      path: 'productCategoryAdd',
      name: 'ProductCategoryAdd',
      component: () => import('@/views/goods/productCategory/productCategoryAdd'),
      meta: { title: '添加商品分类' },
      hidden: true
    },
    {
      path: 'productCategoryEdit',
      name: 'ProductCategoryEdit',
      component: () => import('@/views/goods/productCategory/productCategoryEdit'),
      meta: { title: '编辑商品分类' },
      hidden: true
    }]
  },
  {
    path: 'attributes',
    name: 'Attributes',
    component: nested,
    redirect: '/goods/attributes/categories',
    meta: { title: '商品类型', icon: 'example' },
    children: [{
      path: 'categories',
      name: 'Categories',
      component: () => import('@/views/goods/productAttribute/productAttributeCategories'),
      meta: { title: '商品类型', breadcrumb: false },
      hidden: false
    },
    {
      path: 'productAttributes',
      name: 'ProductAttributes',
      component: () => import('@/views/goods/productAttribute/productAttributes'),
      meta: { title: '商品属性管理' },
      hidden: true
    }, {
      path: 'productAttributeAdd',
      name: 'ProductAttributeAdd',
      component: () => import('@/views/goods/productAttribute/productAttributeAdd'),
      meta: { title: '添加商品属性' },
      hidden: true
    },
    {
      path: 'productAttributeEdit',
      name: 'ProductAttributeEdit',
      component: () => import('@/views/goods/productAttribute/productAttributeEdit'),
      meta: { title: '编辑商品属性' },
      hidden: true
    }]
  },
  {
    path: 'brands',
    name: 'Brands',
    component: nested,
    redirect: '/goods/brands/brandList',
    meta: { title: '品牌管理', icon: 'nested' },
    children: [{
      path: 'brandList',
      name: 'BrandList',
      component: () => import('@/views/goods/brand/brands'),
      meta: { title: '品牌管理', breadcrumb: false },
      hidden: false
    }, {
      path: 'brandAdd',
      name: 'BrandAdd',
      component: () => import('@/views/goods/brand/brandAdd'),
      meta: { title: '添加品牌' },
      hidden: true
    },
    {
      path: 'brandEdit',
      name: 'BrandEdit',
      component: () => import('@/views/goods/brand/brandEdit'),
      meta: { title: '编辑品牌' },
      hidden: true
    }]
  }]
}

export default goods
