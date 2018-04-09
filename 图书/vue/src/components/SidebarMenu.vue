<template>
  <ul class="sidebar-menu">
    <li class="pageLink" v-for="item in menuList">
          <a v-if="item.type === 0" href="javascript:;">
          <img v-if="isCustomIcon(item.icon)" :src="'../'+item.icon"><template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i></template>
          <span class="margin_left15">{{item.name}}</span>
          <i class="fa fa-angle-left fa-fw pull-right"></i>
          </a>
          <ul v-if="item.type === 0" class="treeview-menu">
            <li :item="item1" v-for="item in item.list">
              <a v-if="item.type === 0" href="javascript:;">
                <img v-if="isCustomIcon(item.icon)" :src="'../'+item.icon"><template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i></template>
                <span class="margin_left15">{{item.name}}</span>
                <i class="fa fa-angle-left fa-fw pull-right"></i>
              </a>
                <ul v-if="item.type === 0" class="treeview-menu">
                    <li :item="item" v-for="item in item.list">
                        <a v-if="item.type === 0" href="javascript:;">
                           <img v-if="isCustomIcon(item.icon)" :src="'../'+item.icon"><template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i></template>
                           <span class="margin_left15">{{item.name}}</span>
                           <i class="fa fa-angle-left fa-fw pull-right"></i>
                        </a>
                        <router-link v-if="item.type === 1" :to="'../dashboard/'+item.url" >
                        <img v-if="isCustomIcon(item.icon)" :src="'../'+item.icon"><template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i>
                        </template> <span class="margin_left15">{{item.name}}</span></router-link>
                     </li>
                </ul>
                <router-link v-if="item.type === 1" :to="'../dashboard/'+item.url">
                <img v-if="isCustomIcon(item.icon)" :src="'../'+item.icon">
                <template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i>
                </template> <span class="margin_left15">{{item.name}}</span></router-link>
             </li>
          </ul>
          <router-link v-if="item.type === 1" :to="'../dashboard/'+item.url">
          <img v-if="isCustomIcon(item.icon)" :src="'../'+item.icon">
          <template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i>
          </template> <span class="margin_left15">{{item.name}}</span></router-link>
      </li>
  </ul>
</template>
<script>
import $ from 'jquery'
import api from '../api'

export default {
  name: 'SidebarMenu',
  props: {item: {}},
  data () {
    return {
      menuList: [{}]
    }
  },
  created  () {
    this.getMenuList()
  },
  methods: {
    toggleMenu (event) {
      // remove active from li
      var active = document.querySelector('li.pageLink.active')

      if (active) {
        active.classList.remove('active')
      }
      // window.$('li.pageLink.active').removeClass('active')
      // Add it to the item that was clicked
      event.toElement.parentElement.className = 'pageLink active'
    },
    isCustomIcon (iconPath) {
      if (iconPath === null) {
        return false
      } else {
        if (iconPath.indexOf('/') >= 0) {
          return true
        } else {
          return false
        }
      }
    },
    isEmpyIcon (iconPath) {
      if (iconPath == null) {
        return true
      } else {
        return false
      }
    },
    getMenuList (event) {
      var _this = this
      /*
      $.getJSON('/cost-manager/sys/menu/user?_' + $.now(), function (r) {
        self.menuList = r.menuList
        console.log(self.menuList)
        // return this.menuList
      })
      */
      api.request('post', '/sys/menu/user?_' + $.now(), null)
        .then(function (rsp) {
          console.log(rsp)
          if (rsp.data.code === 0) {
            console.log(rsp.data)
            _this.menuList = rsp.data.menuList
          } else {
            // refreshCode()
            // 登录失败提示
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    getUser (event) {
      $.getJSON('/cost-manager/sys/user/info?_' + $.now(), function (r) {
        console.log(r)
        // vm.user = r.user;
      })
    }
  }
}
</script>
<style>
  /* override default */
  .sidebar-menu>li>a {
    padding: 12px 15px 12px 15px;
  }

  .sidebar-menu li.active>a>.fa-angle-left, .sidebar-menu li.active>a>.pull-right-container>.fa-angle-left {
    animation-name: rotate;
    animation-duration: .2s;
    animation-fill-mode: forwards;
  }

  @keyframes rotate {
    0% {
      transform: rotate(0deg);
    }

    100% {
      transform: rotate(-90deg);
    }
  }
</style>
