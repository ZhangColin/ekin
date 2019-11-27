<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" class="filter-item" @click="handleAdd()">新增</el-button>
    </div>
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
      <el-table-column align="left" label="名称" prop="name" />
      <el-table-column align="left" label="编码" prop="code" />
      <el-table-column align="left" label="描述" prop="description" />
      <el-table-column align="left" label="排序" prop="sort" />
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="scope">
          <el-button
            @click="handleEdit(scope.$index, scope.row)"
          >编辑
          </el-button>
          <el-button
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
    >
      <el-form ref="permissionForm" :model="permission" :rules="rules" label-width="120px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="permission.name" placeholder="请输权限名称" />
        </el-form-item>
        <el-form-item label="上级权限">
          <el-cascader
            v-model="selectPermissions"
            style="width: 100%"
            placeholder="请选择父级菜单"
            expand-trigger="hover"
            :options="permissionOptions"
            clearable
            filterable
            change-on-select
          />
        </el-form-item>
        <el-form-item label="编码" prop="code">
          <el-input v-model="permission.code" placeholder="请输入权限编码" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="permission.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="permission.sort" placeholder="请输入权限排序" />
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
import { getPermissionList, addPermission, editPermission, removePermission } from '@/api/system/permission-api'

const defaultPermission = {
  name: '',
  parentId: 0,
  code: '',
  description: '',
  sort: 0
}

export default {
  name: 'Permission',
  data() {
    return {
      permissionOptions: [],
      selectPermissions: [],
      list: null,
      listLoading: true,
      dialogVisible: false,
      dialogTitle: '',
      permission: Object.assign({}, defaultPermission),
      rules: {
        name: [
          { required: true, message: '请输入权限名称', trigger: 'blur' }
        ]
      },
      permissionPaths: {}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getPermissionList().then(response => {
        this.list = response.data
        this.listLoading = false
        this.fetchPermissionPaths(this.list, [])
      })
    },
    fetchPermissionPaths(permissions, parentPath) {
      for (let i = 0; i < permissions.length; i++) {
        this.permissionPaths[permissions[i].id] = Object.assign([], parentPath)
        const newPath = Object.assign([], parentPath)
        newPath.push(permissions[i].id)
        this.fetchPermissionPaths(permissions[i].children, newPath)
      }
    },
    fetchPermissionOptions(permissions, currentId) {
      let options = null
      if (permissions.length > 0) {
        options = []
      }
      for (let i = 0; i < permissions.length; i++) {
        const option = {
          label: permissions[i].name,
          value: permissions[i].id,
          disabled: permissions[i].id === currentId
        }

        option.children = this.fetchPermissionOptions(permissions[i].children, currentId)

        options.push(option)
      }

      return options
    },
    handleAdd() {
      this.permissionOptions = []
      this.permissionOptions = this.fetchPermissionOptions(this.list, 0) || []
      this.permission = Object.assign({}, defaultPermission)
      this.selectPermissions = []
      this.dialogTitle = '添加权限'
      this.dialogVisible = true
    },
    handleEdit(index, row) {
      this.permissionOptions = []
      this.permissionOptions = this.fetchPermissionOptions(this.list, row.id) || []
      this.permission = row
      this.selectPermissions = this.permissionPaths[this.permission.id]
      this.dialogTitle = '编辑权限'
      this.dialogVisible = true
    },
    handleDelete(index, row) {
      this.$confirm('是否要删除该权限', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removePermission(row.id).then(response => {
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
      this.$refs['permissionForm'].validate((valid) => {
        if (valid) {
          if (this.selectPermissions.length > 0) {
            this.permission.parentId = this.selectPermissions[this.selectPermissions.length - 1]
          } else {
            this.permission.parentId = 0
          }
          if (this.dialogTitle === '添加权限') {
            addPermission(this.permission).then(response => {
              this.$message({
                message: '添加成功',
                type: 'success',
                duration: 1000
              })
              this.dialogVisible = false
              this.fetchData()
            })
          } else {
            editPermission(this.permission.id, this.permission).then(response => {
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 1000
              })
              this.dialogVisible = false
              this.fetchData()
            })
          }
        } else {
          this.$message({
            message: '验证失败',
            type: 'error',
            duration: 1000
          })
          return false
        }
      })
    }
  }
}
</script>
