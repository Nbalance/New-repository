<template>
  <div class="container container-table">
    <div class="row vertical-10p">
      <div class="container">
        <img src="../../static/img/stock/15.png" height="288" width="294" class="center-block logo"/>
        <div class="text-center col-md-4 col-sm-offset-4" style="margin-top: 20px">
          <div class="input-group">
            <span class="input-group-addon" style="width: 40px"><i class="fa fa-user"></i></span>
            <input class="form-control" style="width: 310px" placeholder="请输入用户账号" type="text" v-model="username">
          </div>
          <div class="input-group s">
            <span class="input-group-addon" style="width: 40px"><i class="fa fa-lock"></i></span>
            <input class="form-control" style="width: 310px" placeholder="请输入密码" type="password" v-model="password">
          </div>
          <div class="input-group s">
            <span class="input-group-addon" style="width: 43px"><i class="fa fa-key"></i></span>
            <input class="form-control" type="text" placeholder="验证码" v-model="captcha" @keyup.enter="login">
          </div>
          <div class="vCodeImg">
            <img alt="单击图片刷新" class="pointer" :src="src" @click="refreshCode" style="width: 100px;height: 42px;margin-left: 13px">
          </div>
          <div class="register">
            <el-row>
              <span class="jz"><el-checkbox v-model="checked" @click="rememberword()">记住密码</el-checkbox></span>
            </el-row>
          </div>
          <div class="login">
            <el-button type="primary"size="large" style="width: 350px;margin-top: -25px" @click="login":loading="logining">登录</el-button>
          </div>
          <div class="company">
            <span>版权所有：伟哥（家里蹲）搬砖无限公司</span>
            <p>技术支持：伟哥工作室@请叫我伟哥</p>
            <p>Copyright © 2018 by lkeng. All rights reserved.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '../api'
import $ from 'jquery'
import config from '../config'

export default {
  name: 'Login',
  data (router) {
    return {
      section: 'Login',
      logining: false,
      username: null,
      password: null,
      response: '',
      captcha: '',
      validateCode: '',
      checked: true,
      errorMsg: '',
      getuserId: 0,
      type2: 0,
      error: false,
      src: config.serverURI + '/captcha.jpg',
      iphoneSrc: '/user_book/api/outAuthority/getQrCode/2',
      andriodSrc: '/user_book/api/outAuthority/getQrCode/1'
    }
  },
  mounted () {
    this.getCookie()
  },
  methods: {
    refreshCode () {
      this.src = config.serverURI + '/captcha.jpg?t=' + $.now()
    },
    getUserInfo (callback) {
      var _this = this
      api.request('post', '/api/sys/user/info')
        .then(function (rsp) {
          if (rsp.data.code === 0) {
            console.log('info ID ' + rsp.data.user.userId)
            _this.getuserId = rsp.data.user.userId
            if (callback !== null) {
              callback()
            }
            console.log('getuserId ' + _this.getuserId)
          } else {
            // refreshCode()
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    getUserrole (userid) {
      var _this = this
      api.request('post', '/api/sys/user/infouserrole/' + userid)
        .then(function (rsp) {
          if (rsp.data.code === 0) {
            console.log(rsp.data.type)
            _this.type2 = rsp.data.type
            if (_this.type2 === 1) {
              _this.$router.push('dashboard/sensorInfoSuper')
            } else if (_this.type2 === 2) {
              _this.$router.push('dashboard/sensorInfoCustomer')
            } else if (_this.type2 === 3) {
              _this.$router.push('dashboard/checkCheckUnit')
            }
          } else {
            // refreshCode()
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    login (event) {
//      debugger
      console.log(window.location)
      var _this = this
      if (_this.captcha === null || _this.captcha === '') {
        _this.$message({
          message: '验证码不能为空',
          type: 'error'
        })
      }
      if (_this.password === null || _this.password === '') {
        _this.$message({
          message: '密码不能为空',
          type: 'error'
        })
      }
      if (_this.username === null || _this.username === '') {
        _this.$message({
          message: '账号不能为空',
          type: 'error'
        })
      }
      if ((_this.username !== null && _this.username !== '') && (_this.password !== null && _this.password !== '') && (_this.captcha !== null && _this.captcha !== '')) {
        var data = {
          'username': _this.username,
          'password': _this.password,
          'captcha': _this.captcha,
          'checked': _this.checked
        }
        // var data = 'username=' + _this.username + '&password=' + _this.password + '&captcha=' + _this.captcha
        _this.logining = true
        api.request('POST', '/sys/login', data).then(function (rsp) {
          console.log(rsp)
          if (rsp.data.code === 0) { // 登录成功
            // console.log(rsp.data)
            _this.$store.commit('SET_USER', rsp.data.premissionList)
            window.sessionStorage.setItem('SET_USER', JSON.stringify(rsp.data.premissionList))
            _this.$router.push('dashboard')
            _this.$message('登录成功')
          } else {
            // 登录失败提示
            _this.$message.error(rsp.data.message)
            _this.logining = false
            // if (message === '验证码不正确') {
            _this.refreshCode()
            // }
          }
        })
          .catch(error => {
            console.log(error)
          })
      }
    },
    rememberword (checked) {
      var _this = this
      console.log(checked)
      _this.checked === true ? 1 : 0
    },
    getCookie: function () {
      if (document.cookie.length > 0) {
        var arr = document.cookie.split('; ')
      }
      for (var i = 0; i < arr.length; i++) {
        var arr2 = arr[i].split('=')
        if (arr2[0] === 'username') {
          this.username = arr2[1]
        } else if (arr2[0] === 'password') {
          this.password = arr2[1]
        }
      }
    }
  }
}
</script>

<style>
  html, body, .container-table {
    height: 100%;
    background-color: #c8cec3 !important;
  }
  .container-table {
    display: table;
    color: white;
  }
  .vertical-center-row {
    display: table-cell;
    vertical-align: middle;
  }
  .vertical-20p {
    padding-top: 20%;
  }
  .vertical-10p {
    padding-top: 10%;
  }
  /*.logo {*/
  /*width: 15em;*/
  /*padding: 3em;*/
  /*}*/
  .loginForm .input-group {
    padding-bottom: 1em;
    height: 0em;
  }
  .input-group input {
    height: 3em;
  }
  .s{
    margin-top: 10px;
  }
  .vCodeImg{
    float: right;
    margin-top: -42px;
  }
  .login{
    margin-top: 20px;
  }
  .company{
    margin-top: 40px;
    color: #282b30;
    font-size: 15px;
  }
  .register{
    margin-top: 10px;
  }
  .jz{
    float: left;
    font-family:"微软雅黑","黑体","宋体";
    margin-left: 2px;
  }
  .wj{
    font-family:"微软雅黑","黑体","宋体";
    cursor: pointer;
    color: #f1573e;
    margin-left: 100px;
  }
  .zc{
    float: right;
    font-family:"微软雅黑","黑体","宋体";
    color : #498dff;
    margin-right: 5px;
    cursor:pointer;
  }
</style>


