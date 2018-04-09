<template>
  <div id="seniorSearch">
    <el-dialog title="高级搜索" v-model="dialogFormVisible">
      <div>
    <el-row>
      <el-col :span="3">
      <el-button @click="addOr" v-show="!showConditionSelect" class="condition_button">{{condition.label}}</el-button>
      <el-select v-model="condition" placeholder="请选择" v-show="showConditionSelect" size="mini" @change="conditionChange(condition)" style="width: 85px">
      <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item">
      </el-option>
      </el-select>
      </el-col>
      <el-col :span="4">
      <el-button @click="addCondition(clickNum + 1)" class="add_button">+</el-button>
      </el-col>
    </el-row>
    <el-row v-for="(item, index) in row" style="margin-left: 3%;margin-top: 10px">
      <el-col :span="4">
      <el-button v-show="!row[index].showDcheckInfoSelect" class="blue_btn" @click="getDcheckInfoSelect(row[index])">{{row[index].dcheckInfo.columnRemark}}</el-button>
      <el-select v-model="row[index].dcheckInfo" placeholder="请选择" v-show="row[index].showDcheckInfoSelect" size="mini" @change="dcheckInfoChange(row[index])" style="width: 95px;">
      <el-option
      v-for="item in dcheckInfoColumn"
      :key="item.value"
      :label="item.columnRemark"
      :value="item">
      </el-option>
      </el-select>
      </el-col>
      <el-col :span="4">
      <el-button @click="getSymbolConditionSelect(row[index])" v-show="!row[index].showSymbolCondition" class="add_button">{{row[index].symbolCondition.label}}</el-button>
      <el-select v-model="row[index].symbolCondition" placeholder="请选择" v-if="row[index].showSymbolCondition && row[index].dcheckInfo.columnRemark !== '检定员'" size="mini" @change="symbolConditionChange(row[index])" style="width: 95px;">
      <el-option
      v-for="item in symbolConditionList"
      :key="item.value"
      :label="item.label"
      :value="item">
      </el-option>
      </el-select>
        <el-select v-model="row[index].symbolCondition" placeholder="请选择" v-else-if="row[index].showSymbolCondition && row[index].dcheckInfo.columnRemark === '检定员'" size="mini" @change="symbolConditionChange(row[index])" style="width: 95px;">
          <el-option
            v-for="item in symbolConditionList2"
            :key="item.value"
            :label="item.label"
            :value="item">
          </el-option>
        </el-select>
      </el-col>
      <el-button v-if="showInput(row[index])" class="button-new-tag" size="mini" @click="showInput1(row[index])" >{{row[index].inputValue1}}</el-button>
      <el-input
      class="input-new-tag"
      v-if="row[index].inputVisible1"
      placeholder="请输入1"
      v-model="row[index].inputValue1"
      ref="saveTagInput"
      size="mini"
      style="width: 100px"
      >
      </el-input>
      <el-button v-if="row[index].symbolCondition.value === 7 && row[index].dcheckInfo.columnType !== 3" class="button-new-tag" size="mini" @click="showInput2(row[index])">{{row[index].inputValue2}}</el-button>
      <el-input
        class="input-new-tag"
        v-if="row[index].inputVisible2"
        placeholder="请输入2"
        v-model="row[index].inputValue2"
        ref="saveTagInput"
        size="mini"
        style="width: 100px"
      ></el-input>
      <el-date-picker
        v-model="row[index].inputValue1"
        type="date"
        placeholder="选择日期1"
        v-if="row[index].dcheckInfo.columnType === 3"
        size="mini"
        tyle="width: 95px;"
        :picker-options="pickerOptions0">
      </el-date-picker>
      <el-date-picker
        v-model="row[index].inputValue2"
        type="date"
        placeholder="选择日期2"
        v-if="row[index].dcheckInfo.columnType === 3 && row[index].symbolCondition.value === 7"
        size="mini"
        tyle="width: 95px;"
        :picker-options="pickerOptions0">
      </el-date-picker>
      <el-button class="button-new-tag" size="mini" @click="delRow(index)" >x</el-button>
    </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeSerchCondition">取 消</el-button>
        <el-button type="primary" @click="getSerchCondition">确 定</el-button>
      </div>
    </el-dialog>
  </div>

  <!--<el-tag type="danger">并且</el-tag>-->
