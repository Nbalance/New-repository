<template>
  <div v-cloak>
    <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
    <div v-show="showList" v-loading="loading" element-loading-text="正在加载...">
      <!-- table -->
      <div class="row center-block">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
              <el-button class="btn btn-primary ag" @click="backup" type="primary" v-show="isPermission('db:backup:backup')"><i class="fa fa-plus"></i>&nbsp;备份</el-button>
              <!-- <el-button @click="init" type="danger" v-show="isPermission('db:backup:initDb')"><i class="fa fa-plus"></i>&nbsp;初始化</el-button> -->
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
                <el-table :data="tableList" border style="width: 100%;">
                  <el-table-column prop="index" label="序号" type="index" width="70"></el-table-column>
                  <!-- <el-table-column prop="id" label="标准器ID" ></el-table-column> -->
                  <el-table-column prop="fileName" label="文件名"></el-table-column>
                  <el-table-column prop="createBy" label="备份人"></el-table-column>
                  <el-table-column prop="createTime" label="备份日期"></el-table-column>
                  <!--<el-table-column prop="remark" label="备注" ></el-table-column>-->
                  <el-table-column  label="操作" width="200px;">
                    <template scope="scope">
                      <el-button @click.native.prevent="recover(scope.$index, scope.row.id)" size="small" v-show="isPermission('db:backup:returnBack')">
                        恢复
                      </el-button>
                      <el-button @click.native.prevent="educe(scope.$index, scope.row.id)" size="small" v-show="isPermission('db:backup:exportDb')">
                        导出
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
  </div>
</template>

<script>
  import 'element-ui/lib/theme-default/index.css'
  import api from '../../api'
  import config from '../../config'

  export default {
    data () {
      return {
        remark: '',
        createTime: '',
        fileName: '',
        createBy: '',
        list: [{}],
        showFlag1: true,
        showList: true,
        title: null,
        count: '',
        tableList: [{}],
        statusList: [{}],
        currentPage: 1,
        pageSize: config.pageSize,
        pageSizes: config.pageSizes,
        totalSize: 1,
        dbBackupEntity: {},
        loading: false
      }
    },
    created () {
      this.getTableList()
      // 获取状态列表
      this.getStatusList()
    },
    methods: {
      getTableList () {
        var _this = this
        var data = {'currentPage': _this.currentPage, 'pageSize': _this.pageSize}
        api.request('POST', '/api/db/list', data).then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.tableList = rsp.data.page.list
            _this.totalSize = rsp.data.page.totalCount
          } else {
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      backup () {
        var _this = this
        window.confirm('确定要备份？', function () {
          _this.loading = true
          api.request('GET', '/api/db/backup').then(function (rsp) {
            if (rsp.data.code === 0) {
              // console.log('备份成功')
              _this.loading = false
              _this.$message('备份成功')
              _this.getTableList()
            } else {
              _this.loading = false
              _this.$message('备份失败')
              _this.getTableList()
            }
          }).catch(error => {
            _this.loading = false
            console.log('error:' + error)
            _this.getTableList()
          })
        })
      },
      init () {
        var _this = this
        window.confirm('确定要初始化？（初始化将清空所有数据）', function () {
          _this.loading = true
          api.request('GET', '/api/db/initDb').then(function (rsp) {
            if (rsp.data.code === 0) {
              // console.log('初始化成功')
              _this.loading = false
              _this.$message('初始化成功')
              _this.getTableList()
            } else {
              _this.loading = false
              _this.$message('初始化失败')
              _this.getTableList()
            }
          }).catch(error => {
            _this.loading = false
            console.log('error:' + error)
            _this.getTableList()
          })
        })
      },
      recover (index, event) {
        var _this = this
        var ids = event
        if (ids == null) {
          return
        }
        window.confirm('确定要恢复选中的记录？', function () {
          _this.dbBackupEntity.id = ids
          var data = _this.dbBackupEntity
          _this.loading = true
          api.request('post', '/api/db/returnBack', data).then(function (rsp) {
            if (rsp.data.code === 0) {
              _this.loading = false
//            _this.logout()
              _this.$message('恢复成功')
              _this.getTableList()
            } else {
              _this.loading = false
              _this.$message('恢复失败')
            }
          }).catch(error => {
            _this.loading = false
            console.log('error:' + error)
          })
        })
      },
      educe (index, event) {
        var _this = this
        var ids = event
        if (ids == null) {
          return
        }
        window.confirm('确定要导出选中的记录？', function () {
          _this.dbBackupEntity.id = ids
          window.location.href = window.location.origin + '/user_book/api/db/exportDb/' + _this.dbBackupEntity.id
          /**
          api.request('post', '/api/db/exportDb', _this.dbBackupEntity).then(function (rsp) {
            if (rsp.data.code === 0) {
              _this.$message('导出成功')
            } else {
              _this.$message('导出失败')
            }
          }).catch(error => {
            console.log('error:' + error)
          })
          */
        })
      },
      isPermission (state) {
        return api.hasPermission(state)
      },
      handleSizeChange (pageSize) {
        this.pageSize = pageSize
        this.getTableList()
      },
      handleCurrentChange (currentPage) {
        this.currentPage = currentPage
        this.getTableList()
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
  .ag{
    margin-top: -4px;
  }
  .zuo{
    margin-left: 0px;
  }
  .setMargin{
    width: 100px;
  }
</style>
