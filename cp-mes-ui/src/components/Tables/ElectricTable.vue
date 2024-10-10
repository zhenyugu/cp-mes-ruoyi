<template>
  <div class="table-container">
    <el-table class="currentTable" :data="tableData" height="100%" border style="width: 100%">
      <!-- <el-table-column prop="name" label="回路名称"> </el-table-column> -->
      <el-table-column prop="ts" label="采集时间" sortable v-if="type == 'origin'"> </el-table-column>
      <el-table-column prop="val" :label="configData.yName + '/' + configData.unit" v-if="type == 'origin'"> </el-table-column>

      <el-table-column prop="date" label="采集时间" sortable v-if="type == 'peak'"> </el-table-column>
      <el-table-column prop="min" :label="'最小' + configData.yName + '/' + configData.unit" v-if="type == 'peak'">
        <template slot-scope="scope">
          <span style="color: #91cc75;">{{scope.row.min}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="max" :label="'最大' + configData.yName + '/' + configData.unit" v-if="type == 'peak'">
        <template slot-scope="scope">
          <span style="color: #5470c6;">{{scope.row.max}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="ave" :label="'平均' + configData.yName + '/' + configData.unit" v-if="type == 'peak'"> </el-table-column>
      <!-- <el-table-column prop="Pb" label="Pb(kW·h)"> </el-table-column>
      <el-table-column prop="Pc" label="Pc(kW·h)"> </el-table-column>
      <el-table-column prop="P" label="P(kW·h)"> </el-table-column> -->
      <!-- <el-table-column prop="yoy" label="环比(%)">
        <template slot-scope="scope">
          <span :class="scope.row.yoy > 0 ? 'yoy-up' : 'yoy-down'">{{scope.row.yoy}}</span>
        </template>
      </el-table-column> -->
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // tableData: [
      //   {
      //     name: '一层',
      //     time: '2023-04-17 14:30:12',
      //     Pa: 73.12,
      //     Pb: 86.01,
      //     Pc: 179.38,
      //     P: 179.38,
      //   },
      //   {
      //     name: '101',
      //     time: '2023-04-17 14:52:51',
      //     Pa: 73.12,
      //     Pb: 86.01,
      //     Pc: 179.38,
      //     P: 179.38,
      //   },
      //   {
      //     name: '102',
      //     time: '2023-04-17 15:10:27',
      //     Pa: 162.05,
      //     Pb: 541.12,
      //     Pc: 79.05,
      //     P: 26.46,
      //   },
      // ]
    }
  },
  props: {
    type: {
      type: String,
      default: 'origin'
    },
    tableData: {
      type: Array,
      required: true
    },
    configData: {
      type: Object,
      default: () => {
        return {yName: '电流', unit: 'A', energyType: 'electricityi'}
      }
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
<style lang='scss'>
.table-container {
  .el-table .descending .sort-caret.descending {
    border-top-color: #fff;
  }
  .el-table .ascending .sort-caret.ascending {
    border-bottom-color: #fff;
  }
}
</style>
