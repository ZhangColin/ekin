<template>
  <div>

    <el-select v-model="childCode" :multiple="multiple" :multiple-limit="multiplelimit" :filterable="filterable" :reserve-keyword="reserveKeyword" placeholder="请选择" style="width: 100%" @change="changeselect()">
      <el-option
        v-for="option in roleOptions"
        :key="option.code"
        :label="option.name"
        :value="option.code"
      />
    </el-select>
  </div>
</template>
<script>
import { getAllRoles } from '@/api/system/role-api'
export default {
  name: 'Roleselect',
  props: {
    codes: {
      type: Array,
      default: () => []
    }, // 这里指定了字符串类型，如果类型不一致会警告的哦
    multiple: {
      type: Boolean,
      required: false,
      default: true
    }, // 是否多选
    multiplelimit: {
      type: Number,
      required: false,
      default: 0
    }, // 多选限制，0代表无限制
    filterable: {
      type: Boolean,
      required: false,
      default: true
    }, // 是否支持搜索
    reserveKeyword: {
      type: Boolean,
      required: false,
      default: false
    } // 在搜索选中时输入框是否保留关键字

  },
  data: function() {
    return {
      roleOptions: [],
      childCode: []
    }
  },
  watch: {
    codes: function(newValue, oldValue) {
      this.childCode = newValue
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      getAllRoles().then(res => {
        this.roleOptions = res.data
      })
    },
    changeselect() {
      this.$emit('showbox', this.childCode)
    }
  }

}
</script>
