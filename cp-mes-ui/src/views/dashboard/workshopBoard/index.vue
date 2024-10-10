<template>
  <div class="container-bg" ref="containerBg" id="container-bg">
    <div class="board-cotainer" id="board-container">
      <div v-if="showWhich" class="back-btn" @click="backHome"><img src="@/assets/images/home.png">云脉数字工厂</div>
      <img v-else src="@/assets/images/fullscreen.png" alt="" class="fullscreen-img" @click="toDataBoard">
      <div class="board-title">
        <span style="margin-left: 8px">云脉数字工厂大屏</span>
      </div>
      <div class="board-time">{{ nowTime }}</div>
      <div class="board-content">
        <div class="content-left flex-column-between">
          <div class="board-left-top flex-between">
            <div class="left-top-first flex-column-between">
              <div class="top-first-top">
                <div class="item-title" style="color: #fff">
                  <div class="item-title-icon"></div>
                  <span>工单产出趋势</span>
                  <img src="@/assets/images/title-icon.png" alt="" />
                </div>
                <div class="item-body">
                  <BoardLineChartVue height="100%" :xData="outPut.xData" :yData="outPut.yData"
                    :animationData="outPut.animationData" />
                </div>
              </div>
              <div class="top-first-bottom">
                <div class="item-title" style="color: #fff">
                  <div class="item-title-icon"></div>
                  <span>良品/不良品趋势</span>
                  <img src="@/assets/images/title-icon.png" alt="" />
                </div>
                <div class="item-body">
                  <BoardBarLineChartVue height="100%" :xData="goodAndBad.xData" :barData="goodAndBad.barData"
                    :lineDate="goodAndBad.lineDate" />
                </div>
              </div>
            </div>
            <div class="left-top-second flex-column-between">
              <div class="top-second-top flex-between">
                <div class="middle-top-count">
                  <div class="count-item flex-column-center">
                    <div>工单数</div>
                    <div class="count-num">{{ total }}</div>
                  </div>
                  <div class="count-item flex-column-center">
                    <div>计划数</div>
                    <div class="count-num">{{ totalPlan }}</div>
                  </div>
                  <div class="count-item flex-column-center">
                    <div>完成数</div>
                    <div class="count-num">{{ finish }}</div>
                  </div>
                  <div class="count-item flex-column-center">
                    <div>延期工单数</div>
                    <div class="count-num">{{ delay }}</div>
                  </div>
                </div>
                <div class="middle-top-yield">
                  <BoardYieldChartVue height="100%" :chartData="parseFloat(yield)" />
                </div>
              </div>
              <div class="top-first-bottom">
                <div class="item-title" style="color: #fff">
                  <div class="item-title-icon"></div>
                  <span>任务进度</span>
                  <img src="@/assets/images/title-icon.png" alt="" />
                </div>
                <div class="item-body">
                  <BoardTaskTableVue :tableData="taskProgress" />
                </div>
              </div>
            </div>
          </div>
          <div class="board-left-bottom">
            <div class="item-title" style="color: #fff">
              <div class="item-title-icon"></div>
              <span>工单进度</span>
              <img src="@/assets/images/title-icon.png" alt="" />
            </div>
            <div class="item-body">
              <BoardSheetTableVue :tableData="sheetList" />
            </div>
          </div>
        </div>
        <div class="content-right flex-column-between">
          <div class="board-right-top">
            <div class="item-title" style="color: #fff">
              <div class="item-title-icon"></div>
              <span>报工轮播</span>
              <img src="@/assets/images/title-icon.png" alt="" />
            </div>
            <div class="item-body">
              <BoardJobBookingVue :jobBookingList="jobBookingList" />
            </div>
          </div>
          <div class="board-right-middle">
            <div class="item-title" style="color: #fff">
              <div class="item-title-icon"></div>
              <span>工单计划数Top5</span>
              <img src="@/assets/images/title-icon.png" alt="" />
            </div>
            <div class="item-body">
              <BoardBarXChartVue height="100%" :xData="sheetPlan.xData" :yData="sheetPlan.yData" />
            </div>
          </div>
          <div class="board-right-bottom">
            <div class="item-title" style="color: #fff">
              <div class="item-title-icon"></div>
              <span>工序报工数Top5</span>
              <img src="@/assets/images/title-icon.png" alt="" />
            </div>
            <div class="item-body">
              <BoardPieChartVue height="100%" :chartData="jobBookingNums" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import BoardLineChartVue from "./BoardLineChart.vue";
