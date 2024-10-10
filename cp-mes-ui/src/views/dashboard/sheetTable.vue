<template>
    <div>
        <el-table v-loading="loading" :data="sheetList" @selection-change="handleSelectionChange" height="430">
            <el-table-column type="index" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="sheetNumber" width="180">
                <template slot-scope="scope">
                    <a @click="handleSheetDetail(scope.row)" style="color: #4874cb;">{{ scope.row.sheetNumber }}</a>
                </template>
            </el-table-column>
            <!-- <el-table-column label="产品编号" align="center" prop="productNumber" width="180">
                <template slot-scope="scope">
                    <a @click="handleProductDetail(scope.row)" style="color: #4874cb;">{{ scope.row.productNumber }}</a>
                </template>
            </el-table-column> -->
            <el-table-column label="产品名称" align="center" prop="productName" />
            <el-table-column label="客户" align="center" prop="clientName" />
            <el-table-column label="计划数" align="center" prop="plannedQuantity" />
            <el-table-column label="状态" align="center" prop="status">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.sheet_status" :value="scope.row.status" />
                </template>
            </el-table-column>
            <el-table-column label="计划开始时间" align="center" prop="planStart" width="180">
            </el-table-column>
            <el-table-column label="计划结束时间" align="center" prop="planEnd" width="180">
            </el-table-column>
            <el-table-column label="工单进度条" align="center" prop="tasks" :width="calculateWith()">
                <template slot-scope="scope">
                    <div style="display: flex;">
                        <circleProgress v-for="(item, index) in scope.row.tasks" :key="index"
                            :length="scope.row.tasks.length" :currentIndex="index" :task="item" @data="handleSheetList"
                            :procedureStatusList="procedureStatusList"></circleProgress>
                    </div>
                </template>
            </el-table-column>
            
            
            <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
                </template>
            </el-table-column> -->
        </el-table>

        <!-- <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" /> -->

        <!-- 添加或修改工单对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1250px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="110px">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="工单编号" prop="sheetNumber">
                            <el-input v-model="form.sheetNumber" placeholder="请输入工单编号" :disabled="true"
                                style="width: 270px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" style="display: flex; justify-content: space-between;">
                        <el-form-item label="计划开始时间" prop="planStart">
                            <el-date-picker clearable v-model="form.planStart" type="date" placeholder="开始日期" :disabled="true"
                                value-format="yyyy-MM-dd" style="width: 135px;">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="selectStart" label-width="0px">
                            <el-time-select v-model="form.selectStart"
                                :key="form.selectStart"
                                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间" :disabled="true"
                                value-format="HH:mm" style="width: 135px;">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" style="display: flex; justify-content: space-between;">
                        <el-form-item label="计划结束时间" prop="planEnd">
                            <el-date-picker clearable v-model="form.planEnd" type="date" style="width: 135px;" :disabled="true"
                                value-format="yyyy-MM-dd" placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="selectEnd" label-width="0px">
                            <el-time-select v-model="form.selectEnd" 
                                :key="form.selectEnd"
                                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间" :disabled="true"
                                value-format="HH:mm" style="width: 135px;">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="产品信息" prop="productName">
                            <el-select v-model="form.productName" placeholder="请选择产品信息" @change="productChange" :disabled="true"
                                style="width: 270px;">
                                <el-option v-for="item in productList" :key="item.productId"
                                    :label="item.productNumber + ' - ' + item.productName"
                                    :value="item.productId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="计划数" prop="plannedQuantity">
                            <el-input-number v-model="form.plannedQuantity" placeholder="请输入计划数" :min="0" :disabled="true"
                                style="width: 270px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="工单状态" prop="status">
                            <dict-tag :options="dict.type.sheet_status" :value="form.status" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24">

                    <el-col :span="8">
                        <el-form-item label="客户" prop="clientName">
                            <el-select v-model="form.clientName" placeholder="请选择客户信息" style="width: 270px;"  :disabled="true">
                                <el-option v-for="item in clientList" :key="item.clientId" :label="item.clientName"
                                    :value="item.clientId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="关联单据" prop="relatedDocuments">
                            <el-input v-model="form.relatedDocuments" placeholder="请输入关联单据" style="width: 270px;" :disabled="true" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" v-if="title == '修改工单'">
                        <el-form-item label="二维码">
                            <div>
                                <canvas ref="canvas" height="0px" width="0px"></canvas>
                                <img :src="imageUrl" alt="QR code" height="130px">
                            </div>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="备注" prop="remark">
                            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" style="width: 270px;" :disabled="true" />
                        </el-form-item>
                    </el-col>

                </el-row>
                <div>
                    <el-form-item label="生产任务" prop="taskIds">
                        <div>
                            <el-table v-loading="paramLoading" :data="bindingProcedure" ref="sortableTable"
                                row-key="procedureId">
                                <el-table-column type="index" width="55" align="center">
                                    <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                                </el-table-column>
                                <el-table-column label="顺序" type="index" width="55" align="center" />
                                <el-table-column label="工序名称" prop="procedureName" align="center">
                                </el-table-column>
                                <el-table-column label="工序编号" prop="procedureNumber" align="center">
                                </el-table-column>
                                <el-table-column label="报工权限" prop="reportingName" align="center">
                                </el-table-column>
                                <el-table-column label="分配列表" prop="distributionList" align="center">
                                </el-table-column>
                                <el-table-column label="报工数配比" prop="reportingRatio" align="center">
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
        <!-- 添加任务对话框 -->
        <el-dialog :title="'添加任务'" :visible.sync="openAddTask" width="800px" append-to-body>
            <el-table ref="task" v-loading="loading" :data="procedureList"
                @selection-change="handleProcedureSelectionChange">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column type="index" width="55" align="center" />
                <el-table-column label="工序编号" align="center" prop="procedureNumber" />
                <el-table-column label="工序名称" align="center" prop="procedureName" />
                <el-table-column label="报工权限" align="center" prop="reportingName" />
                <el-table-column label="计划数默认值" align="center" prop="plannedQuantity" />
                <el-table-column label="报工数配比" align="center" prop="reportingRatio" />
                <el-table-column label="不良品项列表" align="center" prop="defectiveNames" />
            </el-table>
            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitTask">确 定</el-button>
                <el-button @click="cancelSubmitTask">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 添加或修改任务对话框 -->
        <el-dialog :title="'编辑工序'" :visible.sync="openEditTask" width="800px" append-to-body>
            <el-form ref="editTaskForm" :model="editTaskForm" :rules="taskRules" label-width="120px">
                <el-form-item label="工序编号" prop="procedureId">
                    <el-select v-model="editTaskForm.procedureId" :clearable="true" placeholder="请选择工序"
                        @change="procedureChange">
                        <el-option v-for="item in procedureList" :key="item.procedureId" :label="item.procedureNumber"
                            :value="item.procedureId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="工序名称" prop="procedureName">
                    <el-input v-model="editTaskForm.procedureName" placeholder="请输入工序名称" style="width: 250px;"
                        :disabled="true" />
                </el-form-item>
                <el-form-item label="报工权限" prop="reportingName">
                    <el-input v-model="editTaskForm.reportingName" placeholder="请输入报工权限" style="width: 250px;"
                        :disabled="true" />
                </el-form-item>
                <el-form-item label="报工数配比" prop="reportingRatio">
                    <el-input v-model="editTaskForm.reportingRatio" placeholder="请输入报工数配比" style="width: 250px;" />
                </el-form-item>
                <!-- <el-form-item label="计划数" prop="planQuantity">
                    <el-input v-model="editTaskForm.planQuantity" placeholder="请输入计划数" style="width: 250px;" />
                </el-form-item> -->
                <el-form-item label="良品数" prop="goodQuantity">
                    <el-input v-model="editTaskForm.goodQuantity" placeholder="请输入良品数" style="width: 250px;"
                        :disabled="true" />
                </el-form-item>
                <el-form-item label="不良品数" prop="defectQuantity">
                    <el-input v-model="editTaskForm.defectQuantity" placeholder="请输入不良品数" style="width: 250px;"
                        :disabled="true" />
                </el-form-item>
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="计划开始时间" prop="startTimePlan">
                            <el-date-picker clearable v-model="editTaskForm.startTimePlan" type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择计划开始时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="计划结束时间" prop="endTimePlan">
                            <el-date-picker clearable v-model="editTaskForm.endTimePlan" type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择计划结束时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24">
                    <el-col :span="12">
                        <el-form-item label="实际开始时间" prop="startTime">
                            <el-date-picker clearable v-model="editTaskForm.startTime" type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择实际开始时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="实际结束时间" prop="endTime">
                            <el-date-picker clearable v-model="editTaskForm.endTime" type="datetime"
                                value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择实际结束时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitEditTaskForm">确 定</el-button>
                <el-button @click="cancelSubmitEditTaskForm">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 产品详情对话框 -->
        <el-dialog :title="title" :visible.sync="openProduct" width="500px" append-to-body>
            <el-form ref="formProduct" :model="formProduct" :rules="rules" label-width="80px">
                <el-form-item label="产品编号" prop="productNumber">
                    <el-input v-model="formProduct.productNumber" placeholder="请输入产品编号" :disabled="true" />
                </el-form-item>
                <el-form-item label="产品名称" prop="productName">
                    <el-input v-model="formProduct.productName" placeholder="请输入产品名称" :disabled="true" />
                </el-form-item>
                <el-form-item label="库存数量" prop="productQuantity">
                    <el-input-number v-model="formProduct.productQuantity" placeholder="请输入库存数量" :disabled="true"
                        :min="0" />
                </el-form-item>
                <el-form-item label="库存单位" prop="productUnit">
                    <el-input v-model="formProduct.productUnit" placeholder="请输入库存单位" :disabled="true" />
                </el-form-item>
                <el-form-item label="产品规格" prop="specification">
                    <el-input v-model="formProduct.specification" placeholder="请输入产品规格" :disabled="true" />
                </el-form-item>
                <el-form-item label="产品属性" prop="productAttribute">
                    <el-input v-model="formProduct.productAttribute" placeholder="请输入产品属性" :disabled="true" />
                </el-form-item>
                <el-form-item label="最大库存" prop="inventoryMax">
                    <el-input-number v-model="formProduct.inventoryMax" placeholder="请输入最大库存" :disabled="true" :min="0" />
                </el-form-item>
                <el-form-item label="最小库存" prop="inventoryMin">
                    <el-input-number v-model="formProduct.inventoryMin" placeholder="请输入最小库存" :disabled="true" :min="0" />
                </el-form-item>
                <el-form-item label="安全库存" prop="inventorySafe">
                    <el-input-number v-model="formProduct.inventorySafe" placeholder="请输入安全库存" :disabled="true" :min="0" />
                </el-form-item>
                <el-form-item label="工艺路线" prop="routeId">
                    <el-select v-model="formProduct.routeId" placeholder="请选择工艺路线" :disabled="true">
                        <el-option v-for="item in routeList" :key="item.processRouteId" :label="item.processRouteName"
                            :value="item.processRouteId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="formProduct.remark" placeholder="请输入备注" :disabled="true" />
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
  
