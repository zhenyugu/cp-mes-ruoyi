<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from "echarts";
import resize from "../mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "350px",
    },
    autoResize: {
      type: Boolean,
      default: true,
    },
    chartData: {
      type: Number,
      default: 90,
    },
  },
  data() {
    return {
      chart: null,
      timer: null,
      angle: 0, // 角度
    };
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val);
      },
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();

      // 定时任务
      setTimeout(() => {
        if (this.timer) {
          clearInterval(this.timer);
          this.timer = null;
        }
        this.timer = setInterval(() => {
          this.drawChart();
        }, 50);
      }, 1500);
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
    clearInterval(this.timer);
    this.timer = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, null, { devicePixelRatio: 2 });
      this.setOptions();
    },
    setOptions() {
      var _this = this
      let angleValue = _this.angle
      let colors = ["#0a74ff", "#4386FA", "#4FADFD", "#0CD3DB", "#646CF9"]
      _this.chart.setOption({
        backgroundColor: "rgba(0, 0, 0, 0)",
        color: colors,
        tooltip: {
          show: false,
        },
        series: [
          {
            name: "",
            type: "pie",
            center: ["50%", "50%"],
            radius: ["47%", "52%"],
            animationDuration: 1500,
            avoidLabelOverlap: false, // 做同心圆用到
            clockwise: false, // 顺时针排列
            startAngle: 90, // 起始角度
            label: {
              show: true,
              position: "center",
              formatter: (params) => {
                return "{name|良率}\n{value|" + _this.chartData + "}%";
              },
              rich: {
                name: {
                  fontSize: 16,
                  fontWeight: "normal",
                  color: "#fff",
                  padding: [0, 0, 8, 0]
                },
                value: {
                  fontSize: 30,
                  fontWeight: "bold",
                  color: "#fff",
                  padding: [8, 0, 0, 0]
                }
              },
              fontSize: 30,
              fontWeight: "bold",
              color: "#fff",
            },
            data: [
              {
                value: _this.chartData,
                name: "完成",
                itemStyle: {
                  color: {
                    type: "linear",
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [0, 0.3, 0.6, 0.8, 1].map((offset, index) => ({
                      offset,
                      color: colors[index],
                    })),
                    global: false, // 缺省为 false
                  },
                  opacity: 1,
                },
              },
              {
                value: 100 - _this.chartData,
                name: "未完成",
                itemStyle: { color: "#282c40" },
              },
            ].sort((a, b) => b.value - a.value), //数组从大到小排序
            emphasis: {
              scale: false,
            },
          },
          {
            name: "",
            type: "pie",
            center: ["50%", "50%"], //圆心的位置
            radius: ["55%", "70%"], //环形图的本质就在这里 [内半径!=0，外半径] 外半径越大，圆越大
            animationDuration: 1500,
            avoidLabelOverlap: false, //做同心圆用到
            clockwise: false, //顺时针排列
            startAngle: 90, //起始角度 影响不大
            label: {
              show: false, //false不显示饼图上的标签
            },
            data: [
              {
                value: _this.chartData,
                name: "完成",
                itemStyle: {
                  color: {
                    type: "linear",
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [0, 0.3, 0.6, 0.8, 1].map((offset, index) => ({
                      offset,
                      color: colors[index],
                    })),
                    global: false, // 缺省为 false
                  },
                  opacity: 1,
                },
              },
              {
                value: 100 - _this.chartData,
                name: "未完成",
                itemStyle: { color: "#282c40" },
              },
            ].sort((a, b) => b.value - a.value), //数组从大到小排序
            emphasis: {
              scale: false,
            },
          },
          {
            name: "ring5", // colors[0]line
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.77,
                  startAngle: ((0 + angleValue) * Math.PI) / 180,
                  endAngle: ((90 + angleValue) * Math.PI) / 180,
                },
                style: {
                  stroke: colors[0],
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5", // 蓝色
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.77,
                  startAngle: ((180 + angleValue) * Math.PI) / 180,
                  endAngle: ((270 + angleValue) * Math.PI) / 180,
                },
                style: {
                  stroke: colors[1],
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.85,
                  startAngle: ((270 + -angleValue) * Math.PI) / 180,
                  endAngle: ((40 + -angleValue) * Math.PI) / 180,
                },
                style: {
                  stroke: colors[2],
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5", // 橘色
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              return {
                type: "arc",
                shape: {
                  cx: api.getWidth() / 2,
                  cy: api.getHeight() / 2,
                  r: (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.85,
                  startAngle: ((90 + -angleValue) * Math.PI) / 180,
                  endAngle: ((220 + -angleValue) * Math.PI) / 180,
                },
                style: {
                  stroke: colors[2],
                  fill: "transparent",
                  lineWidth: 1.5,
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5",
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              let x0 = api.getWidth() / 2;
              let y0 = api.getHeight() / 2;
              let r = (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.85;
              let point = _this.getCirclePoint(x0, y0, r, 90 + -angleValue);
              return {
                type: "circle",
                shape: {
                  cx: point.x,
                  cy: point.y,
                  r: 4,
                },
                style: {
                  stroke: colors[3], //粉
                  fill: colors[3],
                },
                silent: true,
              };
            },
            data: [0],
          },
          {
            name: "ring5", //绿点
            type: "custom",
            coordinateSystem: "none",
            renderItem: function (params, api) {
              let x0 = api.getWidth() / 2;
              let y0 = api.getHeight() / 2;
              let r = (Math.min(api.getWidth(), api.getHeight()) / 2) * 0.85;
              let point = _this.getCirclePoint(x0, y0, r, 270 + -angleValue);
              return {
                type: "circle",
                shape: {
                  cx: point.x,
                  cy: point.y,
                  r: 4,
                },
                style: {
                  stroke: colors[3], //绿
                  fill: colors[3],
                },
                silent: true,
              };
            },
            data: [0],
          },
        ],
      });
    },
    // 获取圆上某点的坐标（x0, y0, r半径, angle角度）
    getCirclePoint(x0, y0, r, angle) {
      let x1 = x0 + r * Math.cos((angle * Math.PI) / 180);
      let y1 = y0 + r * Math.sin((angle * Math.PI) / 180);
      return { x: x1, y: y1 };
    },
    drawChart() {
      this.angle = this.angle + 2;
      if(this.chart == null) return
      this.setOptions();
    },
  },
};
</script>
<style scoped>
</style>
