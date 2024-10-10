<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="不良项编号" prop="defectNumber">
        <el-input v-model="queryParams.defectNumber" placeholder="请输入不良项编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="不良项名称" prop="defectName">
        <el-input v-model="queryParams.defectName" placeholder="请输入不良项名称" clearable @keyup.enter.native="handleQuery" />
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

    <el-table v-loading="loading" :data="defectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="defectId" v-if="true"/> -->
      <el-table-column label="不良项编号" align="center" prop="defectNumber" />
      <el-table-column label="不良项名称" align="center" prop="defectName" />
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
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

    <!-- 添加或修改不良项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="不良项编号" prop="defectNumber">
          <el-input v-model="form.defectNumber" placeholder="请输入不良项编号" style="width: 220px;"
            :disabled="title == '修改不良项'" /> <el-button type="primary" size="mini" @click="generateCode"
            :disabled="title == '修改不良项'">自动生成</el-button>
        </el-form-item>
        <el-form-item label="不良项名称" prop="defectName">
          <el-input v-model="form.defectName" placeholder="请输入不良项名称" style="width: 220px;" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" style="width: 220px;" v-model="form.remark" placeholder="请输入备注" />
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
import { listDefect, getDefect, delDefect, addDefect, updateDefect } from "@/api/basicData/defect";

export default {
  name: "Defect",
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
      // 不良项表格数据
      defectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        defectNumber: undefined,
        defectName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        defectId: [
          { required: true, message: "主键ID不能为空", trigger: "blur" }
        ],
        defectNumber: [
          { required: true, message: "不良项编号不能为空", trigger: "blur" }
        ],
        defectName: [
          { required: true, message: "不良项名称不能为空", trigger: "blur" }
        ],
        /* remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ] */
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 编号生成：BL + 时间戳
    generateCode() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hour = String(currentDate.getHours()).padStart(2, '0');
      const minute = String(currentDate.getMinutes()).padStart(2, '0');
      const second = String(currentDate.getSeconds()).padStart(2, '0');
      const timestamp = String(currentDate.getTime()).substring(10);
      this.form.defectNumber = `BL${year}${month}${day}${hour}${minute}${second}${timestamp}`
    },
    /** 查询不良项列表 */
    getList() {
      this.loading = true;
      listDefect(this.queryParams).then(response => {
        this.defectList = response.rows;
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
      this.form = {
        defectId: undefined,
        defectNumber: undefined,
        defectName: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        delFlag: undefined,
        remark: undefined
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
      this.ids = selection.map(item => item.defectId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      //this.form.defectNumber = this.generateCode();
      this.open = true;
      this.title = "添加不良项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const defectId = row.defectId || this.ids
      getDefect(defectId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改不良项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.defectId != null) {
            updateDefect(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addDefect(this.form).then(response => {
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
      const defectIds = row.defectId || this.ids;
      this.$modal.confirm('是否确认删除不良项编号为"' + defectIds + '"的数据项？').then(() => {
        this.loading = true;
        return delDefect(defectIds);
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
      this.download('system/defect/export', {
        ...this.queryParams
      }, `defect_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
  