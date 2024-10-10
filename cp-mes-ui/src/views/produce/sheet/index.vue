<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
            label-width="68px">
            <el-form-item label="工单状态" prop="queryStatus">
                <el-select v-model="queryParams.queryStatus" multiple placeholder="请选择状态" clearable
                    @keyup.enter.native="handleQuery">
                    <el-option v-for="item in dict.type.sheet_status" :key="item.value" :label="item.label"
                        :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="客户订单" prop="clientId">
                <el-select v-model="queryParams.clientId" placeholder="请选择所属客户" clearable
                    @keyup.enter.native="handleQuery">
                    <el-option v-for="item in clientList" :key="item.clientId" :label="item.clientName"
                        :value="item.clientId">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="工单编号" prop="sheetNumber">
                <el-input v-model="queryParams.sheetNumber" placeholder="请输入工单编号" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>

            <el-form-item label="产品名称" prop="productName">
                <el-input v-model="queryParams.productName" placeholder="请输入产品名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">创建工单</el-button>
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

        <el-table v-loading="loading" :data="sheetList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="sheetNumber" width="180" />
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
                            :procedureStatusList="procedureStatusList">
                        </circleProgress>
                    </div>
                </template>
            </el-table-column>

            <el-table-column label="姓名" align="center" prop="createBy" />
            <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="95" fixed="right">
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

        <!-- 创建工单对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="1250px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="110px">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="工单编号" prop="sheetNumber">
                            <el-input v-model="form.sheetNumber" placeholder="请输入工单编号" style="width: 185px;" />
                            <el-button type="primary" size="mini" @click="generateCode">自动生成</el-button>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" style="display: flex; justify-content: space-between;">
                        <el-form-item label="计划开始时间" prop="planStart">
                            <el-date-picker clearable v-model="form.planStart" type="date" placeholder="开始日期"
                                value-format="yyyy-MM-dd" style="width: 135px;">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="selectStart" label-width="0px">
                            <el-time-select v-model="form.selectStart"
                                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间"
                                value-format="HH:mm" style="width: 135px;">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" style="display: flex; justify-content: space-between;">
                        <el-form-item label="计划结束时间" prop="planEnd">
                            <el-date-picker clearable v-model="form.planEnd" type="date" style="width: 135px;"
                                value-format="yyyy-MM-dd" placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="selectEnd" label-width="0px">
                            <el-time-select v-model="form.selectEnd"
                                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间"
                                value-format="HH:mm" style="width: 135px;">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="产品信息" prop="productName">
                            <el-select v-model="form.productName" placeholder="请选择产品信息" @change="productChange"
                                style="width: 270px;">
                                <el-option v-for="item in productList" :key="item.productId"
                                    :label="item.productNumber + ' - ' + item.productName"
                                    :value="item.productId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="计划数" prop="plannedQuantity">
                            <el-input-number v-model="form.plannedQuantity" placeholder="请输入计划数" :min="0"
                                style="width: 270px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="工单状态" prop="status">
                            <dict-tag :options="dict.type.sheet_status" :value="0" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="24">

                    <el-col :span="8">
                        <el-form-item label="客户" prop="clientName">
                            <el-select v-model="form.clientName" placeholder="请选择客户信息" style="width: 270px;">
                                <el-option v-for="item in clientList" :key="item.clientId" :label="item.clientName"
                                    :value="item.clientId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="关联单据" prop="relatedDocuments">
                            <el-input v-model="form.relatedDocuments" placeholder="请输入关联单据" style="width: 270px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="备注" prop="remark">
                            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" style="width: 270px;" />
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
                </el-row>
                <div>
                    <el-form-item label="生产任务">
                        <el-button type="primary" icon="el-icon-plus" style="margin-bottom: 8px;"
                            @click="selectProcedure">添加任务</el-button>
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
                                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="text" icon="el-icon-edit"
                                            @click="handleUpdateTask(scope.row)">修改</el-button>
                                        <el-button size="mini" type="text" icon="el-icon-delete"
                                            @click="handleDeleteTask(scope.row)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-form-item>
                </div>
                <div>
                    <el-form-item label="用料清单">
                        <el-button type="primary" icon="el-icon-plus" style="margin-bottom: 8px;"
                            @click="selectMaterial">添加用料</el-button>
                        <div>
                            <el-table v-loading="paramLoading" :data="bindingMaterial" ref="sortableTableMaterial"
                                row-key="attachmentId">
                                <el-table-column type="index" width="55" align="center">
                                    <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                                </el-table-column>
                                <el-table-column label="顺序" type="index" width="55" align="center" />
                                <el-table-column label="库存编号" prop="attachmentCode" width="100" align="center">
                                </el-table-column>
                                <el-table-column label="库存类型" prop="type" align="center">
                                    <template slot-scope="scope">
                                        <dict-tag :options="dict.type.inventory_types" :value="scope.row.type" />
                                    </template>
                                </el-table-column>
                                <el-table-column label="库存名称" prop="attachmentName" align="center">
                                </el-table-column>
                                <el-table-column label="库存规格" prop="model" align="center">
                                </el-table-column>
                                <el-table-column label="库存数量" prop="quantity" align="center">
                                </el-table-column>
                                <el-table-column label="单位" prop="unit" align="center">
                                </el-table-column>
                                <el-table-column label="用量" prop="dosage" align="center">
                                </el-table-column>
                                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="text" icon="el-icon-edit"
                                            @click="handleUpdateMaterial(scope.row)">修改</el-button>
                                        <el-button size="mini" type="text" icon="el-icon-delete"
                                            @click="handleDeleteMaterial(scope.row)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-form-item>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 编辑工单对话框 -->
        <el-dialog :title="title" :visible.sync="openEditSheet" width="1250px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="110px">
                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="工单编号" prop="sheetNumber">
                            <el-input v-model="form.sheetNumber" placeholder="请输入工单编号" :disabled="title == '修改工单'"
                                style="width: 270px;" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" style="display: flex; justify-content: space-between;">
                        <el-form-item label="计划开始时间" prop="planStart">
                            <el-date-picker clearable v-model="form.planStart" type="date" placeholder="开始日期"
                                value-format="yyyy-MM-dd" style="width: 135px;">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="selectStart" label-width="0px">
                            <el-time-select v-model="form.selectStart" :key="form.selectStart"
                                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="开始时间"
                                value-format="HH:mm" style="width: 135px;">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8" style="display: flex; justify-content: space-between;">
                        <el-form-item label="计划结束时间" prop="planEnd">
                            <el-date-picker clearable v-model="form.planEnd" type="date" style="width: 135px;"
                                value-format="yyyy-MM-dd" placeholder="结束日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item prop="selectEnd" label-width="0px">
                            <el-time-select v-model="form.selectEnd" :key="form.selectEnd"
                                :picker-options="{ start: '00:00', step: '00:15', end: '23:59' }" placeholder="结束时间"
                                value-format="HH:mm" style="width: 135px;">
                            </el-time-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="24">
                    <el-col :span="8">
                        <el-form-item label="产品信息" prop="productName">
                            <el-select v-model="form.productName" placeholder="请选择产品信息" @change="productChange"
                                style="width: 270px;">
                                <el-option v-for="item in productList" :key="item.productId"
                                    :label="item.productNumber + ' - ' + item.productName"
                                    :value="item.productId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="计划数" prop="plannedQuantity">
                            <el-input-number v-model="form.plannedQuantity" placeholder="请输入计划数" :min="0"
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
                            <el-select v-model="form.clientName" placeholder="请选择客户信息" style="width: 270px;">
                                <el-option v-for="item in clientList" :key="item.clientId" :label="item.clientName"
                                    :value="item.clientId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="关联单据" prop="relatedDocuments">
                            <el-input v-model="form.relatedDocuments" placeholder="请输入关联单据" style="width: 270px;" />
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
                            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" style="width: 270px;" />
                        </el-form-item>
                    </el-col>

                </el-row>
                <div>
                    <el-form-item label="生产任务" prop="taskIds">
                        <el-button type="primary" icon="el-icon-plus" style="margin-bottom: 8px;"
                            @click="selectProcedure">添加任务</el-button>
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
                                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="text" icon="el-icon-edit"
                                            @click="handleUpdateTask(scope.row)">修改</el-button>
                                        <el-button size="mini" type="text" icon="el-icon-delete"
                                            @click="handleDeleteTask(scope.row)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-form-item>
                </div>
                <div>
                    <el-form-item label="用料清单">
                        <el-button type="primary" icon="el-icon-plus" style="margin-bottom: 8px;"
                            @click="selectMaterial">添加用料</el-button>
                        <div>
                            <el-table v-loading="paramLoading" :data="bindingMaterial" ref="sortableTableMaterial"
                                row-key="attachmentId">
                                <el-table-column type="index" width="55" align="center">
                                    <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                                </el-table-column>
                                <el-table-column label="顺序" type="index" width="55" align="center" />
                                <el-table-column label="库存编号" prop="attachmentCode" width="100" align="center">
                                </el-table-column>
                                <el-table-column label="库存类型" prop="type" align="center">
                                    <template slot-scope="scope">
                                        <dict-tag :options="dict.type.inventory_types" :value="scope.row.type" />
                                    </template>
                                </el-table-column>
                                <el-table-column label="库存名称" prop="attachmentName" align="center">
                                </el-table-column>
                                <el-table-column label="库存规格" prop="model" align="center">
                                </el-table-column>
                                <el-table-column label="库存数量" prop="quantity" align="center">
                                </el-table-column>
                                <el-table-column label="单位" prop="unit" align="center">
                                </el-table-column>
                                <el-table-column label="用量" prop="dosage" align="center">
                                </el-table-column>
                                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="text" icon="el-icon-edit"
                                            @click="handleUpdateMaterial(scope.row)">修改</el-button>
                                        <el-button size="mini" type="text" icon="el-icon-delete"
                                            @click="handleDeleteMaterial(scope.row)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-form-item>
                </div>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 添加任务对话框 -->
        <el-dialog :title="'添加任务'" :visible.sync="openAddTask" width="1000px" append-to-body>
            <el-tabs v-model="activeName">
                <el-tab-pane label="选择工序" name="first">
                    <div style="display:flex;height:500px;">
                        <div style="width: 750px; height: 400px;">
                            <el-table ref="task" v-loading="loading" :data="procedureList"
                                @selection-change="handleProcedureSelectionChange" height="440px">
                                <el-table-column type="selection" width="55" align="center" />
                                <el-table-column type="index" width="55" align="center" />
                                <el-table-column label="工序编号" align="center" prop="procedureNumber" />
                                <el-table-column label="工序名称" align="center" prop="procedureName" />
                                <el-table-column label="报工权限" align="center" prop="reportingName" />
                                <el-table-column label="计划数默认值" align="center" prop="plannedQuantity" />
                                <el-table-column label="报工数配比" align="center" prop="reportingRatio" />
                                <el-table-column label="不良品项列表" align="center" prop="defectiveNames" />
                            </el-table>
                            <pagination v-show="procedureTotal > 0" :total="procedureTotal"
                                :page.sync="queryProcedureParams.pageNum" :limit.sync="queryProcedureParams.pageSize"
                                @pagination="getProcedureList" />
                        </div>
                        <div
                            style="width:200px;height:500px;padding: 12px 0 0 12px;border-left: 1px solid var(--base-color-6);margin-left: 12px;">
                            <div style="color: var(--base-color-1);margin-bottom: 8px;">已选 {{ selectedProcedure.length
                                }} 项
                            </div>
                            <div class="select-list">
                                <draggable style="height: calc(100% - 8px);" v-model="selectedProcedure"
                                    handle=".drag-icon" :chosen-class="'drag-chosen'" animation="500" group="procedure"
                                    @start="dragStart" @end="dragEnd" @change="dragChange">
                                    <div class="drag-item" v-for="(item, index) in selectedProcedure" :key="index">
                                        <div>
                                            <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                                            <span>{{ item.procedureName }}</span>
                                        </div>
                                        <svg-icon class="close-icon" :icon-class="'close'"
                                            @click="removeSelectProcedure(index)" />
                                    </div>
                                </draggable>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="选择工艺路线" name="second">
                    <div style="display:flex;height:500px;">
                        <div style="width: 750px; height: 400px;">
                            <el-table ref="processRoute" :header-cell-class-name="cellClass" v-loading="loading"
                                :data="processRouteList" @select="selectInfo" height="440px">
                                <el-table-column type="selection" width="55" align="center" />
                                <el-table-column type="index" width="55" align="center" />
                                <el-table-column label="工艺路线编号" align="center" prop="processRouteNumber" />
                                <el-table-column label="工艺路线名称" align="center" prop="processRouteName" />
                                <el-table-column label="工序列表" align="center" prop="procedureNames" />
                            </el-table>
                            <pagination v-show="processRouteTotal > 0" :total="processRouteTotal"
                                :page.sync="queryProcessRouteParams.pageNum"
                                :limit.sync="queryProcessRouteParams.pageSize" @pagination="getProcessRouteList" />
                        </div>
                        <div
                            style="width:200px;height:500px;padding: 12px 0 0 12px;border-left: 1px solid var(--base-color-6);margin-left: 12px;">
                            <div style="color: var(--base-color-1);margin-bottom: 8px;">已选 {{
            selectedProcessRoute.length }}
                                项
                            </div>
                            <div class="select-list">
                                <draggable style="height: calc(100% - 8px);" v-model="selectedProcessRoute"
                                    handle=".drag-icon" :chosen-class="'drag-chosen'" animation="500"
                                    group="processRoute" @start="dragStart" @end="dragEnd" @change="dragChange">
                                    <div class="drag-item" v-for="(item, index) in selectedProcessRoute" :key="index">
                                        <div>
                                            <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                                            <span>{{ item.procedureName }}</span>
                                        </div>
                                    </div>
                                </draggable>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>

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
        <!-- 添加用料对话框 -->
        <el-dialog :title="'添加用料'" :visible.sync="openAddMaterial" width="1000px" append-to-body>
            <div style="display:flex;height:500px;">
                <div style="width: 750px; height: 400px;">
                    <el-table ref="material" v-loading="loading" :data="materialList"
                        @selection-change="handleMaterialSelectionChange" height="440px">
                        <el-table-column type="selection" width="55" align="center" />
                        <el-table-column type="index" width="55" align="center" />
                        <el-table-column label="库存编号" prop="attachmentCode" width="100" align="center">
                        </el-table-column>
                        <el-table-column label="库存类型" prop="type" align="center">
                            <template slot-scope="scope">
                                <dict-tag :options="dict.type.inventory_types" :value="scope.row.type" />
                            </template>
                        </el-table-column>
                        <el-table-column label="库存名称" prop="attachmentName" align="center">
                        </el-table-column>
                        <el-table-column label="库存规格" prop="model" align="center">
                        </el-table-column>
                        <el-table-column label="库存数量" prop="quantity" align="center">
                        </el-table-column>
                        <el-table-column label="单位" prop="unit" align="center">
                        </el-table-column>
                    </el-table>
                    <pagination v-show="materialTotal > 0" :total="materialTotal"
                        :page.sync="queryMaterialParams.pageNum" :limit.sync="queryMaterialParams.pageSize"
                        @pagination="getMaterialList" />
                </div>
                <div
                    style="width:200px;height:500px;padding: 12px 0 0 12px;border-left: 1px solid var(--base-color-6);margin-left: 12px;">
                    <div style="color: var(--base-color-1);margin-bottom: 8px;">已选 {{ selectedMaterial.length
                        }} 项
                    </div>
                    <div class="select-list">
                        <draggable style="height: calc(100% - 8px);" v-model="selectedMaterial" handle=".drag-icon"
                            :chosen-class="'drag-chosen'" animation="500" group="procedure" @start="dragStart"
                            @end="dragEnd" @change="dragChange">
                            <div class="drag-item" v-for="(item, index) in selectedMaterial" :key="index">
                                <div>
                                    <svg-icon class="drag-icon" :icon-class="'drag-icon'" />
                                    <span>{{ item.attachmentName }}</span>
                                </div>
                                <svg-icon class="close-icon" :icon-class="'close'"
                                    @click="removeSelectMaterial(index)" />
                            </div>
                        </draggable>
                    </div>
                </div>
            </div>


            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitMaterial">确 定</el-button>
                <el-button @click="cancelSubmitMaterial">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 修改用料对话框 -->
        <el-dialog :title="'编辑用料'" :visible.sync="openEditMaterial" width="500px" append-to-body>
            <el-form ref="editMaterialForm" :model="editMaterialForm" :rules="taskRules" label-width="120px">
                <el-form-item label="库存编号" prop="attachmentCode">
                    <el-select v-model="editMaterialForm.attachmentCode" placeholder="请选择库存信息" @change="materialChange">
                        <el-option v-for="item in materialList" :key="item.attachmentId" :label="item.attachmentCode"
                            :value="item.attachmentId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="库存类型" prop="type">
                    <el-select clearable v-model="editMaterialForm.type" placeholder="请选择库存类型" :disabled="true">
                        <el-option v-for="dict in dict.type.inventory_types" :key="dict.value" :label="dict.label"
                            :value="dict.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="库存名称" prop="attachmentName">
                    <el-select v-model="editMaterialForm.attachmentName" placeholder="请选择库存信息" :disabled="true">
                        <el-option v-for="item in materialList" :key="item.materialId" :label="item.materialName"
                            :value="item.materialId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="库存规格" prop="model">
                    <el-input v-model="editMaterialForm.model" placeholder="请输入库存规格" style="width: 220px;"
                        :disabled="true" />
                </el-form-item>
                <el-form-item label="库存数量" prop="quantity">
                    <el-input v-model="editMaterialForm.quantity" placeholder="请输入库存数量" style="width: 220px;"
                        :disabled="true" />
                </el-form-item>
                <el-form-item label="单位" prop="unit">
                    <el-select clearable v-model="editMaterialForm.unit" placeholder="请选择单位" :disabled="true">
                        <el-option v-for="dict in dict.type.produce_unit" :key="dict.value" :label="dict.value"
                            :value="dict.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="用量" prop="dosage">
                    <el-input-number v-model="editMaterialForm.dosage" placeholder="请输入用量" :min="1"
                        style="width: 220px;" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitEditMaterialForm">确 定</el-button>
                <el-button @click="cancelSubmitEditMaterialForm">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import draggable from 'vuedraggable';