import BoardBarLineChartVue from "./BoardBarLineChart.vue";
import BoardBarXChartVue from "./BoardBarXChart.vue";
import BoardPieChartVue from "./BoardPieChart.vue";
import BoardYieldChartVue from "./BoardYieldChart.vue";
import BoardJobBookingVue from "./BoardJobBooking.vue";
import BoardTaskTableVue from "./BoardTaskTable.vue";
import BoardSheetTableVue from "./BoardSheetTable.vue";
import { dataDashboards } from "@/api/produce/task";
export default {
  data() {
    return {
      nowTime: moment().format("YYYY-MM-DD HH:mm:ss"),
      timer: null,
      erd: null,
      outPut: {
        xData: [],
        yData: [],
        animationData: [[]],
      },
      goodAndBad: {
        xData: [],
        lineDate: [],
        barData: [],
      },
      total: undefined,
      totalPlan: undefined,
      finish: undefined,
      delay: undefined,
      yield: undefined,
      taskProgress: [],
      sheetPlan: {
        xData: [],
        yData: [],
      },
      jobBookingNums: [],
      sheetList:[],
      jobBookingList:[],
      showWhich: false, // 全屏按钮切换
    };
  },
  components: {
    BoardLineChartVue,
    BoardBarLineChartVue,
    BoardBarXChartVue,
    BoardPieChartVue,
    BoardYieldChartVue,
    BoardJobBookingVue,
    BoardTaskTableVue,
    BoardSheetTableVue,
  },
  created() {
    this.getAllData();
    if (this.timer) {
      clearInterval(this.timer);
      this.timer = null;
    }
    this.timer = setInterval(this.getNowTime, 1000);
    this.showWhich = this.$router.currentRoute.path == '/data-board' ? false : true

  },
  mounted() {
    // 监听元素变化
    let elementReSizeDetectorMarker = require("element-resize-detector");
    this.erd = elementReSizeDetectorMarker();
    this.erd.listenTo(document.getElementById("container-bg"), (ele) => {
      if (ele) {
        this.reSize();
      }
    });
  },
  methods: {
    getNowTime() {
      this.nowTime = moment().format("YYYY-MM-DD HH:mm:ss");
    },
    getAllData() {
      dataDashboards().then(response => {
        var planAndOut = response.data.planAndOut;
        var outPut = {
          yData: [],
          xData: [],
          animationData: [],
        };
        var goodAndBad = {
          xData: [],
          lineDate: [],
          barData: [],
        };
        planAndOut.forEach(e => {
          outPut.xData.push(e.dateTime);
          outPut.yData.push(e.plannedQuantity);
          outPut.animationData.push([e.dateTime, e.plannedQuantity])
          goodAndBad.xData.push(e.dateTime);
          goodAndBad.lineDate.push(e.goodQuantity);
          goodAndBad.barData.push(e.defectQuantity);
        });
        this.outPut = outPut;
        this.goodAndBad = goodAndBad;
        this.total = response.data.total;
        this.totalPlan = response.data.totalPlan;
        this.finish = response.data.finish;
        this.delay = response.data.delay;
        this.yield = response.data.yield;
        this.taskProgress = response.data.taskProgress;
        this.sheetList = response.data.sheetList;
        this.jobBookingList = response.data.jobBookingList;
        var sheetPlan = response.data.sheetPlan;
        var sheetData = {
          xData: [],
          yData: [],
        }
        sheetPlan.forEach(e => {
          sheetData.xData.push(e.productName);
          sheetData.yData.push(e.plannedQuantity);
        });
        this.sheetPlan = sheetData;
        var jobBookingNums = response.data.jobBookingNums;
        jobBookingNums.forEach(e => {
          var jobBookingData = {
            name: e.procedureName,
            value: e.jobBookingCountNum,
          }
          this.jobBookingNums.push(jobBookingData);
        });


      });
    },
    toDataBoard() {
      this.$router.push('/system/data-board')
    },
    backHome() {
      this.$router.push('/data-board')
      // this.$router.push('/dashboard')
    }
  },
  beforeDestory() {
    clearInterval(this.timer);
    this.timer = null;
    if (this.erd != null) {
      // this.erf.uninstall(this.$refs.containerBg);
      this.erd.removeAllListeners(this.$refs.containerBg);
      this.erd = null;
    }
  },
  
};
</script>

