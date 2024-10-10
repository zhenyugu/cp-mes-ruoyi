<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="时间范围" prop="dateRange">
        <el-date-picker v-model="queryParams.dateRange" style="width: 300px" value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange" range-separator="-" start-placeholder="工单计划开始日期" end-placeholder="工单计划结束日期"
          :default-time="['00:00:00', '23:59:59']"></el-date-picker>
      </el-form-item>
      <el-form-item label="产品编号" prop="productNumber">
        <el-input v-model="queryParams.productNumber" placeholder="请输入产品编号" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="产品规格" prop="specification">
        <el-input v-model="queryParams.specification" placeholder="请输入产品规格" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="productList">
      <el-table-column type="index" width="55" align="center" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="产品编号" align="center" prop="productNumber" width="180" />
      <el-table-column label="产品规格" align="center" prop="specification" />
      <el-table-column label="产品属性" align="center" prop="productAttribute" />
      <el-table-column label="库存单位" align="center" prop="productUnit" />
      <el-table-column label="工艺路线" align="center" prop="routeName" />
      <el-table-column label="最大库存" align="center" prop="inventoryMax" />
      <el-table-column label="最小库存" align="center" prop="inventoryMin" />
      <el-table-column label="安全库存" align="center" prop="inventorySafe" />
      <el-table-column label="库存数量" align="center" prop="productQuantity" />
      <el-table-column v-for="(column, index) in columns" :label="column.timeLabel" :key="index" align="center">
        <el-table-column label="良品数" align="center">
          <template v-slot="{ row }">
            <span>{{ row.columns[index].goodQuantity }}</span>
          </template>
        </el-table-column>
        <el-table-column label="不良品数" align="center">

          <template v-slot="{ row }">
            <span>{{ row.columns[index].defectQuantity }}</span>
          </template>
        </el-table-column>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


  </div>
</template>

<script>
import { listProductionStatistics } from "@/api/basicData/product";
export default {
  name: "Product",
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
        dateRange: [],
      },
      columns: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询产品列表 */
    getList() {
      this.loading = true;
      let params = {
        reportStart: this.queryParams.dateRange[0],
        reportEnd: this.queryParams.dateRange[1],
      }
      this.queryParams.params = params;
      listProductionStatistics(this.queryParams).then(response => {
        this.productList = response.rows;
        if (this.productList.length > 0) {
          this.columns = this.productList[0].columns;
        }
        this.total = response.total;
        this.loading = false;
      });
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/product/export', {
        ...this.queryParams
      }, `产量统计_${new Date().getTime()}.xlsx`)
    },
  }
};
</script>