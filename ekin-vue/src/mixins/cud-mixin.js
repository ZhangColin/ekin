import { add, edit, remove } from '@/api/common-api'

export const CudMixin = {
  data() {
    return {
      apiBaseUrl: '',
      drawerVisible: false,
      drawerTitle: '',
      defaultData: {},
      entityData: {},
      title: ''
    }
  },
  methods: {
    handleAdd() {
      this.entityData = Object.assign({}, this.defaultData)
      this.drawerTitle = `添加${this.title}`
      this.drawerVisible = true
    },
    handleEdit(index, row) {
      this.dataProcessBeforeEdit(row)
      this.entityData = Object.assign({}, row)

      this.drawerTitle = `编辑${this.title}`
      this.drawerVisible = true
    },
    dataProcessBeforeEdit(data) {},
    handleDelete(index, row) {
      this.$confirm(`是否要删除该${this.title}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        remove(this.apiBaseUrl, row.id).then(() => {
          this.$notify.success({
            title: '成功',
            message: '删除成功'
          })
          this.fetchData()
        })
      }).catch(() => {})
    },
    handleConfirm() {
      this.$refs['entityDataForm'].validate((valid) => {
        if (valid) {
          if (this.drawerTitle === `添加${this.title}`) {
            add(this.apiBaseUrl, this.entityData).then(() => {
              this.$notify.success({
                title: '成功',
                message: '添加成功'
              })
              this.drawerVisible = false
              this.fetchData()
            }).catch(() => {})
          } else {
            edit(this.apiBaseUrl, this.entityData.id, this.entityData).then(() => {
              this.$notify.success({
                title: '成功',
                message: '修改成功'
              })
              this.drawerVisible = false
              this.fetchData()
            }).catch(() => {})
          }
        }
      })
    }
  }
}
