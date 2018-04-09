<template>
<div v-cloak>
  <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
  <div v-show="showList">
    <!-- table -->
    <div class="row center-block">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
         <el-button type="primary" @click="add" v-show="isPermission('sys:menu:save')"><i class="fa fa-plus"></i>&nbsp;新增</el-button>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap" id="example1_wrapper">
              <div class="row">
                <div class="col-sm-6">
                  <div id="example1_length" class="dataTables_length">
                  </div>
                </div>
              </div>
                <el-table :data="tableList" border style="width: 100%">

                  <el-table-column prop="menuId" label="主键ID" ></el-table-column>

                  <el-table-column prop="name" label="菜单名称" ></el-table-column>

                  <el-table-column prop="url" label="菜单URL" ></el-table-column>

                  <el-table-column prop="perms" label="授权" ></el-table-column><!--（多个用逗号分隔，如：user:list,user:create）-->

                  <el-table-column prop="type" label="类型" ></el-table-column><!--（0：目录；1：菜单；2：按钮）-->

                  <el-table-column prop="icon" label="菜单图标" ></el-table-column>

                  <el-table-column prop="orderNum" label="排序" ></el-table-column>

                  <el-table-column prop="remark" label="备注" ></el-table-column>

                  <el-table-column  label="操作">

                  <template scope="scope">
                    <el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.menuId)" size="small" v-show="isPermission('sys:menu:update')">
                     	编辑
                    </el-button>
                    <el-button @click.native.prevent="del(scope.$index, scope.row.menuId)" type="danger" size="small" v-show="isPermission('sys:menu:delete')">
                    	  删除
                    </el-button>
                  </template>
                </el-table-column>
                </el-table>
                <el-col class="toolbar" style="padding:10px;">
                <el-pagination  @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-size="pageSize"
                :page-sizes="pageSizes"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalSize" style="float:right">
                </el-pagination> </el-col>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- /table -->

  <el-dialog :title="title" :visible.sync="dialogSaveVisible">
    <el-form ref="sysMenu" :model="sysMenu" :rules="rules" label-width="80px">

      <el-form-item>
        <label class="radio-inline">
          <el-radio class="radio"v-model="sysMenu.type" :label="0">目录</el-radio>
				</label>
        <label class="radio-inline">
          <el-radio class="radio"v-model="sysMenu.type" :label="1">菜单</el-radio>
				</label>
        <label class="radio-inline">
          <el-radio class="radio"v-model="sysMenu.type" :label="2">按钮</el-radio>
				</label>
      </el-form-item>
      <el-form-item label="菜单名称" prop="name">
          <el-input type="text"class="setWidth"  v-model="sysMenu.name" placeholder="菜单名称"/>
      </el-form-item>

      <el-form-item label="上级菜单" prop="parentName">
          <el-input type="text" class="setWidth" v-model="sysMenu.parentName" @click.native.prevent="menuTree" readonly="true" placeholder="一级菜单"/>
      </el-form-item>

      <el-form-item label="菜单URL" prop="url">
          <el-input type="text" class="setWidth" v-model="sysMenu.url" placeholder="菜单URL"/>
      </el-form-item>


      <el-form-item label="授权" prop="perms">
          <el-input type="text" class="setWidth"  v-model="sysMenu.perms" placeholder="授权"/><!--（多个用逗号分隔，如：user:list,user:create）-->
      </el-form-item>

         <el-form-item label="菜单图标" prop="icon">
          <el-input type="text" class="setWidth" v-model="sysMenu.icon" placeholder="菜单图标"/>
         </el-form-item>

           <el-form-item label="排序">
          <el-input type="text" class="setWidth" v-model="sysMenu.orderNum" placeholder="排序"/>
           </el-form-item>

          <el-form-item label="备注" prop="remark">
          <el-input type="text" class="setWidth" v-model="sysMenu.remark" placeholder="备注"/>
          </el-form-item>

      <div class="form-group">
        <el-form-item>
          <el-button type="primary"  @click="submitForm('sysMenu')">确定</el-button>
          <el-button @click="reload">返回</el-button>
        </el-form-item>
      </div>
    </el-form>
  </el-dialog>
  <!-- 选择菜单 -->
  <div id="menuLayer" style="display: none;padding:10px;">
    <ul id="menuTree" class="ztree"></ul>
  </div>
  </div>
</template>
<script>
import 'element-ui/lib/theme-default/index.css'
import api from '../../api'
import config from '../../config'
import $ from 'jquery'

