<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="物料编号" prop="materialNumber">
                <el-input v-model="queryParams.materialNumber" placeholder="请输入物料编号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="物料名称" prop="materialName">
                <el-input v-model="queryParams.materialName" placeholder="请输入物料名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="物料规格" prop="specification">
                <el-input v-model="queryParams.specification" placeholder="请输入物料规格" clearable
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

        <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="物料编号" align="center" prop="materialNumber" width="180" />
            <el-table-column label="物料名称" align="center" prop="materialName" />
            <!-- <el-table-column label="库存数量" align="center" prop="materialQuantity" /> -->
            <el-table-column label="库存单位" align="center" prop="materialUnit" />
            <el-table-column label="物料规格" align="center" prop="specification" />
            <el-table-column label="物料属性" align="center" prop="materialAttribute" />
            <!-- <el-table-column label="最大库存" align="center" prop="inventoryMax" />
            <el-table-column label="最小库存" align="center" prop="inventoryMin" />
            <el-table-column label="安全库存" align="center" prop="inventorySafe" /> -->
            <el-table-column label="创建人" align="center" prop="createBy" />
            <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit"
                        @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete"
                        @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />

        <!-- 添加或修改物料对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="物料编号" prop="materialNumber">
                            <el-input v-model="form.materialNumber" placeholder="请输入物料编号" style="width: 220px;"
                                :disabled="title == '修改物料'" /> <el-button type="primary" size="mini"
                                @click="generateCode" :disabled="title == '修改物料'">自动生成</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="物料名称" prop="materialName">
                            <el-input v-model="form.materialName" placeholder="请输入物料名称" style="width: 220px;" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24">
                    <!-- <el-col :span="12">
                        <el-form-item label="库存数量" prop="materialQuantity">
                            <el-input-number v-model="form.materialQuantity" placeholder="请输入库存数量" :min="0" style="width: 220px;"/>
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="12">
                        <el-form-item label="库存单位" prop="materialUnit">
                            <el-select clearable v-model="form.materialUnit" placeholder="请选择单位" style="width: 220px;">
                                <el-option v-for="dict in dict.type.produce_unit" :key="dict.value" :label="dict.value"
                                    :value="dict.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="物料规格" prop="specification">
                            <el-input v-model="form.specification" placeholder="请输入物料规格" style="width: 220px;" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24">

                    <el-col :span="12">
                        <el-form-item label="物料属性" prop="materialAttribute">
                            <el-input v-model="form.materialAttribute" placeholder="请输入物料属性" style="width: 220px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="备注" prop="remark">
                            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <!-- <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="最大库存" prop="inventoryMax">
                            <el-input-number v-model="form.inventoryMax" placeholder="请输入最大库存" :min="0" style="width: 220px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="最小库存" prop="inventoryMin">
                            <el-input-number v-model="form.inventoryMin" placeholder="请输入最小库存" :min="0" style="width: 220px;" />
                        </el-form-item>
                    </el-col>
                </el-row> -->
                <!-- <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="安全库存" prop="inventorySafe">
                            <el-input-number v-model="form.inventorySafe" placeholder="请输入安全库存" :min="0" style="width: 220px;"/>
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
import { listMaterial, getMaterial, delMaterial, addMaterial, updateMaterial } from "@/api/system/material";
export default {
    name: "material",
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
            // 物料表格数据
            materialList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                materialNumber: undefined,
                materialName: undefined,
                materialQuantity: undefined,
                materialUnit: undefined,
                specification: undefined,
                materialAttribute: undefined,
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
                materialId: [
                    { required: true, message: "主键ID不能为空", trigger: "blur" }
                ],
                materialNumber: [
                    { required: true, message: "物料编号不能为空", trigger: "blur" }
                ],
                materialName: [
                    { required: true, message: "物料名称不能为空", trigger: "blur" }
                ],
                materialQuantity: [
                    { required: true, message: "库存数量不能为空", trigger: "blur" }
                ],
                materialUnit: [
                    { required: true, message: "库存单位不能为空", trigger: "blur" }
                ],
                specification: [
                    { required: true, message: "物料规格不能为空", trigger: "blur" }
                ],
                materialAttribute: [
                    { required: true, message: "物料属性不能为空", trigger: "blur" }
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
            }
        };
    },
    created() {
        this.getList();
    },
    methods: {
        // 编号生成：WL + 时间戳
        generateCode() {
            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = String(currentDate.getMonth() + 1).padStart(2, '0');
            const day = String(currentDate.getDate()).padStart(2, '0');
            const hour = String(currentDate.getHours()).padStart(2, '0');
            const minute = String(currentDate.getMinutes()).padStart(2, '0');
            const second = String(currentDate.getSeconds()).padStart(2, '0');
            const timestamp = String(currentDate.getTime()).substring(10);
            this.form.materialNumber = `WL${year}${month}${day}${hour}${minute}${second}${timestamp}`
        },
        /** 查询物料列表 */
        getList() {
            this.loading = true;
            listMaterial(this.queryParams).then(response => {
                this.materialList = response.rows;
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
                materialId: undefined,
                materialNumber: undefined,
                materialName: undefined,
                materialQuantity: 0,
                materialUnit: undefined,
                specification: undefined,
                materialAttribute: undefined,
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
            this.ids = selection.map(item => item.materialId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "添加物料";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.loading = true;
            this.reset();
            const materialId = row.materialId || this.ids
            getMaterial(materialId).then(response => {
                this.loading = false;
                this.form = response.data;
                this.open = true;
                this.title = "修改物料";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    if (this.form.materialId != null) {
                        updateMaterial(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        }).finally(() => {
                            this.buttonLoading = false;
                        });
                    } else {
                        addMaterial(this.form).then(response => {
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
            const materialIds = row.materialId || this.ids;
            this.$modal.confirm('是否确认删除物料编号为"' + materialIds + '"的数据项？').then(() => {
                this.loading = true;
                return delMaterial(materialIds);
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
            this.download('system/material/export', {
                ...this.queryParams
            }, `material_${new Date().getTime()}.xlsx`)
        },
    }
};
</script>