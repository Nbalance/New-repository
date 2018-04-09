<template>
  <!--<div class="container container-table">
      <div class="row vertical-10p">
        <div class="container">
          <img src="/static/img/404_img_bg.png" class="center-block logo">
          <div class="text-center col-sm-6 col-sm-offset-3">
            <h1>You are lost.</h1>
            <h4>This page doesn't exist.</h4>
            <router-link to="/">Take me home.</router-link>
          </div>
        </div>
      </div>
  </div>-->
  <div class="warnbg">
    <div style="width: 100%;">
      <img src="/static/img/404_img_bg.png" width="100%" :height="height"/>
      <router-link to="/" class="back">返回首页</router-link>
    </div>

  </div>
</template>
<script>
  export default {
    name: 'NotFound',
    data () {
      return {
        height: 0
      }
    },
    created  () {
      this.autoHeighr()
    },
    methods: {
      autoHeighr () {
        var _this = this
        console.log(document.body.scrollHeight)
        _this.height = document.body.scrollHeight
      }
    }
  }
</script>

<style>
  .warnbg{
    width: 100%;
  }
  .back{
    position: relative;
    left: 55%;
    top: -305px;
    margin-left: 2%;
  }
</style>
