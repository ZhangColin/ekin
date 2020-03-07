import { search, getAll } from '@/api/common-api'

export const ListMixin = {
  data() {
    return {
      apiBaseUrl: '',
      useSearch: true,
      queryParam: {},
      toggleSearchStatus: false,
      dataSource: [],
      loading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.loading = true
      if (this.useSearch) {
        search(this.apiBaseUrl, this.queryParam).then(response => {
          this.dataSource = response.data
          this.loading = false
        })
      } else {
        getAll(this.apiBaseUrl).then(response => {
          this.dataSource = response.data
          this.loading = false
        })
      }
    }
  }
}
