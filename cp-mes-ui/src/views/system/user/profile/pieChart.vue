<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
// require('echarts/theme/macarons') // echarts theme
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
      default: '100%'
    },
    residue: {
      type: Object,
      default: () => {
        return {
          residueName: '工单数量',
          residueValue: '110',
          residueClass: '#3671e8',
        }
      }
    },
    pieData: {
      type: Array,
      default: function () {
        return [
          { value: 0, name: "进行中" },
          { value: 0, name: "已完成" },
          { value: 0, name: "已取消" },
        ];
      },
    },
    itemTitle: {
      type: String,
      default: '时间'
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
        // color:['#5470c6','#15C2D9'],//修改图表颜色1
        //graphic控制环形中间出现字，其中有两个对象，就是两行字
        graphic: [{
          type: 'text',//控制内容为文本文字
          left: 'center',
          top: '40%',//调整距离盒子高处的位置
          style: {
            fill: this.residue.residueClass,//控制字体颜色
            text: this.residue.residueName,//控制第一行字显示内容
            fontSize: '22px',
          }
        }, {
          type: 'text',
          left: 'center',
          top: '60%',
          z: 10,
          style: {
            text: this.residue.residueValue,
            // font: 'Microsoft YaHei',
            fontSize: '20px',
            fill: this.residue.residueClass,
          }
        }],
        series: [
          {
            name: this.itemTitle,
            type: 'pie',
            // roseType: 'radius',
            radius: ['60%', '75%'],//在此处控制环的大小，第一个数据为内环，第二个为外环
            // avoidLabelOverlap: false,
            // radius: [15, 95],
            itemStyle: {
              borderRadius: 5,
              // borderColor: '#fff',
              // borderWidth: 2
            },
            // label: {
            //   show: false,
            //   position: 'center'
            // },
            center: ['50%', '50%'],
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
            //设置提示线
            label: {
              position: 'outer',
              // alignTo: 'labelLine',
              bleedMargin: 5,
              formatter: '{b}: {c}'
            },
          }
        ]
      })
    }
  }
}
</script>
