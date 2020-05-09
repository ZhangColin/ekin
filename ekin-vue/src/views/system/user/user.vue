<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.username" class="filter-item" placeholder="请输入账号查询" />
      </el-col>
      <el-col :span="6">
        <el-select v-model="queryParam.status" placeholder="请选择用户状态" clearable style="width: 100%">
          <el-option label="正常" value="1" />
          <el-option label="冻结" value="2" />
        </el-select>
      </el-col>
      <template v-if="toggleSearchStatus">
        <el-col :span="6">
          <el-input v-model="queryParam.phone" class="filter-item" placeholder="请输入手机号查询" />
        </el-col>
        <el-col :span="6">
          <el-input v-model="queryParam.email" class="filter-item" placeholder="请输入邮箱查询" />
        </el-col>
        <el-col :span="6">
          <el-select
            v-model="queryParam.gender"
            class="filter-item"
            placeholder="请选择用户性别"
            clearable
            style="width: 100%"
          >
            <el-option label="男" value="1" />
            <el-option label="女" value="2" />
          </el-select>
        </el-col>
      </template>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="queryParam={}">重置</el-button>
        <el-button class="filter-item" type="text" @click="toggleSearchStatus=!toggleSearchStatus">
          {{ toggleSearchStatus?'收起':'展开' }}
          <i :class="toggleSearchStatus?'el-icon-arrow-up':'el-icon-arrow-down'" class="el-icon--right" />
        </el-button>
      </el-col>
    </el-row>
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
    >
      <el-table-column align="center" label="用户ID" prop="id" width="180" />
      <el-table-column align="center" label="用户账号" prop="username" />
      <el-table-column align="center" label="真实姓名" prop="realName" />
      <el-table-column align="center" label="手机" prop="phone" />
      <el-table-column align="center" label="邮箱" prop="email" />
      <el-table-column align="center" label="性别" prop="gender">
        <template slot-scope="scope">
          <span>{{ scope.row.gender===1?'男':'女' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-popconfirm
            :title="`确定要${(scope.row.status===1?'启用':'禁用')}[${scope.row.username}]`"
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
          <el-dropdown split-button @click="handleViewDetail(scope.$index, scope.row)">
            详情
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleRoleAssign(scope.$index, scope.row)">分配角色
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleOrganizationAssign(scope.$index, scope.row)">切换组织
              </el-dropdown-item>
              <el-dropdown-item @click.native="handleResetPassword(scope.$index, scope.row)">密码重置
              </el-dropdown-item>
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
      title="创建用户"
      :visible.sync="drawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="userForm" :model="user" label-width="120px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username" />
          </el-form-item>
          <el-form-item label="手机" prop="phone">
            <el-input v-model="user.phone" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="user.email" />
          </el-form-item>
          <el-form-item label="真实姓名" prop="realName">
            <el-input v-model="user.realName" />
          </el-form-item>
          <el-form-item label="组织">
            <el-cascader
              v-model="user.organizationId"
              :options="organizations"
              style="width: 100%"
              clearable
              filterable
              :props="organizationCascaderProps"
            />
          </el-form-item>
          <el-form-item label="角色">
            <el-checkbox-group v-model="user.roleIds">
              <el-checkbox v-for="role in roles" :key="role.id" :label="role.id">{{ role.name }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="drawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      title="分配角色"
      :visible.sync="roleDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="assignRoleForm" :model="user" label-width="120px">
          <el-form-item label="角色">
            <el-checkbox-group v-model="user.roleIds">
              <el-checkbox v-for="role in roles" :key="role.id" :label="role.id">{{ role.name }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="roleDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleRoleConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      title="切换组织"
      :visible.sync="organizationDrawerVisible"
      :wrapper-closable="false"
      size="50%"
    >
      <div class="drawer__content">
        <el-form ref="assignOrganizationForm" label-width="120px">
          <el-form-item label="组织">
            <el-cascader
              v-model="user.organizationId"
              :options="organizations"
              style="width: 100%"
              clearable
              filterable
              :props="organizationCascaderProps"
            />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="organizationDrawerVisible=false">取消</el-button>
          <el-button type="primary" @click="handleOrganizationConfirm()">确定</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { PaginationMixin } from '@/mixins/pagination-mixin'
import { getUser, createUser, disableUser, enableUser, resetPassword, assignRoles, assignOrganization }
  from '@/api/system/user-api'
import { getAllRoles } from '@/api/system/role-api'
import { getOrganizationList } from '@/api/system/organization-api'

const defaultUser = {
  username: '',
  phone: '',
  email: '',
  realName: '',
  organizationId: 0,
  roleIds: []
}

export default {
  name: 'User',
  mixins: [PaginationMixin],
  data() {
    return {
      apiBaseUrl: '/system/users',
      user: {},
      drawerVisible: false,
      roleDrawerVisible: false,
      roles: [],
      organizationDrawerVisible: false,
      organizations: [],
      organizationCascaderProps: {
        emitPath: false,
        expandTrigger: 'hover',
        checkStrictly: true,
        label: 'name',
        value: 'id'
      }
    }
  },
  created() {
    getAllRoles().then(response => {
      this.roles = response.data
    })
    getOrganizationList().then(response => {
      this.organizations = response.data
    })
  },
  methods: {
    handleAdd() {
      this.user = Object.assign({}, defaultUser)
      this.drawerVisible = true
    },
    handleConfirm() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          createUser(this.user).then(() => {
            this.$notify.success({
              title: '成功',
              message: '添加成功'
            })
            this.drawerVisible = false
            this.fetchData()
          })
        }
      })
    },
    handleStatusConfirmChange(index, row) {
      if (row.status === 1) {
        enableUser(row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '启用成功'
          })
        }).catch(() => {
          row.status = 0
        })
      } else {
        disableUser(row.id).then(() => {
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
    handleResetPassword(index, row) {
      this.$confirm('是否要重置该用户密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        resetPassword(row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '密码重置成功'
          })
          this.fetchData()
        })
      })
    },
    handleRoleAssign(index, row) {
      getUser(row.id).then(response => {
        response.data.roleIds = []
        for (const index in response.data.roles) {
          response.data.roleIds.push(response.data.roles[index].id)
        }

        this.user = response.data
        this.roleDrawerVisible = true
      })
    },
    handleRoleConfirm() {
      assignRoles(this.user.id, this.user.roleIds).then(() => {
        this.$notify.success({
          title: '成功',
          message: '角色分配成功'
        })
        this.roleDrawerVisible = false
      })
    },
    handleOrganizationAssign(index, row) {
      getUser(row.id).then(response => {
        this.user = response.data
        this.user.organizationId = this.user.organization.id
        this.organizationDrawerVisible = true
      })
    },
    handleOrganizationConfirm() {
      assignOrganization(this.user.id, this.user.organizationId).then(() => {
        this.$notify.success({
          title: '成功',
          message: '组织切换成功'
        })
        this.organizationDrawerVisible = false
      })
    },
    handleViewDetail() {

    }
  }
}
</script>
