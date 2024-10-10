<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工序编号" prop="procedureNumber">
        <el-input v-model="queryParams.procedureNumber" placeholder="请输入工序编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="工序名称" prop="procedureName">
        <el-input v-model="queryParams.procedureName" placeholder="请输入工序名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input v-model="queryParams.createBy" placeholder="请输入创建人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
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

    <el-table v-loading="loading" :data="procedureList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工序编号" align="center" prop="procedureNumber" />
      <el-table-column label="工序名称" align="center" prop="procedureName" />
      <el-table-column label="报工权限" align="center" prop="reportingName" />
      <el-table-column label="计划数默认值" align="center" prop="plannedQuantity" />
      <el-table-column label="报工数配比" align="center" prop="reportingRatio" />
      <el-table-column label="不良品项列表" align="center" prop="defectiveNames" />
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改工序对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="890px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="工序编号" prop="procedureNumber">
              <el-input v-model="form.procedureNumber" placeholder="请输入工序编号" style="width: 215px;"
                :disabled="title == '修改工序'" /> <el-button type="primary" size="mini" @click="generateProcedureCode"
                :disabled="title == '修改工序'">自动生成</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工序名称" prop="procedureName">
              <el-input v-model="form.procedureName" placeholder="请输入工序名称" style="width: 215px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="计划数默认值" prop="plannedQuantity">
              <el-input-number v-model="form.plannedQuantity" placeholder="请输入计划数默认值" :min="1" style="width: 215px;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报工数配比" prop="reportingRatio">
              <el-input-number v-model="form.reportingRatio" placeholder="请输入报工数配比" :min="1" style="width: 215px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="报工权限" prop="reportingAuthority">
              <el-select multiple v-model="userIds" placeholder="请选择" @change="handleUserChange">
                <el-option v-for="item in userList" :key="item.userId" :label="item.nickName" :value="item.userId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="不良品列表" prop="defectiveProducts">
              <el-select multiple v-model="defectIds" placeholder="请选择" @change="handleDefectChange">
                <el-option v-for="item in defectList" :key="item.defectId" :label="item.defectName"
                  :value="item.defectId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="工艺要求" prop="technologicalRequirements">
              <el-input v-model="form.technologicalRequirements" placeholder="请输入工艺要求" style="width: 215px;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否打印下料单" prop="isPrint">
              <el-select v-model="form.isPrint" placeholder="请选择是否打印下料单">
                <el-option v-for="dict in dict.type.is_print" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" style="width: 650px;" v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listProcedure,
  getProcedure,
  delProcedure,
  addProcedure,
  updateProcedure,
} from "@/api/basicData/procedure";
import { listUser } from "@/api/system/user";
import { listDefect } from "@/api/basicData/defect";
export default {
  name: "Procedure",
  dicts: ['sys_normal_disable', 'is_print'],
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
      // 工序表格数据
      procedureList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        procedureNumber: undefined,
        procedureName: undefined,
        createBy: undefined,
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {
        procedureId: [
          { required: true, message: "主键ID不能为空", trigger: "change" },
        ],
        procedureNumber: [
          { required: true, message: "工序编号不能为空", trigger: "change" },
        ],
        procedureName: [
          { required: true, message: "工序名称不能为空", trigger: "change" },
        ],
        reportingAuthority: [
          { required: true, message: "报工权限不能为空", trigger: "change" },
        ],
        plannedQuantity: [
          { required: true, message: "计划数默认值不能为空", trigger: "change" },
        ],
        reportingRatio: [
          { required: true, message: "报工数配比不能为空", trigger: "change" },
        ],
        defectiveProducts: [
          { required: true, message: "不良品列表不能为空", trigger: "change" },
        ],
        checkIn: [
          { required: true, message: "任务打卡不能为空", trigger: "change" },
        ],
        isPrint: [
          {
            required: true,
            message: "是否打印下料单不能为空",
            trigger: "change",
          },
        ],
        // collectionData: [
        //   { required: true, message: "工序采集数据不能为空", trigger: "blur" },
        // ],
      },
      userList: [], // 报工权限用户列表
      defectList: [], // 不良项列表
      userIds: [],
      defectIds: [],
    };
  },
  created() {
    this.getList();
    this.getUserList();
    this.getDefectList();
  },
  methods: {
    /** 查询工序列表 */
    getList() {
      this.loading = true;
      listProcedure(this.queryParams).then((response) => {
        this.procedureList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
        this.userIds = [],
        this.defectIds = [],
        this.form = {
          procedureId: undefined,
          procedureNumber: undefined,
          procedureName: undefined,
          reportingAuthority: undefined,
          plannedQuantity: 1,
          reportingRatio: 1,
          defectiveProducts: undefined,
          checkIn: undefined,
          technologicalRequirements: undefined,
          isPrint: undefined,
          collectionData: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined,
          delFlag: undefined,
          remark: undefined,
          reportingName: undefined,
          defectiveNames: undefined
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
      this.ids = selection.map((item) => item.procedureId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工序";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const procedureId = row.procedureId || this.ids;
      getProcedure(procedureId).then((response) => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改工序";
        this.userIds = this.form.reportingAuthority.split(",");
        this.defectIds = this.form.defectiveProducts.split(",");
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.buttonLoading = true;
          var nickNames = '';
          this.userIds.forEach(e => {
            var user = this.userList.find(u => u.userId === e)
            nickNames += user.nickName + ',';
          })
          this.form.reportingName = nickNames.substring(0, nickNames.length - 1)
          var defectnames = '';
          this.defectIds.forEach(e => {
            var defect = this.defectList.find(d => d.defectId == e)
            defectnames += defect.defectName + ',';
          })
          this.form.defectiveNames = defectnames.substring(0, defectnames.length - 1);
          if (this.form.procedureId != null) {
            updateProcedure(this.form)
              .then((response) => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.buttonLoading = false;
              });
          } else {
            addProcedure(this.form)
              .then((response) => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              })
              .finally(() => {
                this.buttonLoading = false;
              });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const procedureIds = row.procedureId || this.ids;
      this.$modal
        .confirm('是否确认删除工序编号为"' + procedureIds + '"的数据项？')
        .then(() => {
          this.loading = true;
          return delProcedure(procedureIds);
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { })
        .finally(() => {
          this.loading = false;
        });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/procedure/export",
        {
          ...this.queryParams,
        },
        `procedure_${new Date().getTime()}.xlsx`
      );
    },
    // 工序编号生成：GX + 时间戳
    generateProcedureCode() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hour = String(currentDate.getHours()).padStart(2, '0');
      const minute = String(currentDate.getMinutes()).padStart(2, '0');
      const second = String(currentDate.getSeconds()).padStart(2, '0');
      const timestamp = String(currentDate.getTime()).substring(10);
      this.form.procedureNumber = `GX${year}${month}${day}${hour}${minute}${second}${timestamp}`
    },
    // 获取报工权限用户列表
    getUserList() {
      listUser().then(res => {
        this.userList = res.rows;
        this.userList.unshift({ userId: 'all', nickName: '不限制' })
      });
    },
    // 获取不良项列表
    getDefectList() {
      listDefect().then(res => {
        this.defectList = res.rows;
      })
    },
    // 报工权限修改
    handleUserChange(value) {
      if (value.length > 1) {
        if (value[value.length - 1] === 'all') {
          this.userIds = [value[value.length - 1]]
        } else if (value[0] === 'all') {
          this.userIds = value.filter(item => item !== value[0]);
        }
      }
      this.form.reportingAuthority = this.userIds.join(',');
    },
    handleDefectChange() {
      this.form.defectiveProducts = this.defectIds.join(',');
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
    }
  },
};
</script>