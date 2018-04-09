<template>
  <div :class="['wrapper', classes]">
    <header class="main-header ys">
      <a href="/" class="logo">
        <!-- mini logo for sidebar mini 40x50 pixels -->
        <span class="logo-mini"><img src="/static/img/icon/ez.ico" alt="Logo"></span>
        <!-- logo for regular state and mobile devices -->
        <div class="logo-lg">
          <img src="/static/img/icon/akl.ico" alt="Logo">
          <!--<span>CoPilot</span>-->
        </div>
      </a>

      <!-- Header Navbar -->
      <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="javascript:;" class="sidebar-toggle" data-toggle="offcanvas" role="button">
          <span class="sr-only">Toggle navigation</span>
        </a>

        <!-- <div class="column is-12">
            <label class="label" for="email">Email</label>
            <p :class="{ 'control': true }">
                <input v-validate="'required|email'" :class="{'input': true, 'is-danger': errors.has('email') }" name="email" type="text" placeholder="Email">
                <span v-show="errors.has('email')" class="help is-danger">{{ errors.first('email') }}</span>
            </p>
            </div>-->

        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
           <ul class="nav navbar-nav">
            <!-- Messages-->
            <!-- <li class="dropdown messages-menu">
              <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-envelope-o"></i>
                <span class="label label-success">{{ userInfo.messages | count }}</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have {{ userInfo.messages | count }} message(s)</li>
                <li v-if="userInfo.messages.length > 0"> -->
                  <!-- inner menu: contains the messages -->
                  <!-- <ul class="menu"> -->
                   <!-- <li> --><!-- start message -->
                      <!-- <a href="javascript:;"> -->
                        <!-- Message title and timestamp -->
                        <!-- <h4>
                          Support Team
                          <small><i class="fa fa-clock-o"></i> 5 mins</small>
                        </h4> -->
                        <!-- The message -->
                        <!-- <p>Why not consider this a test message?</p>
                      </a>
                    </li> -->
                    <!-- end message -->
                  <!-- </ul> -->
                  <!-- /.menu -->
                <!-- </li>
                <li class="footer" v-if="userInfo.messages.length > 0"><a href="javascript:;">See All Messages</a></li>
              </ul>
            </li> -->
            <!-- /.messages-menu -->

            <!-- Notifications Menu -->
            <!-- <li class="dropdown notifications-menu">
              <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-bell-o"></i>
                <span class="label label-warning">{{ userInfo.notifications | count }}</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have {{ userInfo.notifications | count }} notification(s)</li>
                <li v-if="userInfo.notifications.length > 0"> -->
                  <!-- Inner Menu: contains the notifications -->
                  <!-- <ul class="menu"> -->
                    <!-- <li> --><!-- start notification -->
                      <!-- <a href="javascript:;">
                        <i class="fa fa-users text-aqua"></i> 5 new members joined today
                      </a>
                    </li> -->
                    <!-- end notification -->
                  <!-- </ul>
                </li>
                <li class="footer" v-if="userInfo.notifications.length > 0"><a href="javascript:;">View all</a></li>
              </ul>
            </li> -->

            <!-- Tasks Menu -->
            <!-- <li class="dropdown tasks-menu">
              <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-flag-o"></i>
                <span class="label label-danger">{{ userInfo.tasks | count }} </span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have {{ userInfo.tasks | count }}  task(s)</li>
                <li v-if="userInfo.tasks.length > 0"> -->
                  <!-- Inner menu: contains the tasks -->
                  <!-- <ul class="menu">
                    <li> --><!-- Task item -->
                      <!-- <a href="javascript:;"> -->
                        <!-- Task title and progress text -->
                        <!-- <h3>
                          Design some buttons
                          <small class="pull-right">20%</small>
                        </h3> -->
                        <!-- The progress bar -->
                        <!-- <div class="progress xs"> -->
                          <!-- Change the css width attribute to simulate progress -->
                          <!-- <div class="progress-bar progress-bar-aqua"
                            style="width: 20%"
                            role="progressbar"
                            aria-valuenow="20"
                            aria-valuemin="0"
                            aria-valuemax="100">
                            <span class="sr-only">20% Complete</span>
                          </div>
                        </div>
                      </a>
                    </li> -->
                    <!-- end task item -->
                  <!-- </ul>
                </li>
                <li class="footer" v-if="userInfo.tasks.length > 0">
                  <a href="javascript:;">View all tasks</a>
                </li>
              </ul>
            </li> -->
             <li class="dropdown" v-if="userUnit !== null?true:false">
               <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                 <!--<span class="hidden-xs">{{ userUnit.unitName }}</span>-->
               </a>
             </li>
             <li class="dropdown user user-menu">
               <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                 <!-- The user image in the navbar-->
                 <img src="/user_book/api/sys/user/head" class="user-image" alt="User Image">
                 <!-- hidden-xs hides the username on small devices so only the image appears. -->
                 <span class="hidden-xs">{{ userInfos.account }}</span>
               </a>
               <ul class="dropdown-menu" style="min-width: 125px; width: 125px;">
                 <li class="dropdown userInfo"  @click="showChangePasswordDialog">
                   <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                     <!-- The user image in the navbar-->
                     <!-- <img v-bind:src="demo.avatar" class="user-image" alt="User Image"> -->
                     <!-- hidden-xs hides the username on small devices so only the image appears. -->
                     <i class="fa fa-lock"></i>
                     <span class="hidden-xs">修改密码</span>
                   </a>
                 </li>
                 <!-- User Account Menu -->
                 <li class="dropdown userInfo" @click="logout">
                   <!--
                  <router-link to="/" class="dropdown-toggle" data-toggle="dropdown">
                  -->
                   <!-- The user image in the navbar-->
                   <!-- <img v-bind:src="demo.avatar" class="user-image" alt="User Image"> -->
                   <!-- hidden-xs hides the username on small devices so only the image appears. -->
                   <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                     <i class="fa fa-sign-out"></i>
                     <span class="hidden-xs" style="margin-left: -5px;">退出登录</span>
                   </a>
                   <!--
                  </router-link>
                  -->
                 </li>
               </ul>
             </li>

          </ul>
        </div>
      </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <sidebar :display-name="demo.displayName"
             :picture-url="demo.avatar" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h5>
          <!-- {{$route.name.toUpperCase() }}
          <small>{{ $route.meta.description }}</small> -->
          <ol class="breadcrumb">
              <li><a href="javascript:;"><i class="fa fa-home"></i>首页</a></li>
              <li class="active">{{$route.name.toUpperCase()}}</li>
          </ol>
        </h5>
        <!-- <ol class="breadcrumb">
          <li><a href="javascript:;"><i class="fa fa-home"></i>Home</a></li>
          <li class="active">{{$route.name.toUpperCase()}}</li>
        </ol> -->
      </section>

      <router-view></router-view>


    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <!-- <footer class="main-footer">
      <strong>Copyright &copy; {{year}} <a href="javascript:;">CoPilot</a>.</strong> All rights reserved.
    </footer> -->

    <el-dialog title="修改密码" v-model="dialogFormVisible">

     <el-form :model="chanagePassword">

       <el-form-item label="旧密码" :label-width="formLabelWidth">
         <el-input type="password" v-model="chanagePassword.oldPassword" auto-complete="off"></el-input>
       </el-form-item>
       <el-form-item label="新密码" :label-width="formLabelWidth">
         <el-input type="password" v-model="chanagePassword.newPassowrd" auto-complete="off"></el-input>
       </el-form-item>
       <el-form-item label="确认新密码" :label-width="formLabelWidth">
         <el-input type="password" v-model="chanagePassword.confirmPassword" auto-complete="off"></el-input>
       </el-form-item>
     </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button @click="dialogFormVisible = false">取 消</el-button>
       <el-button type="primary" @click="changePassword(chanagePassword.oldPassword,chanagePassword.newPassowrd,chanagePassword.confirmPassword)">确 定</el-button>
     </div>
   </el-dialog>


 </div>
 <!-- ./wrapper -->
