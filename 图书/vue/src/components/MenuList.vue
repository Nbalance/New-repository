<template>
<li class="menu-item">
      <!-- <a v-if="item.type === 0" href="javascript:;">
      /* '<img v-if="item.icon != null" :src="item.icon">', */
      <img v-if="isCustomIcon(item.icon)" :src="item.icon"><template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i></template>
      <span class="margin_left15">{{item.name}}</span>
      <i class="fa fa-angle-left pull-right"></i>
      </a>
      <ul v-if="item.type === 0" class="treeview-menu">
      <menu-item :item="item" v-for="item in item.list"></menu-item>
      </ul>
      <a v-if="item.type === 1"><img v-if="isCustomIcon(item.icon)" :src="item.icon"><template v-else> <i v-if="isEmpyIcon(item.icon) == false" :class="item.icon"></i> <i v-else class="fa fa-circle-o"></i> </template> {{item.name}}</a> -->
      </li>
</template>
<script>
export default {
  name: 'MenuList',
  // props: {item: {}},
  methods: {
    isCustomIcon: function (iconPath) {
      if (iconPath.indexOf('/') >= 0) {
        return true
      } else {
        return false
      }
    },
    isEmpyIcon: function (iconPath) {
      if (iconPath == null) {
        return true
      } else {
        return false
      }
    }
  }
}
</script>
