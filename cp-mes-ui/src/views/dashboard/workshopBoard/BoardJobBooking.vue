<template>
  <div class="list-container">
    <VueSeamlessScroll :data="jobBookingList" class="seamless-warp" :class-option="defineScroll">
      <div class="list-item" v-for="item in jobBookingList" :key="item.jobBookingId">
        <div class="item-top flex-between">
          <div>{{item.personnelName}} | {{item.procedureName}}</div>
          <div>{{item.createTime}}</div>
        </div>
        <div
          class="item-bottom flex-between"
          style="display: flex; margin-top: 6px"
        >
          <div style="">{{item.productName}} | {{item.productNumber}}</div>
          <div style="display: flex">
            <div style="">良品: {{item.goodQuantity}}</div>
            <div style="margin-left: 20px">不良品： {{item.defectQuantity}}</div>
          </div>
        </div>
      </div>
    </VueSeamlessScroll>
  </div>
</template>

<script>
import VueSeamlessScroll from "vue-seamless-scroll";
export default {
  data() {
    return {};
  },
  components: {
    VueSeamlessScroll,
  },
  props: {
    jobBookingList: {
      type: Array,
      default: () => {
        return [
          {
            name: "测试员 | 工序名称",
            time: "2024-03-15 00:00:00",
            productName: "产品名 | 产品型号",
            good: 123,
            bad: 12,
          },
          {
            name: "测试员 | 工序名称",
            time: "2024-03-15 00:00:00",
            productName: "产品名 | 产品型号",
            good: 123,
            bad: 12,
          },
          {
            name: "测试员 | 工序名称",
            time: "2024-03-15 00:00:00",
            productName: "产品名 | 产品型号",
            good: 123,
            bad: 12,
          },
          {
            name: "测试员 | 工序名称",
            time: "2024-03-15 00:00:00",
            productName: "产品名 | 产品型号",
            good: 123,
            bad: 12,
          },
          {
            name: "测试员 | 工序名称",
            time: "2024-03-15 00:00:00",
            productName: "产品名 | 产品型号",
            good: 123,
            bad: 12,
          },
          {
            name: "测试员 | 工序名称",
            time: "2024-03-15 00:00:00",
            productName: "产品名 | 产品型号",
            good: 123,
            bad: 12,
          },
        ];
      },
    },
  },
  computed: {
    defineScroll() {
      return {
        step: 0.5, // 数值越大速度滚动越快
        limitMoveNum: 4, // 开始无缝滚动的数据量 this.dataList.length
        hoverStop: true, // 是否开启鼠标悬停stop
        direction: 1, // 0向下 1向上 2向左 3向右
        openWatch: true, // 开启数据实时监控刷新dom
        singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
        singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
        waitTime: 2000, // 单步运动停止的时间(默认值1000ms)
      };
    },
  },
  methods: {},
};
</script>

<style scoped>
.list-container {
  height: 100%;
  width: 100%;
}
.seamless-warp {
  height: 100%;
  width: 100%;
  overflow: hidden;
}
.list-item {
  color: #fff;
  padding: 14px 16px;
  background-color: rgba(11, 117, 255, 0.1);
  border-bottom: 2px dashed #7eecf2;
  font-size: 14px;
}
</style>