<script>
import { allListSheet, getSheet, delSheet, addSheet, updateSheet } from "@/api/produce/sheet";
import Vue from 'vue'
import {
    listProcedure,
} from "@/api/basicData/procedure";
import { listProduct } from "@/api/basicData/product";
import CircleProgress from '../produce/sheet/progress.vue';
import { listProcessRoute } from "@/api/basicData/processRoute";
import { getDicts } from "@/api/system/dict/data";
import QRCode from 'qrcode';
export default {
    components: {
        CircleProgress,
    },
    name: "Sheet",
    dicts: ['sheet_status', 'produce_unit'],
    props: {
        params: {},
    },
    data() {
        return {
            formProduct: {},
            // 按钮loading
            buttonLoading: false,
            // 遮罩层
            loading: true,
            paramLoading: false,
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
            // 工单表格数据
            sheetList: [],
            //选中的工序
            selectedProcedure: [],
            //工单绑定的工序任务
            bindingProcedure: [],
            //工序表格数据
            procedureList: [],
            //产品表格数据
            productList: [],
            // 工艺路线数据
            routeList: [],
            procedureStatusList: [],
            clientList:[],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            openAddTask: false,
            openEditTask: false,
            openProduct: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                sheetNumber: undefined,
                productNumber: undefined,
                productName: undefined,
                productSpecification: undefined,
                unit: undefined,
                planStart: undefined,
                planEnd: undefined,
                plannedQuantity: undefined,
                relatedDocuments: undefined,
                status: undefined,
                qrcode: undefined,
            },
            // 表单参数
            form: {},
            editTaskForm: {},
            //被选中修改任务的index
            selectTaskIndex: undefined,
            // 表单校验
            taskRules: {
                taskId: [
                    { required: true, message: "主键ID不能为空", trigger: "blur" }
                ],
                productNumber: [
                    { required: true, message: "产品编号不能为空", trigger: "blur" }
                ],
                productName: [
                    { required: true, message: "产品名称不能为空", trigger: "blur" }
                ],
                productSpecification: [
                    { required: true, message: "产品规格不能为空", trigger: "blur" }
                ],
                procedureNumber: [
                    { required: true, message: "工序编号不能为空", trigger: "blur" }
                ],
                procedureName: [
                    { required: true, message: "工序名称不能为空", trigger: "blur" }
                ],
                reportingAuthority: [
                    { required: true, message: "报工权限不能为空", trigger: "blur" }
                ],
                distributionList: [
                    { required: true, message: "分配列表不能为空", trigger: "blur" }
                ],
                reportingRatio: [
                    { required: true, message: "报工数配比不能为空", trigger: "blur" }
                ],
                procedureStatus: [
                    { required: true, message: "工序状态不能为空", trigger: "change" }
                ],
                planQuantity: [
                    { required: true, message: "计划数不能为空", trigger: "blur" }
                ],
                startTimePlan: [
                    { required: true, message: "计划开始时间不能为空", trigger: "blur" }
                ],
                endTimePlan: [
                    { required: true, message: "计划结束时间不能为空", trigger: "blur" }
                ],

            },
            // 表单校验
            rules: {
                sheetId: [
                    { required: true, message: "主键ID不能为空", trigger: "blur" }
                ],
                sheetNumber: [
                    { required: true, message: "工单编号不能为空", trigger: "blur" }
                ],
                productNumber: [
                    { required: true, message: "产品编号不能为空", trigger: "blur" }
                ],

                unit: [
                    { required: true, message: "单位不能为空", trigger: "blur" }
                ],
                planStart: [
                    { required: true, message: "计划开始时间不能为空", trigger: "blur" }
                ],
                planEnd: [
                    { required: true, message: "计划结束时间不能为空", trigger: "blur" }
                ],
                plannedQuantity: [
                    { required: true, message: "计划数不能为空", trigger: "blur" }
                ],

                status: [
                    { required: true, message: "状态不能为空", trigger: "change" }
                ],

            },
            paramsValue: {},
            procedureStatusList: [],
            imageUrl:'',
        };
    },
    watch: {
        params(newValue) {
            this.paramsValue = newValue
            this.queryParams.params = newValue;
            this.getList();
        }
    },
    created() {

        /* this.getProcedureList(); */
        this.getProductList();
        this.getRouteList();
        this.getComponents();
    },
    methods: {
        getComponents() {
            getDicts("procedure_status").then(response => {
                this.procedureStatusList = response.data;
            });
        },
        // 编号生成：GD + 时间戳
        generateCode() {
            const currentDate = new Date();
            const year = currentDate.getFullYear();
            const month = String(currentDate.getMonth() + 1).padStart(2, '0');
            const day = String(currentDate.getDate()).padStart(2, '0');
            const hour = String(currentDate.getHours()).padStart(2, '0');
            const minute = String(currentDate.getMinutes()).padStart(2, '0');
            const second = String(currentDate.getSeconds()).padStart(2, '0');
            const timestamp = String(currentDate.getTime()).substring(10);
            return `GD${year}${month}${day}${hour}${minute}${second}${timestamp}`
        },
        /** 查询工单列表 */
        getList() {
            this.loading = true;
            allListSheet(this.queryParams).then(response => {
                this.sheetList = response.data;
                //this.total = response.total;
                this.loading = false;
                var data = {};
                data.sheetNum =this.sheetList.length;
                data.planNum = this.sheetList.reduce((sum, item) => sum + item.plannedQuantity, 0);
                data.finishNum = this.sheetList.filter(item => item.status === '2').length;
                data.averageProduce = (data.finishNum / (this.day(this.queryParams.params.startTime, this.queryParams.params.endTime) + 1)).toFixed(1);
                data.delayNum = this.sheetList.filter(item => item.status !== '2' && this.day(new Date(), item.planEnd) < 0).length;
                this.$emit('data', data);
            });
        },
        // 获取工序列表
        getProcedureList() {
            listProcedure().then(res => {
                this.procedureList = res.rows;
            })
        },
        /** 查询产品列表 */
        getProductList() {
            listProduct().then(response => {
                this.productList = response.rows;
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
            this.selectedProcedure = [],
                this.bindingProcedure = [],
                this.form = {
                    sheetId: undefined,
                    sheetNumber: undefined,
                    productNumber: undefined,
                    productName: undefined,
                    productSpecification: undefined,
                    unit: undefined,
                    planStart: undefined,
                    planEnd: undefined,
                    plannedQuantity: 0,
                    relatedDocuments: undefined,
                    status: undefined,
                    qrcode: undefined,
                    createBy: undefined,
                    createTime: undefined,
                    updateBy: undefined,
                    updateTime: undefined,
                    delFlag: undefined,
                    remark: undefined
                };
            this.formProduct = {},
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
            this.ids = selection.map(item => item.sheetId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },

        // 添加任务时多选框选中数据
        handleProcedureSelectionChange(selection) {
            this.selectedProcedure = selection;
            /* this.ids = selection.map(item => item.sheetId)
            this.single = selection.length !== 1
            this.multiple = !selection.length */
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.form.sheetNumber = this.generateCode();
            this.open = true;
            this.title = "创建工单";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.loading = true;
            this.reset();
            const sheetId = row.sheetId || this.ids
            getSheet(sheetId).then(response => {
                this.loading = false;
                this.form = response.data;
                this.bindingProcedure = response.data.tasks;
                this.open = true;
                this.title = "修改工单";
            });
        },
        /** 查看工单详情按钮操作 */
        handleSheetDetail(row) {
            this.loading = true;
            this.reset();
            const sheetId = row.sheetId || this.ids
            getSheet(sheetId).then(response => {
                this.loading = false;
                this.form = response.data;
                this.generateQRCode();
                this.bindingProcedure = response.data.tasks;
                this.open = true;
                this.title = "工单详情";
            });
        },
        /** 查看产品详情按钮操作 */
        handleProductDetail(row) {
            this.loading = true;
            this.reset();
            this.formProduct = this.productList.find(p => p.productNumber == row.productNumber);
            this.loading = false;
            this.openProduct = true;
            this.title = "产品详情";

        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    this.bindingProcedure.forEach(e => {
                        e.sheetNumber = this.form.sheetNumber;
                        e.productNumber = this.form.productNumber;
                        e.productName = this.form.productName;
                        e.productSpecification = this.form.productSpecification;
                    })
                    this.form.tasks = this.bindingProcedure;
                    if (this.form.sheetId != null) {
                        updateSheet(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        }).finally(() => {
                            this.buttonLoading = false;
                        });
                    } else {
                        addSheet(this.form).then(response => {
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
            const sheetIds = row.sheetId || this.ids;
            this.$modal.confirm('是否确认删除工单编号为"' + sheetIds + '"的数据项？').then(() => {
                this.loading = true;
                return delSheet(sheetIds);
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
            this.download('system/sheet/export', {
                ...this.queryParams
            }, `sheet_${new Date().getTime()}.xlsx`)
        },
        selectProcedure() {
            this.openAddTask = true;
        },

        submitTask() {
            this.bindingProcedure = this.bindingProcedure.concat(this.selectedProcedure);
            this.$refs.task.clearSelection(); // 清空选中行
            this.openAddTask = false;
        },
        cancelSubmitTask() {
            this.openAddTask = false;
        },
        // 任务删除按钮
        handleDeleteTask(row) {
            let index = this.bindingProcedure.findIndex(p => p.procedureId == row.procedureId)
            if (index < 0) return
            this.bindingProcedure.splice(index, 1)
        },
        //任务修改按钮
        handleUpdateTask(row) {
            this.selectTaskIndex = this.bindingProcedure.findIndex(p => p.procedureId == row.procedureId);
            var procedure = this.bindingProcedure.find(p => p.procedureId == row.procedureId)
            this.editTaskForm = { ...procedure };
            this.openEditTask = true;
        },
        //任务修改确定按钮
        submitEditTaskForm() {
            this.$refs["editTaskForm"].validate(valid => {
                if (valid) {
                    Vue.set(this.bindingProcedure, this.selectTaskIndex, this.editTaskForm);
                    this.openEditTask = false;
                }
            }
            )
        },
        //取消提交修改工序表单
        cancelSubmitEditTaskForm() {
            this.editTaskForm = {};
            this.openEditTask = false;
        },
        procedureChange(data) {
            var procedure = this.procedureList.find(p => p.procedureId == data);
            this.editTaskForm = { ...procedure };
        },
        productChange(data) {
            var product = this.productList.find(p => p.productId == data)
            this.form.productNumber = product.productNumber;
            this.form.productName = product.productName;
            this.form.productSpecification = product.specification;
        },
        calculateWith() {
            var max
            for (var i = 0; i < this.sheetList.length; i++) {
                max = max === undefined ? this.sheetList[i].tasks.length : (max > this.sheetList[i].tasks.length ? max : this.sheetList[i].tasks.length)
            }
            return max * 120 - 60
        },
        day(startTime, endTime) {
            const start = new Date(startTime);
            const end = new Date(endTime);
            const timeDifference = end.getTime() - start.getTime();
            const dayDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
            return dayDifference;
        },
        handleSheetList(data) {
            this.getList();
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
        },
        async generateQRCode() {
            try {
                const canvas = this.$refs.canvas;
                const imageUrl = await QRCode.toDataURL(this.form.sheetNumber, { canvas });
                this.imageUrl = imageUrl;
            } catch (error) {
                console.error(error);
            }
        },


    }
};
</script>
<style>
.custom{
    width: 270px;
}
</style>