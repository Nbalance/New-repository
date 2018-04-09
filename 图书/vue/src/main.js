// Import System requirements
import Vue from 'vue'
import VueRouter from 'vue-router'
import { sync } from 'vuex-router-sync'
import routes from './routes'
import store from './store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import echarts from 'echarts'

// Import Helpers for filters
import { domain, count, prettyDate, pluralize } from './filters'

// import { SweetModal, SweetModalTab } from 'sweet-modal-vue'
import common from '../ignore_lib/common.js'

// Import Views - Top level
import AppView from './components/App.vue'

import DatePicker from 'vue-datepicker'

// Import Install and register helper items
Vue.filter('count', count)
Vue.filter('domain', domain)
Vue.filter('prettyDate', prettyDate)
Vue.filter('pluralize', pluralize)
Vue.prototype.$echarts = echarts

// configuration vee-validate
// import cn from 'vee-validate/dist/locale/zh_CN'
// import VeeValidate, { Validator } from 'vee-validate'
// const config = {
//   locale: 'zh_CN'
// }
// Add locale helper.
// Validator.addLocale(cn)
// Vue.use(VeeValidate, config)

Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.use(common)
Vue.use(DatePicker)

// Routing logic
var router = new VueRouter({
  routes: routes,
  mode: 'history',
  scrollBehavior: function (to, from, savedPosition) {
    return savedPosition || { x: 0, y: 0 }
  }
})
// 全局导航钩子
router.beforeEach((to, from, next) => {
  // 判断该路由是否需要登录权限
  if (to.meta.auth) {
    // 通过vuex state获取当前的token是否存在
    console.log('seac---->' + window.gtstore)
    var user = window.sessionStorage.getItem('SET_USER')
    if (!isEmptyObject(user)) {
      next()
    } else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  } else {
    next()
  }
})

window.router = router

function isEmptyObject (obj) {
  for (var key in obj) {
    return false
  }
  return true
}
sync(store, router)

// Start out app!
// eslint-disable-next-line no-new
new Vue({
  el: '#root',
  router: router,
  store: store,
  components: {
    // SweetModal,
    // SweetModalTab
  },
  render: h => h(AppView)
})

// Check local storage to handle refreshes
if (window.localStorage) {
  var localUserString = window.sessionStorage.getItem('user') || 'null'
  var localUser = JSON.parse(localUserString)

  if (localUser && store.state.user !== localUser) {
    store.commit('SET_USER', localUser)
    store.commit('SET_TOKEN', window.localStorage.getItem('token'))
  }
}

window.gtstore = store

// let instance = new Validator({ trueField: 'percentNum' })

// Also there is an instance 'extend' method for convience.
// instance.extend('falsy', (value) => ! value);

// instance.attach('falseField', 'falsy');

