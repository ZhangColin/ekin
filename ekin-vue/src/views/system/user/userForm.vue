<template>
  <div class="app-container">
    <el-form ref="userForm" :model="user" :rules="rules" label-width="150px">
      <el-form-item label="用户账号" prop="username">
        <el-input v-model="user.username" />
      </el-form-item>
      <el-form-item v-if="!isEdit" label="登录密码" prop="password">
        <el-input v-model="user.password" type="password" :show-password="true" />
      </el-form-item>
      <el-form-item v-if="!isEdit" label="确认密码" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" type="password" :show-password="true" />
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input v-model="user.realName" />
      </el-form-item>
      <el-form-item label="分配角色">
        <roleselect v-model="user.roleCodes" :codes="codes" :multiple="true" :filterable="true" :reserve-keyword="false" @showbox="toshow" />
      </el-form-item>
      <el-form-item label="分配组织">
        <el-input v-model="selectDepartmentNames" placeholder="请选择组织" readonly>
          <el-button slot="append" icon="el-icon-setting" @click="selectDepartments" />
        </el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8889/system/import"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="user.avatar" alt="头像" :src="user.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker v-model="user.birthday" type="date" placeholder="请选择生日" />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="user.sex">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="手机" prop="phone">
        <el-input v-model="user.phone" />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="user.email" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit()">提交</el-button>
        <el-button @click="handleReset()">重置</el-button>
        <el-button @click="handleBack()">返回</el-button>
      </el-form-item>
    </el-form>
    <el-dialog
      title="选择组织"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
    >
      <QiuxiTree
        ref="selectDepartments"
        node-key="id"
        :default-checked-keys="user.departmentIds"
        class-name="organization"
        default-expand-all
        check-strictly
      />
      <span slot="footer">
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="handleSelectDepartments()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getUser, addUser, editUser } from '@/api/system/user-api'
import { getTreeByClassName } from '@/api/system/tree-node-api'
import QiuxiTree from '@/components/QiuxiTree/Qiuxi-Tree'
import roleselect from '../../../components/RoleSelect/index'

const defaultUser = {
  username: '',
  password: '',
  confirmPassword: '',
  realName: '',
  avatar: '',
  birthday: '',
  sex: 1,
  email: '',
  phone: '',
  roleCodes: [],
  departmentIds: []
}

export default {
  name: 'UserFrom',
  components: { QiuxiTree, roleselect },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },

  data() {
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else {
        if (this.user.confirmPassword !== '') {
          this.$refs.userForm.validateField('confirmPassword')
        }
        callback()
      }
    }

    const validateConfirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback('请再次输入密码')
      } else if (value !== this.user.password) {
        callback('两次输入的密码不一致')
      } else {
        callback()
      }
    }

    const validatephone = (rule, value, callback) => {
      if (value.length > 11 || value.length < 7) {
        callback('请输入正确的手机号')
      } else {
        callback()
      }
    }

    const validateemail = (rule, value, callbacks) => {
      var email = value
      var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/
      if (!reg.test(email)) {
        callbacks('邮箱格式不正确')
      } else {
        callbacks()
      }
    }
    return {
      codes: [],
      roleOptions: [],
      departmentList: {},
      dialogVisible: false,
      user: Object.assign({}, defaultUser),
      rules: {
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
        realName: [
          { required: true, message: '请输入真实姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机' },
          { validator: validatephone, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱' },
          { validator: validateemail, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    selectDepartmentNames() {
      const selectDepartments = []
      this.user.departmentIds.forEach(departmentId => {
        if (this.departmentList[departmentId]) {
          selectDepartments.push(this.departmentList[departmentId].name)
        }
      })
      return selectDepartments.join('，')
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      getTreeByClassName('organization').then(response => {
        this.fetchDepartmentList(response.data)

        // TODO: 因为异步，可能 user 返回时，departmentList 还没构建好，这会导致看不到组织，暂时使用同步化的手段来处理，以后再寻求解决方案
        if (this.isEdit) {
          getUser(this.$route.query.id).then(response => {
            this.user = response.data
            this.codes = this.user.roleCodes
          })
        } else {
          this.user = Object.assign({}, defaultUser)
        }
      })
    },
    fetchDepartmentList(departmentNodes) {
      departmentNodes.forEach(node => {
        this.departmentList[node.id] = node
        this.fetchDepartmentList(node.children)
      })
    },
    handleSubmit() {
      this.$refs['userForm'].validate(valid => {
        if (valid) {
          this.$confirm('是否提交数据', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            if (this.isEdit) {
              const param = {
                username: this.user.username,
                realName: this.user.realName,
                avatar: this.user.avatar,
                birthday: this.user.birthday,
                sex: this.user.sex,
                email: this.user.email,
                phone: this.user.phone,
                roleCodes: this.user.roleCodes,
                departmentIds: this.user.departmentIds
              }
              editUser(this.$route.query.id, param).then(response => {
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  duration: 1000
                })
                this.$router.back()
              })
            } else {
              const param = {
                username: this.user.username,
                password: this.user.password,
                realName: this.user.realName,
                avatar: this.user.avatar,
                birthday: this.user.birthday,
                sex: this.user.sex,
                email: this.user.email,
                phone: this.user.phone,
                roleCodes: this.user.roleCodes,
                departmentIds: this.user.departmentIds
              }
              addUser(param).then(response => {
                this.$message({
                  message: '提交成功',
                  type: 'success',
                  duration: 1000
                })
                this.$router.back()
              })
            }
          })
        }
      })
    },
    handleReset() {
      this.init()
    },
    handleBack() {
      this.$router.back()
    },
    handleAvatarSuccess(res, file) {
      this.user.avatar = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    selectDepartments() {
      this.dialogVisible = true
    },
    handleSelectDepartments() {
      this.user.departmentIds = []
      this.$refs.selectDepartments.getRef().getCheckedNodes().forEach(
        node => {
          this.user.departmentIds.push(node.id)
        }
      )
      this.dialogVisible = false
    },
    toshow(data) {
      this.codes = data
      this.user.roleCodes = data
      console.log(this.user.roleCodes)
    }
  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
