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
      default: '300px'
    },
    pieData: {
      type: Array,
      default: function () {
        return [
          { value: 0, name: "正常设备" },
          { value: 0, name: "报警设备" },
          { value: 0, name: "离线设备" },
        ];
      },
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

      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          // formatter: '{a} <br/>{b} : {c} ({d}%)',
          appendToBody: true,
        },
        // legend: {
        //   left: 'center',
        //   bottom: '10',
        //   data: ['Industries', 'Technology', 'Forex', 'Gold', 'Forecasts']
        // },
        series: [
          {
            // name: 'WEEKLY WRITE ARTICLES',
            type: 'pie',
            // roseType: 'radius',
            radius: ['40%', '70%'],
            // avoidLabelOverlap: false,
            // radius: [15, 95],
            center: ['40%', '50%'],
            data:this.pieData,
            // data: [
            //   { value: 345, name: '正常设备', itemStyle: {color: '#1890ff'} },
            //   { value: 0, name: '报警设备', itemStyle: {color: '#ff4949'} },
            //   { value: 100, name: '离线设备', itemStyle: {color: '#ffba00'} },
            //   // { value: 100, name: 'Gold' },
            //   // { value: 59, name: 'Forecasts' }
            // ],
            // animationEasing: 'cubicInOut',
            animationDuration: 1500,
            labelLine: {
              show: false
            }
          }
        ]
      })
    }
  }
}
</script>
