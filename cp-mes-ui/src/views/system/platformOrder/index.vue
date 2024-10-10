<template>
    <div class="app-container">
        <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="平台工单附件ids" prop="ossIds">
          <el-input
            v-model="queryParams.ossIds"
            placeholder="请输入平台工单附件ids"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="用户id" prop="userId">
          <el-input
            v-model="queryParams.userId"
            placeholder="请输入用户id"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form> -->

        <el-row :gutter="10" class="mb8">
            <!-- <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
            </el-col> -->
            <el-col :span="1.5">
                <el-button type="success" v-if="userId !== 1" plain icon="el-icon-edit" size="mini" :disabled="single"
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

        <el-table v-loading="loading" :data="PlatformOrderList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <!-- <el-table-column label="平台工单id" align="center" prop="platformOrderId" v-if="true" /> -->
            <el-table-column label="平台工单类型" align="center" prop="orderType">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.platform_order_type" :value="scope.row.orderType" />
                </template>
            </el-table-column>
            <!-- <el-table-column label="平台工单内容" align="center" prop="orderContent" />
            <el-table-column label="平台工单附件ids" align="center" prop="ossIds" /> -->
            <el-table-column label="平台工单状态" align="center" prop="status">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.platform_order_status" :value="scope.row.status" />
                </template>
            </el-table-column>
            <!-- <el-table-column label="用户id" align="center" prop="userId" /> -->
            <el-table-column label="提交人" align="center" prop="createBy" />
            <el-table-column label="备注" align="center" prop="remark" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" v-if="userId === 1 && scope.row.status !== '1'" type="text" icon="el-icon-edit"
                        @click="handleDispose(scope.row)">处理</el-button>
                    <el-button size="mini" v-if="userId !== 1" type="text" icon="el-icon-edit"
                        @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

        <!-- 添加或修改平台工单信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="工单类型" prop="orderType">
                    <el-radio-group v-model="form.orderType">
                        <el-radio v-for="dict in dict.type.platform_order_type" :key="dict.value" :label="dict.value">{{
                            dict.label
                        }}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="工单内容" prop="orderContent">
                    <editor v-model="form.orderContent" :min-height="192" />
                </el-form-item>
                <el-form-item label="工单附件" prop="ossIds">
                    <!-- <el-input v-model="form.ossIds" placeholder="请输入平台工单附件ids" /> -->
                    <fileUpload v-model="form.ossIds" />
                </el-form-item>
                <!-- <el-form-item label="用户id" prop="userId">
                    <el-input v-model="form.userId" placeholder="请输入用户id" />
                </el-form-item> -->
                <el-form-item label="备注" prop="remark">
                    <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button v-if="userId !== 1" :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
                <el-button v-if="userId === 1" :loading="buttonLoading" type="primary" @click="submitForm">完 成</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
  
<script>
import { listPlatformOrder, getPlatformOrder, delPlatformOrder, addPlatformOrder, updatePlatformOrder } from "@/api/system/platformOrder";

export default {
    name: "platformOrder",
    dicts: ['platform_order_type', 'platform_order_status'],
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
            // 平台工单信息表格数据
            PlatformOrderList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                orderType: undefined,
                orderContent: undefined,
                ossIds: undefined,
                status: undefined,
                userId: undefined,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                platformOrderId: [
                    { required: true, message: "平台工单id不能为空", trigger: "blur" }
                ],
                orderType: [
                    { required: true, message: "平台工单类型不能为空", trigger: "change" }
                ],
                orderContent: [
                    { required: true, message: "平台工单内容不能为空", trigger: "blur" }
                ],
                /* ossIds: [
                    { required: true, message: "平台工单附件ids不能为空", trigger: "blur" }
                ],
                status: [
                    { required: true, message: "平台工单状态不能为空", trigger: "change" }
                ],
                userId: [
                    { required: true, message: "用户id不能为空", trigger: "blur" }
                ],
                remark: [
                    { required: true, message: "备注不能为空", trigger: "blur" }
                ], */
            },
            userId: this.$store.getters.userId,
        };
    },
    created() {
        const userId = this.$route.params && this.$route.params.userId;
        if (userId) {
            this.queryParams.userId = userId;
        }
        this.getList();
    },
    methods: {
        /** 查询平台工单信息列表 */
        getList() {
            this.loading = true;
            if (this.userId !== 1) {
                this.queryParams.userId = this.userId;
            }
            listPlatformOrder(this.queryParams).then(response => {
                this.PlatformOrderList = response.rows;
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
                platformOrderId: undefined,
                orderType: undefined,
                orderContent: undefined,
                ossIds: undefined,
                status: undefined,
                userId: undefined,
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
            this.ids = selection.map(item => item.platformOrderId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加平台工单信息";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.loading = true;
            this.reset();
            const platformOrderId = row.platformOrderId || this.ids
            getPlatformOrder(platformOrderId).then(response => {
                this.loading = false;
                this.form = response.data;
                this.open = true;
                this.title = "修改平台工单信息";
            });
        },
        /** 处理按钮操作 */
        handleDispose(row) {
            this.loading = true;
            this.reset();
            const platformOrderId = row.platformOrderId || this.ids
            getPlatformOrder(platformOrderId).then(response => {
                this.loading = false;
                this.form = response.data;
                this.form.status = '1'
                this.open = true;
                this.title = "处理平台工单";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    if (this.form.platformOrderId != null) {
                        updatePlatformOrder(this.form).then(response => {
                            this.$modal.msgSuccess("操作成功");
                            this.open = false;
                            this.getList();
                        }).finally(() => {
                            this.buttonLoading = false;
                        });
                    } else {
                        addPlatformOrder(this.form).then(response => {
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
            const platformOrderIds = row.platformOrderId || this.ids;
            this.$modal.confirm('是否确认删除平台工单信息编号为"' + platformOrderIds + '"的数据项？').then(() => {
                this.loading = true;
                return delPlatformOrder(platformOrderIds);
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
            this.download('system/PlatformOrder/export', {
                ...this.queryParams
            }, `PlatformOrder_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
  