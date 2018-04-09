<template>
  <!-- Main content -->
  <div class="row center-block">
    <!-- Info boxes -->
    <div class="row" style="margin-left: 3px;margin-right: 3px">
    <div class="col-md-3 col-sm-6 col-xs-12">
        <div class="info-box">
          <span class="info-box-icon bg-aqua dashbg-1">
          <img src="../../../static/img/stock/user.png" height="42" width="42"/>
          </span>
          <div class="info-box-content">
            <span class="info-box-number">{{total}}</span>
            <span class="info-box-text">总计人数</span>
          </div>
        </div>
    </div>

      <div class="col-md-3 col-sm-6 col-xs-12">
        <div class="info-box">
          <span class="info-box-icon bg-red dashbg-2">
          <img src="../../../static/img/stock/blue.png" height="42" width="42"/>
          </span>
          <div class="info-box-content">
            <span class="info-box-number">{{totalSize}}</span>
            <span class="info-box-text">访问次数</span>
          </div>
        </div>
      </div>

      <div class="col-md-3 col-sm-6 col-xs-12">
        <div class="info-box">
          <span class="info-box-icon bg-green dashbg-3">
              <img src="../../../static/img/stock/smo.png" height="42" width="42"/>
          </span>
          <div class="info-box-content">
            <span class="info-box-number">{{lines}}</span>
            <span class="info-box-text">在线用户</span>
          </div>
        </div>
      </div>

      <div class="col-md-3 col-sm-6 col-xs-12">
        <div class="info-box">
          <span class="info-box-icon bg-yellow dashbg-4">
          <img src="../../../static/img/stock/userr.png" height="42" width="42"/>
          </span>
          <div class="info-box-content">
            <span class="info-box-number">{{line}}</span>
            <span class="info-box-text">离线用户</span>
          </div>
        </div>
      </div>
    </div>
    <div class="table-date">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
              <el-input  v-model="queryName"  placeholder="请输入用户名" style="width:309px"></el-input>
              <el-button  type="primary" icon="search" @click.native.prevent="searchCase">搜索</el-button>
              <el-button  type="danger" icon="delete" @click.native.prevent="deleteh">清除日志</el-button>
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
      <el-table :data="tableList" border style="width: 100%" @selection-change="handleSelectionChange" tooltip-effect="dark">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="username" label="用户名" ></el-table-column>
        <el-table-column prop="type" label="类型" ></el-table-column>
        <el-table-column prop="detail" label="详情" ></el-table-column>
        <el-table-column prop="ip" label="IP地址" ></el-table-column>
        <el-table-column prop="place" label="归属地" ></el-table-column>
        <el-table-column prop="time" label="时间" ></el-table-column>
      </el-table>
      <el-col class="toolbar" style="padding:10px;">
        <el-pagination  @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-size="pageSize"
                        :page-sizes="pageSizes"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="totalSize" style="float:right">
        </el-pagination>
      </el-col>
    </div>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
  import 'element-ui/lib/theme-default/index.css'
  import api from '../../api'
  export default {
    data () {
      return {
        tableList: [{}],
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 30, 50, 100],
        totalSize: 1,
        queryName: '',
        line: '',
        lines: '',
        total: '',
        multipleSelection: []
      }
    },
    created () {
      this.getTableList()
      this.getTotal()
    },
    methods: {
      searchCase () {
        this.getTableList()
      },
      handleSelectionChange (val) {
        this.multipleSelection = val
        console.log(this.multipleSelection)
      },
      getTableList () {
        var _this = this
        var data = {
          'queryName': _this.queryName,
          'currentPage': _this.currentPage,
          'pageSize': _this.pageSize
        }
        api.request('POST', '/api/v1/syslog/list', data).then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.tableList = rsp.data.page.list
            _this.totalSize = rsp.data.page.totalCount
          } else {
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      getTotal () {
        var _this = this
        api.request('POST', '/api/v1/syslog/lists').then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.line = rsp.data.page.line
            _this.lines = rsp.data.page.lines
            _this.total = rsp.data.page.total
          } else {
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
      deleteh () {
        var _this = this
        if (this.multipleSelection.length === 0) {
          _this.$message.error('请选择一条数据')
        } else {
          _this.deletehCase()
        }
      },
      deletehCase () {
        var _this = this
        var jobIds = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          jobIds.push(this.multipleSelection[i].id)
          console.log(jobIds)
        }
        window.confirm('确定要删除选中的记录？', function () {
          api.request('POST', '/api/v1/syslog/deletes', jobIds).then(function (rsp) {
            if (rsp.data.code === 0) {
              console.log(rsp)
              _this.getTableList()
              _this.$message('删除成功')
            } else {
              window.alert(rsp.data.message)
            }
          }).catch(error => {
            console.log('error:' + error)
          })
        })
      }
    }
  }
</script>
<style >
  .info-box {
    cursor: pointer;
  }
  .info-box-content {
    text-align: center;
    vertical-align: middle;
    display: inherit;
  }
  .fullCanvas {
    width: 100%;
  }
  #equipmentactive, #equipmentonline, #useronline {
    height: 300px;
  }
  .itempadding{
    padding: 0 10px;
  }
  .activefont{
    font-weight:bold;
  }
  .info-box-content  {
    height: 90px;
    line-height: 38px;
  }
  .dashbg-1{
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position:center;
  }
  .dashbg-2{
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position:center;
  }
  .dashbg-3{
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position:center;
  }
  .dashbg-4{
    background-size: 100% 100%;
    background-repeat: no-repeat;
    background-position:center;
  }
  .table-date{
    margin-top: 10px;
  }
</style>
