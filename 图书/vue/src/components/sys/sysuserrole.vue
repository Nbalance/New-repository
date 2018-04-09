
<template>
<div v-cloak>
  <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
  <div v-show="showList">
    <!-- table -->
    <div class="row center-block">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <a class="btn btn-primary" @click="add" v-show="isPermission('sysuserrole:create')"><i class="fa fa-plus"></i>&nbsp;新增</a>
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
                                  <el-table-column prop=id" label="主键ID"></el-table-column>
                  <el-table-column prop="id" label="主键ID" ></el-table-column>
                                  <el-table-column prop=userId" label="用户ID"></el-table-column>
                  <el-table-column prop="userId" label="用户ID" ></el-table-column>
                                  <el-table-column prop=roleId" label="角色ID"></el-table-column>
                  <el-table-column prop="roleId" label="角色ID" ></el-table-column>
                                  <el-table-column  label="操作">

                  <template scope="scope">
                    <el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.id)" size="small" v-show="isPermission('sysuserrole:update')">
                     	编辑
                    </el-button>
                    <el-button @click.native.prevent="del(scope.$index, scope.row.id)" type="danger" size="small" v-show="isPermission('sysuserrole:delete')">
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

  <div class="panel panel-default" v-if="!showList">
    <div class="panel-heading">{{title}}</div>
    <form class="form-horizontal">
    			        		      <div class="form-group">
        <div class="col-sm-2 control-label">用户ID</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysUserRole.userId" placeholder="用户ID"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">角色ID</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysUserRole.roleId" placeholder="角色ID"/>
        </div>
      </div>
                      <div class="form-group">
        <div class="col-sm-2 control-label"></div>
        <input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
        <input type="button" class="btn btn-warning" @click="reload" value="返回"/>
      </div>
    </form>
  </div>
</div>
</template>

<script>
import 'element-ui/lib/theme-default/index.css'
import api from '../../api'
import config from '../../config'

export default {
  name: 'sysUserRole',
  data () {
    return {
      showList: true,
      title: null,
      sysUserRole: {},
      tableList: [{}],
      currentPage: 1,
      pageSize: config.pageSize,
      pageSizes: config.pageSizes,
      totalSize: 1
    }
  },
  created () {
    this.getTableList()
  },
  methods: {
    getTableList () {
      var _this = this
      // this.listLoading = true
      var data = {'currentPage': _this.currentPage, 'pageSize': _this.pageSize}
      api.request('POST', '/cost-manager/project/base/sysUserRole/list', data).then(function (rsp) {
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
    add () {
      var _this = this
      _this.showList = false
      _this.title = '新增'
      _this.sysUserRole = {}
    },
    update (index, event) {
      var _this = this
      var id = event
      if (id == null) {
        return
      }
      _this.title = '修改'
      _this.getInfo(id)
    },
    saveOrUpdate (event) {
      var _this = this
      var url = _this.sysUserRole.id == null ? '/cost-manager/project/base/sysUserRole/create' : '/cost-manager/project/base/sysUserRole/update'
      api.request('POST', url, _this.sysUserRole).then(function (rsp) {
        _this.sysUserRole = {}
        if (rsp.data.code === 0) {
          console.log(rsp)
          _this.getTableList()
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
        api.request('POST', '/cost-manager/project/base/sysUserRole/delete', delId).then(function (rsp) {
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
      this.showList = true
    },
    getInfo (id) {
      var _this = this
      _this.sysUserRole = {}
      api.request('POST', '/cost-manager/project/base/sysUserRole/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          _this.showList = false
          _this.sysUserRole = rsp.data.sysUserRole
        } else {
          console.log(rsp.data.message)
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
</style>
