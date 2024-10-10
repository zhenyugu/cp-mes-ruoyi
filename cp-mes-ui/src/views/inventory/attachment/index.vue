<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="库存编号" prop="attachmentCode">
        <el-input v-model="queryParams.attachmentCode" placeholder="请输入库存编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="库存名称" prop="attachmentName">
        <el-input v-model="queryParams.attachmentName" placeholder="请输入库存名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="供应商" prop="purveyorId">
        <el-select v-model="queryParams.purveyorId" placeholder="请选择供应商">
          <el-option v-for="purveyor in purveyorList"
            :key="purveyor.purveyorId"
            :label="purveyor.purveyorName"
            :value="purveyor.purveyorId"></el-option>
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
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

    <el-table v-loading="loading" :data="attachmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="attachmentId" v-if="true"/> -->
      <el-table-column label="库存编号" align="center" prop="attachmentCode" width="200" />
      <el-table-column label="库存类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inventory_types" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="库存名称" align="center" prop="attachmentName" />
      <el-table-column label="库存规格" align="center" prop="model" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="单位" align="center" prop="unit" />
      <!-- <el-table-column label="领料人" align="center" prop="purveyorName" /> -->
      <!-- <el-table-column label="备注" align="center" prop="remark" show-overflow-tooltip /> -->
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:attachment:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:attachment:remove']">删除</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleIn(scope.row)">入库</el-button>
          <el-button size="mini" type="text" icon="el-icon-minus" @click="handleOut(scope.row)">出库</el-button>

        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改备件库管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="库存类型" prop="type">
          <el-select clearable v-model="form.type" placeholder="请选择库存类型" @change="typeChange">
            <el-option v-for="dict in dict.type.inventory_types" :key="dict.value" :label="dict.label"
              :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库存名称" prop="attachmentName" v-if="form.type === '1'">
          <el-select v-model="form.attachmentName" placeholder="请选择库存信息" @change="materialChange">
            <el-option v-for="item in materialList" :key="item.materialId" :label="item.materialName"
              :value="item.materialId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库存名称" prop="attachmentName" v-if="form.type === '0'">
          <el-select v-model="form.attachmentName" placeholder="请选择库存信息" @change="productChange">
            <el-option v-for="item in productList" :key="item.productId" :label="item.productName"
              :value="item.productId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库存编号" prop="attachmentCode">
          <el-input v-model="form.attachmentCode" placeholder="请输入库存编号" style="width: 220px;" :disabled="true" />
        </el-form-item>
        <el-form-item label="库存规格" prop="model">
          <el-input v-model="form.model" placeholder="请输入库存规格" style="width: 220px;" :disabled="true" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-select clearable v-model="form.unit" placeholder="请选择单位" :disabled="true">
            <el-option v-for="dict in dict.type.produce_unit" :key="dict.value" :label="dict.value" :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="初始数量" prop="quantity">
          <el-input-number v-model="form.quantity" placeholder="请输入初始数量" :min="0" style="width: 220px;" />
        </el-form-item>
        <!-- <el-form-item label="供应商" prop="purveyorId">
          <el-select v-model="form.purveyorId" placeholder="请选择供应商" @change="purveyorChange">
            <el-option v-for="purveyor in purveyorList"
              :key="purveyor.purveyorId"
              :label="purveyor.purveyorName"
              :value="purveyor.purveyorId"></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" style="width: 220px;" v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="openIn" width="400px" append-to-body>
      <p>出（入）库数量：</p>
      <el-input-number v-if="title == '出库管理'" v-model="quantityChange" :min="1"
        :max="formIn.quantity"></el-input-number>
      <el-input-number v-if="title == '入库管理'" v-model="quantityChange" :min="1"></el-input-number>
      <div v-if="title == '出库管理'">
        <p>领料人：</p>
        <el-select v-model="form.purveyorId" placeholder="请选择领料人" @change="purveyorChange">
          <el-option v-for="purveyor in purveyorList" :key="purveyor.userId" :label="purveyor.nickName"
            :value="purveyor.userId"></el-option>
        </el-select>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitFormIn">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAttachment, getAttachment, delAttachment, addAttachment, updateAttachment } from "@/api/system/attachment";
