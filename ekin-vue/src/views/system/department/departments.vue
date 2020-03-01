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
      <el-table-column align="left" label="组织名称" prop="name" />
      <el-table-column align="left" label="组织ID" prop="id" />
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
      <el-form ref="departmentForm" :model="organization" :rules="rules" label-width="120px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="organization.name" placeholder="请输入组织名称" />
        </el-form-item>
        <el-form-item label="上级组织">
          <el-cascader
            v-model="selectDepartments"
            expand-trigger="hover"
            :options="departmentOptions"
            style="width: 100%"
            clearable
            filterable
            change-on-select
          />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="organization.description" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="organization.sort" placeholder="请输入组织排序" />
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
import { getDepartmentList, addDepartment, editDepartment, removeDepartment } from '@/api/system/organization-api'

const defaultDepartment = {
  name: '',
  parentId: 0,
  description: '',
  sort: 0
}

export default {
  name: 'Department',
  data() {
    return {
      departmentOptions: [],
      selectDepartments: [],
      list: null,
      listLoading: true,
      dialogVisible: false,
      dialogTitle: '',
      organization: Object.assign({}, defaultDepartment),
      rules: {
        name: [
          { required: true, message: '请输入组织名称', trigger: 'blur' }
        ]
      },
      departmentPaths: {}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getDepartmentList().then(response => {
        this.list = response.data
        this.listLoading = false
        this.fetchDepartmentPaths(this.list, [])
      })
    },
    fetchDepartmentPaths(organizations, parentPath) {
      for (let i = 0; i < organizations.length; i++) {
        this.departmentPaths[organizations[i].id] = Object.assign([], parentPath)
        const newPath = Object.assign([], parentPath)
        newPath.push(organizations[i].id)
        this.fetchDepartmentPaths(organizations[i].children, newPath)
      }
    },
    fetchDepartmentOptions(organizations, currentId) {
      let options = null
      if (organizations.length > 0) {
        options = []
      }
      for (let i = 0; i < organizations.length; i++) {
        const option = {
          label: organizations[i].name,
          value: organizations[i].id,
          disabled: organizations[i].id === currentId
        }

        option.children = this.fetchDepartmentOptions(organizations[i].children, currentId)

        options.push(option)
      }

      return options
    },
    handleAdd() {
      this.departmentOptions = []
      this.departmentOptions = this.fetchDepartmentOptions(this.list, 0) || []
      this.organization = Object.assign({}, defaultDepartment)
      this.selectDepartments = []
      this.dialogTitle = '添加组织'
      this.dialogVisible = true
    },
    handleEdit(index, row) {
      this.departmentOptions = []
      this.departmentOptions = this.fetchDepartmentOptions(this.list, row.id) || []
      this.organization = row
      this.selectDepartments = this.departmentPaths[this.organization.id]
      this.dialogTitle = '编辑组织'
      this.dialogVisible = true
    },
    handleDelete(index, row) {
      this.$confirm('是否要删除该组织', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeDepartment(row.id).then(response => {
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
      this.$refs['departmentForm'].validate((valid) => {
        if (valid) {
          if (this.selectDepartments.length > 0) {
            this.organization.parentId = this.selectDepartments[this.selectDepartments.length - 1]
          } else {
            this.organization.parentId = 0
          }
          if (this.dialogTitle === '添加组织') {
            addDepartment(this.organization).then(response => {
              this.$message({
                message: '添加成功',
                type: 'success',
                duration: 1000
              })
              this.dialogVisible = false
              this.fetchData()
            })
          } else {
            editDepartment(this.organization.id, this.organization).then(response => {
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
    }
  }
}
</script>
