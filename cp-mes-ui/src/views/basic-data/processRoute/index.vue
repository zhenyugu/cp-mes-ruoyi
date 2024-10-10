<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="工艺路线编号" prop="processRouteNumber">
        <el-input v-model="queryParams.processRouteNumber" placeholder="请输入工艺路线编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="工艺路线名称" prop="processRouteName">
        <el-input v-model="queryParams.processRouteName" placeholder="请输入工艺路线名称" clearable
          @keyup.enter.native="handleQuery" />
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

    <el-table v-loading="loading" :data="processRouteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工艺路线编号" align="center" prop="processRouteNumber" />
      <el-table-column label="工艺路线名称" align="center" prop="processRouteName" />
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


    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="工艺路线编号" prop="processRouteNumber">
          <el-input v-model="form.processRouteNumber" placeholder="请输入工艺路线编号" style="width: 250px;"
            :disabled="title == '修改工艺路线'" /> <el-button type="primary" size="mini" @click="generateCode"
            :disabled="title == '修改工艺路线'">自动生成</el-button>
        </el-form-item>
        <el-form-item label="工艺路线名称" prop="processRouteName">
          <el-input v-model="form.processRouteName" style="width: 250px;" placeholder="请输入工艺路线名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="form.remark" style="width: 250px;" placeholder="请输入备注" />
        </el-form-item>
        <div>
          <el-form-item label="工序" prop="procedureIds">
            <el-button type="primary" icon="el-icon-plus" style="margin-bottom: 8px;"
              @click="addProcedure">新增工序</el-button>
            <div>
              <el-table v-loading="paramLoading" :data="bindingProcedure" ref="sortableTable" row-key="procedureId">
                <el-table-column type="index" width="55" align="center">
                  <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                </el-table-column>
                <el-table-column label="工序" prop="procedureId" align="center">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.procedureId" placeholder="请选择工序" @change="procedureChange">
                      <el-option v-for="item in procedureList" :key="item.procedureId" :label="item.procedureName"
                        :value="item.procedureId"></el-option>
                    </el-select>
                  </template>
                </el-table-column>

                <el-table-column label="报工数配比" prop="reportingRatio" align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.reportingRatio" placeholder="请输入" :controls="false" :disabled="true"
                      style="width: 100%;"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="工艺要求" prop="technologicalRequirements" align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.technologicalRequirements" placeholder="请输入" :controls="false"
                      :disabled="true" style="width: 100%;"></el-input>
                  </template>
                </el-table-column>
                <el-table-column label="是否打印下料单" prop="isPrint" align="center">
                  <template slot-scope="scope">
                    <el-select v-model="scope.row.isPrint" placeholder="请选择是否打印下料单" :disabled="true">
                      <el-option v-for="dict in dict.type.is_print" :key="dict.value" :label="dict.label"
                        :value="dict.value"></el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-delete"
                      @click="delectParam(scope.row)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
