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
      type: Array,
      default: () => {
        return [
          {
            name: "A类",
            value: "3720",
          },
          {
            name: "B类",
            value: "2920",
          },
          {
            name: "C类",
            value: "2200",
          },
          {
            name: "D类",
            value: "1420",
          },
          {
            name: "E类",
            value: "3200",
          },
        ];
      },
    },
    legendData: {
      type: Array,
      default: () => {
        return ["报工"];
      },
    },
  },
  data() {
    return {
      chart: null,
      color: ["#88d9ff", "#0092ff", "#b0fa93", "#63f2ff", "#9999fe", "#88d9ff"],
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
        backgroundColor: "rgba(0, 0, 0, 0)",
        color: this.color,
        grid: {
          top: "15%",
          left: "1%",
          right: "1%",
          bottom: "1%",
          containLabel: true,
        },
        tooltip: {
          trigger: "item",
          confine: true,
          backgroundColor: "rgba(114, 178, 255, 0.8)",
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
        series: [
          {
            type: "pie",
            roseType: "radius",
            radius: ["35%", "70%"],
            center: ["50%", "50%"],
            data: this.chartData,
            animationDuration: 1500,
            // hoverAnimation: false,
            itemStyle: {
              borderWidth: 2,
            },
            labelLine: {
              length: 20,
              length2: 70,
              lineStyle: {
                // color: '#e6e6e6'
              },
            },
            label: {
              formatter: (params) => {
                return (
                  "{icon|●}{name|" +
                  params.name +
                  "}\n{value|" +
                  params.value +
                  "}"
                );
              },
              // padding: [0 , -100, 25, -100],
              rich: {
                icon: {
                  fontSize: 20,
                  color: "inherit",
                },
                name: {
                  fontSize: 14,
                  padding: [0, 0, 0, 6],
                  color: "#fefefe",
                },
                value: {
                  fontSize: 16,
                  fontWeight: "bolder",
                  padding: [0, 0, 0, 0],
                  color: "inherit",
                  // color: '#333333'
                },
              },
            },
          },
        ],
      });
    },
  },
};
</script>
