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
    yData: {
      type: Array,
      default: () => {
        return [79, 52, 200, 334, 390, 330, 220, 65, 21, 67, 234, 21, 89, 56, 79, 79];
      },
    },
    yName: {
      type: String,
      default: "",
    },
    xName: {
      type: String,
      default: "",
    },
    xData: {
      type: Array,
      default: () => {
        return [
          "00:00",
          "01:00",
          "02:00",
          "03:00",
          "04:00",
          "05:00",
          "06:00",
          "07:00",
          "08:00",
          "09:00",
          "10:00",
          "11:00",
          "12:00",
          "13:00",
          "14:00",
          "15:00",
          // "16:00",
          // "17:00",
          // "18:00",
          // "19:00",
          // "20:00",
          // "21:00",
          // "22:00",
          // "23:00",
        ];
      },
    },
    animationData: {
      type: Array,
      default: () => {
        return [['00:00', 79], ['01:00', 52], ['02:00', 200], ['03:00', 334], ['04:00', 390], ['05:00', 330], ['06:00', 220], ['07:00', 65], ['08:00', 21], ['09:00', 67], ['10:00', 234], ['11:00', 21], ['12:00', 89], ['13:00', 56], ['14:00', 79], ['15:00', 79],]
      }
    },
    legendData: {
      type: Array,
      default: () => {
        return ['产出']
      }
    }
  },
  data() {
    return {
      chart: null,
    };
  },
  watch: {
    animationData: {
      deep: true,
      handler(val) {
        this.$nextTick(() => {
          this.setOptions(val);
        });
      },
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, null, { devicePixelRatio: 2 });
      this.setOptions();
    },
    setOptions() {
      this.chart.setOption({
        xAxis: {
          name: this.xName,
          data: this.xData,
          boundaryGap: true,
          axisLine: {
            show: true,
            symbol: ["none", "rect"],
            symbolSize: [6, 12],
            lineStyle: {
              width: 2,
              color: "#537DAA",
            },
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            color: "#fff",
          },
        },
        backgroundColor: "rgba(0, 0, 0, 0)",
        grid: {
          top: "15%",
          left: "1%",
          right: "1%",
          bottom: "1%",
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          confine: true,
          backgroundColor: "rgba(114, 178, 255, 0.6)",
          borderColor: "#72b2ff",
          textStyle: {
            fontSize: 16,
            color: "#fff",
          },
          // 鼠标移入时竖线的样式
          axisPointer: {
            type: "line",
            lineStyle: {
              color: "#64ffff",
            },
          },
        },
        dataZoom: {
          show: true,
          type: "inside",
        },
        yAxis: {
          name: this.yName,
          nameTextStyle: {
            color: "#fff",
            fontWeight: 400,
            fontSize: 14,
          },
          axisLabel: {
            color: "#fff",
          },
          // x、y轴顶端的样式，小矩形
          axisLine: {
            show: true,
            symbol: ["none", "rect"],
            symbolSize: [6, 12],
            lineStyle: {
              width: 2,
              color: "#537DAA",
            },
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "rgba(83, 125, 170, 0.2)",
            },
          },
        },
        legend: {
          data: this.legendData,
          textStyle: {
            color: "#fff",
          }
        },
        series: [
          {
            name: this.legendData[0],
            // smooth: true,
            type: "line",
            data: this.yData,
            animationDuration: 1500,
            // 线条节点的样式
            symbol:
              "image://https://easyv.assets.dtstack.com/data/114350/729633/img/aqp4b9ektk_1642493282093_00kx2xfruc.png",
            symbolSize: 15,
            itemStyle: {
              color: "rgba(114, 178, 255, 1)",
            },
            // 线条样式
            lineStyle: {
              width: 2,
              shadowBlur: 20,
              shadowColor: "#72B2FF",
              shadowOffsetY: 10,
            },
            // 线条下面阴影的样式，线性渐变
            areaStyle: {
              color: new echarts.graphic.LinearGradient(
                0,
                0,
                0,
                1,
                [
                  {
                    offset: 0,
                    color: "rgba(114, 178, 255, 0.25)",
                  },
                  {
                    offset: 1,
                    color: "rgba(114, 178, 255, 0)",
                  },
                ],
                false
              ),
              opacity: 1,
            },
          },
          {
            type: "lines",
            coordinateSystem: "cartesian2d",
            data: [
              {
                coords: this.animationData,
              },
            ],
            zlevel: 1,
            // 是否是多线段
            polyline: true,
            symbol: "circle",
            effect: {
              show: true,
              trailLength: 0.4,
              symbol: "circle",
              period: 8,
              symbolSize: 8,
            },
            lineStyle: {
              color: "#64FFFF",
              width: 0,
              opacity: 0,
              curveness: 1,
            },
          },
        ],
      });
    },
  },
};
</script>
