 <template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
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
          xName: '日',
          yName: '个',
          legendName: ['未开始', '已结束','执行中']
        }
      }
    },
    xData: {
      type: Array,
      default: () => {
        /* return ['佛山1', '佛山2', '佛山3', '佛山4', '佛山5', '佛山6', '佛山7', '佛山8'] */
        return ['1-17','1-18', '1-19', '1-20', '1-21', '1-22', '1-23']
      }
    },
    yData: {
      type: Object,
      default: () => {
        return {
          notStart:[3, 4, 5, 4, 3, 5, 6, 3],
          finish: [1, 3, 4, 4, 3, 4, 5, 2],
          loading: [2, 1, 1, 0, 0, 1, 1, 1]
        }
      }
    },
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
    'yData': {
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
          bottom: '0%',
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
          data: this.xData,
          name: this.setInfo.xName,
          nameLocation: 'end',
          nameGap: 8,
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
            
          symbolSize: 8,
            name: this.setInfo.legendName[0],
            type: 'bar',
            stack: 'vistors',
            barWidth: '30%',
            data: this.yData.notStart,
            animationDuration,
            itemStyle: {
              color: '',
            },
            
          },
          {
            name: this.setInfo.legendName[1],
            type: 'bar',
            stack: 'vistors',
            barWidth: '30%',
            data: this.yData.finish,
            animationDuration,
            itemStyle: {
              color: 'rgb(34, 195, 170)',
            },
          },
          {
            
            name: this.setInfo.legendName[2],
            type: 'bar',
            stack: 'vistors',
            barWidth: '30%',
            data: this.yData.loading,
            animationDuration,
            itemStyle: {
              color: 'rgb(238, 70, 108)',
            },
            
          }
        ]
      })
    }
  }
}
</script>
