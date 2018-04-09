
<template>
<div v-cloak>
  <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
  <div v-show="showList">
    <!-- table -->
    <div class="row center-block">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
          <el-button type="primary" @click="add" v-show="isPermission('sys:config:save')"><i class="fa fa-plus"></i>&nbsp;新增</el-button>
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
                  <!--
                  <el-table-column prop="id" label="主键ID" ></el-table-column>
                  -->
                  <el-table-column prop="key" label="参数名" ></el-table-column>

                  <el-table-column prop="value" label="参数值" ></el-table-column>

                  <el-table-column prop="remark" label="备注" ></el-table-column>

                  <!--<el-table-column prop="remark" label="类型（0：目录；1：菜单；2：按钮）" ></el-table-column>

                  <el-table-column prop="createBy" label="创建者" ></el-table-column>

                  <el-table-column prop="createTime" label="创建时间" ></el-table-column>

                  <el-table-column prop="updateBy" label="更新者" ></el-table-column>

                  <el-table-column prop="updateTime" label="更新时间" ></el-table-column>

                  <el-table-column prop="isdelete" label="是否删除（0：未删除；1：删除）" ></el-table-column>-->
                                  <el-table-column  label="操作">

                  <template scope="scope">
                    <el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.id)" size="small" v-show="isPermission('sys:config:update')">
                     	编辑
                    </el-button>
                    <el-button @click.native.prevent="del(scope.$index, scope.row.id)" type="danger" size="small" v-show="isPermission('sys:config:delete')">
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
    <el-form ref="sysConfig" :model="sysConfig" :rules="rules" label-width="80px">
      <el-form-item label="参数名"  prop="key" style="margin-top: 10px;">
          <el-input type="text" class="setWidth" v-model="sysConfig.key" placeholder="参数名"/>
      </el-form-item>
      <el-form-item label="参数值" prop="value">
      <el-input type="text" class="setWidth" v-model="sysConfig.value" placeholder="参数值"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
          <el-input type="text" class="setWidth" v-model="sysConfig.remark" placeholder="备注"/>
      </el-form-item>
                		     <!-- <div class="form-group">
        <div class="col-sm-2 control-label">创建者</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysConfig.createBy" placeholder="创建者"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">创建时间</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysConfig.createTime" placeholder="创建时间"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">更新者</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysConfig.updateBy" placeholder="更新者"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">更新时间</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysConfig.updateTime" placeholder="更新时间"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">是否删除（0：未删除；1：删除）</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="sysConfig.isdelete" placeholder="是否删除（0：未删除；1：删除）"/>
        </div>
      </div>-->
      <div class="form-group">
        <el-form-item>
          <el-button type="primary"  @click="submitForm('sysConfig')">确定</el-button>
          <el-button @click="reload">返回</el-button>
        </el-form-item>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
import 'element-ui/lib/theme-default/index.css'
import api from '../../api'
import config from '../../config'

export default {
  name: 'sysConfig',
  data () {
    return {
      showList: true,
      title: null,
      sysConfig: {},
      tableList: [{}],
      currentPage: 1,
      pageSize: config.pageSize,
      pageSizes: config.pageSizes,
      totalSize: 1,
      rules: {
        key: [
          { required: true, message: '请输入参数名', trigger: 'blur' }
        ],
        value: [
          { required: true, message: '请输入参数值', trigger: 'blur' }
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
      api.request('POST', '/sys/config/list?currentPage=' + _this.currentPage + '&pageSize=' + _this.pageSize + '&key=' + '', null).then(function (rsp) {
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
      _this.showList = false
      _this.title = '新增'
      _this.sysConfig = {}
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
      var ids = _this.sysConfig.id
      var url = _this.sysConfig.id == null ? '/sys/config/save' : '/sys/config/update'
      api.request('POST', url, _this.sysConfig).then(function (rsp) {
        _this.sysConfig = {}
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
        api.request('POST', '/sys/config/delete', delId).then(function (rsp) {
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
      _this.sysConfig = {}
      api.request('POST', '/sys/config/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          _this.showList = false
          _this.sysConfig = rsp.data.config
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
