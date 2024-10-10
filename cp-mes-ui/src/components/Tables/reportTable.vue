<template>
  <div class="table-container">
    <el-table class="currentTable unchanged" :data="tableData" height="100%" border style="width: 100%"
              :header-cell-style="rowClass"
              :cell-style="{'text-align':'center'}">
      <el-table-column label="分类">
        <el-table-column label="" prop="type"></el-table-column>
        <el-table-column label="" prop="data">
          <template slot-scope="scope">
            <span v-if="scope.row.type == '用电量' ">{{scope.row.data}} kW.h</span>
            <span v-if="scope.row.type == '用水量' ">{{scope.row.data}} t</span>
            <span v-if="scope.row.type == '综合能耗' ">{{scope.row.data}} kgce</span>
            <span v-if="scope.row.type == '碳排放量' ">{{scope.row.data}} kg</span>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column prop="chain" label="环比(%)">
        <template slot-scope="scope">
          <span v-if="scope.row.chain != 0" :class="scope.row.chain > 0 ? 'yoy-up' : 'yoy-down'">{{scope.row.chain}}</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column prop="same" label="同比(%)">
        <template slot-scope="scope">
          <span v-if="scope.row.same != 0" :class="scope.row.same > 0 ? 'yoy-up' : 'yoy-down'">{{scope.row.same}}</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: 'reportTable',
  props: {
    chainData: {
      type: Array,
    },
  },
  data() {
    return {
      tableData: [
        {
          type: "用电量",
          data: 1756,
          chain: 35.23,
          same: 35.23
        },
        {
          type: '用水量',
          data: 2345,
          chain: -23.42,
          same: 35.23
        },
        {
          type: "综合能耗",
          data: 343,
          chain: 34.21,
          same: 35.23
        },
        {
          type: "碳排放量",
          data: 343,
          chain: 34.21,
          same: 35.23
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
    rowClass({ row, column,rowIndex,columnIndex}) {
      if(rowIndex===0) {
        // this.$nextTick(()=> {
        //   if(document.getElementsByClassName(column.id).length!==0) {
        //     document.getElementsByClassName(column.id)[0].setAttribute('rowSpan',2);
        //     return false
        //   }
        // })
        return {'background-color': '#3671e8','text-align':'center'}
      }
      if(rowIndex===1 && (columnIndex===0 || columnIndex===1)) {
        return {display:'none'}
      }
    },
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
