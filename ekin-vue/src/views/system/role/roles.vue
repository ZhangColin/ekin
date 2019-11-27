<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="searchParams.name" class="filter-item" placeholder="请输入角色名称查询" />
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="handleAdd">新增</el-button>
      </el-col>
    </el-row>
    <el-table
      v-loading="listLoading"
      :data="list"
      row-key="id"
      class="table-container"
      element-loading-text="加载中..."
      stripe
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="角色ID" prop="id" />
      <el-table-column align="center" label="角色名称" prop="name" />
      <el-table-column align="center" label="角色编码" prop="code" />
      <el-table-column align="center" label="描述" prop="description" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handlePermissionAssign(scope.$index, scope.row)">授权</el-dropdown-item>
              <el-dropdown-item @click.native="handleDelete(scope.$index, scope.row)">删除</el-dropdown-item>
              <el-dropdown-item v-if="scope.row.status===1" @click.native="handleFrozen(scope.$index, scope.row)">冻结</el-dropdown-item>
              <el-dropdown-item v-else @click.native="handleUnFrozen(scope.$index, scope.row)">解冻</el-dropdown-item>
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
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
    >
      <el-form ref="roleForm" :model="role" :rules="rules" label-width="120px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="role.name" />
        </el-form-item>
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="role.code" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="role.description" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleConfirm()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { searchRoles, addRole, editRole, removeRole } from '@/api/system/role-api'

const defaultRole = {
  name: '',
  code: '',
  description: ''
}

export default {
  name: 'Role',
  data() {
    return {
      list: null,
      listLoading: true,
      searchParams: {
        name: ''
      },
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      },
      dialogVisible: false,
      dialogTitle: '',
      role: Object.assign({}, defaultRole),
      rules: {
        name: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      searchRoles(this.page.currentPage, this.page.pageSize, this.searchParams).then(response => {
        this.list = response.data.rows
        this.page.total = response.data.total
        this.listLoading = false
      })
    },
    handleSearch() {
      this.page.currentPage = 1
      this.fetchData()
    },
    handleSizeChange(pageSize) {
      this.page.currentPage = 1
      this.page.pageSize = pageSize
      this.fetchData()
    },
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage
      this.fetchData()
    },
    handleAdd() {
      this.role = Object.assign({}, defaultRole)
      this.dialogTitle = '添加角色'
      this.dialogVisible = true
    },
    handleEdit(index, row) {
      this.role = row
      this.dialogTitle = '编辑角色'
      this.dialogVisible = true
    },
    handleDelete(index, row) {
      this.$confirm('是否要删除该角色', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeRole(row.id).then(response => {
          this.$message({
            message: '删除成功',
            type: 'success',
            duration: 1000
          })
          this.fetchData()
        })
      })
    },
    handleConfirm() {
      this.$refs['roleForm'].validate((valid) => {
        if (valid) {
          if (this.dialogTitle === '添加角色') {
            addRole(this.role).then(response => {
              this.$message({
                message: '添加成功',
                type: 'success',
                duration: 1000
              })
              this.dialogVisible = false
              this.fetchData()
            })
          } else {
            editRole(this.role.id, this.role).then(response => {
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 1000
              })
              this.dialogVisible = false
              this.fetchData()
            })
          }
        }
      })
    },
    handlePermissionAssign(index, row) {
      this.$router.push({ path: '/system/roles/permissionAssign', query: { id: row.id }})
    }
  }
}
</script>
