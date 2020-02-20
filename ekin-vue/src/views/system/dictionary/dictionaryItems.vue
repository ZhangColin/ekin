<template>
  <div>
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        <span>{{ dict }}  字典项</span>
      </div>
      <el-table
        v-loading="listLoading"
        :data="list"
        row-key="value"
        class="table-container"
        element-loading-text="加载中..."
        stripe
        border
        fit
        highlight-current-row
      >
        <el-table-column align="center" label="名称" prop="label" />
        <el-table-column align="center" label="键值" prop="value" />
        <el-table-column align="center" label="排序" prop="sort" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { searchItemsByDictCode } from '@/api/system/dictionary-api'

export default {
  name: 'DictionaryItem',
  data() {
    return {
      dict: '',
      list: null,
      listLoading: true
    }
  },
  created() {
    this.dict = this.$route.query.code
    this.init()
  },

  methods: {
    init() {
      searchItemsByDictCode(this.dict).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    }
  }

}
</script>
