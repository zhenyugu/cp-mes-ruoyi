<template>
  <div class="progress-bar">
    <el-popover placement="bottom-start" :title="taskName" width="280" trigger="hover">
      <div class="hover-content">
        <div style="display: flex; ">
          <span>时间进度%：</span>
          <el-progress :percentage="handleTimeProgress(task)" color="#6EEE96" style="width: 60%;"></el-progress>
        </div>
        <div style="display: flex;">
          <span>任务进度%：</span>
          <el-progress :percentage="handleTaskProgress(task)" color="#6EEE96" style="width: 60%;"></el-progress>
        </div>
        <div style="display: flex;">
          <span>计划结束时间：</span>
          <div>{{ task.endTimePlan }}</div>
        </div>
        <div style="display: flex;">
          <span>逾期天数：</span>
          <div style="color: #ff3b30;">{{ handleDelay(task) }}</div>
        </div>
      </div>
      <el-divider class="custom-divider"></el-divider>
      <div style="display: flex;">
        <div>
          <el-button class="small-button" size="mini"
            :disabled="(!task.reportingAuthority.includes(this.$store.getters.userId) && task.reportingAuthority !=='all') || task.procedureStatus === '2' " 
            round @click="handleAddJobBooking(task)"><i class="el-icon-plus"></i>新建报工</el-button>
          <el-divider class="divider-vertical" direction="vertical"></el-divider>
          <el-button class="small-button" size="mini" round @click="handleTaskDetail(task)"><i
              class="el-icon-s-claim"></i>查看任务</el-button>
          <el-divider class="divider-vertical" direction="vertical"></el-divider>
          <el-button class="small-button" size="mini" round @click="handleJobBookingDetail(task)"><i
              class="el-icon-s-order"></i>查看报工</el-button>
        </div>
      </div>
      <div class="step-left" slot="reference">
        <div :class="handleCircleClass(task)">
          <!--  <div :class="handleContentClass(task)">{{ handleStatus(task) }}</div> -->
          <div :class="handleContentClass(task)"><i :class="handleStatus(task)" style="font-size: 24px;"></i></div>
        </div>
        <div class="step-name">{{ taskName }}</div>
      </div>
    </el-popover>
    <div v-show="currentIndex + 1 < length" class="step-line"></div>

    <!-- 添加或修改报工对话框 -->
    <el-dialog :title="title" :visible.sync="openAddJobBooking" width="800px" append-to-body>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工单编号" prop="sheetNumber">
              <el-select v-model="form.sheetNumber" placeholder="请选择工单" :disabled="true">
                <el-option v-for="item in sheetList" :key="item.sheetId" :label="item.sheetNumber"
                  :value="item.sheetNumber"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productNumber">
              <el-input v-model="form.productNumber" placeholder="请输入产品编号" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序名称" prop="procedureName">
              <el-select v-model="form.procedureName" placeholder="请选择工序" @change="taskChange" :disabled="true">
                <el-option v-for="item in taskList" :key="item.taskId" :label="item.procedureName"
                  :value="item.procedureName"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序状态" prop="procedureStatus">
              <el-radio-group v-model="form.procedureStatus" :disabled="!form.procedureName">
                <el-radio v-for="dict in procedureStatusList" :key="dict.dictValue" :label="dict.dictValue">{{
      dict.dictLabel
    }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品规格" prop="productSpecification">
              <el-input v-model="form.productSpecification" placeholder="请输入产品规格" :disabled="true" />
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
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker clearable v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择报工开始时间" @change="handleReportingTime" :disabled="!form.sheetNumber">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="报工数量" prop="jobBookingNum">
              <el-input-number v-model="form.jobBookingNum" placeholder="请输入报工数量" :disabled="!form.procedureName"
                :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker clearable v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择报工结束时间" :disabled="!form.sheetNumber" @change="handleReportingTime">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="单位" prop="unit">
              <el-select clearable v-model="form.unit" placeholder="请选择单位" :disabled="!form.procedureName">
                <el-option v-for="dict in dict.type.produce_unit" :key="dict.value" :label="dict.value"
                  :value="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报工时长" prop="jobBookingCount">
              <el-input v-model="form.jobBookingCount" placeholder="请输入报工时长" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="良品数" prop="goodQuantity">
              <el-input-number v-model="form.goodQuantity" placeholder="请输入良品数" :disabled="!form.procedureName"
                :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="不良品数" prop="defectQuantity">
              <el-input-number v-model="form.defectQuantity" placeholder="请输入不良品数" :disabled="!form.procedureName"
                :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="不良品项" prop="defectId">
              <el-input :placeholder="handleDefectExist()" :disabled="!form.procedureName" @focus="handleDefect">

                <template slot="prefix">
                  <span v-for="(defect, index) in lastDefect" :key="index"
                    style="padding: 5px; color: rgb(70, 67, 67); background-color: #e9e9e9; border: 1px solid #ccc; margin-right: 5px;">{{
      defect.defectName + ' ' + defect.number }}</span>
                </template>
              </el-input>
              <!-- <el-select multiple v-model="defectIds" placeholder="请选择" @change="handleDefectChange"
                :disabled="!form.procedureName">
                <el-option v-for="item in defectList" :key="item.defectId" :label="item.defectName"
                  :value="item.defectId">
                </el-option>
              </el-select> -->
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm"v-if="form.procedureStatus">{{ handleButtonLabel() }}</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看任务对话框 -->
    <el-dialog :title="title" :visible.sync="openTaskDetail" width="800px" append-to-body>
      <el-form ref="taskForm" :model="taskForm" :rules="rules" label-width="120px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工单编号" prop="sheetNumber">
              <el-input v-model="taskForm.sheetNumber" placeholder="请输入工单编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报工权限" prop="reportingName">
              <el-input v-model="taskForm.reportingName" placeholder="请输入报工权限" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productNumber">
              <el-input v-model="taskForm.productNumber" placeholder="请输入产品编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="taskForm.productName" placeholder="请输入产品名称" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序编号" prop="procedureNumber">
              <el-input v-model="taskForm.procedureNumber" placeholder="请输入工序编号" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工序名称" prop="procedureName">
              <el-input v-model="taskForm.procedureName" placeholder="请输入工序名称" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="报工数配比" prop="reportingRatio">
              <el-input-number v-model="taskForm.reportingRatio" placeholder="请输入报工数配比" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划数" prop="plannedQuantity">
              <el-input-number v-model="taskForm.plannedQuantity" placeholder="请输入计划数" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="良品数" prop="goodQuantity">
              <el-input-number v-model="taskForm.goodQuantity" placeholder="请输入良品数" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="不良品数" prop="defectQuantity">
              <el-input-number v-model="taskForm.defectQuantity" placeholder="请输入不良品数" :disabled="true" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="计划开始时间" prop="startTimePlan">
              <el-date-picker clearable v-model="taskForm.startTimePlan" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" :disabled="true" placeholder="请选择计划开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划结束时间" prop="endTimePlan">
              <el-date-picker clearable v-model="taskForm.endTimePlan" type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss" :disabled="true" placeholder="请选择计划结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="实际开始时间" prop="startTime">
              <el-date-picker clearable v-model="taskForm.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                :disabled="true" placeholder="请选择实际开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际结束时间" prop="endTime">
              <el-date-picker clearable v-model="taskForm.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                :disabled="true" placeholder="请选择实际结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="taskForm.remark" placeholder="请输入备注" :disabled="true" />
        </el-form-item>
      </el-form>
    </el-dialog>


    <!-- 编辑不良项 -->
    <el-dialog :title="'编辑不良项'" :visible.sync="openDefect" width="600px" append-to-body>
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
import { addJobBooking, updateJobBooking } from "@/api/produce/jobBooking";
import { listSheet } from "@/api/produce/sheet";
import { listTask, getTask, updateTask } from "@/api/produce/task";
import { listDefect } from "@/api/basicData/defect";
import { listUser } from "@/api/system/user";
import Vue from 'vue'
export default {
  dicts: ['produce_unit'],
  props: {
    task: {},
    length: undefined,
    currentIndex: undefined,
    procedureStatusList: {
      type: Array,
    },
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      loading: false,
      paramLoading: false,
      taskName: this.task.procedureName,
      //工单表格数据
      sheetList: [],
      //任务工序表格数据
      taskList: [],
      //用户表格数据
      userList: [],
      // 不良项列表
      defectList: [],
      defectIds: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      openAddJobBooking: false,
      openTaskDetail: false,
      openDefect: false,
      // 表单参数
      form: {},
      taskForm: {},
      queryTaskParams: {},
      sheet: {},
      // 表单校验
      rules: {
        jobBookingId: [
          { required: true, message: "主键ID不能为空", trigger: "blur" }
        ],
        sheetNumber: [
          { required: true, message: "工单编号不能为空", trigger: "blur" }
        ],
        productionPersonnel: [
          { required: true, message: "生产人员不能为空", trigger: "blur" }
        ],
        procedureName: [
          { required: true, message: "工序名称不能为空", trigger: "blur" }
        ],
      },
      selectedDefect: [],
      lastDefect: [],
      defectNames: '',
    };
  },
  created() {

  },
  methods: {
    /** 查询工单列表 */
    getSheetList() {
      this.loading = true;
      listSheet().then(response => {
        this.sheetList = response.rows;
        this.sheet = this.sheetList.find(s => s.sheetNumber == this.task.sheetNumber);
        this.form.productNumber = this.sheet.productNumber;
        this.form.productName = this.sheet.productName;
        this.form.productSpecification = this.sheet.productSpecification;
        this.form.unit = this.sheet.unit;
        this.loading = false;
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
    /** 创建报工按钮操作 */
    handleAddJobBooking() {
      this.loading = true;
      this.getSheetList();
      this.getDefectList();
      this.getUserList();
      this.reset();
      this.form.sheetNumber = this.task.sheetNumber;
      this.form.procedureName = this.task.procedureName;
      this.form.procedureStatus = this.task.procedureStatus;
      this.form.jobBookingNum = this.task.plannedQuantity;
      this.form.goodQuantity = this.task.plannedQuantity;
      this.form.defectQuantity = this.task.defectQuantity;
      this.form.procedureNumber = this.task.procedureNumber;
      this.defectIds = this.task.defectiveProducts.split(",");
      this.form.defectId = this.defectIds.join(',');
      //this.form.defectiveNames = this.task.defectiveNames;
      /* this.queryTaskParams.sheetNumber = this.task.sheetNumber;
      listTask(this.queryTaskParams).then(response => {
        this.taskList = response.rows;
      }); */
      this.loading = false;
      this.openAddJobBooking = true;
      this.title = "创建报工";
    },
    /** 查看任务按钮操作 */
    handleTaskDetail() {
      this.loading = true;
      this.reset();
      const taskId = this.task.taskId;
      getTask(taskId).then(response => {
        this.loading = false;
        this.taskForm = response.data;
        this.openTaskDetail = true;
        this.title = "任务详情";
      });
    },
    /** 查看报工按钮操作 */
    handleJobBookingDetail() {
      const sheetNumber = this.task.sheetNumber;
      this.$router.push("/produce/job-booking/" + sheetNumber);
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
          this.form.defectiveNames = this.lastDefect.map(d => JSON.stringify(d)).join("~~");
          if (this.form.jobBookingId != null) {
            updateJobBooking(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openAddJobBooking = false;
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
              this.openAddJobBooking = false;
            }).finally(() => {
              this.task.procedureStatus = this.form.procedureStatus;
              this.task.goodQuantity = this.form.goodQuantity;
              this.task.defectQuantity = this.form.defectQuantity;
              this.task.plannedQuantity = this.form.jobBookingNum;
              this.task.defectList = this.lastDefect.map(d => JSON.stringify(d)).join("~~");
              updateTask(this.task).then(response => {
                this.sheetList = [];
                listSheet().then(response => {
                  this.sheetList = response.rows;
                  this.$emit('data', this.sheetList);
                });

              });
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    // 取消按钮
    cancel() {
      this.openAddJobBooking = false;
      this.reset();
    },
    // 表单重置
    reset() {
        this.taskForm = {},
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
          remark: undefined
        };
      this.resetForm("form");
      this.resetForm("taskForm");
    },
    handleStatus(task) {
      if (task.procedureStatus == '0') {
        return 'el-icon-minus'
      }
      if (task.procedureStatus == '1') {
        return 'el-icon-more'
      }
      if (task.procedureStatus == '2') {
        return 'el-icon-check'
      }
    },
    handleContentClass(task) {
      if (task.procedureStatus == '0') {
        return 'content-wait'
      }
      if (task.procedureStatus == '1') {
        return 'content-loading'
      }
      if (task.procedureStatus == '2') {
        return 'content-finish'
      }
    },
    handleCircleClass(task) {
      if (task.procedureStatus == '0') {
        return 'circle-wait'
      }
      if (task.procedureStatus == '1') {
        return 'circle-loading'
      }
      if (task.procedureStatus == '2') {
        return 'circle-finish'
      }
    },
    handleTimeProgress(task) {
      if (task.startTimePlan && task.endTimePlan) {
        const all = this.calculateTimeDiff(task.startTimePlan, task.endTimePlan);
        const now = this.calculateTimeDiff(task.startTimePlan, new Date());
        if (now > all) return 100;
        if (now < 0) return 0;
        return parseInt((now / all * 100).toFixed(0));
      } else {
        return 0;
      }
    },
    handleTaskProgress(task) {
      if (task.procedureStatus == '0') {
        return 0
      }
      if (task.procedureStatus == '1') {
        return 50
      }
      if (task.procedureStatus == '2') {
        return 100
      }
    },
    handleDelay(task) {
      if (task.endTimePlan && task.procedureStatus !== '2') {
        const now = new Date();
        const planEnd = new Date(task.endTimePlan);
        if (now < planEnd) {
          return ''
        } else {
          return this.day(planEnd, now) + '天' + this.hours(planEnd, now) + '时'
        }
      }
    },
    calculateTimeDiff(startTime, endTime) {
      const start = new Date(startTime);
      const end = new Date(endTime);
      const diffInMs = end - start;
      const diffInMinutes = Math.floor(diffInMs / (1000 * 60));
      return diffInMinutes;
    },
    hours(startTime, endTime) {
      const start = new Date(startTime).getTime();
      const end = new Date(endTime).getTime();
      const diff = ((end - start) / 1000 / 60 / 60) % 24; // 将毫秒数转换为小时数
      return parseInt(diff);
    },
    day(startTime, endTime) {
      const start = new Date(startTime);
      const end = new Date(endTime);
      const timeDifference = end.getTime() - start.getTime();
      const dayDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
      return dayDifference;
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
      listTask(this.queryTaskParams).then(response => {
        this.taskList = response.rows;
      });
    },
    taskChange(data) {
      var task = this.taskList.find(t => t.procedureName == data);
      this.form.procedureStatus = task.procedureStatus;
      this.form.jobBookingNum = task.reportingRatio;
      this.form.goodQuantity = task.goodQuantity;
      this.form.defectQuantity = task.defectQuantity;
      this.form.procedureNumber = task.procedureNumber;
      this.defectIds = task.defectiveProducts.split(",");
      this.form.defectId = this.defectIds.join(',');
      this.form.defectiveNames = task.defectiveNames;
    },
    handleDefectChange() {
      this.form.defectId = this.defectIds.join(',');
    },
    hour() {
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
        this.form.jobBookingCount = this.hour() + '小时' + this.minutes() + '分钟';
      } else {
        this.form.jobBookingCount = undefined;
      }
    },
    handleDefectExist() {
      if (this.selectedDefect.length > 0) {
        return;
      } else {
        return '请选择不良项'
      }
    },
    handleDefect() {
      this.openDefect = true;
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
  },


};
</script>

<style>
.circle-wait {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  background: linear-gradient(0deg, #36A5ED 0%);
  /* background: linear-gradient(0deg, #36A5ED 0%, #6EEE96 100%); */
  position: relative;
  cursor: pointer;
}

.content-wait {
  position: absolute;
  left: 5px;
  top: 5px;
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #36A5ED;
}

.circle-loading {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  background: linear-gradient(0deg, rgb(241, 206, 6)0%);
  /* background: linear-gradient(0deg, #36A5ED 0%, #6EEE96 100%); */
  position: relative;
  cursor: pointer;
}

.content-loading {
  position: absolute;
  left: 5px;
  top: 5px;
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(241, 206, 6);
}

.circle-finish {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  /* background: linear-gradient(0deg, #36A5ED 0%); */
  background: linear-gradient(0deg, #6EEE96 100%);
  position: relative;
  cursor: pointer;
}

.content-finish {
  position: absolute;
  left: 5px;
  top: 5px;
  width: 35px;
  height: 35px;
  border-radius: 50%;
  background-color: #ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #6EEE96;
}


.progress-bar {
  display: flex;
  align-items: center;
  height: 100%;
}

.step-circle {
  height: 50px;
  width: 50px;
  border-radius: 50%;
  background-color: rgb(187, 187, 245);
}

.step-name {
  font-size: 12px;
  text-align: center;
}

.step-line {
  width: 50px;
  height: 4px;
  background-color: #dbd8db;
  border-radius: 6px;
  margin: 0 8px 20px;
}

.small-button {
  font-size: 11px;
  width: 80px;

}

.small-button .el-button__content {
  margin-right: 5px;
}

.custom-divider {
  margin: 15px 0;
}

.divider-vertical {
  margin: 0 2px;
}
</style>
