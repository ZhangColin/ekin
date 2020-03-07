<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" class="filter-item" @click="handleAdd()">新增</el-button>
    </div>
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
      :default-expand-all="true"
    >
      <el-table-column align="left" label="名称" prop="name" />
      <el-table-column align="left" label="编号" prop="id" />
      <el-table-column align="left" label="描述" prop="description" />
      <el-table-column align="left" label="排序" prop="sort" />
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
    <el-drawer
      :title="drawerTitle"
      :visible.sync="drawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="entityDataForm" :model="entityData" :rules="rules" label-width="120px">
          <el-form-item label="名称" prop="title">
            <el-input v-model="entityData.name" placeholder="请输入组织名称" />
          </el-form-item>
          <el-form-item label="上级组织">
            <el-cascader
              v-model="entityData.parentId"
              :options="dataSource"
              style="width: 100%"
              clearable
              filterable
              :props="organizationCascaderProps"
            />
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="entityData.description" type="textarea" />
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input-number v-model="entityData.sort" placeholder="请输入组织排序" />
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
import { ListMixin } from '@/mixins/list-mixin'
import { CudMixin } from '@/mixins/cud-mixin'

export default {
  name: 'Organization',
  mixins: [ListMixin, CudMixin],
  data() {
    return {
      useSearch: false,
      apiBaseUrl: '/system/organizations',
      organizationCascaderProps: {
        emitPath: false,
        expandTrigger: 'hover',
        checkStrictly: true,
        label: 'name',
        value: 'id'
      },

      defaultData: {
        name: '',
        parentId: '0',
        description: '',
        sort: 10
      },
      title: '组织',
      rules: {
        name: [
          { required: true, message: '请输入组织名称', trigger: 'blur' }
        ]
      }
    }
  }
}
</script>
