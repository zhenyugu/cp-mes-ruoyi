<template>
  <div class="table-container">
    <el-table class="currentTable" :data="tableData" :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}" height="100%" border style="width: 100%">
      <el-table-column prop="date" label="时间"> </el-table-column>
      <el-table-column prop="unit" label="单位"> </el-table-column>
      <el-table-column prop="realEnergy" label="实际能耗"> </el-table-column>
      <el-table-column prop="chainEnergy" label="能耗环比"> </el-table-column>
      <el-table-column prop="yoyEnergy" label="能耗同比"> </el-table-column>
      <el-table-column prop="quotaAverage" label="日均定额"> </el-table-column>
      <el-table-column prop="quotaPercent" label="定额完成百分比" min-width="100"> </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props: {
    chainData: {
      type: Array,
    },
  },
  data() {
    return {
      tableData: [
        {
          date: '2023-9-10',
          unit: 'kW.h',
          realEnergy: 1756,
          chainEnergy: 453,
          yoyEnergy: 3523,
          quotaAverage: 453,
          quotaPercent: "35%"
        },
      ],
    }
  },
  watch: {
    'chainData': {
      handler() {
        this.tableData = this.chainData;
      },
      deep: true,
      immediate: true
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
    //   //overflow-y: scroll;
    //   //scrollbar-color: #bebebf transparent;
    //   //scrollbar-width: thin;
    // }
  }
}
</style>
<style>

</style>