</template>
<script>
  import ElButton from '../../../../node_modules/element-ui/packages/button/src/button'
  import api from '../../../api'

  export default {
    components: {ElButton},
    name: 'seniorSearch',
    data () {
      return {
        condition: {
          label: '并且',
          value: 1
        },
        showConditionSelect: false,
        options: [
          {
            label: '并且',
            value: 1
          },
          {
            label: '或者',
            value: 2
          }
        ],
        row: [],
        clickNum: -1,
        dcheckInfoColumn: [],
        dcheckInfo: {},
        symbolConditionList: [
          {
            label: '等于',
            value: 1
          },
          {
            label: '不等于',
            value: 2
          },
          {
            label: '大于',
            value: 3
          },
          {
            label: '大于等于',
            value: 4
          },
          {
            label: '小于',
            value: 5
          },
          {
            label: '小于等于',
            value: 6
          },
          {
            label: '介于',
            value: 7
          },
          {
            label: '不介于',
            value: 8
          },
          {
            label: '包含',
            value: 9
          },
          {
            label: '不包含',
            value: 10
          },
          {
            label: '是下列任一项',
            value: 11
          },
          {
            label: '都不是',
            value: 12
          },
          {
            label: '为空',
            value: 13
          },
          {
            label: '不为空',
            value: 14
          }
        ],
        symbolConditionList2: [
          {
            label: '等于',
            value: 1
          },
          {
            label: '不等于',
            value: 2
          },
          {
            label: '包含',
            value: 9
          },
          {
            label: '不包含',
            value: 10
          },
          {
            label: '是下列任一项',
            value: 11
          },
          {
            label: '都不是',
            value: 12
          },
          {
            label: '为空',
            value: 13
          },
          {
            label: '不为空',
            value: 14
          }
        ],
        symbolCondition: {
          label: '等于',
          value: 1
        },
        rowInfo: {},
        pickerOptions0: {
          disabledDate (time) {
            return time.getTime() < Date.now() - 8.64e7
          }
        },
        dataValue1: '',
        dataValue2: '',
        dialogFormShow: false
      }
    },
    props: {
      dialogFormVisible: false
    },
//    created () {
//      this.dialogFormShow = this.dialogFormVisibles
//    },
//    mounted () {
//      this.dialogFormShow = this.dialogFormVisibles
//    },
    methods: {
      addOr () {
//        debugger
        this.showConditionSelect = true
      },
      conditionChange (conditions) {
        this.showConditionSelect = false
      },
      addCondition (index) {
        var _this = this
        _this.clickNum = index
        _this.rowInfo.index = index
        _this.getDcheckInfoColumn(
          function () {
            _this.rowInfo.dcheckInfo = _this.dcheckInfo
            _this.rowInfo.symbolCondition = {label: '等于', value: 1}
            _this.rowInfo.inputValue1 = '请输入1'
            _this.rowInfo.inputValue2 = '请输入2'
            _this.rowInfo.showDcheckInfoSelect = false
            _this.rowInfo.showSymbolCondition = false
            _this.rowInfo.inputVisible1 = false
            _this.rowInfo.inputVisible2 = false
            _this.row.push(JSON.parse(JSON.stringify(_this.rowInfo)))
          }
        )
      },
      getDcheckInfoSelect (row) {
//        debugger
        row.showDcheckInfoSelect = true
      },
      getDcheckInfoColumn (callback) {
        var _this = this
        api.request('POST', '/api/outAuthority/getDcheckInfoColumn', null).then(function (rsp) {
//          debugger
          if (rsp.data.code === 0) {
            _this.dcheckInfoColumn = rsp.data.checkInfoList
            _this.dcheckInfo = rsp.data.checkInfoList[0]
            if (callback !== null && callback !== undefined) {
              callback()
            }
          } else {
          }
        }).catch(error => {
          console.log('error:' + error)
        })
      },
      dcheckInfoChange (row) {
//        var _this = this
//        debugger
        console.log(row)
        row.inputValue1 = '请输入1'
        row.inputValue2 = '请输入2'
        row.showDcheckInfoSelect = false
        row.showSymbolCondition = false
        row.inputVisible1 = false
        row.inputVisible2 = false
        this.showDcheckInfoSelect = false
      },
      getSymbolConditionSelect (row) {
        row.showSymbolCondition = true
      },
      symbolConditionChange (row) {
//        debugger
        row.showSymbolCondition = false
      },
      showInput1 (row) {
        row.inputValue1 = ''
        row.inputVisible1 = true
      },
      showInput2 (row) {
        row.inputValue2 = ''
        row.inputVisible2 = true
      },
      delRow (index) {
//        debugger
        this.row.splice(index, 1)
      },
      closeSerchCondition () {
        this._self.$parent.dialogFormVisibles = false
        this.row = []
//        this.$emit('serchcondition', this.row)
      },
      getSerchCondition () {
        // 传递的总数据
        let data = {}
        // advancesSearchBean字段
        let advancedSearchBean = []
        let advancedSearch = {}
        for (var i = 0; i < this.row.length; i++) {
          let dataCond = {}
          let condition = []
          dataCond.columnName = this.row[i].dcheckInfo.columnName
          dataCond.columnType = this.row[i].dcheckInfo.columnType
          dataCond.condition = this.row[i].symbolCondition.value
          if (this.row[i].inputValue1 === '请输入1') {
            dataCond.value1 = ''
          } else {
            dataCond.value1 = this.row[i].inputValue1
          }
          if (this.row[i].inputValue2 === '请输入2') {
            dataCond.value2 = ''
          } else {
            dataCond.value2 = this.row[i].inputValue2
          }
//          dataCond.value1 = this.row[i].inputValue1
//          dataCond.value2 = this.row[i].inputValue2
          condition.push(dataCond)
          advancedSearch.andOr = this.condition.value
          advancedSearch.condition = condition
        }
        advancedSearchBean.push(advancedSearch)
        data.advancedSearchBean = advancedSearchBean
        data.advancedSearch = true
//        data.checkEndDate = ''
//        data.checkStartDate = ''
//        data.content = ''
//        data.currentPage = 0
//        data.pageSize = 15
//        data.type = ''
//        debugger
        this.$emit('advancedSearchBean', advancedSearchBean)
        this.$emit('advancedSearch', true)
        this.$emit('row', this.row)
        this._self.$parent.getTableList()
        console.log(data)
      },
      showInput (row) {
        if (row.dcheckInfo.columnType !== 3 && !row.inputVisible1 && row.symbolCondition.value !== 12 && row.symbolCondition.value !== 13 && row.symbolCondition.value !== 14) {
          return true
        } else {
          return false
        }
      }
    }
  }
</script>
<style>
  .condition_button{
    padding: 0 5px;
    height: 24px;
    line-height: 22px;
    font-size: 12px;
    border-radius: 4px;
    box-sizing: border-box;
    border: 1px solid transparent;
    white-space: nowrap;
    background-color: rgba(255,73,73,.1);
    border-color: rgba(255,73,73,.2);
    color: #ff4949;
    width: 100px;
  }
  .add_button{
    padding: 0 5px;
    height: 24px;
    line-height: 22px;
    font-size: 12px;
    border-radius: 4px;
    box-sizing: border-box;
    border: 1px solid transparent;
    white-space: nowrap;
    background-color: #e4e8f1;
    border-color: #e4e8f1;
    color: #48576a;
  }
  .blue_btn{
    padding: 0 5px;
    height: 24px;
    line-height: 22px;
    font-size: 12px;
    border-radius: 4px;
    box-sizing: border-box;
    border: 1px solid transparent;
    white-space: nowrap;
    background-color: rgba(32,160,255,.1);
    border-color: rgba(32,160,255,.2);
    color: #20a0ff;
  }
</style>
