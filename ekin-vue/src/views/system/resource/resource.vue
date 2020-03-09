<template>
  <div class="app-container">

    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.blurry" class="filter-item" placeholder="请输入名称、权限编码、Url查询" />
      </el-col>
      <el-col :span="6">
        <el-select v-model="queryParam.categoryId" placeholder="请选择资源分类" clearable style="width: 100%">
          <el-option
            v-for="resourceCategory in resourceCategories"
            :key="resourceCategory.id"
            :label="resourceCategory.name"
            :value="resourceCategory.id"
          />
        </el-select>
      </el-col>
      <el-col :span="6">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="6" align="right">
        <el-button class="filter-item" @click="redirectResourceCategory">资源分类</el-button>
      </el-col>
    </el-row>
    <el-table
      v-loading="loading"
      :data="dataSource"
      row-key="id"
      class="table-container"
      element-loading-text="加载中..."
      stripe
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="编号" prop="id" />
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column align="center" label="权限编码" prop="code" />
      <el-table-column align="center" label="Url" prop="url" />
      <el-table-column align="center" label="分类" prop="category.name" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleDelete(scope.$index, scope.row)">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page.sync="page.currentPage"
      :page-sizes="[5, 10, 20]"
      :page-size="page.pageSize"
      :total="page.total"
      class="pagination-container"
      background
      align="right"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <el-drawer
      :title="drawerTitle"
      :visible.sync="drawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="entityDataForm" :model="entityData" :rules="rules" label-width="120px">
          <el-form-item label="资源名称" prop="name">
            <el-input v-model="entityData.name" />
          </el-form-item>
          <el-form-item label="权限编码" prop="code">
            <el-input v-model="entityData.code" />
          </el-form-item>
          <el-form-item label="url" prop="url">
            <el-input v-model="entityData.url" />
          </el-form-item>
          <el-form-item label="资源分类" prop="categoryId">
            <el-select v-model="entityData.categoryId" placeholder="请选择资源分类" style="width: 100%">
              <el-option
                v-for="resourceCategory in resourceCategories"
                :key="resourceCategory.id"
                :label="resourceCategory.name"
                :value="resourceCategory.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="资源描述" prop="description">
            <el-input v-model="entityData.description" type="textarea" />
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input-number v-model="entityData.sort" />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="drawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { PaginationMixin } from '@/mixins/pagination-mixin'
import { CudMixin } from '@/mixins/cud-mixin'
import { getAllResourceCategories } from '@/api/system/resource-api'

export default {
  name: 'Resource',
  mixins: [PaginationMixin, CudMixin],
  data() {
    return {
      apiBaseUrl: '/system/resources',

      defaultData: {
        name: '',
        code: '',
        url: '',
        categoryId: '1',
        description: '',
        sort: 10
      },
      title: '资源',
      rules: {
        name: [
          { required: true, message: '请输入资源名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入权限编码', trigger: 'blur' }
        ]
      },

      resourceCategories: []
    }
  },
  created() {
    getAllResourceCategories().then(response => {
      this.resourceCategories = response.data
    })
  },
  methods: {
    dataProcessBeforeEdit(data) {
      data.categoryId = data.category.id
    },
    redirectResourceCategory() {
      this.$router.push({ path: '/system/resource/resource-category' })
    }
  }
}
</script>

