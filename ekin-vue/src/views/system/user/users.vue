<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="searchParams.username" class="filter-item" placeholder="请输入账号查询" />
      </el-col>
      <el-col :span="6">
        <el-select v-model="searchParams.status" placeholder="请选择用户状态" clearable style="width: 100%">
          <el-option label="正常" value="1" />
          <el-option label="冻结" value="2" />
        </el-select>
      </el-col>
      <template v-if="toggleSearchStatus">
        <el-col :span="6">
          <el-input v-model="searchParams.phone" class="filter-item" placeholder="请输入手机号查询" />
        </el-col>
        <el-col :span="6">
          <el-input v-model="searchParams.email" class="filter-item" placeholder="请输入邮箱查询" />
        </el-col>
        <el-col :span="6">
          <el-select v-model="searchParams.sex" class="filter-item" placeholder="请选择用户性别" clearable style="width: 100%">
            <el-option label="男" value="1" />
            <el-option label="女" value="2" />
          </el-select>
        </el-col>
      </template>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="searchParams={}">重置</el-button>
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
      <el-table-column align="center" label="用户ID" prop="id" />
      <el-table-column align="center" label="用户账号" prop="username" />
      <el-table-column align="center" label="真实姓名" prop="realName" />
      <el-table-column align="center" label="头像" prop="avatar">
        <template slot-scope="scope">
          <img :src="scope.row.avatar">
        </template>
      </el-table-column>
      <el-table-column align="center" label="性别" prop="sex">
        <template slot-scope="scope">
          <span>{{ scope.row.sex===1?'男':'女' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="生日" prop="birthday">
        <template slot-scope="scope">
          <span>{{ scope.row.birthday | parseTime('{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="手机" prop="phone" />
      <el-table-column align="center" label="邮箱" prop="email" />
      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <span>{{ scope.row.status===1?'正常':'冻结' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-dropdown split-button @click="handleEdit(scope.$index, scope.row)">
            编辑
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handleDelete(scope.$index, scope.row)">删除</el-dropdown-item>
              <el-dropdown-item @click.native="handleChangePassword(scope.$index, scope.row)">修改密码</el-dropdown-item>
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
      title="修改密码"
      :visible.sync="changePasswordDialogVisible"
      :close-on-click-modal="false"
    >
      <el-form ref="changePasswordForm" :model="passwordModel" :rules="rules" label-width="150px">
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="passwordModel.username" readonly />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="passwordModel.password" type="password" :show-password="true" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordModel.confirmPassword" type="password" :show-password="true" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="changePasswordDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSubmitPassword()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { searchUsers, removeUser, frozenUser, unFrozenUser, changePassword } from '@/api/system/user-api'

export default {
  name: 'User',
  data() {
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else {
        if (this.passwordModel.confirmPassword !== '') {
          this.$refs.changePasswordForm.validateField('confirmPassword')
        }
        callback()
      }
    }

    const validateConfirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback('请再次输入密码')
      } else if (value !== this.passwordModel.password) {
        callback('两次输入的密码不一致')
      } else {
        callback()
      }
    }
    return {
      list: null,
      listLoading: true,
      toggleSearchStatus: false,
      changePasswordDialogVisible: false,
      searchParams: {
        username: ''
      },
      page: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      },
      passwordModel: {},
      rules: {
        password: [
          { required: true, message: '请输入密码' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码' },
          { validator: validateConfirmPassword, trigger: 'blur' }
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
      searchUsers(this.page.currentPage, this.page.pageSize, this.searchParams).then(response => {
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
      this.$router.push({ path: '/system/users/userAdd' })
    },
    handleEdit(index, row) {
      this.$router.push({ path: '/system/users/userEdit', query: { id: row.id }})
    },
    handleDelete(index, row) {
      this.$confirm('是否要删除该用户', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        removeUser(row.id).then(response => {
          this.$message({
            message: '删除成功',
            type: 'success',
            duration: 1000
          })
          this.fetchData()
        })
      }).catch(() => {
      })
    },
    handleChangePassword(index, row) {
      this.changePasswordDialogVisible = true
      this.passwordModel = {
        id: row.id,
        username: row.username,
        password: '',
        confirmPassword: ''
      }
    },
    handleSubmitPassword() {
      this.$refs['changePasswordForm'].validate(valid => {
        if (valid) {
          this.$confirm('是否提交数据', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            changePassword(this.passwordModel.id, this.passwordModel.password).then(response => {
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 1000
              })
              // this.fetchData()
              this.changePasswordDialogVisible = false
            })
          }).catch(() => {
          })
        }
      })
    },
    handleFrozen(index, row) {
      this.$confirm('是否要冻结该用户', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        frozenUser(row.id).then(response => {
          this.$message({
            message: '冻结成功',
            type: 'success',
            duration: 1000
          })
          this.fetchData()
        })
      }).catch(() => {
      })
    },
    handleUnFrozen(index, row) {
      this.$confirm('是否要解冻该用户', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        unFrozenUser(row.id).then(response => {
          this.$message({
            message: '解冻成功',
            type: 'success',
            duration: 1000
          })
          this.fetchData()
        })
      }).catch(() => {
      })
    }
  }
}
</script>
