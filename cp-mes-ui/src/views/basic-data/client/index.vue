<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="客户编号" prop="clientNumber">
                <el-input v-model="queryParams.clientNumber" placeholder="请输入客户编号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="客户名称" prop="clientName">
                <el-input v-model="queryParams.clientName" placeholder="请输入客户名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="客户简称" prop="clientAbbreviation">
                <el-input v-model="queryParams.clientAbbreviation" placeholder="请输入客户简称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="联系人" prop="clientContact">
                <el-input v-model="queryParams.clientContact" placeholder="请输入联系人" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="联系电话" prop="clientPhone">
                <el-input v-model="queryParams.clientPhone" placeholder="请输入联系电话" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="客户地址" prop="clientAvatar">
                <el-input v-model="queryParams.clientAvatar" placeholder="请输入客户地址" clearable
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

        <el-table v-loading="loading" :data="clientList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="客户编号" align="center" prop="clientNumber" />
            <el-table-column label="客户名称" align="center" prop="clientName" />
            <el-table-column label="客户简称" align="center" prop="clientAbbreviation" />
            <el-table-column label="联系人" align="center" prop="clientContact" />
            <el-table-column label="联系电话" align="center" prop="clientPhone" />
            <el-table-column label="客户地址" align="center" prop="clientAvatar" />
            <el-table-column label="客户状态" align="center" prop="status">
                <template slot-scope="scope">
                    <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                        @change="handleStatusChange(scope.row)"></el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

        <!-- 添加或修改客户信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-form-item label="客户编号" prop="clientNumber">
                    <el-input v-model="form.clientNumber" placeholder="请输入客户编号" style="width: 250px;"
                        :disabled="title == '修改客户信息'" /> <el-button type="primary" size="mini" @click="generateCode"
                        :disabled="title == '修改客户信息'">自动生成</el-button>
                </el-form-item>
                <el-form-item label="客户名称" prop="clientName">
                    <el-input v-model="form.clientName" placeholder="请输入客户名称" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="客户简称" prop="clientAbbreviation">
                    <el-input v-model="form.clientAbbreviation" placeholder="请输入客户简称" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="联系人" prop="clientContact">
                    <el-input v-model="form.clientContact" placeholder="请输入联系人" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="联系电话" prop="clientPhone">
                    <el-input v-model="form.clientPhone" placeholder="请输入联系电话" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="客户地址" prop="clientAvatar">
                    <el-input v-model="form.clientAvatar" placeholder="请输入客户地址" style="width: 250px;" />
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="form.remark" style="width: 250px;" type="textarea" placeholder="请输入内容" />
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
import { listClient, getClient, delClient, addClient, updateClient } from "@/api/basicData/client";

export default {
    name: "Client",
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
            // 客户信息表格数据
            clientList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                clientNumber: undefined,
                clientName: undefined,
                clientAbbreviation: undefined,
                clientContact: undefined,
                clientPhone: undefined,
                clientAvatar: undefined,
                status: undefined,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                clientId: [
                    { required: true, message: "客户ID不能为空", trigger: "blur" }
                ],
                clientNumber: [
                    { required: true, message: "客户编号不能为空", trigger: "blur" }
                ],
                clientName: [
                    { required: true, message: "客户名称不能为空", trigger: "blur" }
                ],
                clientPhone: [
                    {
                        pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                        message: "请输入正确的手机号码",
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    created() {
        this.getList();
    },
    methods: {
        // 编号生成：KH + 时间戳
        generateCode() {
            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = String(currentDate.getMonth() + 1).padStart(2, '0');
            const day = String(currentDate.getDate()).padStart(2, '0');
            const hour = String(currentDate.getHours()).padStart(2, '0');
            const minute = String(currentDate.getMinutes()).padStart(2, '0');
            const second = String(currentDate.getSeconds()).padStart(2, '0');
            const timestamp = String(currentDate.getTime()).substring(10);
            this.form.clientNumber = `KH${year}${month}${day}${hour}${minute}${second}${timestamp}`
        },
        /** 查询客户信息列表 */
        getList() {
            this.loading = true;
            listClient(this.queryParams).then(response => {
                this.clientList = response.rows;
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
                clientId: undefined,
                clientNumber: undefined,
                clientName: undefined,
                clientAbbreviation: undefined,
                clientContact: undefined,
                clientPhone: undefined,
                clientAvatar: undefined,
                status: undefined,
                delFlag: undefined,
                createBy: undefined,
                createTime: undefined,
                updateBy: undefined,
                updateTime: undefined,
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
            this.ids = selection.map(item => item.clientId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加客户信息";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.loading = true;
            this.reset();
            const clientId = row.clientId || this.ids
            getClient(clientId).then(response => {
                this.loading = false;
                this.form = response.data;
                this.open = true;
                this.title = "修改客户信息";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    if (this.form.clientId != null) {
                        updateClient(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        }).finally(() => {
                            this.buttonLoading = false;
                        });
                    } else {
                        addClient(this.form).then(response => {
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
            const clientIds = row.clientId || this.ids;
            this.$modal.confirm('是否确认删除客户信息编号为"' + clientIds + '"的数据项？').then(() => {
                this.loading = true;
                return delClient(clientIds);
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
            this.download('system/client/export', {
                ...this.queryParams
            }, `client_${new Date().getTime()}.xlsx`)
        },
        // 客户状态修改
        handleStatusChange(row) {
            let text = row.status === "0" ? "启用" : "停用";
            this.$modal.confirm('确认要"' + text + '""' + row.clientName + '"客户吗？').then(function () {
                return updateClient(row);
            }).then(() => {
                this.$modal.msgSuccess(text + "成功");
            }).catch(function () {
                row.status = row.status === "0" ? "1" : "0";
            });
        },
    }
};
</script>
  