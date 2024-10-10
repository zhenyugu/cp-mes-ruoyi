<template>
  <div class="register">
    <div class="top-logo">
      <a href="https://www.szcloudpulse.com/" target="_blank"><img src="../assets/logo/logo-heng.png"
          style="height: 35px;"></a>
      <div style="margin: 2px 5px; ">|</div>
      <div style="font-weight: 700; padding-bottom: 2px;">{{ sysTitle }}</div>
    </div>
    <div class="left-pic">
      <img src="../assets/images/hero-img.png" style="height: 450px;">
    </div>
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form">
      <h3 class="title">
        <h2 style="font-weight: 700; margin: 0;">注册</h2>
      </h3>
      <el-form-item prop="username">
        <el-input v-model="registerForm.username" type="text" auto-complete="off" placeholder="账号" class="unchanged">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="registerForm.password" type="password" auto-complete="new-password" placeholder="密码"
          @keyup.enter.native="handleRegister" show-password class="unchanged">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="registerForm.name" type="text" auto-complete="off" placeholder="请输入您的姓名" class="unchanged">
          <svg-icon slot="prefix" icon-class="nickname" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input v-model="registerForm.phone" type="text" auto-complete="off" placeholder="请输入您的手机号" class="unchanged">
          <svg-icon slot="prefix" icon-class="phone" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="corporateName">
        <el-input v-model="registerForm.corporateName" type="text" auto-complete="off" placeholder="请输入公司名称"
          class="unchanged">
          <svg-icon slot="prefix" icon-class="chart" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input v-model="registerForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
          @keyup.enter.native="handleRegister" class="unchanged">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="register-code">
          <img :src="codeUrl" @click="getCode" class="register-code-img" />
        </div>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;" class="btn-fixed"
          @click.native.prevent="handleRegister">
          <span v-if="!loading">注 册</span>
          <span v-else>注 册 中...</span>
        </el-button>
        <div style="float: right;">
          <router-link class="link-type" :to="'/login'">使用已有账户登录</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-register-footer">
      <a href="https://www.szcloudpulse.com/" style="font-weight: 700;" target="_blank">苏州云脉软件技术有限公司 © 版权所有</a>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import { experienceTenant } from "@/api/system/tenant";
export default {
  name: "applyAccount",
  data() {
    return {
      codeUrl: "",
      registerForm: {
        username: undefined,
        password: undefined,
        rememberMe: false,
        code: "",
        uuid: ""
      },
      registerRules: {
        /* tenantId: [
          { required: true, trigger: "blur", message: "请输入您的租户编号" }
        ], */
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "blur", message: "请输入验证码" }],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        email: [
          { required: true, message: "邮箱不能为空", trigger: "blur" }
        ],
        corporateName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "联系电话不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      tenantEnabled: false,
      // 注册开关
      register: false,
      redirect: undefined,
      // 租户列表
      tenantList: []
    };
  },
  computed: {
    sysTitle: function () {
      return this.$store.getters.logoInfo.sysTitle
      // return process.env.VUE_APP_TITLE
    },
    logoShow() {
      return this.$store.getters.logoInfo.registerLogo
    },
    experienceShow() {
      return this.$store.getters.logoInfo.experienceShow
    }
  },

  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.data.captchaEnabled === undefined ? true : res.data.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.data.img;
          this.registerForm.uuid = res.data.uuid;
        }
      });
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true;
          const registerForm = {
            username: this.registerForm.username,
            password: this.registerForm.password,
            contactUserName: this.registerForm.name,
            //email: this.form.email,
            contactPhone: this.registerForm.phone,
            companyName: this.registerForm.corporateName,
            uuid : this.registerForm.uuid,
            code : this.registerForm.code,
          }
          experienceTenant(registerForm).then(res => {
            const username = this.registerForm.username;
            const password = this.registerForm.password;
            this.$alert("<font color='red'>恭喜你，您的账号: " + username +"，密码为："+ password +" 注册成功！</font>" , '系统提示', {
              dangerouslyUseHTMLString: true,
              type: 'success'
            }).then(() => {
              this.$router.push("/login");
            }).catch(() => { });
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          })
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.register {
  padding: 200px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  background-size: cover;
  background-color: #0e1d34;
  /* background-image: url("../assets/images/cta-bg.jpg"); */
  background-image: url("../assets/images/hero-bg.png");
}