<script>
import draggable from 'vuedraggable';
import { listProcessRoute, getProcessRoute, delProcessRoute, addProcessRoute, updateProcessRoute } from "@/api/basicData/processRoute";
import { listProcedure, getProcedure } from "@/api/basicData/procedure";
import Sortable from 'sortablejs';
export default {
  name: "ProcessRoute",
  components: {
    draggable,
  },
  dicts: ['is_print'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      paramLoading: false,
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
      // 工艺路线表格数据
      processRouteList: [],
      // 工序表格数据
      procedureList: [],
      //绑定工序表格数据
      bindingProcedure: [],
      //选中的工序
      currentProcedure: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processRouteNumber: undefined,
        processRouteName: undefined,
        procedureIds: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        processRouteId: [
          { required: true, message: "主键ID不能为空", trigger: "blur" }
        ],
        processRouteNumber: [
          { required: true, message: "工艺路线编号不能为空", trigger: "blur" }
        ],
        processRouteName: [
          { required: true, message: "工艺路线名称不能为空", trigger: "blur" }
        ],
        /* procedureIds: [
          { required: true, message: "工序不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ] */
      }
    };
  },
  created() {
    this.getProcedureList();
    this.getList();
  },
  watch: {
    open(val) {
      if (val) {
        this.$nextTick(() => {
          this.initSortable();
        })
      }
    }
  },
  methods: {
    initSortable() {
      const tableBody = this.$refs.sortableTable.$el.querySelector('.el-table__body-wrapper tbody');
      Sortable.create(tableBody, {
        animation: 150,
        onEnd: evt => {
          const item = this.bindingProcedure.splice(evt.oldIndex, 1)[0];
          this.bindingProcedure.splice(evt.newIndex, 0, item);
        }
      });
    },
    // 编号生成：GY + 时间戳
    generateCode() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hour = String(currentDate.getHours()).padStart(2, '0');
      const minute = String(currentDate.getMinutes()).padStart(2, '0');
      const second = String(currentDate.getSeconds()).padStart(2, '0');
      const timestamp = String(currentDate.getTime()).substring(10);
      this.form.processRouteNumber = `GY${year}${month}${day}${hour}${minute}${second}${timestamp}`
    },
    /** 查询工艺路线列表 */
    getList() {
      this.loading = true;
      listProcessRoute(this.queryParams).then(response => {
        this.processRouteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询工序列表 */
    getProcedureList() {
      this.loading = true;
      listProcedure().then(response => {
        this.procedureList = response.rows;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.bindingProcedure = [],
        this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        processRouteId: undefined,
        processRouteNumber: undefined,
        processRouteName: undefined,
        procedureIds: undefined,
        procedureNames: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        delFlag: undefined,
        remark: undefined,
        procedureList: [],
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
      this.ids = selection.map(item => item.processRouteId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.bindingProcedure = [],
        //this.form.processRouteNumber = this.generateCode();
        this.open = true;
      this.title = "添加工艺路线";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      this.bindingProcedure = [];
      const processRouteId = row.processRouteId || this.ids
      getProcessRoute(processRouteId).then(response => {
        this.loading = false;
        this.form = response.data;
        if (this.form.procedureList) {
          this.bindingProcedure = this.form.procedureList;
        }
        this.open = true;
        this.title = "修改工艺路线";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          var procedureIds = '';
          var procedureNames = '';
          this.bindingProcedure.forEach(e => {
            procedureIds += e.procedureId + ',';
            procedureNames += e.procedureName + ',';
          });
          this.form.procedureIds = procedureIds.substring(0, procedureIds.length - 1)
          this.form.procedureNames = procedureNames.substring(0, procedureNames.length - 1)
          if (this.form.processRouteId != null) {
            updateProcessRoute(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProcessRoute(this.form).then(response => {
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
      const processRouteIds = row.processRouteId || this.ids;
      this.$modal.confirm('是否确认删除工艺路线编号为"' + processRouteIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProcessRoute(processRouteIds);
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
      this.download('system/processRoute/export', {
        ...this.queryParams
      }, `processRoute_${new Date().getTime()}.xlsx`)
    },
    // 价格参数新增按钮
    addProcedure() {
      var paramItem = {
        procedureId: undefined,
        reportingRatio: undefined,
        technologicalRequirements: undefined,
        isPrint: undefined
      }
      this.bindingProcedure.push(paramItem)
    },
    // 价格参数删除按钮
    delectParam(row) {
      let index = this.bindingProcedure.findIndex(p => p.procedureId == row.procedureId)
      if (index < 0) return
      this.bindingProcedure.splice(index, 1)
    },
    procedureChange(data) {
      getProcedure(data).then(response => {
        this.currentProcedure = response.data;
        this.bindingProcedure.forEach(e => {
          if (e.procedureId === data) {
            e.reportingRatio = this.currentProcedure.reportingRatio;
            e.technologicalRequirements = this.currentProcedure.technologicalRequirements;
            e.isPrint = this.currentProcedure.isPrint;
            e.procedureName = this.currentProcedure.procedureName;
          }
        })
      });
    },
  }
};
</script>

   