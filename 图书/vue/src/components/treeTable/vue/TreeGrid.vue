<template>
  <el-table
    :data="data"
    border
    style="width: 100%"
    :row-style="showTr">
    <el-table-column v-for="(column, index) in columns" :key="column.dataIndex"
      :label="column.text">
      <template scope="scope">
        <span v-if="spaceIconShow(index,scope)" v-for="(space, levelIndex) in scope.row._level" class="ms-tree-space"></span>
        <button class="button is-primary is-small btn" v-if="toggleIconShow(index,scope.row)" @click="toggle(scope.$index,scope)">
          <i v-if="!scope.row._expanded" aria-hidden="true"><img src="/static/img/more_nor.png"/></i>
          <i v-if="scope.row._expanded" aria-hidden="true"><img src="/static/img/more_sel.png"/></i>
        </button>
        <span v-else-if="index===0" class="ms-tree-space"></span>
        {{scope.row[column.dataIndex]}}
      </template>
    </el-table-column>
    <el-table-column label="操作" v-if="treeType === 'normal'" width="260">
      <template scope="scope">
        <!--<button type="button" class="el-button el-button&#45;&#45;default el-button&#45;&#45;small">
          <router-link
            :to="{ path: requestUrl }"
            tag="span"
            type="text"
            @click="handleUpdate(scope)">
            编辑
          </router-link>
        </button>-->
        <el-button
          size="small"
          @click="handleUpdate(scope)">
          编辑
        </el-button>
        <el-button
          size="small"
          type="danger"
          @click="handleDelete(scope)">
          删除
        </el-button>
        <!--<button type="button" class="el-button el-button&#45;&#45;success el-button&#45;&#45;small">
          <router-link :to="{ path: requestUrl, query: {parentId: scope.row.parentId} }"
                       tag="span">
            添加下级
          </router-link>
        </button>-->
        <el-button
          size="small"
          type="info"
          @click="handleAddTree(scope)">
          添加下级
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
<script>
  import Utils from '../utils/index.js'
//  import Vue from 'vue'
  export default {
    name: 'tree-grid',
    props: {
// 该属性是确认父组件传过来的数据是否已经是树形结构了，如果是，则不需要进行树形格式化
      treeStructure: {
        type: Boolean,
        default: function () {
          return false
        }
      },
// 这是相应的字段展示
      columns: {
        type: Array,
        default: function () {
          return []
        }
      },
// 这是数据源
      dataSource: {
        type: Array,
        default: function () {
          return []
        }
      },
// 这个作用是根据自己需求来的，比如在操作中涉及相关按钮编辑，删除等，需要向服务端发送请求，则可以把url传过来
      requestUrl: {
        type: String,
        default: function () {
          return ''
        }
      },
// 这个是是否展示操作列
      treeType: {
        type: String,
        default: function () {
          return 'normal'
        }
      },
// 是否默认展开所有树
      defaultExpandAll: {
        type: Boolean,
        default: function () {
          return false
        }
      }
    },
    data () {
      return {
        expanded: []
      }
    },
    computed: {
    // 格式化数据源
      data: function () {
        let me = this
        if (me.treeStructure) {
          let data = Utils.MSDataTransfer.treeToArray(me.dataSource, null, null, me.defaultExpandAll)
          console.log('computed' + data)
          //
          console.log('expanded:' + this.expanded)
          for (var j = 0; j < this.expanded.length; j++) {
            for (var i = 0; i < data.length; i++) {
              var record = data[i]
              if (record.id === this.expanded[j]) {
                record._expanded = true
                break
              }
            }
          }

          return data
        }
        return me.dataSource
      }
    },
    methods: {
    // 显示行
      showTr: function (row, index) {
        let show = (row._parent ? (row._parent._expanded && row._parent._show) : true)
        row._show = show
        return show ? '' : 'display:none;'
      },
    // 展开下级
      toggle: function (trIndex, scope) {
        let me = this
        let record = me.data[trIndex]
        record._expanded = !record._expanded
        // 保存扩展状态
        if (record._expanded) {
          this.expanded.push(record.id)
        } else {
          this.expanded.pop(record.id)
        }

        if (scope.row.parentId !== null && scope.row.remark !== 0) {
          scope._self.$parent.getTableList(1, scope.row.id)
        }
      },
    // 显示层级关系的空格和图标
      spaceIconShow (index, scope) {
        let me = this
        if ((me.treeStructure && index === 0) || (me.treeStructure && scope.row.remark === 1 && index === 0)) {
          return true
        }
        return false
      },
    // 点击展开和关闭的时候，图标的切换
      toggleIconShow (index, record) {
        let me = this
        if ((me.treeStructure && index === 0 && record.list && record.list.length > 0) || (me.treeStructure && index === 0 && record.remark === 1)) {
        // if (me.treeStructure && index === 0 && record.list && record.list.length > 0) {
          return true
        }
        return false
      },
      handleDelete (e) {
        console.log(e)
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
        }).then(() => {
          e._self.$parent.del(e.row)
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      handleUpdate (e) {
        console.log(e)
        e._self.$parent.handleUpdate(e.row)
      },
      handleAddTree (e) {
        console.log(e)
        e._self.$parent.handleAdd(e.row)
      }
    }
  }
</script>
<style scoped>
  .ms-tree-space{position: relative;
    top: 1px;
    display: inline-block;
    font-family: 'Glyphicons Halflings';
    font-style: normal;
    font-weight: 400;
    line-height: 1;
    width: 18px;
    height: 14px;}
  .ms-tree-space::before{content: ""}
  table td{
    line-height: 26px;
  }
  .btn{
    border: none;
    /*background-color: white;*/
    background: transparent;
  }
</style>
