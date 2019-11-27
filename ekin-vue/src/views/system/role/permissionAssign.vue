<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>{{ role.name }}（{{ role.code }}） 所拥有的权限</span>
      </div>
      <el-form ref="permissionAssignForm">
        <el-form-item>
          <el-tree
            ref="selectPermissions"
            :data="permissionOptions"
            show-checkbox
            node-key="id"
            default-expand-all
            check-strictly
            :default-checked-keys="permissionIds"
            :props="{label: 'name', children: 'children'}"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit()">提交</el-button>
          <el-button @click="handleReset()">重置</el-button>
          <el-button @click="handleBack()">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getRole, getRolePermissions, assignPermissions } from '@/api/system/role-api'
import { getPermissionTree } from '@/api/system/permission-api'

export default {
  name: 'PermissionAssign',
  data() {
    return {
      role: {},
      permissionOptions: [],
      permissionIds: []
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      getRole(this.$route.query.id).then(response => {
        this.role = response.data
      })
      getRolePermissions(this.$route.query.id).then(response => {
        this.permissionIds = response.data
      })
      getPermissionTree().then(response => {
        this.permissionOptions = response.data
      })
    },
    handleSubmit() {
      this.$confirm('是否提交数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.permissionIds = []
        this.$refs.selectPermissions.getCheckedNodes().forEach(
          node => {
            this.permissionIds.push(node.id)
          }
        )
        assignPermissions(this.$route.query.id, this.permissionIds).then(response => {
          this.$message({
            message: '提交成功',
            type: 'success',
            duration: 1000
          })
          this.$router.back()
        })
      })
    },
    handleReset() {
      this.init()
    },
    handleBack() {
      this.$router.back()
    }
  }
}
</script>
