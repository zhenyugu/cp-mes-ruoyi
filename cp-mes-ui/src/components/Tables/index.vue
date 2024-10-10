<template>
  <div class="table-container">
    <el-table class="currentTable" :data="tableData" height="100%" border style="width: 100%">
      <el-table-column prop="month" label="本期时间"> </el-table-column>
      <el-table-column prop="currentPeriod" :label="'本期能耗(' + unit + ')'"> </el-table-column>
      <el-table-column prop="correspondingPeriod" :label="'同比能耗(' + unit + ')'"> </el-table-column>
      <el-table-column prop="yoy" label="同比(%)" >
        <template slot-scope="scope">
          <span :class="scope.row.yoy > 0 ? 'yoy-up' : 'yoy-down'" v-if="scope.row.yoy!==null">{{scope.row.yoy}}</span>
          <span v-if="scope.row.yoy===null">--</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props: {
    tableData: {
      type: Array
    },
    unit: {
      type: String,
      default: 'kW.h'
    }
  },
  data() {
    return {
      tableData1: [
        {
          time: '01',
          currentEnergy: 122,
          sameEnergy: 324,
          yoy: 34.23
        },
        {
          time: '02',
          currentEnergy: 3435,
          sameEnergy: 1232,
          yoy: -12.56
        },
        {
          time: '03',
          currentEnergy: 788,
          sameEnergy: 678,
          yoy: 34.66
        },
        {
          time: '04',
          currentEnergy: 3455,
          sameEnergy: 2342,
          yoy: -12.22
        },
        {
          time: '05',
          currentEnergy: 4455,
          sameEnergy: 1234,
          yoy: 34.76
        },
        {
          time: '06',
          currentEnergy: 2314,
          sameEnergy: 421,
          yoy: -75.41
        },
        {
          time: '07',
          currentEnergy: 787,
          sameEnergy: 212,
          yoy: 54.12
        },
        {
          time: '08',
          currentEnergy: 5311,
          sameEnergy: 3452,
          yoy: -34.44
        },
        {
          time: '09',
          currentEnergy: 2344,
          sameEnergy: 3221,
          yoy: 12.23
        },
        {
          time: '10',
          currentEnergy: 4655,
          sameEnergy: 2132,
          yoy: -21.23
        },
        {
          time: '11',
          currentEnergy: 3453,
          sameEnergy: 1233,
          yoy: 52.31
        },
        {
          time: '12',
          currentEnergy: 2354,
          sameEnergy: 1234,
          yoy: -34.21
        },
      ]
    }
  },
  methods: {

  }
};
</script>

<style lang="scss" scoped>
.table-container {
  height: 100%;
  width: 100%;

  .currentTable {
    .yoy-down::after {
      content: "↓";
      position: relative;
      left: 5px;
      top: -2px;
      color: #49a798;
    }
    .yoy-up::after {
      content: "↑";
      position: relative;
      left: 5px;
      top: -2px;
      color: #dd65a1;
    }

    // 滚动条的宽度
    // ::v-deep .el-table__body-wrapper::-webkit-scrollbar {
    //   width: 6px; // 横向滚动条
    //   height: 6px; // 纵向滚动条 必写
    // }
    // // 滚动条的滑块
    // ::v-deep .el-table__body-wrapper::-webkit-scrollbar-thumb {
    //   background-color: #ddd;
    //   border-radius: 3px;
    // }

    // 去除滚动条上方多余显示
    ::v-deep colgroup col[name='gutter']{
      // display: none;
      width: 6px !important;
    }
    ::v-deep .el-table__body{
      width: 100% !important;
    }
    // Firefox滚动条样式设置
    // ::v-deep .el-table__body-wrapper {
    //   overflow-y: scroll;
    //   scrollbar-color: #bebebf transparent;
    //   scrollbar-width: thin;
    // }
  }
}
</style>
<style>

</style>
