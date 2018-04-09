
<template>
<div v-cloak>
  <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
  <div v-show="showList">
    <!-- table -->
    <div class="row center-block">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <el-button @click="add" type="primary" v-show="isPermission('sys:role:save')"><i class="fa fa-plus"></i>&nbsp;新增</el-button>
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
                  <el-table-column prop="roleName" label="角色名称" ></el-table-column>
                  <el-table-column prop="remark" label="备注" ></el-table-column>
                  <el-table-column  label="操作">

                  <template scope="scope">
                    <el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.roleId)" size="small" v-show="isPermission('sys:role:update')">
                     	编辑
                    </el-button>
                    <el-button @click.native.prevent="del(scope.$index, scope.row.roleId)" type="danger" size="small" v-show="isPermission('sys:role:delete')">
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
  <!-- /角色新增 -->
    <el-dialog :title="title" :visible.sync="dialogSaveVisible">
    <el-form ref="sysRole" :model="sysRole" :rules="rules" label-width="80px">

          <el-form-item label="角色名称" prop="roleName" style="margin-top: 10px;">
          <el-input type="text" class="setWidth" v-model="sysRole.roleName" placeholder="角色名称"/>
          </el-form-item>

          <el-form-item label="备注" prop="remark">
            <el-input type="text" class="setWidth"  v-model="sysRole.remark" placeholder="备注"></el-input>
          </el-form-item>

      <div class="form-group">
        <el-form-item label="授权">
        <div class="col-sm-10">
          <ul id="menuTree" class="ztree"></ul>
        </div>
        </el-form-item>
      </div>

      <div class="form-group">
        <el-form-item>
        <el-button type="primary"  @click="submitForm('sysRole')">确定</el-button>
        <el-button @click="reload">返回</el-button>
        </el-form-item>
      </div>
    </el-form>
    </el-dialog>
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
      url: 'nourl',
      title: 'remark'
    }
  },
  check: {
    enable: true,
    nocheckInherit: true
  }
}
var ztree
export default {
  name: 'sysRole',
  data () {
    return {
      showList: true,
      dialogSaveVisible: false,
      sysRole: {},
      tableList: [{}],
      currentPage: 1,
      pageSize: config.pageSize,
      pageSizes: config.pageSizes,
      totalSize: 1,
      roleName: '',
      remark: '',
      treeMenu: {},
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
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
      api.request('POST', '/sys/role/list?rolename=' + _this.roleName + '&currentPage=' + _this.currentPage + '&pageSize=' + _this.pageSize, null).then(function (rsp) {
        if (rsp.data.code === 0) {
          console.log(rsp.data.page.list)
          _this.tableList = rsp.data.page.list
          _this.totalSize = rsp.data.page.totalCount
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
    query: function () {
      var _this = this
      _this.reload()
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
      _this.title = '新增角色'
      // _this.sysRole = {}
      _this.sysRole = {}
      _this.getMenuTree(null)
    },
    update (index, event) {
      var _this = this
      var id = event
      if (id == null) {
        return
      }
      _this.dialogSaveVisible = true
      _this.title = '修改角色'
      _this.getRole(id)
      _this.getMenuTree(id)
    },
    saveOrUpdate (event) {
      var _this = this
      // 获取选择的菜单
      var nodes = ztree.getCheckedNodes(true)
      var menuIdList = []
      for (var i = 0; i < nodes.length; i++) {
        menuIdList.push(nodes[i].menuId)
      }
      _this.sysRole.menuIdList = menuIdList
      console.log(_this.sysRole)
      console.log(_this.sysRole)
      var ids = _this.sysRole.roleId
      var url = _this.sysRole.roleId == null ? '/sys/role/save' : '/sys/role/update'
      api.request('POST', url, _this.sysRole).then(function (rsp) {
        // _this.sysRole = {}
        if (rsp.data.code === 0) {
          console.log(rsp)
          if (ids == null) {
            _this.$message('新增成功')
          } else {
            _this.$message('更新成功')
          }
          // _this.getTableList()
          _this.reload()
        } else {
          console.log(rsp.data.message)
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
        api.request('POST', '/sys/role/delete', delId).then(function (rsp) {
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
    getRole (id) {
      var _this = this
      // _this.sysRole = {}
      api.request('POST', '/sys/role/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          // _this.sysRole = rsp.data.role
          // _this.showList = false
          _this.sysRole = rsp.data.role
           // 勾选角色所拥有的菜单
          var menuIds = _this.sysRole.menuIdList
          for (var i = 0; i < menuIds.length; i++) {
            var node = ztree.getNodeByParam('menuId', menuIds[i])
            ztree.checkNode(node, true, false)
          }
        } else {
          _this.$message.error(rsp.data.msg)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    getMenuTree (roleId) {
      var _this = this
      // 加载菜单树
      /*
      $.get('/sys/menu/perms', function (r) {
        console.log(JSON.parse(r))
        if (JSON.parse(r).code === 0) {
          ztree = window.ztree.init($('#menuTree'), setting, JSON.parse(r).menuList)
          // 展开所有节点
          ztree.expandAll(true)
          if (roleId != null) {
            _this.getRole(roleId)
          }
        }
      })
      */
      api.request('POST', '/sys/menu/perms', null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          ztree = window.ztree.init($('#menuTree'), setting, rsp.data.menuList)
          // 展开所有节点
          ztree.expandAll(true)
          if (roleId != null) {
            _this.getRole(roleId)
          }
        } else {
          _this.$message.error(rsp.data.msg)
        }
      }).catch(error => {
        console.log('error:' + error)
      })
    },
    isPermission (state) {
      return api.hasPermission(state)
    },
    stateFormatter (row, column) {
      if (row.unbalance_factor) {
        return '启用'
      } else {
        return '关闭'
      }
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
.setWidth{
  width: 350px;
  }
.setMargin {
  margin-top: 10px;
}

</style>
