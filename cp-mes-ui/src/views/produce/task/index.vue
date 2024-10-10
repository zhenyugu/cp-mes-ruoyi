<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="工单ID" prop="sheetId">
          <el-input
            v-model="queryParams.sheetId"
            placeholder="请输入工单ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工单编号" prop="sheetNumber">
          <el-input
            v-model="queryParams.sheetNumber"
            placeholder="请输入工单编号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="产品编号" prop="productNumber">
          <el-input
            v-model="queryParams.productNumber"
            placeholder="请输入产品编号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="产品名称" prop="productName">
          <el-input
            v-model="queryParams.productName"
            placeholder="请输入产品名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="产品规格" prop="productSpecification">
          <el-input
            v-model="queryParams.productSpecification"
            placeholder="请输入产品规格"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工序ID" prop="procedureId">
          <el-input
            v-model="queryParams.procedureId"
            placeholder="请输入工序ID"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工序编号" prop="procedureNumber">
          <el-input
            v-model="queryParams.procedureNumber"
            placeholder="请输入工序编号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
      <el-form-item label="工序名称" prop="procedureName">
        <el-select v-model="queryParams.procedureName" placeholder="请选择工序名称" clearable
          @keyup.enter.native="handleQuery">
          <el-option v-for="item in procedureList" :key="item.procedureId" :label="item.procedureName"
            :value="item.procedureName">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工序状态" prop="procedureStatus">
        <el-select v-model="queryParams.procedureStatus" placeholder="请选择状态" clearable
          @keyup.enter.native="handleQuery">
          <el-option v-for="item in dict.type.procedure_status" :key="item.value" :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="报工权限" prop="reportingAuthority">
        <el-input v-model="queryParams.reportingAuthority" placeholder="请输入报工权限" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="报工人" prop="reportingName">
        <el-input v-model="queryParams.reportingName" placeholder="请输入报工人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="分配列表" prop="distributionList">
        <el-input v-model="queryParams.distributionList" placeholder="请输入分配列表" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="报工数配比" prop="reportingRatio">
        <el-input v-model="queryParams.reportingRatio" placeholder="请输入报工数配比" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划数" prop="plannedQuantity">
        <el-input v-model="queryParams.plannedQuantity" placeholder="请输入计划数" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="良品数" prop="goodQuantity">
        <el-input v-model="queryParams.goodQuantity" placeholder="请输入良品数" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="不良品数" prop="defectQuantity">
        <el-input v-model="queryParams.defectQuantity" placeholder="请输入不良品数" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="不良品列表" prop="defectiveProducts">
        <el-input v-model="queryParams.defectiveProducts" placeholder="请输入不良品列表" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="不良品名列表" prop="defectiveNames">
        <el-input v-model="queryParams.defectiveNames" placeholder="请输入不良品名列表" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="计划开始时间" prop="startTimePlan">
        <el-date-picker clearable v-model="queryParams.startTimePlan" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择计划开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="计划结束时间" prop="endTimePlan">
        <el-date-picker clearable v-model="queryParams.endTimePlan" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择计划结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际开始时间" prop="startTime">
        <el-date-picker clearable v-model="queryParams.startTime" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择实际开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际结束时间" prop="endTime">
        <el-date-picker clearable v-model="queryParams.endTime" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择实际结束时间">
        </el-date-picker>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="taskId" v-if="true"/>
        <el-table-column label="工单ID" align="center" prop="sheetId" /> -->
      <el-table-column label="工单编号" align="center" prop="sheetNumber" width="180" />
      <!-- <el-table-column label="产品编号" align="center" prop="productNumber" width="180" /> -->
      <el-table-column label="产品名称" align="center" prop="productName" />
      <!-- <el-table-column label="产品规格" align="center" prop="productSpecification" /> -->
      <!-- <el-table-column label="工序ID" align="center" prop="procedureId" /> -->
      <!-- <el-table-column label="工序编号" align="center" prop="procedureNumber" width="180" /> -->
      <el-table-column label="工序名称" align="center" prop="procedureName" />
      <!-- <el-table-column label="报工权限" align="center" prop="reportingAuthority" /> -->
      <el-table-column label="报工权限" align="center" prop="reportingName" />
      <el-table-column label="分配列表" align="center" prop="distributionList" />
      <el-table-column label="报工数配比" align="center" prop="reportingRatio" width="100" />
      <el-table-column label="任务状态" align="center" prop="procedureStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.procedure_status" :value="scope.row.procedureStatus" />
        </template>
      </el-table-column>
      <el-table-column label="计划数" align="center" prop="plannedQuantity" />
      <el-table-column label="良品数" align="center" prop="goodQuantity" />
      <el-table-column label="不良品数" align="center" prop="defectQuantity" />
      <el-table-column label="完成人" align="center" prop="finishName" />
      <el-table-column label="完成时间" align="center" prop="finishTime" width="180" />
      <!-- <el-table-column label="不良品列表" align="center" prop="defectiveProducts" /> -->
      <!-- <el-table-column label="不良品名列表" align="center" prop="defectiveNames" /> -->
      <!-- <el-table-column label="计划开始时间" align="center" prop="startTimePlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTimePlan, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划结束时间" align="center" prop="endTimePlan" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTimePlan, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="95" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="850px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工单编号" prop="sheetNumber">
              <el-input v-model="form.sheetNumber" placeholder="请输入工单编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报工权限" prop="reportingName">
              <el-input v-model="form.reportingName" placeholder="请输入报工权限" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productNumber">
              <el-input v-model="form.productNumber" placeholder="请输入产品编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序编号" prop="procedureNumber">
              <el-input v-model="form.procedureNumber" placeholder="请输入工序编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工序名称" prop="procedureName">
              <el-input v-model="form.procedureName" placeholder="请输入工序名称" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="报工数配比" prop="reportingRatio">
              <el-input-number style="width: 270px;" v-model="form.reportingRatio" placeholder="请输入报工数配比" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划数" prop="plannedQuantity">
              <el-input-number style="width: 270px;" v-model="form.plannedQuantity" placeholder="请输入计划数" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="良品数" prop="goodQuantity">
              <el-input-number style="width: 270px;" v-model="form.goodQuantity" placeholder="请输入良品数" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="不良品数" prop="defectQuantity">
              <el-input-number style="width: 270px;" v-model="form.defectQuantity" placeholder="请输入不良品数" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12" style="display: flex; justify-content: space-between;">
            <!-- <el-form-item label="计划开始时间" prop="startTimePlan">
              <el-date-picker clearable v-model="form.startTimePlan" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择计划开始时间">
              </el-date-picker>
            </el-form-item> -->
            <el-form-item label="计划开始时间" prop="startTimePlan">
              <el-date-picker clearable v-model="form.startTimePlan" type="date" placeholder="开始日期"
                value-format="yyyy-MM-dd" style="width: 135px;">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectStartPlan" label-width="0px">
              <el-time-select v-model="form.selectStartPlan" :key="form.selectStartPlan||form.taskId"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间"
                value-format="HH:mm" style="width: 135px;">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="display: flex; justify-content: space-between;">
            <!-- <el-form-item label="计划结束时间" prop="endTimePlan">
              <el-date-picker clearable v-model="form.endTimePlan" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择计划结束时间">
              </el-date-picker>
            </el-form-item> -->
            <el-form-item label="计划结束时间" prop="endTimePlan">
              <el-date-picker clearable v-model="form.endTimePlan" type="date" style="width: 135px;"
                value-format="yyyy-MM-dd" placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectEndPlan" label-width="0px">
              <el-time-select v-model="form.selectEndPlan" :key="form.selectEndPlan ||form.taskId"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间"
                value-format="HH:mm" style="width: 135px;">
              </el-time-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12" style="display: flex; justify-content: space-between;">
            <!-- <el-form-item label="实际开始时间" prop="startTime">
              <el-date-picker clearable v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择实际开始时间">
              </el-date-picker>
            </el-form-item> -->
            <el-form-item label="实际开始时间" prop="startTime">
              <el-date-picker clearable v-model="form.startTime" type="date" placeholder="开始日期"
                value-format="yyyy-MM-dd" style="width: 135px;">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectStartTime" label-width="0px">
              <el-time-select v-model="form.selectStartTime" :key="form.selectStartTime ||form.taskId"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间"
                value-format="HH:mm" style="width: 135px;">
              </el-time-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="display: flex; justify-content: space-between;">
            <el-form-item label="实际结束时间" prop="endTime">
              <el-date-picker clearable v-model="form.endTime" type="date" style="width: 135px;"
                value-format="yyyy-MM-dd" placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item prop="selectEndTime" label-width="0px">
              <el-time-select v-model="form.selectEndTime" :key="form.selectEndTime ||form.taskId"
                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间"
                value-format="HH:mm" style="width: 135px;">
              </el-time-select>
            </el-form-item>
            <!-- <el-form-item label="实际结束时间" prop="endTime">
              <el-date-picker clearable v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择实际结束时间">
              </el-date-picker>
            </el-form-item> -->
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTask, getTask, delTask, addTask, updateTask } from "@/api/produce/task";
import {
  listProcedure,
} from "@/api/basicData/procedure";
export default {
  name: "Task",
  dicts: ['procedure_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
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
      // 任务表格数据
      taskList: [],
      taskStatus: undefined,
      //工序表格数据
      procedureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sheetId: undefined,
        sheetNumber: undefined,
        productNumber: undefined,
        productName: undefined,
        productSpecification: undefined,
        procedureId: undefined,
        procedureNumber: undefined,
        procedureName: undefined,
        reportingAuthority: undefined,
        reportingName: undefined,
        distributionList: undefined,
        reportingRatio: undefined,
        procedureStatus: undefined,
        plannedQuantity: undefined,
        goodQuantity: undefined,
        defectQuantity: undefined,
        defectiveProducts: undefined,
        defectiveNames: undefined,
        startTimePlan: undefined,
        endTimePlan: undefined,
        startTime: undefined,
        endTime: undefined,

      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskId: [
          { required: true, message: "主键ID不能为空", trigger: "blur" }
        ],
        procedureNumber: [
          { required: true, message: "工序编号不能为空", trigger: "blur" }
        ],
        procedureName: [
          { required: true, message: "工序名称不能为空", trigger: "blur" }
        ],
        reportingName: [
          { required: true, message: "报工人不能为空", trigger: "blur" }
        ],
        reportingRatio: [
          { required: true, message: "报工数配比不能为空", trigger: "blur" }
        ],
        startTimePlan: [
          { required: true, message: "计划开始时间不能为空", trigger: "blur" }
        ],
        endTimePlan: [
          { required: true, message: "计划结束时间不能为空", trigger: "blur" }
        ],
        selectStartPlan: [
          { required: true, message: "计划开始时间不能为空", trigger: "blur" }
        ],
        selectEndPlan: [
          { required: true, message: "计划结束时间不能为空", trigger: "blur" }
        ],

      }
    };
  },

  created() {
    this.getList();
    this.getProcedureList();
  },
  watch: {
    $route: {
      handler(to, from) {
        this.taskStatus = this.$route.query && this.$route.query.taskStatus;
        if (this.taskStatus) {
          this.getList();
        }
      },
      deep: true,
      immediate: true,
    }
  },
  methods: {
    /** 查询任务列表 */
    getList() {
      this.loading = true;
      //this.queryParams.reportingAuthority = this.$store.getters.userId;
      listTask(this.queryParams).then(response => {
        this.taskList = response.rows;
        if (this.taskStatus) {
          const now = new Date();
          var produce = [];
          var near = [];
          var outline = [];
          this.taskList.filter(item => item.procedureStatus != '2' && item.procedureStatus != '0').forEach(item => {
            if (item.endTimePlan) {
              if (this.hours(now, item.endTimePlan) > 24) {
                produce.push(item);
              } else if (this.hours(now, item.endTimePlan) >= 0 && this.hours(now, item.endTimePlan) <= 24) {
                near.push(item);
              } else if (this.hours(now, item.endTimePlan) < 0) {
                outline.push(item);
              }
            } else {
              produce.push(item);
            }
          })
          if (this.taskStatus == 0) {
            this.taskList = produce;
          } else if (this.taskStatus == 1) {
            this.taskList = near;
          } else if (this.taskStatus == 2) {
            this.taskList = outline;
          }
        }
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取工序列表 */
    getProcedureList() {
      listProcedure().then(res => {
        this.procedureList = res.rows;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        taskId: undefined,
        sheetId: undefined,
        sheetNumber: undefined,
        productNumber: undefined,
        productName: undefined,
        productSpecification: undefined,
        procedureId: undefined,
        procedureNumber: undefined,
        procedureName: undefined,
        reportingAuthority: undefined,
        reportingName: undefined,
        distributionList: undefined,
        reportingRatio: undefined,
        procedureStatus: undefined,
        plannedQuantity: undefined,
        goodQuantity: undefined,
        defectQuantity: undefined,
        defectiveProducts: undefined,
        defectiveNames: undefined,
        startTimePlan: undefined,
        endTimePlan: undefined,
        startTime: undefined,
        endTime: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        delFlag: undefined,
        remark: undefined,
        selectStartPlan: undefined,
        selectEndPlan: undefined,
        selectStartTime: undefined,
        selectEndTime: undefined,
      };
      this.resetForm("form");
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
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const taskId = row.taskId || this.ids
      getTask(taskId).then(response => {
        this.loading = false;
        this.form = response.data;
        if (this.form.startTimePlan) {
          this.$set(this.form, 'selectStartPlan', this.form.startTimePlan.split(' ')[1].substring(0, 5));
          this.form.startTimePlan = this.form.startTimePlan.split(' ')[0];
        }

        if (this.form.endTimePlan) {
          this.$set(this.form, 'selectEndPlan', this.form.endTimePlan.split(' ')[1].substring(0, 5));
          this.form.endTimePlan = this.form.endTimePlan.split(' ')[0];
        }

        if (this.form.startTime) {
          this.$set(this.form, 'selectStartTime', this.form.startTime.split(' ')[1].substring(0, 5));
          this.form.startTime = this.form.startTime.split(' ')[0];
        }

        if (this.form.endTime) {
          this.$set(this.form, 'selectEndTime', this.form.endTime.split(' ')[1].substring(0, 5));
          this.form.endTime = this.form.endTime.split(' ')[0];
        }

        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          this.form.startTimePlan = this.form.startTimePlan + ' ' + this.form.selectStartPlan + ':00';
          this.form.endTimePlan = this.form.endTimePlan + ' ' + this.form.selectEndPlan + ':00';
          if (this.form.startTime && this.form.selectStartTime) {
            this.form.startTime = this.form.startTime + ' ' + this.form.selectStartTime + ':00';
          }
          if (this.form.endTime && this.form.selectEndTime) {
            this.form.endTime = this.form.endTime + ' ' + this.form.selectEndTime + ':00';
          }
          if (this.form.taskId != null) {
            updateTask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addTask(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const taskIds = row.taskId || this.ids;
      this.$modal.confirm('是否确认删除任务编号为"' + taskIds + '"的数据项？').then(() => {
        this.loading = true;
        return delTask(taskIds);
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
      this.download('system/task/export', {
        ...this.queryParams
      }, `task_${new Date().getTime()}.xlsx`)
    },
    hours(startTime, endTime) {
      const start = new Date(startTime).getTime();
      const end = new Date(endTime).getTime();
      const diff = (end - start) / 1000 / 60 / 60; // 将毫秒数转换为小时数
      return parseInt(diff);
    },
  }
};
</script>
