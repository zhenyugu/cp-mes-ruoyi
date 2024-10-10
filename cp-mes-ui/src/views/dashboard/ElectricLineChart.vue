<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
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
      type: Array,
      default: () => {
        return [['2023-04-17 10:01:00',234.23], ['2023-04-17 12:30:10',12.98], ['2023-04-17 12:41:20', 89.16], ['2023-04-17 13:01:00',160.54], ['2023-04-17 13:16:00',68.23], ['2023-04-17 13:40:20', 381.91], ['2023-04-17 13:54:10',21.00], ['2023-04-17 14:12:30', 56.03], ['2023-04-17 14:23:00',167.05], ['2023-04-17 14:33:00', 5.62], ['2023-04-17 14:41:00', 221.06], ['2023-04-17 14:57:00', 123.60], ['2023-04-17 15:01:00', 29.23], ['2023-04-17 15:28:00',15.71]]
      }
      // required: true
    },
    configData: {
      type: Object,
      default: () => {
        return { yName: '电流', unit: 'A', energyType: 'electricityi'}
      }
    },
    xData: {
      type: Array,
      default: () => {
        return ['2023-04-17 10:01:00', '2023-04-17 12:30:10', '2023-04-17 12:41:20', '2023-04-17 13:01:00', '2023-04-17 13:16:00', '2023-04-17 13:40:20', '2023-04-17 13:54:10', '2023-04-17 14:12:30', '2023-04-17 14:23:00', '2023-04-17 14:33:00', '2023-04-17 14:41:00', '2023-04-17 14:57:00', '2023-04-17 15:01:00', '2023-04-17 15:28:00']
      }
    },
    dateRange: {
      type: Array,
      default: () => {
        return ['2023-10-11 00:00:00', '2023-10-17 23:59:59']
      }
    }

  },
  data() {
    return {
      chart: null,
      areaColor: ['rgba(84, 112, 198, 1)','rgba(84, 112, 198, 0.1)']
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
      this.areaColor = this.theme == 'theme-light' ? ['rgba(84, 112, 198, 1)','rgba(84, 112, 198, 0.1)'] : ['rgba(83, 82, 246, 0.3)','rgba(83, 82, 246, 0.1)']
      this.chart = echarts.init(this.$el, echartsTheme)
      this.setOptions(this.chartData)
    },
    setOptions(data) {
      this.chart.setOption({
        xAxis: {
          // data: this.xData,
          type: 'time',
          boundaryGap: false,
          // axisTick: {
          //   show: false
          // },
          min: this.dateRange[0],
          max: this.dateRange[1],
          // interval: 5,
          axisLabel: {
            formatter: '{yyyy}-{MM}-{dd} {HH}:{mm}:{ss}',
            hideOverlap: true
          }
        },
        grid: {
          top: '8%',
          left: '3%',
          right: '2%',
          bottom: '9%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          padding: [5, 10]
        },
        toolbox: {
          right: 10,
          feature: {
            dataZoom: {
              yAxisIndex: 'none'
            },
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
          show: false,
        },
        series: [{
          name: this.configData.yName,
          // smooth: true,
          type: 'line',
          data: data,
          symbol: 'none',
          sampling: 'lttb',
          // data: data.iData,
          // animationDuration: 1500,
          animationEasing: 'cubicInOut',
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: this.areaColor[0]
              },
              {
                offset: 1,
                color: this.areaColor[1]
              }
            ])
          }
        }]
      })
    }
  }
}
</script>
