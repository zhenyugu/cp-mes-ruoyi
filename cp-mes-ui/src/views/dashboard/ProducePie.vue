<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
import resize from '@/views/dashboard/mixins/resize'

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
    unit: {
      type: String,
      default: '个'
    },
    pieData: {
      type: Array,
      default: function () {
        return [
          { value: 23, name: "下料" },
          { value: 11, name: "下料检验" },
          { value: 5, name: "来料检验" },
          { value: 0, name: "领料确认" },
          { value: 0, name: "出货" },
        ];
      },
    },
  },
  data() {
    return {
      chart: null,
      total: 0
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
    'pieData': {
      handler() {
        this.$nextTick(() => {
          if (!this.chart) {
            return;
          }
          this.chart.dispose();
          this.chart = null;
          this.initChart();
        });
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

      // 计算总值
      let total = 0
      this.pieData.forEach(item => {
        total = total + item.value
      })
      this.total = total

      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          appendToBody: true,
          formatter: (params) => {
            return params.seriesName + '<br/>' + params.marker + params.data.name + '： <strong>' + params.data.value + '</strong> ' + this.unit
          }
        },
        legend: {
          bottom: '0%',
          // top: '80%',
          formatter: (param) => {
            let item = this.pieData.find(c => c.name == param)
            if(!item) return ''
            let rate = this.total == 0 ? 0 : item.value / this.total * 100
            return `{name|${param}}{count|${item.value}` + this.unit + `}{percent|${rate.toFixed(1)}%}`
          },
          itemWidth: 14,
          itemGap: 15,
          textStyle: {
            rich: {
              name: {
                // color: '#9da0a2',
                width: 70
              },
              count: {
                // color: '#fff',
                width: 60
              },
              percent: {
                // color: '#fff',
                width: 40
              }
            },
          }
        },
        series: [
          {
            name: '分项占比',
            type: 'pie',
            radius: ['35%', '50%'],
            center: ['50%', '38%'],
            data:this.pieData,
            animationDuration: 1500,
            label: {
              // show: false,
              // formatter: '{b}:{d}%'
            },
            emphasis: {
              label: {
                fontWeight: 'bold',
                fontSize: 12
              }
            }
          }
        ]
      })
    }
  }
}
</script>
