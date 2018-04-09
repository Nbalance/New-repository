<template>
  <div v-cloak>
    <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
    <div v-show="showList" v-loading="loading" element-loading-text="正在加载...">
      <!-- table -->
      <div class="row center-block">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
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
                  <el-table-column prop="beanName" label="bean名称"></el-table-column>
                  <el-table-column prop="methodName" label="方法名称"></el-table-column>
                  <el-table-column prop="params" label="参数"></el-table-column>
                  <el-table-column prop="error" label="失败信息"></el-table-column>
                  <el-table-column prop="times" label="耗时(单位：毫秒)"></el-table-column>
                  <el-table-column prop="status" label="状态" ></el-table-column>

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
              <div class="form-group setM noShow" style="margin-left: 45%;padding-bottom: 20px;">
                <el-button @click="reloadSch">返回</el-button>
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
        showList0: false,
        showList1: false,
        title: null,
        count: '',
        tableList: [{}],
        statusList: [{}],
        currentPage: 1,
        pageSize: config.pageSize,
        pageSizes: config.pageSizes,
        totalSize: 1,
        dbBackupEntity: {},
        loading: false,
        multipleSelection: [],
        scheduleJob: {jobId: ''}
      }
    },
    created () {
      var jobId = this.$route.query.jobId
      this.getTableList(jobId)
      // 获取状态列表
      this.getStatusList()
    },
    methods: {
      getTableList (jobId) {
        var _this = this
        var data = {'jobId': jobId, 'currentPage': _this.currentPage, 'pageSize': _this.pageSize}
        api.request('POST', '/sys/scheduleLog/list', data).then(function (rsp) {
          if (rsp.data.code === 0) {
            console.log(rsp)
            _this.tableList = rsp.data.page.list
            _this.totalSize = rsp.data.page.totalCount
          } else {
            console.log(rsp.data.message)
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      add () {
        var _this = this
        _this.showList = false
        _this.showList0 = true
        _this.showList1 = false
        _this.title = '新增'
        _this.scheduleJob = {}
      },
      searchByCondition () {
        this.currentPage = 1
        this.pageSize = config.pageSize
        this.getTableList()
      },
      update () {
        var _this = this
        if (this.multipleSelection.length === 0) {
          _this.$message('请选择一条数据')
          return
        } else if (this.multipleSelection.length > 1) {
          _this.$message('只能选择一条')
          return
        }
//        var updateId = [ids]
        _this.showList = false
        _this.showList0 = false
        _this.showList1 = true
        _this.title = '修改'
        _this.scheduleJob = {}
        _this.getInfo(this.multipleSelection[0].jobId)
      },
      getInfo (jobId) {
        var _this = this
        _this.scheduleJob = {}
        api.request('POST', '/sys/schedule/info/' + jobId, null, config.contentFromUrlEncode).then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.scheduleJob = rsp.data.schedule
          } else {
            console.log(rsp.data.message)
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      save () {
        var _this = this
        api.request('POST', '/sys/schedule/save', _this.scheduleJob).then(function (rsp) {
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
      Update () {
        var _this = this
        _this.scheduleJob.jobId = this.multipleSelection[0].jobId
        console.log('++' + this.multipleSelection[0].jobId)
        api.request('POST', '/sys/schedule/update', _this.scheduleJob).then(function (rsp) {
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
      del () {
        var _this = this
        var jobIds = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          jobIds.push(this.multipleSelection[i].jobId)
        }
        window.confirm('确定要删除选中的记录？', function () {
          api.request('POST', '/sys/schedule/delete', jobIds).then(function (rsp) {
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
      standstill () {
        var _this = this
        var jobIds = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          jobIds.push(this.multipleSelection[i].jobId)
        }
        window.confirm('确定要暂停选中的记录？', function () {
          api.request('POST', '/sys/schedule/pause', jobIds).then(function (rsp) {
            if (rsp.data.code === 0) {
              console.log(rsp)
              _this.$message('暂停成功')
            } else {
              window.alert(rsp.data.message)
            }
          }).catch(error => {
            console.log('error:' + error)
          })
        })
      },
      immed () {
        var _this = this
        var jobIds = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          jobIds.push(this.multipleSelection[i].jobId)
        }
        window.confirm('确定要执行选中的记录？', function () {
          api.request('POST', '/sys/schedule/run', jobIds).then(function (rsp) {
            if (rsp.data.code === 0) {
              console.log(rsp)
              _this.$message('执行成功')
            } else {
              window.alert(rsp.data.message)
            }
          }).catch(error => {
            console.log('error:' + error)
          })
        })
      },
      tLogList () {
      },
      reload: function (event) {
        this.showList = true
        this.showList0 = false
        this.showList1 = false
      },
//      getInfo (updateId) {
//        var _this = this
//        api.request('', '', ).then(function (rsp) {
//
//        })
//      },
//      backup () {
//        var _this = this
//        window.confirm('确定要备份？', function () {
//          _this.loading = true
//          api.request('GET', '/api/db/backup').then(function (rsp) {
//            if (rsp.data.code === 0) {
//              _this.loading = false
//              console.log('备份成功')
//              _this.getTableList()
//              _this.$message('备份成功')
//            } else {
//              _this.$message('备份失败')
//            }
//          }).catch(error => {
//            console.log('error:' + error)
//          })
//        })
//      },
      handleSizeChange (pageSize) {
        this.pageSize = pageSize
        this.getTableList()
      },
      handleCurrentChange (currentPage) {
        this.currentPage = currentPage
        this.getTableList()
      },
      recover (index, event) {
        var _this = this
        var jobIds = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          jobIds.push(this.multipleSelection[i].jobId)
        }
        window.confirm('确定要恢复选中的记录？', function () {
          _this.loading = true
          api.request('post', '/sys/schedule/resume', jobIds).then(function (rsp) {
            if (rsp.data.code === 0) {
              _this.loading = false
//              _this.logout()
              _this.$message('恢复成功')
            } else {
              _this.$message('恢复失败')
            }
          }).catch(error => {
            console.log('error:' + error)
          })
        })
      },
//      educe (index, event) {
//        var _this = this
//        var ids = event
//        if (ids == null) {
//          return
//        }
//        window.confirm('确定要导出选中的记录？', function () {
//          _this.dbBackupEntity.id = ids
//          window.location.href = window.location.origin + '/smartmeasure_platform/api/db/exportDb/' + _this.dbBackupEntity.id
//          /**
//          api.request('post', '/api/db/exportDb', _this.dbBackupEntity).then(function (rsp) {
//            if (rsp.data.code === 0) {
//              _this.$message('导出成功')
//            } else {
//              _this.$message('导出失败')
//            }
//          }).catch(error => {
//            console.log('error:' + error)
//          })
//          */
//        })
//      },
      handleSelectionChange (val) {
        this.multipleSelection = val
      },
//      getDeliveryOrder () {
//        var ids = []
//        for (var i = 0; i < this.multipleSelection.length; i++) {
//          ids.push(this.multipleSelection[i].id)
//        }
//      },
      isPermission (state) {
//        console.log(state, api.hasPermission(state))
        return api.hasPermission(state)
      },
      reloadSch: function () {
        var _this = this
        _this.$router.push({path: 'timedtask'})
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
