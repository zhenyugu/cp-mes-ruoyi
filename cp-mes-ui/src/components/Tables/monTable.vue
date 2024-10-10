<template>
  <div class="table-container">
    <el-table class="currentTable" :data="tableData" height="100%" border style="width: 100%">
      <el-table-column prop="area" label="能源节点"> </el-table-column>
      <el-table-column prop="currentEnergy" :label="nowTitle"></el-table-column>
      <el-table-column prop="sameEnergy" :label="lastTitle"> </el-table-column>
      <el-table-column prop="addEnergy" :label="addValue"> </el-table-column>
      <el-table-column prop="chain" label="环比(%)">
        <template slot-scope="scope">
          <span v-if="scope.row.chain != 0" :class="scope.row.chain > 0 ? 'yoy-up' : 'yoy-down'">{{scope.row.chain}}</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  props: {
    chainData: {
      type: Array,
    },
    nowTitle:{
      default: "当日用能(kW·h)"
    },
    lastTitle:{
      default: "昨日用能(kW·h)"
    },
    addValue: {
      default: "增加值(kW·h)"
    }
  },
  data() {
    return {
      tableData: [
        {
          area: '一层',
          currentEnergy: 2445,
          sameEnergy: 1756,
          addEnergy: 453,
          chain: 35.23
        },
        {
          area: '102',
          currentEnergy: 4546,
          sameEnergy: 2345,
          addEnergy: 1243,
          chain: -23.42
        },
        {
          area: '101',
          currentEnergy: 1233,
          sameEnergy: 343,
          addEnergy: 908,
          chain: 34.21
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
    //   overflow-y: scroll;
    //   scrollbar-color: #bebebf transparent;
    //   scrollbar-width: thin;
    // }
  }
}
</style>
<style>

</style>
