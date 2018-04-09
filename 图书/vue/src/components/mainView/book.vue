
<template>
<div v-cloak>
  <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
  <div v-show="showList">
    <!-- table -->
    <div class="row center-block">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <a class="btn btn-primary" @click="add" ><i class="fa fa-plus"></i>&nbsp;新增</a>
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
                                  <el-table-column prop="id" label="" ></el-table-column>
                                  <el-table-column prop="author" label="作者" ></el-table-column>
                                  <el-table-column prop="bookName" label="书名" ></el-table-column>
                                  <el-table-column prop="type" label="图书种类" ></el-table-column>
                                  <el-table-column prop="firstEditionTime" label="出版日期" ></el-table-column>
                                  <el-table-column prop="whetherToLend" label="是否出借(0为出借11出借）" ></el-table-column>
                                  <el-table-column prop="loanDate" label="出借日期" ></el-table-column>
                                  <el-table-column prop="numberOfLoans" label="出借次数" ></el-table-column>
                                  <el-table-column prop="borrowerId" label="借书详情id" ></el-table-column>
                                  <el-table-column prop="remark" label="备注" ></el-table-column>
                                  <el-table-column prop="createBy" label="创建人" ></el-table-column>
                                  <el-table-column prop="createTime" label="创建日期" ></el-table-column>
                                  <el-table-column prop="updateBy" label="更新人" ></el-table-column>
                                  <el-table-column prop="updateTime" label="更新日期" ></el-table-column>
                                  <el-table-column prop="isdelete" label="" ></el-table-column>
                                  <el-table-column  label="操作">
                  <template scope="scope">
                    <el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.id)" size="small" >
                     	编辑
                    </el-button>
                    <el-button @click.native.prevent="del(scope.$index, scope.row.id)" type="danger" size="small" >
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
        <div class="col-sm-2 control-label">作者</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.author" placeholder="作者"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">书名</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.bookName" placeholder="书名"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">图书种类</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.type" placeholder="图书种类"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">出版日期</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.firstEditionTime" placeholder="出版日期"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">是否出借(0为出借11出借）</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.whetherToLend" placeholder="是否出借(0为出借11出借）"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">出借日期</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.loanDate" placeholder="出借日期"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">出借次数</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.numberOfLoans" placeholder="出借次数"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">借书详情id</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.borrowerId" placeholder="借书详情id"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">备注</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.remark" placeholder="备注"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">创建人</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.createBy" placeholder="创建人"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">创建日期</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.createTime" placeholder="创建日期"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">更新人</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.updateBy" placeholder="更新人"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">更新日期</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.updateTime" placeholder="更新日期"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label"></div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="book.isdelete" placeholder=""/>
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
  name: 'book',
  data () {
    return {
      showList: true,
      title: null,
      book: {},
      tableList: [{}],
      currentPage: 1,
      pageSize: 10,
      pageSizes: [10, 30, 50, 100],
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
      api.request('POST', '/cost-manager/project/base/book/list', data).then(function (rsp) {
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
      _this.book = {}
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
      var url = _this.book.id == null ? '/cost-manager/project/base/book/add' : '/cost-manager/project/base/book/update'
      api.request('POST', url, _this.book).then(function (rsp) {
        _this.book = {}
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
        api.request('POST', '/cost-manager/project/base/book/delete', delId).then(function (rsp) {
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
      _this.book = {}
      api.request('POST', '/cost-manager/project/base/book/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          _this.showList = false
          _this.book = rsp.data.book
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

@import url('/static/js/plugins/datatables/dataTables.bootstrap.css');

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
