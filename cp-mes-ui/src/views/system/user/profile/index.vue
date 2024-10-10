<template>
  <div class="app-container bg-container" v-loading="loading">
    <el-row :gutter="20">
      <el-col>
        <el-card class="box-card">
          <div class="overview-title">
            <div class="overview-icon"></div><span>个人信息</span>
          </div>
          <div class="info-content">
            <div style="flex: 1;text-align:center;">
              <userAvatar :user="user" />
            </div>
            <div class="info-box">
              <div class="info-body">
                <div><svg-icon icon-class="user" style="margin-right:6px;"/>用户名称：{{user.userName}}</div>
                <div><svg-icon icon-class="nickname" style="margin-right:6px;"/>用户昵称：{{user.nickName}}</div>
                <div><svg-icon icon-class="sex" style="margin-right:6px;"/>性别：{{handleSex(user.sex)}}</div>
                <div><svg-icon icon-class="phone" style="margin-right:6px;"/>手机号码：{{user.phonenumber || '--'}}</div>
                <div style="height:21px;"><svg-icon icon-class="email" style="margin-right:6px;"/>用户邮箱：{{user.email || '--'}}</div>
              </div>
              <div class="info-body">
                <div><svg-icon icon-class="tree" style="margin-right:6px;"/>所属部门：{{ user.dept ? (user.dept.deptName || '--') : '--'}}</div>
                <div><svg-icon icon-class="post" style="margin-right:6px;"/>所在岗位：{{postGroup || '--'}}</div>
                <div><svg-icon icon-class="peoples" style="margin-right:6px;"/>所属角色：{{roleGroup || '--'}}</div>
                <div><svg-icon icon-class="date" style="margin-right:6px;"/>创建时间：{{parseTime(user.createTime, '{y}-{m}-{d}')}}</div>
                <div style="height:21px;">
                  <el-button size="mini" type="primary" @click="openInfoDialog">修改信息</el-button>
                  <el-button size="mini" type="primary" @click="openPwdDialog">重置密码</el-button>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :title="title" :visible.sync="infoDialog" append-to-body destroy-on-close width="500px">
      <user-info :user="user" @closeDialog="closeInfoDialog"/>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="pwdDialog" append-to-body destroy-on-close width="500px">
      <resetPwd @closeDialog="closePwdDialog"/>
    </el-dialog>
  </div>
</template>

<script>
import userAvatar from "./userAvatar";
import resetPwd from "./resetPwd";
import { getUserProfile, countRepairAndInspection } from "@/api/system/user";
import OrderChartVue from './orderChart.vue';
import PieChartVue from './pieChart.vue';
import UserInfo from './userInfo.vue';
export default {
  name: "Profile",
  components: { userAvatar, resetPwd, PieChartVue, OrderChartVue, UserInfo },
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo",
      infoDialog: false,
      pwdDialog: false,
      title: '修改信息',
      repairCount: {},
      inspectionCount: {},
      // repairPie: [],
      // inspectionPie: [],
      repairs: {},
      inspections: {},
      loading: true,
    };
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      this.loading = true
      getUserProfile().then(response => {
        this.user = response.data.user;
        this.roleGroup = response.data.roleGroup;
        this.postGroup = response.data.postGroup;
      }).finally(() => {
        this.loading = false
      });
    },
    handleSex(sex) {
      if(!sex) return '--'
      switch (sex) {
        case '1':
          return '女'
        case '0':
          return '男'
        case '2':
          return '未知'
        default:
          break;
      }
    },
    // 查询我的工单
    getRepairOeder() {
      countRepairAndInspection().then(res => {
        // this.repairCount = {
        //   residueName: '工单数量',
        //   residueValue: res.data.repairCount.total,
        //   residueClass: '#3671e8',
        // }
        // this.inspectionCount = {
        //   residueName: '巡检数量',
        //   residueValue: res.data.inspectionCount.total,
        //   residueClass: '#3671e8',
        // }
        this.repairCount = res.data.repairCount
        this.inspectionCount = res.data.inspectionCount
        // 柱状图数据
        let repairData = {
          xData: [],
          currentData: [],
          sameData: []
        }
        res.data.repairs.forEach(item => {
          repairData.xData.push(item.time)
          repairData.currentData.push(item.finished)
          repairData.sameData.push(item.unfinished)
        })
        this.repairs = repairData
        let inspectionData = {
          xData: [],
          currentData: [],
          sameData: []
        }
        res.data.inspections.forEach(item => {
          inspectionData.xData.push(item.time)
          inspectionData.currentData.push(item.finished)
          inspectionData.sameData.push(item.unfinished)
        })
        this.inspections = inspectionData
      })
    },
    // 打开修改信息弹窗
    openInfoDialog() {
      this.title = '修改信息'
      this.infoDialog = true
    },
    // 重置密码
    openPwdDialog() {
      this.title = '重置密码'
      this.pwdDialog = true
    },
    // 关闭信息修改弹窗
    closeInfoDialog(value) {
      if(value) this.getUser()
      this.infoDialog = false
    },
    // 关闭重置密码弹窗
    closePwdDialog(value) {
      this.pwdDialog = false
    },
    // 跳转我的工单
    toRepairOrder() {
      this.$router.push({path: '/maintenance/my-order'})
    },
    // 跳转我的巡检
    toInspection() {
      this.$router.push({path: '/maintenance/my-inspection'})
    },
  }
};
</script>
<style scoped>
.app-container {
  width: 100%;
  min-height: calc(100vh - 84px);
}
.info-content {
  height: calc(100% - 41px);
  display:flex;
  align-items:center;
  padding:20px 0;
}
.order-content {
  height:100%;
  display:flex;
}
.count-body {
  height:calc(100% - 41px);
  display:flex;
  flex-direction:column;
  align-items:center;
  justify-content:space-evenly;
}
.count-item {
  cursor: pointer;
  height:40%;
  /* display:flex; */
  /* flex-direction:column; */
  /* align-items:center; */
  /* justify-content:center; */
  width:70%;
  border:1px solid var(--border-color-1);
  transition: background 1s;
}
.count-item:hover {
  background: var(--base-color-8);
  border: 1px solid transparent;
  transition: background 1s;
}
.count-num {
  color:var(--theme-color);
  font-size:36px;
  font-weight:bold;
  text-align:center;
  height: calc(100% - 35px);
  display:flex;
  align-items:center;
  justify-content:center;
}
.box-card {
  height: 330px;
  margin-bottom: 20px;
}
.box-card>>>.el-card__body {
  height: 100%;
}
.overview-title {
  color:var(--base-color-1);
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}
.overview-icon {
  width: 5px;
  height: 20px;
  margin-right: 10px;
  background: var(--current-color);
}
.info-box {
  flex:3;
  display:flex;
  height:100%;
  /* border-right: 1px solid var(--base-color-1); */
}
.info-body {
  flex: 1;
  height: 100%;
  display:flex;
  flex-direction:column;
  justify-content: space-between;
}
@media (max-width: 1200px) {
  .info-box {
    border: none
  }
}
</style>
