<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="时间范围" prop="dateRange">
                <el-date-picker v-model="queryParams.dateRange" style="width: 300px" value-format="yyyy-MM-dd HH:mm:ss"
                    type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                    :default-time="['00:00:00', '23:59:59']"></el-date-picker>
            </el-form-item>
            <el-form-item label="工单编号" prop="sheetNumber">
                <el-input v-model="queryParams.sheetNumber" placeholder="请输入工单编号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="产品编号" prop="productNumber">
                <el-input v-model="queryParams.productNumber" placeholder="请输入产品编号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="产品名称" prop="productName">
                <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="产品规格" prop="productSpecification">
                <el-input v-model="queryParams.productSpecification" placeholder="请输入产品规格" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="工序编号" prop="procedureNumber">
                <el-input v-model="queryParams.procedureNumber" placeholder="请输入工序编号" clearable
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

        <el-table v-loading="loading" :data="jobBookingList">
            <el-table-column type="index" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="sheetNumber" width="180" />
            <el-table-column label="产品编号" align="center" prop="productNumber" width="180" />
            <el-table-column label="产品名称" align="center" prop="productName" />
            <el-table-column label="产品规格" align="center" prop="productSpecification" />
            <el-table-column label="工序编号" align="center" prop="procedureNumber" width="180" />
            <el-table-column label="工序名称" align="center" prop="procedureName" />
            <el-table-column label="生产人员" align="center" prop="personnelName" />
            <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
            <el-table-column label="报工数量" align="center" prop="jobBookingNum" />
            <el-table-column label="良品数" align="center" prop="goodQuantity" />
            <el-table-column label="不良品数" align="center" prop="defectQuantity" />
            <el-table-column label="不良品率" align="center" prop="percent" />
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />
    </div>
</template>

<script>
import { listJobBooking } from "@/api/produce/jobBooking";
export default {
    name: "JobBooking",
    dicts: ['procedure_status', 'produce_unit'],
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
            // 报工表格数据
            jobBookingList: [],
            //工单表格数据
            sheetList: [],
            //任务工序表格数据
            taskList: [],
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
                dateRange: [],
            },
            // 表单参数
            form: {},
        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询报工列表 */
        getList() {
            this.loading = true;
            let params = {
                startTime: this.queryParams.dateRange[0],
                endTime: this.queryParams.dateRange[1],
            }
            this.queryParams.params = params;
            listJobBooking(this.queryParams).then(response => {
                this.jobBookingList = response.rows;
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
            this.download('system/jobBooking/export', {
                ...this.queryParams
            }, `不良品项汇总_${new Date().getTime()}.xlsx`)
        },
    }
};
</script>