<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
// require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      default: () => {
        return {
          xData: ['2023-09-17','2023-09-18','2023-09-19','2023-09-20','2023-09-21','2023-09-22','2023-09-23','2023-09-24'],
          minData: [123,145,167,172,127,152,118,172],
          avgData: [263,271,283,251,229,239,216,201],
          maxData: [361,379,358,367,391,326,371,340]
        }
      }
      // required: true
    },
    configData: {
      type: Object,
      default: () => {
        return { yName: '电流', unit: 'A', energyType: 'electricityi'}
      }
    },
    dateRange: {
      type: Array,
      default: () => {
        return ['2023-09-17', '2023-10-17']
      }
    }

  },
  data() {
    return {
      chart: null
    }
  },
  computed: {
    theme() {
      return this.$store.getters.logoInfo.theme || 'theme-light'
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    },
    theme: {
      deep: true,
      handler(val) {
        if (!this.chart) {
          return
        }
        this.chart.dispose()
        this.chart = null
        this.initChart()
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      let echartsTheme = this.theme == 'theme-light' ? 'lightTheme' : 'darkTheme'
      this.chart = echarts.init(this.$el, echartsTheme)
      // this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    setOptions(data) {
      this.chart.setOption({
        xAxis: {
          type: 'category',
          data: data.xData,
          boundaryGap: false,
          // axisTick: {
          //   show: false
          // },
          // interval: 5,
          // axisLabel: {
          //   formatter: '{yyyy}-{MM}-{dd}',
          //   hideOverlap: true
          // }
        },
        grid: {
          top: '10%',
          left: '3%',
          right: '3%',
          bottom: '9%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          // axisPointer: {
          //   type: 'cross'
          // },
          padding: [5, 10]
        },
        toolbox: {
          right: 10,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
            // restore: {},
            saveAsImage: {}
          }
        },
        dataZoom: [
          {
            start: 0,
            end: 100
          },
          {
            type: 'inside'
          }
        ],
        yAxis: [
          {
            name: this.configData.yName + '/' + this.configData.unit,
            axisLine: {
              show: true
            },
            splitLine: {
              show: false
            },
            min: this.configData.energyType == 'electricityu' ? 215 : 0
          },
        ],
        legend: {
          // show: false,
        },
        series: [{
          name: '最小值',
          smooth: true,
          type: 'line',
          data: data.minData,
          symbol: 'none',
          animationDuration: 1500,
          animationEasing: 'cubicInOut',
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          lineStyle: {
            width: 3
          }
        },
        {
          name: '最大值',
          smooth: true,
          type: 'line',
          data: data.maxData,
          symbol: 'none',
          animationDuration: 1500,
          animationEasing: 'cubicInOut',
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          lineStyle: {
            width: 3
          }
        },
        {
          name: '平均值',
          smooth: true,
          type: 'line',
          data: data.avgData,
          symbol: 'none',
          animationDuration: 1500,
          animationEasing: 'cubicInOut',
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          lineStyle: {
            width: 3
          }
        }]
      })
    }
  }
}
</script>