import { addPurveyor } from "@/api/system/purveyor";
import { listProduct } from "@/api/basicData/product";
import { listMaterial } from "@/api/system/material";
import { listUser } from "@/api/system/user";
export default {
  name: "Attachment",
  dicts: ['produce_unit', 'inventory_types'],
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
      // 备件库管理表格数据
      attachmentList: [],
      //库存表格数据
      productList: [],
      materialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openIn: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attachmentCode: undefined,
        attachmentName: undefined,
        model: undefined,
        unit: undefined,
        purveyorId: undefined,
        purveyorName: undefined,
      },
      // 表单参数
      form: {},
      formIn: {},
      // 表单校验
      rules: {
        attachmentCode: [
          { required: true, message: "编号不能为空", trigger: "blur" }
        ],
        attachmentName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "单位不能为空", trigger: "blur" }
        ],
        /* purveyorId: [
          { required: true, message: "供应商id不能为空", trigger: "blur" }
        ], */
      },
      purveyorList: [], // 用户列表
      quantityChange: 1,
    };
  },
  created() {
    /* this.getAllPurveyor() */
    this.getList();
    this.getProductList();
    this.getMaterialList();
    this.getUserList();
  },
  methods: {
    // 编号生成：KC + 时间戳
    generateCode() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hour = String(currentDate.getHours()).padStart(2, '0');
      const minute = String(currentDate.getMinutes()).padStart(2, '0');
      const second = String(currentDate.getSeconds()).padStart(2, '0');
      const timestamp = String(currentDate.getTime()).substring(10);
      this.form.attachmentCode = `KC${year}${month}${day}${hour}${minute}${second}${timestamp}`
    },
    /** 查询备件库管理列表 */
    getList() {
      this.loading = true;
      listAttachment(this.queryParams).then(response => {
        this.attachmentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 获取用户列表
    getUserList() {
      listUser().then(res => {
        this.purveyorList = res.rows;
      })
    },
    /** 查询库存列表 */
    getProductList() {
      listProduct().then(response => {
        this.productList = response.rows;
      });
    },
    /** 查询库存列表 */
    getMaterialList() {
      listMaterial().then(response => {
        this.materialList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openIn = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.quantityChange = 1;
      this.formIn = {
      },
        this.form = {
          attachmentId: undefined,
          attachmentCode: undefined,
          attachmentName: undefined,
          model: undefined,
          quantity: 0,
          unit: undefined,
          purveyorId: undefined,
          purveyorName: undefined,
          remark: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined,
          type: undefined,
        };
      this.resetForm("form");
      this.resetForm("formIn");
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
      this.ids = selection.map(item => item.attachmentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加出入库管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const attachmentId = row.attachmentId || this.ids
      getAttachment(attachmentId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改出入库管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.attachmentId != null) {
            updateAttachment(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAttachment(this.form).then(response => {
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
      const attachmentIds = row.attachmentId || this.ids;
      this.$modal.confirm('是否确认删除出入库信息？').then(() => {
        this.loading = true;
        return delAttachment(attachmentIds);
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
      this.download('system/attachment/export', {
        ...this.queryParams
      }, `attachment_${new Date().getTime()}.xlsx`)
    },
    productChange(data) {
      var product = this.productList.find(p => p.productId == data)
      this.form.attachmentCode = product.productNumber;
      this.form.attachmentName = product.productName;
      this.form.model = product.specification;
      this.form.unit = product.productUnit;
    },
    materialChange(data) {
      var material = this.materialList.find(p => p.materialId == data)
      this.form.attachmentCode = material.materialNumber;
      this.form.attachmentName = material.materialName;
      this.form.model = material.specification;
      this.form.unit = material.materialUnit;
    },
    typeChange(data) {
      this.reset();
      this.form.type = data;
    },
    /** 入库按钮操作 */
    handleIn(row) {
      this.loading = true;
      this.reset();
      const attachmentId = row.attachmentId || this.ids
      getAttachment(attachmentId).then(response => {
        this.loading = false;
        this.formIn = response.data;

        this.openIn = true;
        this.title = "入库管理";
      });
    },
    /** 出库按钮操作 */
    handleOut(row) {
      this.loading = true;
      this.reset();
      const attachmentId = row.attachmentId || this.ids
      getAttachment(attachmentId).then(response => {
        this.loading = false;
        this.formIn = response.data;

        if (this.formIn.quantity < 1) {
          this.$message({
            showClose: true,
            message: '当前库存库存为0',
            type: 'error'
          });
          return;
        }
        this.openIn = true;
        this.title = "出库管理";
      });
    },
    /** 提交按钮 */
    submitFormIn() {
      if (this.title == "出库管理") {
        this.formIn.quantity = this.formIn.quantity - this.quantityChange;
      } else {
        this.formIn.quantity = this.formIn.quantity + this.quantityChange;
      }
      this.buttonLoading = true;
      if (this.formIn.attachmentId != null) {
        updateAttachment(this.formIn).then(response => {
          if (this.title == "出库管理") {
            this.$modal.msgSuccess("出库成功");
          } else {
            this.$modal.msgSuccess("入库成功");
          }
          this.openIn = false;
          this.getList();
        }).finally(() => {
          getAttachment(this.formIn.attachmentId).then(response => {
            var attachment = response.data;
            var history = {
              purveyorCode: attachment.attachmentCode,
              purveyorName: attachment.attachmentName,
              nature: attachment.model,
              supplyType: this.title == "出库管理" ? '0' : '1',
              contractName: attachment.updateBy,
              contractPost: attachment.unit,
              contractPhone: this.quantityChange,
              type: attachment.type,
              contractEmail: attachment.purveyorId,
              address: attachment.purveyorName
            }
            addPurveyor(history);
          });
          this.buttonLoading = false;
        });
      }
    },
    purveyorChange(data) {
      var purveyor = this.purveyorList.find(p => p.userId == data)
      this.formIn.purveyorName = purveyor.nickName;
    }
  }
};
</script>
