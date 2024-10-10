<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品编号" prop="productNumber">
        <el-input v-model="queryParams.productNumber" placeholder="请输入产品编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="库存数量" prop="productQuantity">
          <el-input
            v-model="queryParams.productQuantity"
            placeholder="请输入库存数量"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
      <!-- <el-form-item label="库存单位" prop="productUnit">
          <el-input
            v-model="queryParams.productUnit"
            placeholder="请输入库存单位"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
      <el-form-item label="产品规格" prop="specification">
        <el-input v-model="queryParams.specification" placeholder="请输入产品规格" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="产品属性" prop="productAttribute">
          <el-input
            v-model="queryParams.productAttribute"
            placeholder="请输入产品属性"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="最大库存" prop="inventoryMax">
          <el-input
            v-model="queryParams.inventoryMax"
            placeholder="请输入最大库存"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="最小库存" prop="inventoryMin">
          <el-input
            v-model="queryParams.inventoryMin"
            placeholder="请输入最小库存"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="安全库存" prop="inventorySafe">
          <el-input
            v-model="queryParams.inventorySafe"
            placeholder="请输入安全库存"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工艺路线" prop="routeId">
          <el-input
            v-model="queryParams.routeId"
            placeholder="请输入工艺路线"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item> -->
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

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="productId" v-if="true"/> -->
      <el-table-column label="产品编号" align="center" prop="productNumber" width="180" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="工艺路线" align="center" prop="routeName" />
      <!-- <el-table-column label="库存数量" align="center" prop="productQuantity" /> -->
      <el-table-column label="库存单位" align="center" prop="productUnit" />
      <el-table-column label="产品规格" align="center" prop="specification" />
      <el-table-column label="产品属性" align="center" prop="productAttribute" />
      <!-- <el-table-column label="最大库存" align="center" prop="inventoryMax" />
      <el-table-column label="最小库存" align="center" prop="inventoryMin" />
      <el-table-column label="安全库存" align="center" prop="inventorySafe" /> -->
      <el-table-column label="创建人" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <!-- <el-table-column label="工艺路线" align="center" prop="routeId" />
        <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改产品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="产品编号" prop="productNumber">
              <el-input v-model="form.productNumber" placeholder="请输入产品编号" style="width: 220px;"
                :disabled="title == '修改产品'" /> <el-button type="primary" size="mini" @click="generateCode"
                :disabled="title == '修改产品'">自动生成</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品名称" prop="productName">
              <el-input v-model="form.productName" placeholder="请输入产品名称" style="width: 220px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <!-- <el-col :span="12">
            <el-form-item label="库存数量" prop="productQuantity">
              <el-input-number v-model="form.productQuantity" placeholder="请输入库存数量" :min="0" style="width: 220px;"/>
            </el-form-item>
          </el-col> -->
          
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="产品规格" prop="specification">
              <el-input v-model="form.specification" placeholder="请输入产品规格" style="width: 220px;" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="产品属性" prop="productAttribute">
              <el-input v-model="form.productAttribute" placeholder="请输入产品属性" style="width: 220px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="最大库存" prop="inventoryMax">
              <el-input-number v-model="form.inventoryMax" placeholder="请输入最大库存" :min="0" style="width: 220px;"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最小库存" prop="inventoryMin">
              <el-input-number v-model="form.inventoryMin" placeholder="请输入最小库存" :min="0" style="width: 220px;"/>
            </el-form-item>
          </el-col>
        </el-row> -->
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工艺路线" prop="routeId">
              <!-- <el-input v-model="form.routeId" placeholder="请输入工艺路线" /> -->
              <el-select v-model="form.routeId" placeholder="请选择工艺路线">
                <el-option v-for="item in routeList" :key="item.processRouteId" :label="item.processRouteName"
                  :value="item.processRouteId"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存单位" prop="productUnit">
              <el-select clearable v-model="form.productUnit" placeholder="请选择单位" style="width: 220px;">
                                <el-option v-for="dict in dict.type.produce_unit" :key="dict.value" :label="dict.value"
                                    :value="dict.value">
                                </el-option>
                            </el-select>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-form-item label="安全库存" prop="inventorySafe">
              <el-input-number v-model="form.inventorySafe" placeholder="请输入安全库存" :min="0" style="width: 220px;"/>
            </el-form-item>
          </el-col> -->
          
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" style="width: 660px;" v-model="form.remark" placeholder="请输入备注" />
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
import { listProduct, getProduct, delProduct, addProduct, updateProduct } from "@/api/basicData/product";
import { listProcessRoute } from "@/api/basicData/processRoute";
export default {
  name: "Product",
  dicts: ['produce_unit'],
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
      // 产品表格数据
      productList: [],
      // 工艺路线数据
      routeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productNumber: undefined,
        productName: undefined,
        productQuantity: undefined,
        productUnit: undefined,
        specification: undefined,
        productAttribute: undefined,
        inventoryMax: undefined,
        inventoryMin: undefined,
        inventorySafe: undefined,
        routeId: undefined,
        routeName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productId: [
          { required: true, message: "主键ID不能为空", trigger: "blur" }
        ],
        productNumber: [
          { required: true, message: "产品编号不能为空", trigger: "blur" }
        ],
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        productQuantity: [
          { required: true, message: "库存数量不能为空", trigger: "blur" }
        ],
        productUnit: [
          { required: true, message: "库存单位不能为空", trigger: "blur" }
        ],
        specification: [
          { required: true, message: "产品规格不能为空", trigger: "blur" }
        ],
        productAttribute: [
          { required: true, message: "产品属性不能为空", trigger: "blur" }
        ],
        inventoryMax: [
          { required: true, message: "最大库存不能为空", trigger: "blur" }
        ],
        inventoryMin: [
          { required: true, message: "最小库存不能为空", trigger: "blur" }
        ],
        inventorySafe: [
          { required: true, message: "安全库存不能为空", trigger: "blur" }
        ],
        /* routeId: [
          { required: true, message: "工艺路线不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ] */
      }
    };
  },
  created() {
    this.getList();
    this.getRouteList();
  },
  methods: {
    // 编号生成：CP + 时间戳
    generateCode() {
      const currentDate = new Date();
      const year = currentDate.getFullYear();
      const month = String(currentDate.getMonth() + 1).padStart(2, '0');
      const day = String(currentDate.getDate()).padStart(2, '0');
      const hour = String(currentDate.getHours()).padStart(2, '0');
      const minute = String(currentDate.getMinutes()).padStart(2, '0');
      const second = String(currentDate.getSeconds()).padStart(2, '0');
      const timestamp = String(currentDate.getTime()).substring(10);
      this.form.productNumber = `CP${year}${month}${day}${hour}${minute}${second}${timestamp}`
    },
    /** 查询产品列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询工艺流程列表 */
    getRouteList() {
      this.loading = true;
      listProcessRoute().then(response => {
        this.routeList = response.rows;
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
          productId: undefined,
          productNumber: undefined,
          productName: undefined,
          productQuantity: 0,
          productUnit: undefined,
          specification: undefined,
          productAttribute: undefined,
          inventoryMax: 0,
          inventoryMin: 0,
          inventorySafe: 0,
          routeId: undefined,
          routeName: undefined,
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
      this.ids = selection.map(item => item.productId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      //this.form.productNumber = this.generateCode();
      this.open = true;
      this.title = "添加产品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const productId = row.productId || this.ids
      getProduct(productId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改产品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          var processRoute = this.routeList.find(r => r.processRouteId === this.form.routeId)
          if (processRoute) {
            this.form.routeName = processRoute.processRouteName;
          }
          if (this.form.productId != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addProduct(this.form).then(response => {
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
      const productIds = row.productId || this.ids;
      this.$modal.confirm('是否确认删除产品编号为"' + productIds + '"的数据项？').then(() => {
        this.loading = true;
        return delProduct(productIds);
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
      this.download('system/product/export', {
        ...this.queryParams
      }, `product_${new Date().getTime()}.xlsx`)
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
  }
};
</script>
  