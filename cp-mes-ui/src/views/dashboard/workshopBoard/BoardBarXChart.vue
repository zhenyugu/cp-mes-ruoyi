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
        return [374, 276, 146, 86, 1];
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
        return ["产品1", "产品2", "产品3", "产品4", "产品5"];
      },
    },
    legendData: {
      type: Array,
      default: () => {
        return ["计划数"];
      },
    },
  },
  data() {
    return {
      chart: null,
    };
  },
  watch: {
    yData: {
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
        grid: {
          top: "15%",
          left: "4%",
          right: "4%",
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
            type: "none",
          },
          formatter: function (params) {
            return params[0].name + "<br/>" + params[0].marker + params[0].seriesName + "&nbsp&nbsp&nbsp&nbsp" + params[0].value;
          }
        },
        xAxis: {
          // name: this.xName,
          data: this.yData,
          type: "value",
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
          splitLine: {
            show: false,
          },
        },
        yAxis: {
          // name: this.yName,
          type: "category",
          inverse: true,
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
            show: false,
          },
          data: this.xData,
        },
        legend: {
          data: this.legendData,
          textStyle: {
            color: "#fff",
          },
        },
        series: [
          {
            name: this.legendData[0],
            type: "bar",
            zlevel: 1,
            itemStyle: {
              borderRadius: 30,
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                {
                  offset: 0,
                  color: "rgb(10, 116, 255,1)",
                },
                {
                  offset: 1,
                  color: "rgb(127,237,241,1)",
                },
              ]),
            },
            z: 2,
            barWidth: "12px",
            animationDuration: 1500,
            data: this.yData,
          },
          {
            type: "pictorialBar",
            symbol:
              "image://data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADoAAAA6CAMAAADWZboaAAAAZlBMVEUAAABe3uVe3+Vf3uVf3+Zf3uVg3+Zg3+Zf3+Vi4OZh4OZg3+Z86/Bh3+Zi4Odj4Odi4OZ86/B76/B86/Bj4ed56+9x5+xn4umB7/N87PB36e+A7/N+7fF/7vJ/7vJ+7fGA7/OB7/PReX+lAAAAIXRSTlMABQkVDREmIhk3MR10LEFFPHh7cUprXE35h2XnqMLAp+mHAG9cAAAB5ElEQVRIx83WjU7CMBQFYIoiKMqU/XUboHv/l/Tce7t2XamDNSacETEmX86tlK2rx4py150o+MstMBLwWRfHKo6JCVxLnvmFGBjFQ58oF1//sUZhGy/ClSTWObgnL4O+bkeN4nY2okfNMbkRt9/vtxz8InoTsWplJSCzFxPmO8+GpSIByX3YQAuGDWtRKhKjCnxDXhF6Z4yxnZ20Wgko7BMRDmxtSGVaI4kdTIgb+zTYoJQlIMlDlmUFgrcDWWC201qSayqlTkiCddWWeV62VU0YlnpRi9VOKaSUsiyq/N0krwq2Ugt7lVpZl5BfHNiytjagMi+XYp0kCR45hMlivVQrE/uU5pXSrCB5bM6d1t2lOZItMqmliT3q5uVxqxzyW/ccfYLNKx7ZTeykMvNyac2yt2Fbc61MHLSC0rwoxbiNdlQ3GBm1NLHQsHUrtEXppR/ljNpW6DbSCoqlFiVoN6YdaFlgsSFVPs1BdT8OaB5QyQzVcaqWDows/zepxR8ObLglTrdtCRVuRNj4Rrxh+//0ke2f8KVL+Kon3GCSbmsJN9OUW3j6g0Ns+LgCij2u0h+Sghc8mlMPBMgdx5DFh59VmOVHrvmDnoNxCz3J7MFWsMuaLyR089xz/xhlfijvwutR8gv3zk6BLUUeCgAAAABJRU5ErkJggg==",
            symbolSize: [80, 80],
            symbolOffset: [40, 0],
            z: 10086,
            itemStyle: {
              color: "#0A74FF",
            },
            animationDuration: 1500,
            data: this.getSymbolData(this.yData),
          },
          {
            type: "bar",
            barWidth: "12px",
            barGap: "-100%",
            data: [this.yData[0], this.yData[0], this.yData[0], this.yData[0], this.yData[0]],
            itemStyle: {
              color: "#004298",
              borderRadius: 30,
            },
            z: 1,
            animationDuration: 1500,
          },
        ],
      });
    },
    getSymbolData(data) {
      let arr = [];
      for (var i = 0; i < data.length; i++) {
        arr.push({
          value: data[i],
          symbolPosition: "end",
        });
      }
      return arr;
    },
  },
};
</script>