.register-mobile {
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 100%;
  background-size: cover;
  background-color: #0e1d34;
  /* background-image: url("../assets/images/cta-bg.jpg"); */
  background-image: url("../assets/images/hero-bg.png");
}

.title {
  /* margin: 0px auto 30px auto; */
  text-align: center;
  /* color: #333333; */
  font-size: 21px;
  color: #001f8d;
  letter-spacing: 5px;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 450px;
  padding: 10px 25px 10px 25px;

  .el-input {
    height: 20px;

    input {
      height: 20px;
    }
  }

  .input-icon {
    height: 38px;
    width: 14px;
    margin-left: 2px;
  }
}

.form-register {
  .el-input {
    height: 30px;

    input {
      height: 30px;
    }
  }

  .input-icon {
    height: 30px;
    width: 15px;
    margin-left: 2px;
  }
}

.register-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.register-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-register-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  /* font-family: Arial; */
  font-size: 12px;
  letter-spacing: 1px;
}

.register-code-img {
  height: 35px;
}

.link-type {
  color: #7baafc;
  font-size: 14px;
}

.link-type:hover {
  cursor: pointer;
  color: rgb(32, 160, 255);
}

.select-click {
  display: flex;
  justify-content: space-between;
  // align-items: center;
}

.top-logo {
  display: flex;
  position: fixed;
  left: 100px;
  top: 50px;
  font-size: 30px;
  letter-spacing: 1px;
  margin-bottom: 20px;
  padding: 0;
  color: #ffffff;
}

.top-logo-mobile {
  position: absolute;
  top: 0px;
  font-size: 26px;
  letter-spacing: 1px;
  margin-bottom: 20px;
  padding: 0;
  color: #ffffff;
}

.left-pic {
  padding-top: 70px;
}
</style>
<style scoped>
.default-style>>>.el-input .el-input__inner {
  background-color: #FFFFFF;
  color: #606266;
  border: 1px solid #DCDFE6;
}

.default-style>>>.el-input .el-input__inner:hover {
  border: 1px solid #c0c4cc;
}

.default-style>>>.el-input .el-input__inner:focus {
  border: 1px solid #3671e8;
}

.default-style>>>.el-popper {
  border: 1px solid #e4e7ed;
  background-color: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
}

.default-style>>>.el-popper[x-placement^=bottom] .popper__arrow::after,
.default-style>>>.el-popper[x-placement^=top] .popper__arrow::after {
  border-bottom-color: #fff;
  border-top-color: #fff;
}

.default-style>>>.el-popper[x-placement^=bottom] .popper__arrow,
.default-style>>>.el-popper[x-placement^=top] .popper__arrow {
  border-bottom-color: rgba(0, 0, 0, .1);
  border-top-color: rgba(0, 0, 0, .1);
}

.default-style>>>.el-select-dropdown__item:not(.is-disabled) {
  color: #606266;
  background-color: #fff;
}

.default-style>>>.el-select-dropdown__item:hover {
  color: #606266;
  background-color: #f5f7fa;
}

.default-style>>>.el-select-dropdown__item.selected {
  color: #3671e8;
  background-color: #f5f7fa;
}

.default-style>>>.el-select-dropdown__item.selected:hover {
  background-color: #f5f7fa;
}

.default-style>>>.el-select-dropdown.is-multiple .el-select-dropdown__item.selected.hover {
  background-color: #f5f7fa;
}
</style>