import { listSheet, getSheet, delSheet, addSheet, updateSheet } from "@/api/produce/sheet";
import Vue from 'vue'
import {
    listProcedure,
} from "@/api/basicData/procedure";
import { listProcessRoute } from "@/api/basicData/processRoute";
import { listProduct } from "@/api/basicData/product";
import CircleProgress from './progress.vue';
import QRCode from 'qrcode';
import Sortable from 'sortablejs';
import { getDicts } from "@/api/system/dict/data";
import { listClient } from "@/api/basicData/client";
import { listAttachment } from "@/api/system/attachment";
export default {
    components: {
        CircleProgress,
        draggable,
    },
    name: "Sheet",
    dicts: ['sheet_status', 'produce_unit', 'inventory_types'],
    data() {
        return {
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
            procedureTotal: 0,
            materialTotal: 0,
            processRouteTotal: 0,
            // 工单表格数据
            sheetList: [],
            //选中的工序
            selectedProcedure: [],
            //选中的用料
            selectedMaterial: [],
            //选中的工艺路线
            selectedProcessRoute: [],
            //工单绑定的工序任务
            bindingProcedure: [],
            //工单绑定的用料
            bindingMaterial: [],
            //工序表格数据
            procedureList: [],
            //库存表格数据
            materialList: [],
            //工艺路线表格数据
            processRouteList: [],
            //产品表格数据
            productList: [],
            procedureStatusList: [],
            clientList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            openAddTask: false,
            openEditTask: false,
            openAddMaterial: false,
            openEditMaterial: false,
            openEditSheet: false,
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
                clientName: undefined,
                clientId: undefined,
                queryStatus: [],
            },
            queryProcedureParams: {
                pageNum: 1,
                pageSize: 10,
            },
            queryMaterialParams: {
                pageNum: 1,
                pageSize: 10,
            },
            queryProcessRouteParams: {
                pageNum: 1,
                pageSize: 10,
            },
            // 表单参数
            form: {},
            userId: undefined,
            editTaskForm: {},
            editMaterialForm: {},
            //被选中修改任务的index
            selectTaskIndex: undefined,
            selectMaterialIndex: undefined,
            imageUrl: '',
            activeName: '',
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
                /* goodQuantity: [
                    { required: true, message: "良品数不能为空", trigger: "blur" }
                ],
                defectQuantity: [
                    { required: true, message: "不良品数不能为空", trigger: "blur" }
                ], */
                startTimePlan: [
                    { required: true, message: "计划开始时间不能为空", trigger: "blur" }
                ],
                endTimePlan: [
                    { required: true, message: "计划结束时间不能为空", trigger: "blur" }
                ],
                /* startTime: [
                    { required: true, message: "实际开始时间不能为空", trigger: "blur" }
                ],
                endTime: [
                    { required: true, message: "实际结束时间不能为空", trigger: "blur" }
                ],
                remark: [
                    { required: true, message: "备注不能为空", trigger: "blur" }
                ], */
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
                productName: [
                    { required: true, message: "产品名称不能为空", trigger: "blur" }
                ],
                /* productSpecification: [
                    { required: true, message: "产品规格不能为空", trigger: "blur" }
                ], */
                /* unit: [
                    { required: true, message: "单位不能为空", trigger: "blur" }
                ], */
                planStart: [
                    { required: true, message: "计划开始日期不能为空", trigger: "blur" }
                ],
                planEnd: [
                    { required: true, message: "计划结束日期不能为空", trigger: "blur" }
                ],
                selectStart: [
                    { required: true, message: "计划开始时间不能为空", trigger: "blur" }
                ],
                selectEnd: [
                    { required: true, message: "计划结束时间不能为空", trigger: "blur" }
                ],
                plannedQuantity: [
                    { required: true, message: "计划数不能为空", trigger: "blur" }
                ],
                /* relatedDocuments: [
                    { required: true, message: "关联单据不能为空", trigger: "blur" }
                ], */
                /* status: [
                    { required: true, message: "状态不能为空", trigger: "change" }
                ], */
                /* qrcode: [
                    { required: true, message: "二维码不能为空", trigger: "blur" }
                ],
                remark: [
                    { required: true, message: "备注不能为空", trigger: "blur" }
                ] */
            },

        };
    },
    created() {
        this.queryParams.clientId = this.$store.getters.clientId;
        this.userId = this.$store.getters.userId;
        this.getList();
        this.getProcedureList();
        this.getProductList();
        this.getMaterialList();
        this.getProcessRouteList();
        this.getComponents();
        this.getClientList();
    },
    watch: {
        open(val) {
            if (val) {
                this.$nextTick(() => {
                    this.initSortable();
                })
            }
        },
        openEditSheet(val) {
            if (val) {
                this.$nextTick(() => {
                    this.initSortable();
                })
            }
        }
    },
    methods: {
        getComponents() {
            getDicts("procedure_status").then(response => {
                this.procedureStatusList = response.data;
            });
        },
        initSortable() {
            const tableBody = this.$refs.sortableTable.$el.querySelector('.el-table__body-wrapper tbody');
            Sortable.create(tableBody, {
                animation: 150,
                onEnd: evt => {
                    const item = this.bindingProcedure.splice(evt.oldIndex, 1)[0];
                    this.bindingProcedure.splice(evt.newIndex, 0, item);
                }
            });
            const tableBodyMaterial = this.$refs.sortableTableMaterial.$el.querySelector('.el-table__body-wrapper tbody');
            Sortable.create(tableBodyMaterial, {
                animation: 150,
                onEnd: evt => {
                    const item = this.bindingMaterial.splice(evt.oldIndex, 1)[0];
                    this.bindingMaterial.splice(evt.newIndex, 0, item);
                }
            });
        },
        selectInfo(selection, row) {
            // 清除 所有勾选项
            this.selectedProcessRoute = [];
            this.selectedProcessRoute = row.procedureList;
            this.$refs.processRoute.clearSelection();
            // 当表格数据都没有被勾选的时候 就返回
            // 主要用于将当前勾选的表格状态清除
            if (selection.length == 0) return;
            this.$refs.processRoute.toggleRowSelection(row, true);
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
            this.form.sheetNumber = `GD${year}${month}${day}${hour}${minute}${second}${timestamp}`
        },
        /** 查询工单列表 */
        getList() {
            this.loading = true;
            this.sheetList = [];
            listSheet(this.queryParams).then(response => {
                this.sheetList = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        // 获取工序列表
        getProcedureList() {
            listProcedure(this.queryProcedureParams).then(res => {
                this.procedureList = res.rows;
                this.procedureTotal = res.total;
            })
        },
        // 获取库存列表
        getMaterialList() {
            listAttachment(this.queryMaterialParams).then(res => {
                this.materialList = res.rows;
                this.materialTotal = res.total;
            })
        },
        // 获取工艺路线列表
        getProcessRouteList() {
            listProcessRoute(this.queryProcessRouteParams).then(res => {
                this.processRouteList = res.rows;
                this.processRouteTotal = res.total;
            })
        },
        /** 查询产品列表 */
        getProductList() {
            listProduct().then(response => {
                this.productList = response.rows;
            });
        },
        // 获取客户列表
        getClientList() {
            listClient().then(res => {
                this.clientList = res.rows;
            })
        },
        // 取消按钮
        cancel() {
            this.open = false;
            this.openEditSheet = false;
            this.reset();
        },
        // 表单重置
        reset() {
                this.selectedProcedure = [],
                this.selectedProcessRoute = [],
                this.bindingProcedure = [],
                this.selectedMaterial = [],
                this.bindingMaterial = [],
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
                    remark: undefined,
                    clientId: undefined,
                    clientName: undefined,
                    selectEnd: undefined,
                    selectStart: undefined,
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
        // 添加用料时多选框选中数据
        handleMaterialSelectionChange(selection) {
            this.selectedMaterial = selection;
            /* this.ids = selection.map(item => item.sheetId)
            this.single = selection.length !== 1
            this.multiple = !selection.length */
        },
        // 添加任务时多选框选中数据
        /* handleProcessRouteSelectionChange(selection) {
            this.selectedProcessRoute = selection;
        }, */
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
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
                this.generateQRCode();
                this.bindingProcedure = response.data.tasks;
                this.bindingMaterial = response.data.materials;
                this.openEditSheet = true;
                this.title = "修改工单";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    var client = this.clientList.find(c => c.clientId === this.form.clientName);
                    if (client) {
                        this.form.clientId = client.clientId;
                        this.form.clientName = client.clientName;
                    }
                    this.bindingProcedure.forEach((e, index) => {
                        e.sheetNumber = this.form.sheetNumber;
                        e.productNumber = this.form.productNumber;
                        e.productName = this.form.productName;
                        e.productSpecification = this.form.productSpecification;
                        e.plannedQuantity = this.form.plannedQuantity;
                        e.sequence = index;
                    })
                    this.bindingMaterial.forEach((e, index) => {
                        e.sequence = index;
                    })
                    this.form.tasks = this.bindingProcedure;
                    this.form.materials = this.bindingMaterial;
                    this.form.planStart = this.form.planStart.split(' ')[0] + ' ' + this.form.selectStart + ':00';
                    this.form.planEnd = this.form.planEnd.split(' ')[0] + ' ' + this.form.selectEnd + ':00';
                    if (this.form.sheetId != null) {
                        updateSheet(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.openEditSheet = false;
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
        //选择工序
        selectProcedure() {
            this.activeName = 'first';
            this.openAddTask = true;
            this.$nextTick(() => {
                this.selectedProcessRoute = [];
                this.$refs.task.clearSelection();
                this.$refs.processRoute.clearSelection()
            })
        },
        //选择用料
        selectMaterial() {
            this.openAddMaterial = true;
            this.$nextTick(() => {
                this.$refs.material.clearSelection();
            })
        },

        //提交选中任务
        submitTask() {
            if (this.activeName === 'first') {
                this.bindingProcedure = this.bindingProcedure.concat(this.selectedProcedure);
            } else if (this.activeName === 'second') {
                this.bindingProcedure = this.bindingProcedure.concat(this.selectedProcessRoute);
            }

            this.openAddTask = false;
        },
        //取消提交选中任务
        cancelSubmitTask() {
            this.openAddTask = false;
        },
        //提交选中用料
        submitMaterial() {
            const hasProduct = this.selectedMaterial.find(m => m.attachmentCode === this.form.productNumber);
            if (hasProduct) {
                this.$message({
                    message: '用料清单中不可包含父产品',
                    type: 'warning'
                });
                return;
            }
            this.selectedMaterial.forEach((e) => {
                this.$set(e, "dosage", 1);
            })

            this.bindingMaterial = this.bindingMaterial.concat(this.selectedMaterial);

            this.openAddMaterial = false;
        },
        //取消提交选中用料
        cancelSubmitMaterial() {
            this.openAddMaterial = false;
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
        // 用料删除按钮
        handleDeleteMaterial(row) {
            let index = this.bindingMaterial.findIndex(p => p.attachmentId == row.attachmentId)
            if (index < 0) return
            this.bindingMaterial.splice(index, 1)
        },
        //用料修改按钮
        handleUpdateMaterial(row) {
            this.selectMaterialIndex = this.bindingMaterial.findIndex(p => p.attachmentId == row.attachmentId);
            var material = this.bindingMaterial.find(p => p.attachmentId == row.attachmentId)
            this.editMaterialForm = { ...material };
            this.openEditMaterial = true;
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
        //取消提交修改任务表单
        cancelSubmitEditTaskForm() {
            this.editTaskForm = {};
            this.openEditTask = false;
        },
        //用料修改确定按钮
        submitEditMaterialForm() {
            this.$refs["editMaterialForm"].validate(valid => {
                if (valid) {
                    Vue.set(this.bindingMaterial, this.selectMaterialIndex, this.editMaterialForm);
                    this.openEditMaterial = false;
                }
            }
            )
        },
        //取消提交修改用料表单
        cancelSubmitEditMaterialForm() {
            this.editTaskForm = {};
            this.openEditMaterial = false;
        },
        //编辑工序时变化
        procedureChange(data) {
            var procedure = this.procedureList.find(p => p.procedureId == data);
            this.editTaskForm = { ...procedure };
        },
        //编辑用料时变化
        materialChange(data) {
            var material = this.materialList.find(p => p.attachmentId == data);
            material.dosage = this.editMaterialForm.dosage;
            this.editMaterialForm = { ...material };
        },
        productChange(data) {
            var product = this.productList.find(p => p.productId == data)
            this.form.productNumber = product.productNumber;
            this.form.productName = product.productName;
            this.form.productSpecification = product.specification;
            this.form.unit = product.productUnit;
        },
        calculateWith() {
            var max
            for (var i = 0; i < this.sheetList.length; i++) {
                max = max === undefined ? this.sheetList[i].tasks.length : (max > this.sheetList[i].tasks.length ? max : this.sheetList[i].tasks.length)
            }
            return max * 120 - 60
        },

        dragStart() {
        },
        dragEnd() {
        },
        dragChange() {
        },
        removeSelectProcedure(index) {
            var cancelRow = this.selectedProcedure[index];
            this.selectedProcedure.splice(index, 1);
            this.$refs.task.toggleRowSelection(cancelRow, false);
        },
        removeSelectMaterial(index) {
            var cancelRow = this.selectedMaterial[index];
            this.selectedMaterial.splice(index, 1);
            this.$refs.material.toggleRowSelection(cancelRow, false);
        },
        removeSelectProcessRoute(index) {
            //var cancelRow = this.selectedProcessRoute[index];
            this.selectedProcessRoute.splice(index, 1);
            //this.$refs.processRoute.toggleRowSelection(cancelRow, false);
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
        cellClass(row) {
            if (row.columnIndex === 0) {
                return 'disabledCheck';
            }
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
        handleSheetList(data) {
            this.getList();
        },
        changeStart() {
            this.$forceUpdate();
        }
    }
};
</script>
<style scoped>
::v-deep .el-table .disabledCheck .cell .el-checkbox__inner {
    display: none;
}

::v-deep .el-table .disabledCheck .cell::before {
    content: '';
    text-align: center;
    line-height: 37px;
}

.select-list {
    height: calc(100% - 27px);
    overflow-y: scroll;
}

.drag-item {
    border-radius: 6px;
    background: var(--table-row-bg);
    color: var(--base-color-1);
    padding: 6px 8px;
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.drag-item:hover {
    background-color: var(--table-row-hover-bg);
}

.drag-icon {
    height: 16px;
    width: 16px;
    margin-right: 2px;
    color: var(--base-color-1);
}

.drag-icon:hover {
    cursor: pointer;
}

.drag-chosen {
    background-color: var(--table-row-hover-bg) !important;
    border: 1px solid var(--current-color);
    color: var(--base-color-1) !important;
}

.close-icon {
    height: 16px;
    width: 16px;
    color: var(--base-color-1);
    text-align: right;
}

.close-icon:hover {
    cursor: pointer;
}
</style>
