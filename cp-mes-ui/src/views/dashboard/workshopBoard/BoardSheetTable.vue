<template>
  <div class="task-container">
    <el-table :data="tableData" :height="'100%'" class="board-table" :row-class-name="tableRowClassName">
      <el-table-column label="工单编号" align="center" prop="sheetNumber" width="180"/>
      <el-table-column label="产品名称" align="center" prop="productName" width="120" show-overflow-tooltip/>
      <el-table-column label="计划数" align="center" prop="plannedQuantity" width="80"/>
      <el-table-column label="客户" align="center" prop="clientName" width="80" />
      <el-table-column label="工单状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
                    <dict-tag :options="dict.type.sheet_status" :value="scope.row.status" />
                </template>
      </el-table-column>
      <el-table-column label="生产进度" align="center" prop="tasks">
        <template slot-scope="scope">
          <div class="step-list">
            <ProgressItemVue v-for="(item, index) in scope.row.tasks" :key="index" :task="item" :showLine="index + 1 < scope.row.tasks.length"/>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="createBy" width="100" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
    </el-table>
  </div>
</template>

<script>
import ProgressItemVue from './ProgressItem.vue';
export default {
  components: { ProgressItemVue },
  dicts: ['sheet_status'],
  data() {
    return {
      moniData: [
        {
          taskName: '工序1',
          status: '3'
        },
        {
          taskName: '工序1',
          status: '3'
        },
        {
          taskName: '工序1',
          status: '3'
        },
        {
          taskName: '工序1',
          status: '2'
        },
        {
          taskName: '工序1',
          status: '1'
        },
        {
          taskName: '工序1',
          status: '1'
        },
        {
          taskName: '工序1',
          status: '1'
        },
        {
          taskName: '工序1',
          status: '1'
        },
      ]
    }
  },
  props: {
    tableData: {
      type: Array,
      default: () => {
        return [
          {
            name: 'GD20240115162723630',
            status: '未开始',
            progress: 20,
            productNo: 'GD20240115162723630',
            productName: '产品1',
            productSpec: 'test',
            planCount: 100,
            count: 100,
          },
          {
            name: 'GD20240115162723630',
            status: '未开始',
            progress: 20,
            productNo: 'GD20240115162723630',
            productName: '产品1',
            productSpec: 'test',
            planCount: 100,
            count: 100,
          },
          {
            name: 'GD20240115162723630',
            status: '未开始',
            progress: 20,
            productNo: 'GD20240115162723630',
            productName: '产品1',
            productSpec: 'test',
            planCount: 100,
            count: 100,
          },
          {
            name: 'GD20240115162723630',
            status: '未开始',
            progress: 20,
            productNo: 'GD20240115162723630',
            productName: '产品1',
            productSpec: 'test',
            planCount: 100,
            count: 100,
          },
          {
            name: 'GD20240115162723630',
            status: '未开始',
            progress: 20,
            productNo: 'GD20240115162723630',
            productName: '产品1',
            productSpec: 'test',
            planCount: 100,
            count: 100,
          },
          {
            name: 'GD20240115162723630',
            status: '未开始',
            progress: 20,
            productNo: 'GD20240115162723630',
            productName: '产品1',
            productSpec: 'test',
            planCount: 100,
            count: 100,
          },
        ];
      }
    }
  },
  methods: {
    tableRowClassName({row, rowIndex}) {
      if(rowIndex % 2 === 0) {
        return 'table-row-one'
      }
      return 'table-row-two'
    }
  }
}
</script>

<style scoped>
.task-container {
  height: 100%;
  width: 100%;
}
.board-table {
  color: #fff;
  background-color: #022244;
  border: 0px;
}
.board-table>>>.el-table__header-wrapper th, .board-table>>>.el-table__fixed-header-wrapper th  {
  color: #fff;
  background-color: #053f8b !important;
}
.board-table>>> th.el-table__cell.is-leaf, .board-table>>> td.el-table__cell {
  border: 0;
  padding: 4px 0;
}
.board-table>>>tr {
  background-color: transparent;
}
.board-table::before {
  height: 0;
}
.board-table>>>.el-table__body tr:hover > td.el-table__cell {
  background-color: transparent !important;
  /* background-color: #2f6c94 !important; */
}
.board-table>>>.table-row-one {
  background-color: #022244 !important;
}
.board-table>>>.table-row-two {
  background-color: rgba(95, 152, 220, 0.2) !important;
}
.step-list {
  width: 100%;
  display: flex;
  align-items: center;
  overflow: hidden;
  overflow-x: auto;
}
.board-table >>> colgroup col[name="gutter"] {
  display: none;
  width: 0;
}
.board-table >>> th.gutter {
  display: none;
  width: 0;
}
.board-table>>>.el-table__body {
  width: 100% !important;
}
</style>
<style lang="scss" scoped>
.board-table {
  &.el-table--scrollable-y ::-webkit-scrollbar {
    // display: none !important; //隐藏滚动条
    width: 0px;
  }
}
</style>