</template>

<script>
// import faker from 'faker'
import { mapState } from 'vuex'
import config from '../config'
import Sidebar from './Sidebar'
import 'hideseek'
import 'element-ui/lib/theme-default/index.css'
import api from '../api'

export default {
  name: 'Dash',
  components: {
    Sidebar
  },
  data: function () {
    return {
      // section: 'Dash',
      year: new Date().getFullYear(),
      classes: {
        fixed_layout: config.fixedLayout,
        hide_logo: config.hideLogoOnMobile
      },
      error: '',
      dialogFormVisible: false,
      chanagePassword: {
        oldPassword: '',
        newPassowrd: '',
        confirmPassword: ''
      },
      userInfos: {},
      userUnit: {}
    }
  },
  computed: {
    ...mapState([
      'userInfo'
    ]),
    demo () {
      return {
//        displayName: faker.name.findName(),
//        avatar: faker.image.avatar(),
//        email: faker.internet.email(),
//        randomCard: faker.helpers.createCard()
      }
    }
  },
  created () {
    this.getUserInfo()
  },
  methods: {
    changeloading () {
      this.$store.commit('TOGGLE_SEARCHING')
    },
    logout () {
      var _this = this
      var data = {
        'username': _this.userInfos.username
      }
      window.confirm('确定退出登录？', function () {
        api.request('POST', '/logout', data).then(function (rsp) {
          if (rsp.data.code === 0) {
            console.log('logout')
            _this.$router.push('/')
            _this.$message('注销成功')
          } else {
            window.alert(rsp.data.message)
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      })
    },
    showChangePasswordDialog () {
      this.dialogFormVisible = true
    },
    changePassword (oldPassword, newPassword, confirmPassword) {
      if (newPassword === confirmPassword) {
        console.log('oldPassword:' + oldPassword + ' newPassword:' + newPassword)
        var _this = this
        var data = 'password=' + oldPassword + '&newPassword=' + newPassword
        api.request('post', '/sys/user/modifyPassword', data, config.contentFromUrlEncode)
          .then(function (rsp) {
            console.log(rsp)
            if (rsp.data.code === 0) {
              // 修改密码成功, 跳回登录首页
              _this.$message('密码修改成功')
              _this.confirmPassword = false
              _this.$router.push('/')
            } else {
              _this.$message.error(rsp.data.message)
            }
          })
          .catch(error => {
            console.log(error)
          })
      } else {
        this.$message.error('两次输入的新密码不一致')
      }
    },
    getUserInfo (callback) {
      var _this = this
      api.request('post', '/api/sys/user/info')
        .then(function (rsp) {
          if (rsp.data.code === 0) {
//            debugger
            _this.userInfos = rsp.data.user
            _this.userUnit = rsp.data.unit
            if (callback !== null) {
              callback()
            }
          } else {
            // refreshCode()
          }
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style lang="scss">
.wrapper.fixed_layout {
  .main-header {
    position: fixed;
    width: 100%;
  }

  .content-wrapper {
    padding-top: 50px;
  }

  .main-sidebar {
    position: fixed;
    height: 100vh;
  }
}

.wrapper.hide_logo {
  @media (max-width: 767px) {
    .main-header .logo {
      display: none;
    }
  }
}

.logo-mini, .logo-lg {
  text-align: center;

  /*img {
    padding: .4em;
  }*/
}

/*.logo-lg {
  img {
    display: -webkit-inline-box;
    width: 25%;
  }
}*/
.user-panel {
  height: 4em;
}

hr.visible-xs-block {
  width: 100%;
  background-color: rgba(0, 0, 0, 0.17);
  height: 1px;
  border-color: transparent;
}
  .userInfo{
    height: 36px;
    padding: 5px;
  }
</style>
