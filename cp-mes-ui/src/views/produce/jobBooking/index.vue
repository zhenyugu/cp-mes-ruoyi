<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工单编号" prop="sheetNumber">
        <el-input v-model="queryParams.sheetNumber" placeholder="请输入工单编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品编号" prop="productNumber">
        <el-input v-model="queryParams.productNumber" placeholder="请输入产品编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">创建报工</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobBookingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工单编号" align="center" prop="sheetNumber" width="180">
        <template slot-scope="scope">
          <a @click="handleSheetDetail(scope.row)" style="color: #4874cb;">{{ scope.row.sheetNumber }}</a>
        </template>
      </el-table-column>
      <el-table-column label="工序名称" align="center" prop="procedureName" />
      <el-table-column label="工序状态" align="center" prop="procedureStatus">

        <template slot-scope="scope">
          <dict-tag :options="dict.type.procedure_status" :value="scope.row.procedureStatus" />
        </template>
      </el-table-column>
      <el-table-column label="报工数量" align="center" prop="jobBookingNum" />
      <el-table-column label="良品数" align="center" prop="goodQuantity" />
      <el-table-column label="不良品数" align="center" prop="defectQuantity" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="生产人员" align="center" prop="personnelName">
      </el-table-column>
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="产品规格" align="center" prop="productSpecification" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改报工对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工单编号" prop="sheetNumber">
              <el-select v-model="form.sheetNumber" placeholder="请选择工单" @change="sheetChange"
                :disabled="title == '修改报工'">
                <el-option v-for="item in sheetList" :key="item.sheetId" :label="item.sheetNumber"
                  :value="item.sheetNumber"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productNumber">
              <el-input v-model="form.productNumber" placeholder="请输入产品编号" :disabled="true" class="formCell" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序名称" prop="procedureName">
              <el-select v-model="form.procedureName" placeholder="请选择工序" @change="taskChange"
                :disabled="!form.sheetNumber || title == '修改报工'">
                <el-option v-for="item in taskList" :key="item.taskId" :label="item.procedureName"
                  :value="item.procedureName"
                  :disabled="item.procedureStatus === '2' || (!item.reportingAuthority.includes(userId) && item.reportingAuthority !== 'all')"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称" :disabled="true" class="formCell" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序状态" prop="procedureStatus">
              <el-radio-group v-model="form.procedureStatus" :disabled="!form.procedureName || title == '修改报工'" >
                <el-radio v-for="dict in dict.type.procedure_status" :key="dict.value" :label="dict.value">{{ dict.label
                  }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品规格" prop="productSpecification">
              <el-input v-model="form.productSpecification" placeholder="请输入产品规格" :disabled="true" class="formCell" />
            </el-form-item>
          </el-col>
        </el-row> -->
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="生产人员" prop="productionPersonnel">
              <el-select clearable v-model="form.productionPersonnel" placeholder="请选择生产人员"
                :disabled="!form.procedureName">
                <el-option v-for="item in userList" :key="item.userId" :label="item.nickName" :value="item.userId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="display: flex; justify-content: flex-start;">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker clearable v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择报工开始时间" @change="handleReportingTime" :disabled="!form.sheetNumber">
              </el-date-picker>
            </el-form-item>
            <!-- <el-form-item label="开始时间" prop="startTime">
              <el-date-picker clearable v-model="form.startTime" type="date" placeholder="开始日期"
                value-format="yyyy-MM-dd" style="width: 120px;">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectStartTime" label-width="0px">
              <el-time-select v-model="form.selectStartTime" :key="form.selectStartTime ||form.jobBookingId"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间"
                value-format="HH:mm" style="width: 120px;">
              </el-time-select>
            </el-form-item> -->
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="报工数量" prop="jobBookingNum">
              <el-input-number v-model="form.jobBookingNum" placeholder="请输入报工数量" :disabled="!form.procedureName"
                class="formCell" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12" style="display: flex; justify-content: flex-start;">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker clearable v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择报工结束时间" :disabled="!form.sheetNumber" @change="handleReportingTime">
              </el-date-picker>
            </el-form-item>
            <!-- <el-form-item label="结束时间" prop="endTime">
              <el-date-picker clearable v-model="form.endTime" type="date" style="width: 120px;"
                value-format="yyyy-MM-dd" placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectEndTime" label-width="0px">
              <el-time-select v-model="form.selectEndTime" :key="form.selectEndTime ||form.jobBookingId"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间"
                value-format="HH:mm" style="width: 120px;">
              </el-time-select>
            </el-form-item> -->
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="单位" prop="unit">
              <el-select clearable v-model="form.unit" placeholder="请选择单位" :disabled="true">
                <el-option v-for="dict in dict.type.produce_unit" :key="dict.value" :label="dict.value"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报工时长" prop="jobBookingCount">
              <el-input v-model="form.jobBookingCount" placeholder="请输入报工时长" :disabled="true" class="formCell" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="良品数" prop="goodQuantity">
              <el-input-number v-model="form.goodQuantity" placeholder="请输入良品数" :disabled="!form.procedureName"
                class="formCell" :min="0" />
            </el-form-item>

          </el-col>
          <el-col :span="12">
            <el-form-item label="不良品数" prop="defectQuantity">
              <el-input-number v-model="form.defectQuantity" placeholder="请输入不良品数" :disabled="!form.procedureName"
                class="formCell" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="不良品项" prop="defectId">
              <el-input :placeholder="handleDefectExist()" :disabled="!form.procedureName" @focus="handleDefect"
                style="width: 615px;">
                <template slot="prefix">
                  <span v-for="(defect, index) in lastDefect" :key="index"
                    style="padding: 5px; color: rgb(70, 67, 67); background-color: #e9e9e9; border: 1px solid #ccc; margin-right: 5px;">{{
      defect.defectName + ' ' + defect.number }}</span>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm"
          v-if="form.procedureStatus">{{ handleButtonLabel() }}</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改工单对话框 -->
    <el-dialog :title="title" :visible.sync="openSheetDetail" width="1250px" append-to-body>
      <el-form ref="sheetForm" :model="sheetForm" :rules="rules" label-width="110px">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="工单编号" prop="sheetNumber">
              <el-input v-model="sheetForm.sheetNumber" placeholder="请输入工单编号" :disabled="true" style="width: 270px;" />
            </el-form-item>
          </el-col>
          <el-col :span="8" style="display: flex; justify-content: space-between;">
            <el-form-item label="计划开始时间" prop="planStart">
              <el-date-picker clearable v-model="sheetForm.planStart" type="date" placeholder="开始日期" :disabled="true"
                value-format="yyyy-MM-dd" style="width: 135px;">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectStart" label-width="0px">
              <el-time-select v-model="sheetForm.selectStart" :key="sheetForm.selectStart"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间" :disabled="true"
                value-format="HH:mm" style="width: 135px;">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" style="display: flex; justify-content: space-between;">
            <el-form-item label="计划结束时间" prop="planEnd">
              <el-date-picker clearable v-model="sheetForm.planEnd" type="date" style="width: 135px;" :disabled="true"
                value-format="yyyy-MM-dd" placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectEnd" label-width="0px">
              <el-time-select v-model="sheetForm.selectEnd" :key="sheetForm.selectEnd"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间" :disabled="true"
                value-format="HH:mm" style="width: 135px;">
              </el-time-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item label="产品信息" prop="productName">
              <el-select v-model="sheetForm.productName" placeholder="请选择产品信息" :disabled="true" style="width: 270px;">
                <el-option v-for="item in productList" :key="item.productId"
                  :label="item.productNumber + ' - ' + item.productName" :value="item.productId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="计划数" prop="plannedQuantity">
              <el-input-number v-model="sheetForm.plannedQuantity" placeholder="请输入计划数" :min="0" :disabled="true"
                style="width: 270px;" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="工单状态" prop="status">
              <dict-tag :options="dict.type.sheet_status" :value="sheetForm.status" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">

          <el-col :span="8">
            <el-form-item label="客户" prop="clientName">
              <el-select v-model="sheetForm.clientName" placeholder="请选择客户信息" style="width: 270px;" :disabled="true">
                <el-option v-for="item in clientList" :key="item.clientId" :label="item.clientName"
                  :value="item.clientId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="关联单据" prop="relatedDocuments">
              <el-input v-model="sheetForm.relatedDocuments" placeholder="请输入关联单据" style="width: 270px;"
                :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="8" v-if="title == '修改工单'">
            <el-form-item label="二维码">
              <div>
                <canvas ref="canvas" height="0px" width="0px"></canvas>
                <img :src="imageUrl" alt="QR code" height="130px">
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="sheetForm.remark" placeholder="请输入备注" style="width: 270px;"
                :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <div>
          <el-form-item label="生产任务" prop="taskIds">
            <div>
              <el-table v-loading="paramLoading" :data="bindingProcedure" ref="sortableTable" row-key="procedureId">
                <el-table-column type="index" width="55" align="center">
                  <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                </el-table-column>
                <el-table-column label="顺序" type="index" width="55" align="center" />
                <el-table-column label="工序名称" prop="procedureName" align="center">
                </el-table-column>
                <el-table-column label="工序编号" prop="procedureNumber" align="center">
                </el-table-column>
                <el-table-column label="报工权限" prop="reportingName" align="center">
                </el-table-column>
                <el-table-column label="分配列表" prop="distributionList" align="center">
                </el-table-column>
                <el-table-column label="报工数配比" prop="reportingRatio" align="center">
                </el-table-column>
              </el-table>
            </div>
          </el-form-item>
        </div>
      </el-form>
    </el-dialog>
    <!-- 编辑不良项 -->
    <el-dialog :title="'编辑不良项'" :visible.sync="openDefect" width="600px" append-to-body @close="closeOpenDefect">
      <div>
        <el-button type="primary" icon="el-icon-plus" style="margin-bottom: 8px;" @click="addDefect">新增不良项</el-button>
        <div>
          <el-table v-loading="paramLoading" :data="selectedDefect">
            <el-table-column label="不良品名" prop="defectId" align="center">

              <template slot-scope="scope">
                <el-select v-model="scope.row.defectId" placeholder="请选择工序" @change="defectNameChange">
                  <el-option v-for="item in defectList" :key="item.defectId" :label="item.defectName"
                    :value="item.defectId" :disabled="defectNames.includes(item.defectId)"></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="数量" prop="number" align="center">

              <template slot-scope="scope">
                <el-input-number v-model="scope.row.number" placeholder="请输入" :min="1"
                  style="width: 100%;"></el-input-number>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">

              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-delete" @click="delectParam(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitDefect">确 定</el-button>
        <el-button @click="cancelSubmitTask">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJobBooking, getJobBooking, delJobBooking, addJobBooking, updateJobBooking } from "@/api/produce/jobBooking";
import { listSheet, getSheetByNumber } from "@/api/produce/sheet";
import { listTask, updateTask } from "@/api/produce/task";
import { listDefect } from "@/api/basicData/defect";
import { listUser } from "@/api/system/user";
import QRCode from 'qrcode';
import Vue from 'vue'
export default {
  name: "JobBooking",
  dicts: ['procedure_status', 'produce_unit', 'sheet_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      paramLoading: false,
      bindingProcedure: [],
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报工表格数据
      jobBookingList: [],
      //工单表格数据
      sheetList: [],
      //任务工序表格数据
      taskList: [],
      //用户表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openSheetDetail: false,
      openDefect: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sheetNumber: undefined,
        procedureNumber: undefined,
        productNumber: undefined,
        procedureName: undefined,
        procedureStatus: undefined,
        productName: undefined,
        productSpecification: undefined,
        jobBookingNum: undefined,
        pricingMethod: undefined,
        unitPrice: undefined,
        estimatedSalary: undefined,
        goodQuantity: undefined,
        defectQuantity: undefined,
        unit: undefined,
        defectId: undefined,
        productionPersonnel: undefined,
        startTime: undefined,
        endTime: undefined,
        jobBookingCount: undefined,
      },
      queryTaskParams: {},
      // 表单参数
      form: {},
      sheetForm: {},
      // 表单校验
      rules: {
        jobBookingId: [
          { required: true, message: "主键ID不能为空", trigger: "blur" }
        ],
        sheetNumber: [
          { required: true, message: "工单编号不能为空", trigger: "blur" }
        ],
        /* procedureNumber: [
          { required: true, message: "工序编号不能为空", trigger: "blur" }
        ],
        productNumber: [
          { required: true, message: "产品编号不能为空", trigger: "blur" }
        ], */
        productionPersonnel: [
          { required: true, message: "生产人员不能为空", trigger: "blur" }
        ],
        procedureName: [
          { required: true, message: "工序名称不能为空", trigger: "blur" }
        ],
      },
      defectList: [], // 不良项列表
      defectIds: [],
      productList: [],
      task: {},
      imageUrl: '',
      selectedDefect: [],
      lastDefect: [],
      defectNames: '',
      clientList: [],
      userId: this.$store.getters.userId,
    };
  },
  created() {
    this.sheetNumber = this.$route.params && this.$route.params.sheetNumber;
    if (this.sheetNumber) {
      this.queryParams.sheetNumber = this.sheetNumber;
    }
    this.getList();
    this.getSheetList();
    this.getDefectList();
    this.getUserList();
  },
  methods: {
    /** 查询报工列表 */
    getList() {
      this.loading = true;
      listJobBooking(this.queryParams).then(response => {
        this.jobBookingList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询工单列表 */
    getSheetList() {
      listSheet().then(response => {
        this.sheetList = response.rows;
      });
    },
    // 获取不良项列表
    getDefectList() {
      listDefect().then(res => {
        this.defectList = res.rows;
      })
    },
    // 获取报工权限用户列表
    getUserList() {
      listUser().then(res => {
        this.userList = res.rows;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.defectNames = "",
        this.selectedDefect = [],
        this.lastDefect = [],
        this.task = {},
        this.defectIds = [],
        this.form = {
          jobBookingId: undefined,
          sheetNumber: undefined,
          procedureNumber: undefined,
          productNumber: undefined,
          procedureName: undefined,
          procedureStatus: undefined,
          productName: undefined,
          productSpecification: undefined,
          jobBookingNum: 0,
          pricingMethod: undefined,
          unitPrice: undefined,
          estimatedSalary: undefined,
          goodQuantity: 0,
          defectQuantity: 0,
          unit: undefined,
          defectId: undefined,
          defectiveNames: undefined,
          productionPersonnel: undefined,
          personnelName: undefined,
          startTime: undefined,
          endTime: undefined,
          jobBookingCount: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined,
          delFlag: undefined,
          remark: undefined,
          selectStartTime: undefined,
          selectEndTime: undefined,
        };
      this.sheetForm = {},
        this.resetForm("form");
      this.resetForm("sheetForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobBookingId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "创建报工";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const jobBookingId = row.jobBookingId || this.ids
      this.queryTaskParams.sheetNumber = row.sheetNumber;
      listTask(this.queryTaskParams).then(response => {
        this.taskList = response.rows;
      });
      getJobBooking(jobBookingId).then(response => {
        this.loading = false;
        this.form = response.data;
        if (this.form.defectiveNames) {
          this.form.defectiveNames.split("~~").forEach(item => {
            this.lastDefect.push(JSON.parse(item));
            this.selectedDefect.push(JSON.parse(item))
          })
        }
        /* if (this.form.startTime) {
          this.$set(this.form, 'selectStartTime', this.form.startTime.split(' ')[1].substring(0, 5));
          this.form.startTime = this.form.startTime.split(' ')[0];
        }

        if (this.form.endTime) {
          this.$set(this.form, 'selectEndTime', this.form.endTime.split(' ')[1].substring(0, 5));
          this.form.endTime = this.form.endTime.split(' ')[0];
        } */
        this.open = true;
        this.title = "修改报工";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (this.form.jobBookingNum !== this.form.goodQuantity + this.form.defectQuantity) {
          this.$message({
            message: '操作失败，报工数量必须是良品数与不良品数之和!',
            type: 'warning'
          });
          return;
        }
        if (this.form.defectQuantity !== this.selectedDefect.reduce((total, item) => total + item.number, 0)) {
          this.$message({
            message: '操作失败，不良品项与不良品数不匹配!',
            type: 'warning'
          });
          return;
        }
        if (valid) {
          this.buttonLoading = true;
          var user = this.userList.find(u => u.userId == this.form.productionPersonnel);
          this.form.personnelName = user.nickName;
          /* var defectnames = '';
          this.defectIds.forEach(e => {
            var defect = this.defectList.find(d => d.defectId == e)
            defectnames += defect.defectName + ',';
          })
          this.form.defectiveNames = defectnames.substring(0, defectnames.length - 1) */
          this.form.defectiveNames = this.lastDefect.map(d => JSON.stringify(d)).join("~~");
          /* if (this.form.startTime && this.form.selectStartTime) {
            this.form.startTime = this.form.startTime + ' ' + this.form.selectStartTime + ':00';
          }
          if (this.form.endTime && this.form.selectEndTime) {
            this.form.endTime = this.form.endTime + ' ' + this.form.selectEndTime + ':00';
          } */
          if (this.form.jobBookingId != null) {
            updateJobBooking(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            if (this.form.procedureStatus === '0') {
              this.form.procedureStatus = '1'
            } else if (this.form.procedureStatus === '1') {
              this.form.procedureStatus = '2'
            }
            addJobBooking(this.form).then(response => {
              this.$modal.msgSuccess("创建成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.task.procedureStatus = this.form.procedureStatus;
              this.task.goodQuantity = this.form.goodQuantity;
              this.task.defectQuantity = this.form.defectQuantity;
              this.task.plannedQuantity = this.form.jobBookingNum;
              this.task.defectList = this.lastDefect.map(d => JSON.stringify(d)).join("~~");
              updateTask(this.task);
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const jobBookingIds = row.jobBookingId || this.ids;
      this.$modal.confirm('是否确认删除报工编号为"' + jobBookingIds + '"的数据项？').then(() => {
        this.loading = true;
        return delJobBooking(jobBookingIds);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/jobBooking/export', {
        ...this.queryParams
      }, `工单_${new Date().getTime()}.xlsx`)
    },
    sheetChange(data) {
      var sheet = this.sheetList.find(s => s.sheetNumber == data)
      this.form.productNumber = sheet.productNumber;
      this.form.productName = sheet.productName;
      this.form.productSpecification = sheet.productSpecification;
      this.form.unit = sheet.unit;
      this.form.procedureName = undefined;
      this.form.procedureStatus = undefined;
      this.form.jobBookingNum = undefined;
      this.form.goodQuantity = undefined;
      this.form.defectQuantity = undefined;
      this.form.procedureNumber = undefined;
      this.defectIds = [];
      this.form.defectId = undefined;
      this.form.defectiveNames = undefined;
      this.queryTaskParams.sheetNumber = data;
      //this.queryTaskParams.reportingAuthority = this.$store.getters.userId;
      listTask(this.queryTaskParams).then(response => {
        this.taskList = response.rows;
      });
    },
    taskChange(data) {
      var task = this.taskList.find(t => t.procedureName == data);
      this.task = task;
      this.form.procedureStatus = task.procedureStatus;
      this.form.jobBookingNum = task.plannedQuantity;
      this.form.goodQuantity = task.plannedQuantity;
      this.form.defectQuantity = task.defectQuantity;
      this.form.procedureNumber = task.procedureNumber;
      this.defectIds = task.defectiveProducts.split(",");
      this.form.defectId = this.defectIds.join(',');
      this.form.defectiveNames = task.defectiveNames;
    },
    handleDefectChange() {
      this.form.defectId = this.defectIds.join(',');
    },
    handleDefect() {
      this.openDefect = true;
    },
    handleDefectExist() {
      if (this.lastDefect.length > 0) {
        return;
      } else {
        return '请选择不良项'
      }
    },
    hours() {
      const start = new Date(this.form.startTime).getTime();
      const end = new Date(this.form.endTime).getTime();
      const diff = (end - start) / 1000 / 60 / 60; // 将毫秒数转换为小时数
      return parseInt(diff);
    },
    minutes() {
      const start = new Date(this.form.startTime).getTime();
      const end = new Date(this.form.endTime).getTime();
      const diff = (end - start) / 1000 / 60; // 将毫秒数转换为分钟数
      return parseInt(diff % 60); // 获取余数，即分钟数
    },
    handleReportingTime() {
      if (this.form.startTime && this.form.endTime) {
        this.form.jobBookingCount = this.hours() + '小时' + this.minutes() + '分钟';
      } else {
        this.form.jobBookingCount = undefined;
      }
    },
    handleRadio(val) {
      var radioTypes = [];
      val.split("~~").forEach(item => {
        radioTypes.push(JSON.parse(item));
      })
      return radioTypes;
    },
    splitArrayIntoChunks(array, chunkSize) {
      const result = [];
      for (let i = 0; i < array.length; i += chunkSize) {
        result.push(array.slice(i, i + chunkSize));
      }
      return result;
    },
    handleSheetDetail(row) {
      this.loading = true;
      this.reset();
      const sheetNumber = row.sheetNumber || this.ids
      var queryParams = {};
      queryParams.sheetNumber = sheetNumber;
      getSheetByNumber(queryParams).then(response => {
        this.loading = false;
        this.sheetForm = response.data;
        this.generateQRCode();
        this.bindingProcedure = response.data.tasks;
        this.openSheetDetail = true;
        this.title = "工单详情";
      });
    },
    async generateQRCode() {
      try {
        const canvas = this.$refs.canvas;
        const imageUrl = await QRCode.toDataURL(this.form.sheetNumber, { canvas });
        this.imageUrl = imageUrl;
      } catch (error) {
        console.error(error);
      }
    },
    submitDefect() {
      this.selectedDefect = this.selectedDefect.filter(item => item.defectId !== undefined);
      this.lastDefect = Array.from(this.selectedDefect);
      this.openDefect = false;
    },
    cancelSubmitTask() {
      this.selectedDefect = this.selectedDefect.filter(item => item.defectId !== undefined);
      this.lastDefect = Array.from(this.selectedDefect);
      this.openDefect = false;
    },
    closeOpenDefect() {
      this.selectedDefect = this.selectedDefect.filter(item => item.defectId !== undefined);
      this.lastDefect = Array.from(this.selectedDefect);
      this.openDefect = false;
    },
    addDefect() {
      var paramItem = {
        defectId: undefined,
        defectName: undefined,
        number: 1,
      }
      this.selectedDefect.push(paramItem)
    },
    defectNameChange(value) {
      var defect = this.selectedDefect.find(d => d.defectId == value)
      let index = this.selectedDefect.findIndex(d => d.defectId == value)
      var defectName = this.defectList.find(d => d.defectId == value).defectName
      defect.defectName = defectName;
      Vue.set(this.selectedDefect, index, defect);
      this.defectNames = this.selectedDefect.map(d => d.defectId).join(',');
    },

    delectParam(row) {
      let index = this.selectedDefect.findIndex(d => d.defectId == row.defectId)
      if (index < 0) return
      this.selectedDefect.splice(index, 1)
    },
    handleButtonLabel() {
      if (this.title === '修改报工') {
        return '确定';
      } else {
        if (this.form.procedureStatus === '0') {
          return '执行';
        } else if (this.form.procedureStatus === '1') {
          return '完成';
        } else {
          return '确定';
        }
      }
    }

  }
};
</script>
<style scoped>
.customCell {
  width: 270px;
}

.formCell {
  width: 220px;
}
</style>
