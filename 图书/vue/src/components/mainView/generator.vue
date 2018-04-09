<template>
  <div v-cloak>
    <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
    <div v-show="showList">
      <!-- table -->
      <div class="row center-block">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
              <el-input  v-model="searchCaseKeywords" placeholder="请输入关键字" style="width:200px"></el-input>
              <el-button type="primary" icon="search" @click="searchCase">搜索</el-button>
              <el-button type="primary" icon="date" @click="saveCode()">生成代码</el-button>
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
                <el-table ref="multipleTable" :data="tableList" border tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
                  <el-table-column type="selection" width="55"></el-table-column>
                  <el-table-column prop="tableName" label="表名" width="300"></el-table-column>
                  <el-table-column prop="engine" label="Engine" width="120"></el-table-column>
                  <el-table-column prop="tableComment" label="表备注" width="300"></el-table-column>
                  <el-table-column prop="createTime" label="创建时间" ></el-table-column>
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
  </div>
</template>
<script>
  import 'element-ui/lib/theme-default/index.css'
  import api from '../../api'
  import config from '../../config'

  export default {
    name: 'generator',
    data () {
      return {
        showList: true,
        generator: {},
        tableList: [{}],
        tableNames: [],
        tables: '',
        multipleSelection: [],
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 30, 50, 100],
        totalSize: 1,
        searchCaseKeywords: ''
      }
    },
    created () {
      this.getTableList()
    },
    methods: {
      searchCase () {
        this.getTableList()
      },
      getTableList () {
        var _this = this
        // this.listLoading = true
        var data = {
          'currentPage': _this.currentPage,
          'pageSize': _this.pageSize,
          'tableName': _this.searchCaseKeywords
        }
        api.request('POST', '/sys/generator/list', data).then(function (rsp) {
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
      handleSelectionChange (val) {
        this.multipleSelection = val
        console.log(val)
      },
      saveCode (rows) {
        var tableList = []
        for (var i = 0; i < this.multipleSelection.length; i++) {
          tableList.push(this.multipleSelection[i].tableName)
          var tableNames = tableList
          if (tableNames == null) {
            return
          }
        }
        window.location.href = '/user_book/sys/generator/code?tables=' + JSON.stringify(tableNames)
//        window.location.href = 'http://192.168.1.21:8070/user_book/sys/generator/code'
//        this.$message('代码生成成功')
      },
      getInfo (userId) {
        var _this = this
        _this.appUser = {}
        api.request('POST', '/api/sys/user/info' + userId, null, config.contentFromUrlEncode).then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.showList = false
            _this.appUser = rsp.data.appUser
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
  .rolejs{
    width: 1000px;
    margin:auto;
  }
</style>