var setting = {
  data: {
    simpleData: {
      enable: true,
      idKey: 'menuId',
      pIdKey: 'parentId',
      rootPId: -1
    },
    key: {
      url: 'nourl'
    }
  }
}
var ztree
export default {
  name: 'sysMenu',
  data () {
    return {
      showList: true,
      dialogSaveVisible: false,
      sysMenu: {
        parentName: null,
        parentId: 0,
        type: 1,
        orderNum: 0
      },
      tableList: [{}],
      currentPage: 1,
      pageSize: config.pageSize,
      pageSizes: config.pageSizes,
      totalSize: 1,
      menuName: '',
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' }
        ],
        perms: [
          { required: true, message: '请输入授权', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入排序', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择一个菜单功能', trigger: 'blur' }
        ],
        remark: [
          { required: true, message: '请输入备注', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getTableList()
  },
  methods: {
    getTableList () {
      var _this = this
      // this.listLoading = true
      // var data = {'currentPage': _this.currentPage, 'pageSize': _this.pageSize}
      api.request('POST', '/sys/menu/list?currentPage=' + _this.currentPage + '&pageSize=' + _this.pageSize, null).then(function (rsp) {
        if (rsp.data.code === 0) {
          _this.tableList = rsp.data.page.list
          _this.totalSize = rsp.data.page.totalCount
        } else {
          // this.listLoading = false
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    handleSizeChange (pageSize) {
      this.pageSize = pageSize
      this.getTableList()
    },
    handleCurrentChange (currentPage) {
      this.currentPage = currentPage
      this.getTableList()
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
    add () {
      var _this = this
      _this.dialogSaveVisible = true
      _this.title = '新增菜单'
      // _this.sysMenu = {}
      _this.sysMenu = {parentName: null, parentId: 0, type: 1, orderNum: 0}
      _this.getMenu()
    },
    update (index, event) {
      var _this = this
      var id = event
      if (id == null) {
        return
      }
      _this.dialogSaveVisible = true
      _this.title = '修改菜单'
      /*
      $.get('/sys/menu/info/' + id, function (r) {
        _this.sysMenu = JSON.parse(r).menu
      })
      */
      api.request('POST', '/sys/menu/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
        _this.sysMenu = {}
        if (rsp.data.code === 0) {
          _this.sysMenu = rsp.data.menu
        } else {
          _this.$message.error(rsp.data.msg)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
      _this.getMenu()
    },
    saveOrUpdate (event) {
      var _this = this
      var ids = _this.sysMenu.menuId
      var url = _this.sysMenu.menuId == null ? '/sys/menu/save' : '/sys/menu/update'
      api.request('POST', url, _this.sysMenu).then(function (rsp) {
        _this.sysMenu = {}
        if (rsp.data.code === 0) {
          console.log(rsp)
          if (ids == null) {
            _this.$message('新增成功')
          } else {
            _this.$message('更新成功')
          }
          _this.getTableList()
          _this.reload()
        } else {
          _this.$message.error(rsp.data.msg)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    del (index, event) {
      var ids = event
      if (ids == null) {
        return
      }
      var _this = this
      var delId = [ids]
      window.confirm('确定要删除选中的记录？', function () {
        api.request('POST', '/sys/menu/delete', delId).then(function (rsp) {
          if (rsp.data.code === 0) {
            console.log(rsp)
            _this.getTableList()
            _this.reload()
            _this.$message('删除成功')
          } else {
            window.alert(rsp.data.message)
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      })
    },
    reload: function (event) {
      this.dialogSaveVisible = false
      this.getTableList()
    },
    menuTree () {
      var _this = this
      window.open(function (index) {
        var node = ztree.getSelectedNodes()
        // 选择上级菜单
        _this.sysMenu.parentId = node[0].menuId
        _this.sysMenu.parentName = node[0].name
      })
    },
    getMenu (menuId) {
      var _this = this
      // 加载菜单树
      /*
      $.get('/sys/menu/select', function (r) {
        ztree = window.ztree.init($('#menuTree'), setting, JSON.parse(r).menuList)
        var node = ztree.getNodeByParam('menuId', _this.sysMenu.parentId)
        ztree.selectNode(node)
        console.log(node.name)
        _this.sysMenu.parentName = node.name
      })
      */
      api.request('POST', '/sys/menu/select', null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          ztree = window.ztree.init($('#menuTree'), setting, rsp.data.menuList)
          var node = ztree.getNodeByParam('menuId', _this.sysMenu.parentId)
          ztree.selectNode(node)
          console.log(node.name)
          _this.sysMenu.parentName = node.name
        } else {
          _this.$message.error(rsp.data.msg)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    isPermission (state) {
      return api.hasPermission(state)
    }
  }
}
</script>

<style>

/*@import url('/static/js/plugins/datatables/dataTables.bootstrap.css');*/
/*@import url('/static/js/plugins/ztree/css/zTreeStyle/zTreeStyle.css');*/
/*@import url('/static/js/plugins/ztree/css/metroStyle/metroStyle.css');*/

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
.setMargin {
  margin-top: 10px;
}
</style>
