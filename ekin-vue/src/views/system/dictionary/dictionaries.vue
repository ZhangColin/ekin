<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="searchParam" class="filter-item" placeholder="请输入名称或Code查询" />
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
        <el-button class="filter-item" type="primary" @click="searchParam={}">重置</el-button>
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
      <el-table-column align="center" label="Code" prop="code" />
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column align="center" label="描述" prop="description" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="viewDetails(scope.row)">查看详情</el-button>
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
  </div>
</template>

<script>
import { searchDictionarys } from '@/api/system/dictionary-api'

export default {
  name: 'Dictionary',
  data() {
    return {
      dataSource: null,
      loading: true,
      searchParam: '',
      page: {
        total: 0,
        currentPage: 0,
        pageSize: 10
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      searchDictionarys(this.page.currentPage, this.page.pageSize, this.searchParam).then(response => {
        this.dataSource = response.data.rows
        this.page.total = response.data.total
        this.loading = false
      })
    },
    handleSearch() {
      this.page.currentPage = 0
      this.fetchData()
    },
    handleSizeChange(pageSize) {
      this.page.currentPage = 0
      this.page.pageSize = pageSize
      this.fetchData()
    },
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage - 1
      this.fetchData()
    },
    viewDetails(row) {
      this.$router.push({ path: '/system/dictionaries/dictionaryItems', query: { code: row.code }})
    }
  }
}
</script>
