<template>
  <div class="table-container">
    <el-table class="currentTable" :data="tableData" height="100%" border style="width: 100%" row-key="id" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column prop="label" label="能源节点"> </el-table-column>
      <el-table-column prop="currentConsumption" :label="'当前支路能耗(' + unit + ')'">
        <template slot-scope="scope">
          {{scope.row.currentConsumption ? scope.row.currentConsumption: '--'}}
        </template>
      </el-table-column>
      <el-table-column prop="lowerConsumption" :label="'下级支路能耗合计(' + unit + ')'">
        <template slot-scope="scope">
          {{scope.row.lowerConsumption ? scope.row.lowerConsumption: '--'}}
        </template>
      </el-table-column>
      <el-table-column prop="difference" :label="'上级支路和下级支路能耗合计的差值(' + unit + ')'">
        <template slot-scope="scope">
          {{scope.row.difference ? scope.row.difference: '--'}}
        </template>
      </el-table-column>
      <el-table-column prop="percentage" label="相差百分比(%)">
        <template slot-scope="scope">
          <span v-if="!scope.row.percentage">--</span>
          <span v-else :class="scope.row.percentage > 0 ? 'yoy-up' : 'yoy-down'">{{scope.row.percentage}}</span>
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
          id: '1',
          label: '一层',
          currentConsumption: 1312,
          lowerConsumption: 2343,
          difference: 1243,
          percentage: 35.23,
          children: [
            {
              id: '2',
              label: '101',
              currentConsumption: 1334,
              lowerConsumption: 3455,
              difference: 1213,
              percentage: 23.21,

            },
            {
              id: '3',
              label: '102',
              currentConsumption: 3445,
              lowerConsumption: 2312,
              difference: 1532,
              percentage: 56.41,

            },
          ]
        },
        // {
        //   id: '2',
        //   time: 'test2',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23,
        //   children: [
        //     {
        //       id: '30',
        //       time: 'test2',
        //       currentEnergy: 324,
        //       sameEnergy: 65,
        //       addEnergy: 89,
        //       yoy: -1.23
        //     },
        //     {
        //       id: '31',
        //       time: 'test3',
        //       currentEnergy: 453,
        //       sameEnergy: 45,
        //       addEnergy: 234,
        //       yoy: 56.23
        //     },
        //     {
        //       id: '32',
        //       time: 'test4',
        //       currentEnergy: 45,
        //       sameEnergy: 345,
        //       addEnergy: 2432,
        //       yoy: -45.23
        //     },
        //     {
        //       id: '33',
        //       time: 'test5',
        //       currentEnergy: 453,
        //       sameEnergy: 34635,
        //       addEnergy: 4353,
        //       yoy: 435.23
        //     },
        //     {
        //       id: '34',
        //       time: 'test6',
        //       currentEnergy: 3453,
        //       sameEnergy: 3456,
        //       addEnergy: 23,
        //       yoy: -234.23
        //     },
        //   ]
        // },
        // {
        //   id: '3',
        //   time: 'test3',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: 35.23
        // },
        // {
        //   id: '4',
        //   time: 'test4',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23
        // },
        // {
        //   id: '5',
        //   time: 'test5',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: 35.23
        // },
        // {
        //   id: '6',
        //   time: 'test6',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23
        // },
        // {
        //   id: '7',
        //   time: 'test7',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   yoy: 35.23,
        //   children: [
        //     {
        //       id: '25',
        //       time: 'test2',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //     {
        //       id: '26',
        //       time: 'test3',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: 35.23
        //     },
        //     {
        //       id: '27',
        //       time: 'test4',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //     {
        //       id: '28',
        //       time: 'test5',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: 35.23
        //     },
        //     {
        //       id: '29',
        //       time: 'test6',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //   ]
        // },
        // {
        //   id: '8',
        //   time: 'test8',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23
        // },
        // {
        //   id: '9',
        //   time: 'test9',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: 35.23,
        //   children: [
        //     {
        //       id: '18',
        //       time: 'test6',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //     {
        //       id: '19',
        //       time: 'test7',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       yoy: 35.23
        //     },
        //     {
        //       id: '20',
        //       time: 'test8',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //     {
        //       id: '21',
        //       time: 'test9',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: 35.23
        //     },
        //     {
        //       id: '22',
        //       time: 'test10',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //     {
        //       id: '23',
        //       time: 'test11',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: 35.23
        //     },
        //     {
        //       id: '24',
        //       time: 'test12',
        //       currentEnergy: 1312,
        //       sameEnergy: 2343,
        //       addEnergy: 1243,
        //       yoy: -35.23
        //     },
        //   ]
        // },
        // {
        //   id: '10',
        //   time: 'test10',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23
        // },
        // {
        //   id: '11',
        //   time: 'test11',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: 35.23
        // },
        // {
        //   id: '12',
        //   time: 'test12',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23
        // },
        // {
        //   id: '13',
        //   time: 'test1',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: 35.23
        // },
        // {
        //   id: '14',
        //   time: 'test2',
        //   currentEnergy: 1312,
        //   sameEnergy: 2343,
        //   addEnergy: 1243,
        //   yoy: -35.23
        // },
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
