<template>
  <div class="app-container bg-container">
    <div class="nav-top">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" :rules="rules">
        <el-form-item label="起止时间" prop="dateRange">
          <el-date-picker v-model="queryParams.dateRange" style="width: 300px" value-format="yyyy-MM-dd HH:mm:ss"
            type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
            :default-time="['00:00:00', '23:59:59']"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getAllData">查询</el-button>
        </el-form-item>
      </el-form>

      <el-radio-group v-model="selectArea" @input="selectAreaChange" size="medium">
        <el-radio-button label="今天" value="今天"></el-radio-button>
        <el-radio-button label="昨天" value="昨天"></el-radio-button>
        <el-radio-button label="本月" value="本月"></el-radio-button>
        <el-radio-button label="近7天" value="近7天"></el-radio-button>
        <el-radio-button label="近15天" value="近15天"></el-radio-button>
      </el-radio-group>
    </div>
    <div class="station-top">
      <el-row :gutter="12">
        <el-col :span="4">
          <div class="top-item-box item-box-three" style="display: flex;">
            <div style="flex:2;height:100%;">
              <div>工单数</div>
              <div style="text-align:center;margin-top:15px;"><span style="font-size:50px;font-weight:bold;">{{
                data.sheetNum }}</span>
              </div>
            </div>
          </div>

        </el-col>
        <el-col :span="4">
          <div class="top-item-box item-box-two" style="display: flex;">
            <div style="flex:2;height:100%;">
              <div>完成数</div>
              <div style="text-align:center;margin-top:15px;"><span style="font-size:50px;font-weight:bold;">{{
                data.finishNum }}</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="top-item-box item-box-one" style="display: flex;">
            <div style="flex:2;height:100%;">
              <div>计划数</div>
              <div style="text-align:center;margin-top:15px;"><span style="font-size:50px;font-weight:bold;">{{
                data.planNum }}</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="top-item-box item-box-three" style="display: flex;">
            <div style="flex:2;height:100%;">
              <div>平均生产周期（天）</div>
              <div style="text-align:center;margin-top:15px;"><span style="font-size:50px;font-weight:bold;">{{
                data.averageProduce }}</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="top-item-box item-box-four" style="display: flex;">
            <div style="flex:2;height:100%;">
              <div>不良率</div>
              <div style="text-align:center;margin-top:15px;"><span style="font-size:50px;font-weight:bold;">{{
                defectYield }}%</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="top-item-box item-box-four" style="display: flex;">
            <div style="flex:2;height:100%;">
              <div>超期工单</div>
              <div style="text-align:center;margin-top:15px;"><span style="font-size:50px;font-weight:bold;">{{
                data.delayNum }}</span>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="station-middle">
      <el-row :gutter="12">
        <el-col :span="18">
          <el-card class="box-card" shadow="never">
            <div style="display:flex;justify-content: space-between;align-items: center;">
              <div class="card-title">工单进度表</div>
            </div>
            <div style="height: calc(100% - 30px);">
              <sheetTable :params="params" @data="handleSheetNum"></sheetTable>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="box-card" shadow="never">
            <div class="card-title">生产实时播报</div>
            <div style="height: calc(100% - 30px); position: left; padding: 2px; overflow: auto;" ref="scrollContainer"
              @scroll="handleScroll">
              <el-timeline style="height: 400px;">
                <el-timeline-item placement="top" v-for="(jobBooking, index) in JobBookingList" :key="index"
                  icon="el-icon-more" type="success" size="large" :timestamp="jobBooking.createTime">
                  <el-tooltip class="item" effect="dark" :disabled="tooltipVisible" :content="handleContent(jobBooking)"
                    placement="top">
                    <el-card style="width: 95%;">
                      <span style="font-size:16px;">{{ jobBooking.personnelName }} {{ jobBooking.procedureName }}</span>
                      <p>工单号：{{ jobBooking.sheetNumber }}</p>
                      <p>产品信息：{{ jobBooking.productNumber }}</p>
                      <p>产品名称：{{ jobBooking.productName }}</p>
                      <p>{{ jobBooking.goodQuantity }} 良品 / {{ jobBooking.defectQuantity }} 不良品</p>
                    </el-card>
                  </el-tooltip>
                </el-timeline-item>
              </el-timeline>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div class="station-bottom">
      <el-row :gutter="12">
        <el-col :span="8">
          <el-card class="box-card-chart" shadow="never">
            <div class="card-title">计划与产出</div>
            <div style="height: calc(100% - 30px);">
              <ProduceLine :chartData="planAndOut" height="100%" yName="个" itemColor="#5470c6" />
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card-chart" shadow="never">
            <div class="card-title">任务名分布</div>
            <div style="height: calc(100% - 30px);">
              <ProducePie :pieData="taskNames" height="100%"></ProducePie>
            </div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card class="box-card-chart" shadow="never">
            <div class="card-title">任务状态分布</div>
            <div style="height: calc(100% - 30px);">
              <ProduceBar :yData="taskStatus" :xData="taskStatus.xData" height="100%" />
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import sheetTable from './sheetTable.vue';
import ProduceBar from './ProduceBar.vue';
import ProduceLine from './ProduceLine.vue';
import ProducePie from './ProducePie.vue';
import { listJobBooking } from "@/api/produce/jobBooking";
import moment from 'moment/moment'
import { overviewCharts } from "@/api/produce/task";
export default {
  components: {
    sheetTable,
    ProduceBar,
    ProduceLine,
    ProducePie
  },
  data() {
    return {
      tooltipVisible: false,
      scrollTimer: null,
      selectArea: '',
      queryParams: { dateRange: [], },
      JobBookingList: [],
      planAndOut: {
        yData: [],
        yData2: [],
        yData3: [],
        xData: [],
      },
      taskStatus: {
        notStart: [],
        finish: [],
        loading: [],
        xData: []
      },
      taskNames: [],
      defectYield: undefined,
      rules: {
        dateRange: [
          { required: true, message: "请选择时间", trigger: "change" },
        ],
      },
      params: {},
      data: {},

    }
  },
  created() {
    this.defaultDate();
    this.$nextTick(() => {
      this.getAllData();
    })
  },
  mounted() {
    // 在组件加载时从本地存储中获取已保存的值
    this.selectArea = localStorage.getItem('selectArea') || '今天';
    if (this.selectArea === '今天') {
      this.queryParams.dateRange = [moment().format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
    } else if (this.selectArea === '昨天') {
      this.queryParams.dateRange = [moment().subtract(1, 'days').format("yyyy-MM-DD 00:00:00"), moment().subtract(1, 'days').format("yyyy-MM-DD 23:59:59")];
    } else if (this.selectArea === '本月') {
      this.queryParams.dateRange = [moment().format("yyyy-MM-01 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
    } else if (this.selectArea === '近7天') {
      this.queryParams.dateRange = [moment().subtract(6, 'days').format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
    } else if (this.selectArea === '近15天') {
      this.queryParams.dateRange = [moment().subtract(14, 'days').format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
    }

  },
  methods: {
    /** 播报滚动禁用tooltip */
    handleScroll() {
      // 关闭所有 tooltip
      this.tooltipVisible = true;
      // 在滚动结束后打开 tooltip
      clearTimeout(this.scrollTimer);
      this.scrollTimer = setTimeout(() => {
        this.tooltipVisible = false;
      }, 200); // 在滚动停止 200ms 后打开 tooltip
    },
    getAllData() {
      let params = {
        startTime: this.queryParams.dateRange[0],
        endTime: this.queryParams.dateRange[1],
      }
      this.params = params;

      this.getJobBookingList(params);
      this.getOverviewCharts(params)
    },
    //默认日期
    defaultDate() {
      this.queryParams.dateRange = [moment().format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
    },
    /** 查询报工列表 */
    getJobBookingList(params) {
      var queryParams = {};
      queryParams.procedureStatus = '2';
      queryParams.params = params;
      listJobBooking(queryParams).then(response => {
        this.JobBookingList = response.rows;
      });
    },
    handleContent(jobBooking) {
      return jobBooking.productNumber + ' | ' + jobBooking.productName + '   ' + jobBooking.goodQuantity + '   良品 / ' + jobBooking.defectQuantity + '不良品';
    },
    handleSheetNum(data) {
      this.data = data;
    },
    getOverviewCharts(params) {
      var queryParams = {};
      queryParams.params = params;
      overviewCharts(queryParams).then(response => {
        var planAndOut = response.data.planAndOut;
        var planAndOutData = {
          yData: [],
          yData2: [],
          yData3: [],
          xData: [],
        };
        planAndOut.forEach(e => {
          planAndOutData.xData.push(e.dateTime);
          planAndOutData.yData.push(e.defectQuantity);
          planAndOutData.yData2.push(e.plannedQuantity);
          planAndOutData.yData3.push(e.goodQuantity);
        });
        this.planAndOut = planAndOutData;

        var taskStatus = response.data.taskStatus;
        var taskStatusData = {
          notStart: [],
          finish: [],
          loading: [],
          xData: []
        };
        taskStatus.forEach(e => {
          taskStatusData.notStart.push(e.notStart);
          taskStatusData.finish.push(e.finish);
          taskStatusData.loading.push(e.loading);
          taskStatusData.xData.push(e.dateTime);
        });
        this.taskStatus = taskStatusData;
        this.taskNames = response.data.taskNames;
        this.defectYield = response.data.defectYield;
      });
    },
    selectAreaChange() {
      localStorage.setItem('selectArea', this.selectArea);
      if (this.selectArea === '今天') {
        this.queryParams.dateRange = [moment().format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
      } else if (this.selectArea === '昨天') {
        this.queryParams.dateRange = [moment().subtract(1, 'days').format("yyyy-MM-DD 00:00:00"), moment().subtract(1, 'days').format("yyyy-MM-DD 23:59:59")];
      } else if (this.selectArea === '本月') {
        this.queryParams.dateRange = [moment().format("yyyy-MM-01 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
      } else if (this.selectArea === '近7天') {
        this.queryParams.dateRange = [moment().subtract(6, 'days').format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
      } else if (this.selectArea === '近15天') {
        this.queryParams.dateRange = [moment().subtract(14, 'days').format("yyyy-MM-DD 00:00:00"), moment().format("yyyy-MM-DD 23:59:59")];
      }
      this.getAllData();
    }
  },
}
</script>

<style scoped>
.app-container {
  min-height: calc(100vh - 84px);
  padding: 12px 12px 0 12px;
}

.top-item-box {
  height: 120px;
  background: var(--base-item-bg);
  margin-bottom: 12px;
  border-radius: 12px;
  color: #fff;
  padding: 14px;
}

.top-item-box2 {
  height: 85px;
  background: var(--base-item-bg);
  margin-bottom: 12px;
  border-radius: 12px;
  color: #fff;
  padding: 16px;
}

.item-box-one {
  background: linear-gradient(30deg, #1a94db, #4db1eb, #7acaf9);
  box-shadow: 0 4px 12px #8ed2fa;
}

.item-box-two {
  background: linear-gradient(30deg, #c7a327, #d5ba47, #e3cf65);
  box-shadow: 0 4px 12px #ece7cd;
}

.item-box-three {
  background: linear-gradient(30deg, #6365f7, #9177f1, #cd8ee9);
  box-shadow: 0 4px 12px #dcc9e6;
}

.item-box-four {
  background: linear-gradient(30deg, #ed3a60, #f1557a, #f67da0);
  box-shadow: 0 4px 12px #e7cfd6;
}

.box-card {
  height: 500px;
  margin-bottom: 12px;
  background-color: var(--base-item-bg);
  border-color: var(--border-color-3);
}

.box-card-chart {
  height: 400px;
  margin-bottom: 12px;
  background-color: var(--base-item-bg);
  border-color: var(--border-color-3);
}

.box-card-chart>>>.el-card__body {
  height: 100%;
}

.box-card>>>.el-card__body {
  height: 100%;
}

.card-title {
  font-weight: bold;
  height: 30px;
  display: flex;
  align-items: center;
  color: var(--base-color-1);
}

.card-title::before {
  content: '';
  height: 70%;
  width: 5px;
  background: var(--current-color);
  margin-right: 8px;
}

.nav-top {
  margin: 6px;
  display: flex;
  justify-content: space-between;
}

.time-stamp {
  position: absolute;
  right: 2.5%;
  color: #00d1ff;
  font-size: 20px;
}

.el-timeline {
  padding: 0;
}
</style>
