import { search } from '@/api/common-api'

export const PaginationMixin = {
  data() {
    return {
      apiBaseUrl: '',
      queryParam: {},
      toggleSearchStatus: false,
      dataSource: [],
      loading: true,
      page: {
        total: 0,
        currentPage: 1,
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
      this.queryParam.page = this.page.currentPage - 1
      this.queryParam.size = this.page.pageSize

      search(this.apiBaseUrl, this.queryParam).then(response => {
        this.dataSource = response.data.rows
        this.page.total = response.data.total
        this.loading = false
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
    }
  }
}
