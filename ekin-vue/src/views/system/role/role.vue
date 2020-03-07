<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.name" class="filter-item" placeholder="请输入角色名称查询" />
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="handleAdd">新增</el-button>
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
      <el-table-column align="center" label="角色名称" prop="name" />
      <el-table-column align="center" label="描述" prop="description" />
      <el-table-column align="center" label="排序" prop="sort" />
      <el-table-column align="center" label="是否启用" prop="status">
        <template slot-scope="scope">
          <el-popconfirm
            :title="`确定要${(scope.row.status===1?'启用':'禁用')}[${scope.row.name}]`"
            confirm-button-text="确定"
            cancel-button-text="取消"
            @onConfirm="handleStatusConfirmChange(scope.$index, scope.row)"
            @onCancel="handleStatusCancelChange(scope.$index, scope.row)"
          >
            <el-switch
              slot="reference"
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
            />
          </el-popconfirm>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleMenuAssign(scope.$index, scope.row)">分配菜单</el-dropdown-item>
              <el-dropdown-item @click.native="handleResourceAssign(scope.$index, scope.row)">分配资源</el-dropdown-item>
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
          <el-form-item label="角色名称" prop="name">
            <el-input v-model="entityData.name" />
          </el-form-item>
          <el-form-item label="角色描述" prop="description">
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
    <el-drawer
      title="分配菜单"
      :visible.sync="menuDrawerVisible"
      :wrapper-closable="false"
      size="50%"
      @opened="handleMenuDrawerOpened"
    >
      <div class="drawer__content">
        <el-tree
          ref="menuTree"
          style="flex: 1"
          :data="menus"
          show-checkbox
          default-expand-all
          node-key="id"
          highlight-current
          :props="menuTreeProps"
          :check-strictly="true"
        />
        <div class="drawer__footer">
          <el-button @click="menuDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleMenuConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      title="分配资源"
      :visible.sync="resourceDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-checkbox-group
          v-model="entityData.resourceIds"
          style="flex: 1"
        >
          <template v-for="category in resources">
            <el-divider :key="category.category" content-position="left">{{ category.category }}</el-divider>
            <el-checkbox v-for="resource in category.resources" :key="resource.id" :label="resource.id">{{ resource.name }}</el-checkbox>
          </template>

        </el-checkbox-group>
        <div class="drawer__footer">
          <el-button @click="resourceDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleResourceConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { PaginationMixin } from '@/mixins/pagination-mixin'
import { CudMixin } from '@/mixins/cud-mixin'
import { enableRole, disableRole, assignMenus, assignResources }
  from '@/api/system/role-api'
import { getMenuList } from '@/api/system/menu-api'
import { getAllResources } from '@/api/system/resource-api'

export default {
  name: 'Role',
  mixins: [PaginationMixin, CudMixin],
  data() {
    return {
      apiBaseUrl: '/system/roles',

      defaultData: {
        name: '',
        description: '',
        sort: 10
      },
      title: '角色',
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ]
      },

      menuDrawerVisible: false,
      resourceDrawerVisible: false,
      menus: [],
      menuTreeProps: {
        children: 'children',
        label: 'title'
      },
      resources: []
    }
  },
  created() {
    getMenuList().then(response => {
      this.menus = response.data
    })
    getAllResources().then(response => {
      let resourceCategory
      for (const index in response.data) {
        const resource = response.data[index]
        if (!(resourceCategory && resourceCategory.category === resource.category.name)) {
          resourceCategory = { category: resource.category.name, resources: [] }
          this.resources.push(resourceCategory)
        }
        resourceCategory.resources.push(resource)
      }
    })
  },
  methods: {
    handleStatusConfirmChange(index, row) {
      if (row.status === 1) {
        enableRole(row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '启用成功'
          })
        }).catch(() => {
          row.status = 0
        })
      } else {
        disableRole(row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '禁用成功'
          })
        }).catch(() => {
          row.status = 1
        })
      }
    },
    handleStatusCancelChange(index, row) {
      row.status = row.status === 1 ? 0 : 1
    },
    handleMenuAssign(index, row) {
      this.entityData = Object.assign({}, row)

      this.menuDrawerVisible = true
    },
    handleMenuDrawerOpened() {
      this.$refs.menuTree.setCheckedKeys(this.entityData.menuIds)
    },
    handleMenuConfirm() {
      assignMenus(this.entityData.id, this.$refs.menuTree.getCheckedKeys()).then(() => {
        this.$notify.success({
          title: '成功',
          message: '菜单分配成功'
        })
        this.menuDrawerVisible = false
        this.fetchData()
      })
    },
    handleResourceAssign(index, row) {
      this.entityData = Object.assign({}, row)

      this.resourceDrawerVisible = true
    },
    handleResourceConfirm() {
      assignResources(this.entityData.id, this.entityData.resourceIds).then(() => {
        this.$notify.success({
          title: '成功',
          message: '资源分配成功'
        })
        this.resourceDrawerVisible = false
        this.fetchData()
      })
    }
  }
}
</script>

