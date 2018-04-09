
<template>
  <div v-cloak>
    <!-- <button class="btn btn-primary" @click="open">Open sesame!</button> -->
    <div v-show="showList">
      <!-- table -->
      <div class="row center-block">
        <div class="col-md-12">
          <div class="box">
            <div class="box-header">
              <el-input  v-model="searchCaseKeywords" placeholder="请输入人员姓名" style="width:200px"></el-input>
              <el-button  type="primary" icon="search" @click.native.prevent="searchCase">搜索</el-button>
              <el-button type="primary" @click="add"><i class="fa fa-plus"></i>新增</el-button>
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
                  <el-table-column prop="personnelName" label="人员姓名" ></el-table-column>
                  <el-table-column prop="sex" label="性别" ></el-table-column>
                  <el-table-column prop="age" label="年龄" ></el-table-column>
                  <el-table-column prop="placeOrigin" label="籍贯" ></el-table-column>
                  <el-table-column prop="createTime" label="创建时间" ></el-table-column>
                  <el-table-column  label="操作"width="220">
                    <template scope="scope">
                      <el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.id)" size="small">
                        编辑
                      </el-button>
                      <el-button @click.native.prevent="del(scope.$index, scope.row.id)" type="danger" size="small">
                        删除
                      </el-button>
                      <el-button @click.native.prevent="download(scope.$index, scope.row.id)" type="success" size="small">
                      简历下载
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
    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <form>
        <span>人员姓名</span>
        <input type="text" value="" v-model="personnelName" placeholder="请输入用户名">
        <span>性别</span>
        <input type="text" value="" v-model="sex" placeholder="请输入性别">
        <span>文件上传</span>
        <input type="file" @change="getFile($event)">
        <el-button @click="submitForm($event)">提交</el-button>
      </form>
      <!--<el-form ref="personnel" :model="personnel" :rules="rules" label-width="80px">-->
        <!--<el-form-item label="人员姓名">-->
          <!--<el-input type="text" v-model="personnel.personnelName" placeholder="人员姓名" class="setWidth" ></el-input>-->
        <!--</el-form-item>-->

        <!--<el-form-item label="性别" prop="sex">-->
            <!--<el-radio class="radio" v-model="personnel.sex" label="男" style="margin-top: -10px">男</el-radio>-->
            <!--<el-radio class="radio" v-model="personnel.sex" label="女">女</el-radio>-->
        <!--</el-form-item>-->

        <!--<el-form-item label="年龄">-->
          <!--<el-input type="text" v-model="personnel.age" placeholder="年龄" class="setWidth" ></el-input>-->
        <!--</el-form-item>-->

        <!--<el-form-item label="籍贯">-->
          <!--<el-input type="text" v-model="personnel.placeOrigin" placeholder="籍贯" class="setWidth" ></el-input>-->
        <!--</el-form-item>-->

        <!--<el-form-item label="文件上传">-->
          <!--<input type="file" style="display: none;" id="img-upload" multiple accept="image/*" @change="uploadImg($event)"/>-->
        <!--</el-form-item>-->
        <!--<el-form-item label="文件上传">-->
          <!--<el-input type="file" v-model="personnel.fileName" placeholder="选择文件" class="setWidth"></el-input>-->
        <!--&lt;!&ndash;<el-upload&ndash;&gt;-->
          <!--&lt;!&ndash;class="upload-demo"&ndash;&gt;-->
          <!--&lt;!&ndash;action="/user_book/api/v1/personnel/UploadFile"&ndash;&gt;-->
          <!--&lt;!&ndash;:file-list="fileList"&ndash;&gt;-->
          <!--&lt;!&ndash;:on-change="handleChange"&ndash;&gt;-->
          <!--&lt;!&ndash;:auto-upload="true"&ndash;&gt;-->
           <!--&lt;!&ndash;style="margin-top: 35px;margin-right: 490px">&ndash;&gt;-->
          <!--&lt;!&ndash;<el-button slot="trigger" size="small" type="info">选取文件</el-button>&ndash;&gt;-->
          <!--&lt;!&ndash;&lt;!&ndash;<el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>&ndash;&gt;&ndash;&gt;-->
        <!--&lt;!&ndash;</el-upload>&ndash;&gt;-->
        <!--</el-form-item>-->
        <!--<div class="form-group">-->
          <!--<el-form-item>-->
          <!--<el-button  type="primary" @click="sbuumit">确定</el-button>-->
          <!--<el-button  @click="reload" >返回</el-button>-->
          <!--</el-form-item>-->
        <!--</div>-->
      <!--</el-form>-->
    </el-dialog>
    </div>
</template>

<script>
  import 'element-ui/lib/theme-default/index.css'
  import api from '../../api'
  import config from '../../config'

  export default {
    name: 'personnel',
    data () {
      return {
        showList: true,
        title: null,
        personnel: {},
        tableList: [{}],
        currentPage: 1,
        pageSize: 10,
        pageSizes: [10, 30, 50, 100],
        totalSize: 1,
        dialogFormVisible: false,
        searchCaseKeywords: '',
        file: '',
        fileNames: '',
        sex: '男',
        fileList: [{
          name: '',
          url: '/user_book/api/v1/personnel/UploadFile'
        }]
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
        var data = {'currentPage': _this.currentPage, 'pageSize': _this.pageSize, 'personnelName': _this.searchCaseKeywords}
        api.request('POST', '/api/v1/personnel/list', data).then(function (rsp) {
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
      uploadImg (e) {
      },
      download (index, event) {
        var _this = this
        var id = event
        if (id == null) {
          return
        }
        api.request('POST', '/api/v1/personnel/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.personnel = rsp.data.personnel
            var fileNames = rsp.data.personnel.fileName
            window.location.href = '/user_book/api/v1/personnel/upload?fileName=' + fileNames
            _this.$message('导出成功')
          } else {
            console.log(rsp.data.message)
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      add () {
        var _this = this
        _this.dialogFormVisible = true
        _this.title = '新增人员'
        _this.personnel = {}
        _this.$refs.upload.submit()
      },
      update (index, event) {
        var _this = this
        var id = event
        if (id == null) {
          return
        }
        _this.dialogFormVisible = true
        _this.title = '修改人员'
        _this.$refs.upload.submit()
        api.request('POST', '/api/v1/personnel/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
          if (rsp.data.code === 0) {
            _this.personnel = rsp.data.personnel
          } else {
            console.log(rsp.data.message)
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      saveOrUpdate (event) {
        var _this = this
        var url = _this.personnel.id == null ? '/api/v1/personnel/save' : '/api/v1/personnel/update'
        api.request('POST', url, _this.personnel).then(function (rsp) {
          _this.personnel = {}
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
          api.request('POST', '/api/v1/personnel/delete', delId).then(function (rsp) {
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
        this.dialogFormVisible = false
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
</style>
