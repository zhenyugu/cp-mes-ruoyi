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
      type: Object,
      default: () => {
        return {
          yesterdayData: [79, 52, 200, 334, 390, 330, 220, 65, 21, 67, 234, 21, 89, 56],
          todayData: [23, 456, 123, 32, 87, 143, 220, 0, 67, 45, 21, 4, 289, 145]
        }
      }
      // required: true
    },
    yName: {
      type: String,
      default: 'kW'
    },
    xName: {
      type: String,
      default: '时'
    },
    xData: {
      type: Array,
      default: () => {
        return ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00']
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
      this.setOptions(this.chartData)
    },
    setOptions({ yesterdayData, todayData } = {}) {
      this.chart.setOption({
        xAxis: {
          name: this.xName,
          data: this.xData,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          top: '15%',
          left: '2%',
          right: '5%',
          bottom: '3%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        toolbox: {
          show: true,
          feature: {
            magicType: { show: true, type: ['line', 'bar'], title: '' },
            // restore: { show: true, title: '' },
          }
        },
        dataZoom: {
          show: true,
          type: 'inside'
        },
        yAxis: {
          name: this.yName,
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ['昨日', '当日']
        },
        series: [{
          name: '昨日',
          // itemStyle: {
          //   color: '#FF005A',
          // },
          // lineStyle: {
          //   color: '#FF005A',
          //   width: 2
          // },
          smooth: true,
          type: 'line',
          data: yesterdayData,
          animationDuration: 1500,
          animationEasing: 'cubicInOut',
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          markLine: {
            data: [{ type: 'average', name: 'Avg' }]
          }
        },
        {
          name: '当日',
          smooth: true,
          type: 'line',
          // itemStyle: {
          //   color: '#3888fa',
          // },
          // lineStyle: {
          //   color: '#3888fa',
          //   width: 2
          // },
          data: todayData,
          animationDuration: 1500,
          animationEasing: 'quadraticOut',
          markPoint: {
            data: [
              { type: 'max', name: 'Max' },
              { type: 'min', name: 'Min' }
            ]
          },
          markLine: {
            data: [{ type: 'average', name: 'Avg' }]
          }
        }]
      })
    }
  }
}
</script>
