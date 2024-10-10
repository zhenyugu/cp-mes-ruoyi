<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="库存编号" prop="purveyorCode">
        <el-input v-model="queryParams.purveyorCode" placeholder="请输入库存编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="库存名称" prop="purveyorName">
        <el-input v-model="queryParams.purveyorName" placeholder="请输入库存名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="库存规格" prop="nature">
        <el-input v-model="queryParams.nature" placeholder="请输入库存规格" clearable @keyup.enter.native="handleQuery" />
        <!-- <el-select v-model="queryParams.nature" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.purveyor_nature"
            :key="dict.id"
            :label="dict.label"
            :value="dict.value"></el-option>
        </el-select> -->
      </el-form-item>
      <!-- <el-form-item label="供货类型" prop="supplyType">
        <el-select v-model="queryParams.supplyType" placeholder="请选择">
          <el-option v-for="dict in dict.type.purveyor_supply_type"
            :key="dict.id"
            :label="dict.label"
            :value="dict.value"></el-option>
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="联系人姓名" prop="contractName">
        <el-input
          v-model="queryParams.contractName"
          placeholder="请输入联系人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:purveyor:add']">新增</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:purveyor:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purveyorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="purveyorId" v-if="true"/> -->
      <el-table-column label="库存编号" align="center" prop="purveyorCode" width="200px" />
      <el-table-column label="库存类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inventory_types" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="库存名称" align="center" prop="purveyorName" />
      <el-table-column label="库存规格" align="center" prop="nature" width="200">
      </el-table-column>
      <el-table-column label="操作类型" align="center" prop="supplyType" width="200">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inventory_type" :value="scope.row.supplyType" />
        </template>
      </el-table-column>
      <el-table-column label="数量" align="center" prop="contractPhone" />
      <el-table-column label="单位" align="center" prop="contractPost" />
      <el-table-column label="领料人" align="center" prop="address" />
      <el-table-column label="出（入）库人" align="center" prop="contractName" />
      <el-table-column label="出（入）库时间" align="center" prop="updateTime" width="180" />
      <!-- <el-table-column label="联系人邮箱" align="center" prop="contractEmail" />
      <el-table-column label="库存地址" align="center" prop="address" />
      <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding" fixed="right" width="150">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:purveyor:edit']">修改</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改供应商库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="库存编号" prop="purveyorCode">
              <el-input v-model="form.purveyorCode" placeholder="请输入库存编号" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存名称" prop="purveyorName">
              <el-input v-model="form.purveyorName" placeholder="请输入库存名称" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="库存规格" prop="nature">
              <el-input v-model="form.nature" placeholder="请输入库存规格" disabled />
              <!-- <el-select v-model="form.nature" multiple placeholder="请选择">
                <el-option v-for="dict in dict.type.purveyor_nature" :key="dict.id" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select> -->
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位" prop="contractPost">
              <el-input v-model="form.contractPost" placeholder="请输入单位" disabled />
            </el-form-item>
            <!-- <el-form-item label="供货类型" prop="supplyType">
              <el-select v-model="form.supplyType" multiple placeholder="请选择">
                <el-option v-for="dict in dict.type.purveyor_supply_type"
                  :key="dict.id"
                  :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item> -->
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="数量" prop="contractPhone">
              <el-input v-model="form.contractPhone" placeholder="请输入数量" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">

            <el-form-item label="出（入）库人" prop="contractName">
              <el-input v-model="form.contractName" placeholder="请输入出（入）库人" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="数量" prop="contractPhone">
              <el-input v-model="form.contractPhone" placeholder="请输入联系人电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人邮箱" prop="contractEmail">
              <el-input v-model="form.contractEmail" placeholder="请输入联系人邮箱" />
            </el-form-item>
          </el-col>
        </el-row> -->
        <!-- <el-row :gutter="12">
          <el-col :span="24">
            <el-form-item label="库存地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入库存地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPurveyor, getPurveyor, delPurveyor, addPurveyor, updatePurveyor } from "@/api/system/purveyor";

export default {
  name: "Purveyor",
  dicts: ['inventory_type',, 'inventory_types'],
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
      // 供应商库管理表格数据
      purveyorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purveyorCode: undefined,
        purveyorName: undefined,
        nature: undefined,
        supplyType: undefined,
        contractName: undefined,
        contractPost: undefined,
        contractPhone: undefined,
        contractEmail: undefined,
        address: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        /* purveyorCode: [
          { required: true, message: "库存编号不能为空", trigger: "blur" }
        ],
        purveyorName: [
          { required: true, message: "库存名称不能为空", trigger: "blur" }
        ],
        nature: [
          { required: true, message: "库存性质不能为空", trigger: "blur" }
        ],
        supplyType: [
          { required: true, message: "供货类型不能为空", trigger: "blur" }
        ],
        contractName: [
          { required: true, message: "联系人姓名不能为空", trigger: "blur" }
        ],
        contractPhone: [
          { required: true, message: "联系人电话不能为空", trigger: "blur" }
        ],
        address: [
          { required: true, message: "库存地址不能为空", trigger: "blur" }
        ], */
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商库管理列表 */
    getList() {
      this.loading = true;
      listPurveyor(this.queryParams).then(response => {
        this.purveyorList = response.rows;
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
        purveyorId: undefined,
        purveyorCode: undefined,
        purveyorName: undefined,
        nature: undefined,
        supplyType: undefined,
        contractName: undefined,
        contractPost: undefined,
        contractPhone: undefined,
        contractEmail: undefined,
        address: undefined,
        remark: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
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
      this.ids = selection.map(item => item.purveyorId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const purveyorId = row.purveyorId || this.ids
      getPurveyor(purveyorId).then(response => {
        this.loading = false;
        this.form = {
          ...response.data,
          /* nature: response.data.nature.split(","),
          supplyType: response.data.supplyType.split(',') */
        };
        this.open = true;
        this.title = "修改出入库历史管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          let data = {
            ...this.form,
            /* nature: this.form.nature.join(','),
            supplyType: this.form.supplyType.join(',') */
          }
          if (this.form.purveyorId != null) {
            updatePurveyor(data).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addPurveyor(data).then(response => {
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
      const purveyorIds = row.purveyorId || this.ids;
      this.$modal.confirm('是否确认删除该出入库历史信息？').then(() => {
        this.loading = true;
        return delPurveyor(purveyorIds);
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
      this.download('system/purveyor/export', {
        ...this.queryParams
      }, `purveyor_${new Date().getTime()}.xlsx`)
    },
    // 处理标签
    handleTag(dicts, value) {
      let item = dicts.find(d => d.value == value)
      return item.label || '未知'
    }
  }
};
</script>
<style scoped>
.table-item-tag {
  display: flex;
  display: -webkit-box;
  text-overflow: ellipsis;
  overflow: hidden;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
</style>
