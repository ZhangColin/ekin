<template>
  <el-tree
    ref="refOfTree"
    :data="options"
    show-checkbox
    :node-key="nodeKey"
    :default-expand-all="defaultExpandAll"
    :check-strictly="checkStrictly"
    :default-checked-keys="defaultCheckedKeys"
    :props="{label: 'name', children: 'children'}"
  />
</template>>

<script>
import { getTreeByClassName } from '@/api/system/tree-node-api'

export default {
  name: 'QiuxiTree',
  props: {
    // 默认展开全部
    defaultExpandAll: {
      required: false,
      type: Boolean,
      default: false
    },
    // 子父节点是否关联
    checkStrictly: {
      required: false,
      type: Boolean,
      default: false
    },
    // 每个树节点的唯一标识
    nodeKey: {
      required: true,
      type: String
    },
    // 默认勾选的节点的key的数组
    defaultCheckedKeys: {
      required: true,
      type: Array
    },
    // 树的类型
    className: {
      required: true,
      type: String
    }
  },
  data() {
    return {
      options: []
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      getTreeByClassName(this.className).then(response => {
        this.options = response.data
      })
    },
    getRef() {
      return this.$refs.refOfTree
    }
  }

}
</script>
