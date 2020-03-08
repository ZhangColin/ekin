<template>
  <div class="app-container">
    <el-row :gutter="24" class="filter-container">
      <el-col :span="6">
        <el-input v-model="queryParam.blurry" class="filter-item" placeholder="请输入名称或Code查询" />
      </el-col>
      <el-col :span="12">
        <el-button class="filter-item" type="primary" @click="handleSearch">查询</el-button>
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
      <el-table-column align="center" label="编码" prop="code" />
      <el-table-column align="center" label="名称" prop="name" />
      <el-table-column align="center" label="描述" prop="description" />
      <el-table-column align="center" label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="viewDetails(scope.row)">查看字典项</el-button>
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
import { PaginationMixin } from '@/mixins/pagination-mixin'

export default {
  name: 'Dictionary',
  mixins: [PaginationMixin],
  data() {
    return {
      apiBaseUrl: '/system/dicts'
    }
  },
  methods: {
    viewDetails(row) {
      this.$router.push({ path: '/system/dictionary/dictionary-item', query: { code: row.code }})
    }
  }
}
</script>
