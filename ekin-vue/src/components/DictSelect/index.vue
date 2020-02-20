<template>
  <el-select v-model="svalue" clearable placeholder="请选择">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>

<script>
import { searchItemsByDictCode } from '@/api/system/dictionary-api'

export default {
  name: 'DictSelect',
  props: {
    code: {
      required: true,
      type: String
    }
  },
  data() {
    return {
      options: [],
      svalue: ''
    }
  },
  watch: {
    // 判断下拉框的值是否有改变
    svalue(val, oldVal) {
      if (val !== oldVal) {
        this.$emit('input', this.svalue)
      }
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      searchItemsByDictCode(this.code).then(response => {
        console.log(response)
        this.options = response.data
      })
    }
  }
}
</script>
