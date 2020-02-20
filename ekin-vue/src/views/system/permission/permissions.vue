<template>
  <div class="app-container">

    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="searchParams.name" class="filter-item" placeholder="请输入权限名称查询" />
      </el-col>

      <template v-if="toggleSearchStatus">
        <el-col :span="6">
          <el-input v-model="searchParams.code" class="filter-item" placeholder="请输入名称编码查询" />
        </el-col>
        <el-col :span="6">
          <el-input v-model="searchParams.path" class="filter-item" placeholder="请输入名称路径查询" />
        </el-col>
      </template>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="whereSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="searchParams={}">重置</el-button>
        <el-button class="filter-item" type="text" @click="toggleSearchStatus=!toggleSearchStatus">
          {{ toggleSearchStatus?'收起':'展开' }}
          <i :class="toggleSearchStatus?'el-icon-arrow-up':'el-icon-arrow-down'" class="el-icon--right" />
        </el-button>
      </el-col>
    </el-row>

    <div class="filter-container">
      <!--
      <el-button type="primary" class="filter-item" @click="handleAdd()">新增</el-button>
-->
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
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column align="center" label="编码" prop="code" />
      <el-table-column align="center" label="类型" prop="type" />
      <el-table-column align="center" label="组件" prop="component" />
      <!--
      <el-table-column align="center" label="组件名称" prop="component_name" />
-->
      <el-table-column align="center" label="图标" prop="icon" />
      <el-table-column align="center" label="路径" prop="path" width="200" />
      <el-table-column align="center" label="是否外链" prop="i_frame">
        <template>
          <span>{{ i_frame===1?'是':'否' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="缓存" prop="cache">
        <template>
          <span>{{ cache===1?'是':'否' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="隐藏" prop="hidden">
        <template>
          <span>{{ hidden===1?'是':'否' }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="描述" prop="description" />
      <el-table-column align="center" label="排序" prop="sort" />
    </el-table>

  </div>
</template>

<script>
import { getPermissionList } from '@/api/system/permission-api'

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
      cache: '',
      hidden: '',
      i_frame: '',
      toggleSearchStatus: false,
      changePasswordDialogVisible: false,
      searchParams: {
        name: ''
      },
      list: [],
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
      debugger
      getPermissionList(this.searchParams).then(response => {
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
    whereSearch() {
      this.fetchData()
    },
    handleSizeChange(pageSize) {
      this.page.currentPage = 1
      this.page.pageSize = pageSize
      // this.fetchData()
    },
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage
      // this.fetchData()
    }
  }
}
</script>
