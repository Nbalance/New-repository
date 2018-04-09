
<template>
<div v-cloak>
  <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
  <div v-show="showList">
      <!-- table -->
      <div class="row center-block">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
              <el-button type="primary" @click="update">编辑</el-button>
            </div>

            <!-- /.box-header -->
            <div class="box-body-sd">
              <table width="600" border="1" bordercolor="#dedede"  background="../../../static/img/stock/201.gif" cellpadding="10" cellspacing="0" style="margin-left: 10px" >
                <tr class="tabie-ss">
                  <td>用户账号</td>
                  <td>{{sysUser.username}}</td>
                </tr>
                <tr class="tabie-ss">
                  <td>用户姓名</td>
                  <td>{{sysUser.account}}</td>
                </tr>
                <tr class="tabie-ss">
                  <td>用户头像</td>
                  <td><img src="/user_book/api/sys/user/head" height="40" width="40"/></td>
                </tr>
                <tr class="tabie-ss">
                  <td>手机号码</td>
                  <td>{{sysUser.phone}}</td>
                </tr>
                <tr class="tabie-ss">
                  <td>用户邮箱</td>
                  <td>{{sysUser.email}}</td>
                </tr>
                <tr class="tabie-ss">
                  <td>备注</td>
                  <td>{{sysUser.remark}}</td>
                </tr>
              </table>

  <!-- /table -->
    <el-dialog title="用户修改" :visible.sync="dialogUpdateVisible" class="userjs">
    <el-form ref="sysUser" :model="sysUser" :rules="rules" label-width="100px" class="el-from jk">
      <el-form-item label="用户账号" prop="username">
        <div class="col-sm-10" style="margin-top: -1px;">
          {{sysUser.username}}
          </div>
      </el-form-item>

      <el-form-item label="用户姓名" prop="account">
        <el-input type="text" class="setWidth" v-model="sysUser.account" placeholder="用户姓名"/>
      </el-form-item>

      <el-form-item label="用户头像" prop="account">
        <el-upload
          class="upload-demo"
          action="/user_book/api/sys/user/upload"
          :on-success="handleSuccess"
          :data="fileList">
          <el-button size="small" type="success" icon="picture" style ="margin-right: 734px;margin-top: 40px">头像上传</el-button>
        </el-upload>
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input type="text" class="setWidth" v-model="sysUser.phone" placeholder="手机号"/>
      </el-form-item>

      <el-form-item label="用户邮箱" prop="email">
        <div class="col-sm-10" style="margin-top: -1px;">
          {{sysUser.email}}
        </div>
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <div class="col-sm-10" style="margin-top: -1px;">
          {{sysUser.remark}}
        </div>
      </el-form-item>

      <div class="form-group">
        <el-form-item>
          <el-button type="primary" @click="submitForm('sysUser')">确定</el-button>
          <el-button @click="reload">返回</el-button>
        </el-form-item>
      </div>
    </el-form>
    </el-dialog>
  </div>
 </div>
</div>
</div>
</div>
</div>
</template>

<script>
import 'element-ui/lib/theme-default/index.css'
import api from '../../api'
import config from '../../config'

export default {
  name: 'sysUser',
  data () {
    var checkCellphonenumber = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入手机号码'))
      } else {
        if (!api.checkCellphonenumber(value)) {
          callback(new Error('请输入正确的手机号码'))
        } else {
          callback()
        }
      }
    }
    return {
      showList: true,
      title: null,
      userId: '',
      sysUser: {
        status: 1,
        unitId: '',
        roleIdList: []
      },
      tableList: [{}],
      list: [{}],
      currentPage: 1,
      pageSize: config.pageSize,
      pageSizes: config.pageSizes,
      dialogUpdateVisible: false,
      totalSize: 1,
      roleList: {},
      sysUserUnitName: '',
      userName: '',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        account: [
          { required: true, message: '请输入用户姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: checkCellphonenumber, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }
        ],
        remark: [
          { required: true, message: '请输入备注信息', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getUserInfo()
    this.gethead()
  },
  computed: {
    fileList () {
      return {
        file: '',
        username: this.sysUser.username
      }
    }
  },
  methods: {
    handleSuccess (fileList) {
      console.log(fileList)
    },
    getUserInfo (userId) {
      var _this = this
      api.request('post', '/api/sys/user/info').then(function (rsp) {
        if (rsp.data.code === 0) {
          console.log(rsp)
          _this.sysUser = rsp.data.user
          _this.list = rsp.data.list
        } else {
            // refreshCode()
        }
      }).catch(error => {
        console.log(error)
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveOrUpdate()
        } else {
          return false
        }
      })
    },
    update (index, event) {
      var _this = this
      _this.dialogUpdateVisible = true
      // var id = event
      var userId = event
      _this.getUserInfo(userId)
      _this.sysUser = {}
    },
    saveOrUpdate (event) {
      var _this = this
      var url = '/api/sys/user/updateInfo'
      console.log(_this.sysUser)
      api.request('POST', url, _this.sysUser).then(function (rsp) {
        if (rsp.data.code === 0) {
          console.log(rsp)
          _this.getUserInfo()
          _this.reload()
          _this.$message.success('个人信息修改成功,请稍后重新登录')
       //   _this.$router.push('/')
        } else {
          _this.$message.error(rsp.data.message)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    gethead (event) {
      debugger
      var _this = this
      api.request('GET', '/api/sys/user/head').then(function (rsp) {
        if (rsp.data.code === 0) {
          console.log(rsp)
        } else {
          _this.$message.error(rsp.data.message)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    reload: function (event) {
      var _this = this
      _this.dialogUpdateVisible = false
      this.getUserInfo()
    },
    isPermission (state) {
      return api.hasPermission(state)
    }
  }
}
</script>

<style>

/*@import url('/static/js/plugins/datatables/dataTables.bootstrap.css');*/

table.dataTable thead .sorting:after,
table.dataTable thead .sorting_asc:after,
table.dataTable thead .sorting_desc:after {
  font-family: 'FontAwesome';
}

table.dataTable thead .sorting:after {
  content: "\f0dc";
}

table.dataTable thead .sorting_asc:after {
  content: "\f0dd";
}

table.dataTable thead .sorting_desc:after {
  content: "\f0de";
}
.form-horizontal{
  margin-top: 20px;
  width: 50%;
}
.setbackground1{
  background-color: rgba(0, 147, 183, 0.04);
}
  .box-body-sd{
    width: 1024px;
    height: 280px;
  }
  .tabie-ss{
    height: 45px;
    text-align: center;
  }
  </style>
