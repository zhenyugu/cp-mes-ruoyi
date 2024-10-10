<template>
    <div>
        <el-dropdown trigger="hover" class="right-menu-item hover-effect" placement='bottom' @command="handlePlatformOrder">
            <div
                style="display: flex; align-items: center; font-weight: 500; height: 100%;font-size: 0.8rem;color:var(--icon-color)">
                工单
            </div>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="handleAdd">
                    <span>提交工单</span>
                </el-dropdown-item>
                <el-dropdown-item command="my-order">
                    <span>我的工单</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <!-- 添加或修改平台工单信息对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
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
                <!-- <el-form-item label="备注" prop="remark">
                    <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" />
                </el-form-item> -->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button :loading="buttonLoading" type="primary" @click="submitForm">提 交</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
  
<script>
import { addPlatformOrder } from "@/api/system/platformOrder";
export default {
    name: "PlatOrder",
    dicts: ['platform_order_type'],
    data() {
        return {
            // 按钮loading
            buttonLoading: false,
            // 遮罩层
            loading: true,
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
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
                ], */
                /* status: [
                    { required: true, message: "平台工单状态不能为空", trigger: "change" }
                ], */
                /* userId: [
                    { required: true, message: "用户id不能为空", trigger: "blur" }
                ], */
                /* remark: [
                    { required: true, message: "备注不能为空", trigger: "blur" }
                ], */
            }
        };
    },
    methods: {
        // 取消按钮
        cancel() {
            this.open = false;
            this.reset();
        },
        // 表单重置
        reset() {
            this.form = {
                platformOrderId: undefined,
                orderType: "0",
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

        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.open = true;
            this.title = "提交平台工单";
        },

        /** 提交按钮 */
        submitForm() {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.buttonLoading = true;
                    this.form.userId = this.$store.getters.userId;
                    addPlatformOrder(this.form).then(response => {
                        this.$modal.msgSuccess("提交成功");
                        this.open = false;
                        this.getList();
                    }).finally(() => {
                        this.buttonLoading = false;
                    });
                }
            });
        },
        handlePlatformOrder(command) {
            if (command == 'handleAdd') {
                this.handleAdd();
            }else if(command == 'my-order'){
                this.$router.push("/system/platform-order/" + this.$store.getters.userId);
            }
        }


    }
};
</script>
  
<style scoped></style>
  