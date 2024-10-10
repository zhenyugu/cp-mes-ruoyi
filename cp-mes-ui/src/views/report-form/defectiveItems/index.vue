<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="100px">
            <el-form-item label="时间范围" prop="dateRange">
                <el-date-picker v-model="queryParams.dateRange" style="width: 300px" value-format="yyyy-MM-dd HH:mm:ss"
                    type="daterange" range-separator="-" start-placeholder="报工完成开始日期" end-placeholder="报工完成结束日期"
                    :default-time="['00:00:00', '23:59:59']"></el-date-picker>
            </el-form-item>
            <el-form-item label="不良项编号" prop="defectNumber">
                <el-input v-model="queryParams.defectNumber" placeholder="请输入不良项编号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="不良项名称" prop="defectName">
                <el-input v-model="queryParams.defectName" placeholder="请输入不良项名称" clearable
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
        <el-table v-loading="loading" border :data="defectList">
            <el-table-column type="index" width="55" align="center" />
            <el-table-column label="不良项编号" width="180"  align="center" prop="defectNumber" />
            <el-table-column label="不良项名称" align="center" prop="defectName" />
            <el-table-column label="占比" align="center" prop="percent" />
            <el-table-column v-for="(column, index) in columns" :label="column.timeLabel" :key="index"
                align="center">
                <template v-slot="{ row }">
                    <span>{{ row.columns[index].defectQuantity }}</span>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />
    </div>
</template>

<script>
import { defectiveItems } from "@/api/basicData/defect";
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
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                defectNumber: undefined,
                defectName: undefined,
                dateRange: [],
            },
            // 表单参数
            form: {},
            columns: [],

        };
    },
    created() {
        this.getList();
    },
    methods: {
        /** 查询不良项列表 */
        getList() {
            this.loading = true;
            let params = {
                reportStart: this.queryParams.dateRange[0],
                reportEnd: this.queryParams.dateRange[1],
            }
            this.queryParams.params = params;
            defectiveItems(this.queryParams).then(response => {
                this.defectList = response.rows;
                if (this.defectList.length > 0) {
                    this.columns = this.defectList[0].columns;
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
            this.download('system/defect/export', {
                ...this.queryParams
            }, `不良品项分布${new Date().getTime()}.xlsx`)
        },

    }
};
</script>