<style scoped>
.container-bg {
  height: 100%;
  width: 100%;
  background-color: #01182f;
}

.board-cotainer {
  /* height: 100%;
  width: 100%; */
  height: 1080px;
  width: 1920px;
  background-image: url("../../../assets/images/board-bg.png");
  background-size: 100% 100%;
  position: relative;
  /* min-height: 720px; */
  /* min-width: 1280px; */
}

.board-title {
  color: #fff;
  font-size: 48px;
  font-weight: bold;
  text-align: center;
  letter-spacing: 16px;
  /* background-image: -webkit-gradient(linear, left bottom, left top, from(#79f0fd), to(#2571e9)); */
  background-image: linear-gradient(0deg, #79f0fd, #2571e9);
  background-clip: text;
  -webkit-background-clip: text;
  color: transparent;
  height: 9%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.board-time {
  color: #00d1ff;
  font-size: 18px;
  position: absolute;
  top: 60px;
  right: 2.5%;
}

.board-content {
  width: 94%;
  height: 86%;
  margin: 8px auto 0;
  display: flex;
  justify-content: space-between;
}

.content-left {
  width: calc(70% - 8px);
  height: 100%;
}

.board-left-top {
  width: 100%;
  height: calc(67% - 8px);
}

.left-top-first {
  width: calc(43.4% - 8px);
  height: 100%;
}

.top-first-top {
  width: 100%;
  height: calc(50% - 8px);
  border: 1px solid #2070bc;
  padding: 16px;
}

.top-second-top {
  width: 100%;
  height: calc(50% - 8px);
  border: 1px solid #2070bc;
}

.middle-top-count {
  height: 100%;
  width: 60%;
  padding: 12px;
  display: flex;
  flex-wrap: wrap;
}

.count-item {
  flex-basis: 50%;
  height: 50%;
  box-sizing: border-box;
  padding: 12px 6px;
  color: #fff;
  padding-left: 20px
}

.middle-top-yield {
  height: 100%;
  width: 40%;
  padding: 12px;
}

.top-first-bottom {
  border: 1px solid #2070bc;
  width: 100%;
  height: calc(50% - 8px);
  padding: 16px;
}

.left-top-second {
  width: calc(56.6% - 8px);
  height: 100%;
}

.board-left-bottom {
  border: 1px solid #2070bc;
  width: 100%;
  height: calc(33% - 8px);
  padding: 16px;
}

.content-right {
  width: calc(30% - 8px);
  height: 100%;
}

.board-right-top {
  border: 1px solid #2070bc;
  width: 100%;
  height: calc(33.333333% - 10.666666px);
  padding: 16px;
}

.board-right-middle {
  border: 1px solid #2070bc;
  width: 100%;
  height: calc(33.333333% - 10.666666px);
  padding: 16px;
}

.board-right-bottom {
  border: 1px solid #2070bc;
  width: 100%;
  height: calc(33.333333% - 10.666666px);
  padding: 16px;
}

.item-title {
  display: flex;
  align-items: center;
  height: 20px;
  margin-bottom: 12px;
}

.item-body {
  height: calc(100% - 32px);
}

.item-title-icon {
  height: 18px;
  width: 6px;
  background-color: #79f0fd;
  border-radius: 8px;
  margin-right: 6px;
}

.count-num {
  font-size: 36px;
  font-weight: bold;
  margin-top: 12px;
  color: #7debf1;
}
.fullscreen-img {
  position: absolute;
  left: 3%;
  top: 5%;
  height: 3%;
  cursor: pointer;
}
.back-btn {
  position: absolute;
  left: 3%;
  top: 5%;
  height: 3%;
  color: #00d0ff;
  font-size: 17px;
  display: flex;
  align-items: center;
  cursor: pointer;
}
.back-btn img {
  height: 100%;
  margin-right: 6px;
}
</style>