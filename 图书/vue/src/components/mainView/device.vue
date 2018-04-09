<template>
<div v-cloak>
  <div v-show="showList">
    <!-- table -->
    <div class="row center-block">
      <div class="col-md-12">
        <div class="box">
          <div class="box-header">
            <template>
              <el-select v-model="searchvalue" placeholder="请选择" style="width:100px">
                <el-option key="1" label="设备编号" value="1"> </el-option>
                <el-option key="2" label="设备状态" value="2"> </el-option>
                <el-option key="3" label="绑定状态" value="3"> </el-option>
                <el-option key="4" label="最后上线时间" value="4"> </el-option>
              </el-select>
            </template>
            <el-input  v-model="searchCaseKeywords" v-show="searchvalue!=4" placeholder="请输入关键字" style="width:200px"></el-input>
            <el-date-picker
              v-show="searchvalue==4"
              v-model="rangeTime"
              type="daterange"
              placeholder="选择日期范围">
            </el-date-picker>
            <el-button  type="primary" icon="search" @click.native.prevent="searchCase">搜索</el-button>
            <el-button type="primary" icon="plus" @click="add">新增</el-button>
            <el-button  type="primary" @click="aexport">导出Excel</el-button>
            <el-upload
              class="upload-demo"
              action="/web-backend/api/v1/DeviceManagement/Device/excel"
              :on-success="handleSuccess"
              :data="file">
              <el-button type="primary" @click="xexport">导入excel</el-button>
            </el-upload>
          </div>
          <!-- /.box-header -->
          <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
              <div class="row">
                <div class="col-sm-6">
                  <div class="dataTables_length">
                  </div>
                </div>
              </div>
                <el-table :data="tableList" border style="width: 100%">
                                  <el-table-column prop="name" label="设备名称" ></el-table-column>
                                  <el-table-column prop="deviceNumber" label="设备编号" ></el-table-column>
                                  <el-table-column prop="deviceType" label="设备类型"></el-table-column>
                                  <el-table-column prop="ip" label="设备IP" ></el-table-column>
                                  <el-table-column prop="status" label="设备状态" ></el-table-column>
                                  <el-table-column prop="bindStatus" label="绑定状态" ></el-table-column>
                                  <el-table-column prop="hwVersion" label="设备固件版本" ></el-table-column>
                                  <el-table-column prop="productionTime" label="生产时间" ></el-table-column>
                                  <el-table-column prop="identifier" label="绑定用户" ></el-table-column>
                                  <el-table-column prop="lastOnlineTime" label="最后上线时间" ></el-table-column>
                                  <el-table-column prop="remark" label="备注" ></el-table-column>
                                  <!--<el-table-column  label="操作" width:140px>-->
                  <!--<template scope="scope">-->
                    <!--<el-button prop="id" @click.native.prevent="update(scope.$index, scope.row.id)" size="small">-->
                     	<!--编辑-->
                    <!--</el-button>-->
                    <!--<el-button @click.native.prevent="del(scope.$index, scope.row.id)" type="danger" size="small">-->
                    	  <!--删除-->
                    <!--</el-button>-->
                  <!--</template>-->
                <!--</el-table-column>-->
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
    <!--<form class="form-horizontal">-->
        <div class="col-sm-2">
        <div class="col-sm-10">
          <el-input type="text" class="form-control" v-model="device.identifier" placeholder="用户ID，为电子邮件地址"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">设备名称</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.name" placeholder="设备名称"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">设备编号</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.deviceNumber" placeholder="设备编号"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label"></div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.deviceType" placeholder="设备类型/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">设备登陆点ip</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.ip" placeholder="设备登陆点ip"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">设备状态(0:未激活1：离线2：在线3：异常)</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.status" placeholder="设备状态(0:未激活1：离线2：在线3：异常)"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">设备绑定状态（0：未绑定1：绑定）</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.bindStatus" placeholder="设备绑定状态（0：未绑定1：绑定）"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">设备硬件版本号</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.hwVersion" placeholder="设备硬件版本号"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">生产日期</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.productionTime" placeholder="生产日期"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">最后登陆时间</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.lastOnlineTime" placeholder="最后登陆时间"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">固件id</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.firmWareId" placeholder="固件id"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">备注</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.remark" placeholder="备注"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">创建人</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.createBy" placeholder="创建人"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">创建时间</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.createTime" placeholder="创建时间"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">更新人</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.updateBy" placeholder="更新人"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">更新时间</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.updateTime" placeholder="更新时间"/>
        </div>
      </div>
                		      <div class="form-group">
        <div class="col-sm-2 control-label">是否删除（0：未删除；1：删除）</div>
        <div class="col-sm-10">
          <input type="text" class="form-control" v-model="device.isdelete" placeholder="是否删除（0：未删除；1：删除）"/>
        </div>
      </div>
                      <div class="form-group">
        <div class="col-sm-2 control-label"></div>
        <input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
        <input type="button" class="btn btn-warning" @click="reload" value="返回"/>
      </div>
    <!--</form>-->
  </div>
</div>
</div>
</template>

<script>
import 'element-ui/lib/theme-default/index.css'
import api from '../../api'
import config from '../../config'
import {formatDateByDefault, formatDateOneToTwo} from '../../util/DateUtil'
export default {
  name: 'device',
  data () {
    return {
      showList: true,
      title: null,
      device: {},
      tableList: [{}],
      currentPage: 1,
      pageSize: 10,
      pageSizes: [10, 30, 50, 100],
      totalSize: 1,
      searchvalue: '',
      searchCaseKeywords: '',
      rangeTime: '',
      startTime: '',
      endTime: '',
      file: ''
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
      _this.startTime = formatDateOneToTwo(_this.rangeTime, 0)
      _this.endTime = formatDateOneToTwo(_this.rangeTime, 1)
      var data = {
        'type': _this.searchvalue,
        'queryName': _this.searchCaseKeywords,
        'currentPage': _this.currentPage,
        'pageSize': _this.pageSize,
        'startDate': formatDateByDefault(_this.startTime),
        'endDate': formatDateByDefault(_this.endTime)
      }
      api.request('POST', '/api/v1/DeviceManagement/Device/list', data).then(function (rsp) {
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
      _this.device = {}
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
    aexport () {
      window.location.href = 'http://192.168.1.21:8070/web-backend/api/v1/DeviceManagement/Device/exportmenber'
      this.$message('导出成功')
    },
    handleSuccess () {
      this.getTableList()
      this.$message('导入成功')
    },
    saveOrUpdate (event) {
      var _this = this
      var url = _this.device.id == null ? '/cost-manager/project/base/device/add' : '/cost-manager/project/base/device/update'
      api.request('POST', url, _this.device).then(function (rsp) {
        _this.device = {}
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
        api.request('POST', '/cost-manager/project/base/device/delete', delId).then(function (rsp) {
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
      _this.device = {}
      api.request('POST', '/cost-manager/project/base/device/info/' + id, null, config.contentFromUrlEncode).then(function (rsp) {
        if (rsp.data.code === 0) {
          _this.showList = false
          _this.device = rsp.data.device
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
  .el-upload{
    float: right;
    margin-top: -35px;
  }
  .el-upload__input {
    display: none!important
  }
</style>
