<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
// require('echarts/theme/macarons') // echarts theme
import resize from '@/views/dashboard/mixins/resize'

const animationDuration = 1500

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
      default: '300px'
    },
    // yName: {
    //   type: String,
    //   default: 'kM·h'
    // },
    setInfo: {
      type: Object,
      default: () => {
        return {
          xName: '月',
          yName: 'kM·h',
          legendName: ['同期', '本期']
        }
      }
    },
    barData: {
      type:Object,
      default: () => {
        return {
          xData: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
          currentData:[79, 52, 200, 334, 390, 330, 220, 0, 21, 67, 234, 21],
          sameData: [23, 432, 123, 34, 9, 23, 220, 34, 21, 67, 234, 12]
        }
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
  watch: {
    'barData': {
      handler() {
        if(this.chart) {
          this.chart.dispose()
          this.chart = null
        }
        this.initChart()
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
  methods: {
    initChart() {
      let echartsTheme = this.theme == 'theme-light' ? 'lightTheme' : 'darkTheme'
      this.chart = echarts.init(this.$el, echartsTheme)

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        toolbox: {
          show: true,
          feature: {
            magicType: { show: true, type: ['line', 'bar'], title: '' },
            restore: { show: true, title: '' },
          }
        },
        grid: {
          top: '20%',
          left: '2%',
          right: '5%',
          bottom: '3%',
          containLabel: true
        },
        dataZoom: {
          show: true,
          type: 'inside'
        },
        legend: {
          data: this.setInfo.legendName
        },
        xAxis: [{
          type: 'category',
          data: this.barData.xData,
          name: this.setInfo.xName,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          // type: 'value',
          // name: 'kmh',
          // axisTick: {
          //   show: true
          // }
          type: 'value',
          name: this.setInfo.yName,
          position: 'left',
          alignTicks: true,
        }],
        series: [
          {
            name: this.setInfo.legendName[0],
            type: 'bar',
            // stack: 'vistors',
            barWidth: '30%',
            data: this.barData.currentData,
            animationDuration,
            itemStyle: {
              color: '',
            },
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
          },
          {
            name: this.setInfo.legendName[1],
            type: 'bar',
            // stack: 'vistors',
            barWidth: '30%',
            data: this.barData.sameData,
            animationDuration,
            itemStyle: {
              color: '',
            },
            markPoint: {
              data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
              ]
            },
          }
        ]
      })
    }
  }
}
</script>
