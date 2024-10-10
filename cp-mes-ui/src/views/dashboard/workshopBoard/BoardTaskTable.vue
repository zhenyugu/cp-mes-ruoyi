<template>
  <div class="task-container">
    <el-table
      :data="tableData"
      :height="'100%'"
      class="board-table"
      :row-class-name="tableRowClassName"
    >
      <el-table-column
        label="工序名称"
        align="center"
        prop="procedureName"
        show-overflow-tooltip
      />
      <el-table-column label="生产任务数" align="center" prop="value" />
      <el-table-column label="计划数" align="center" prop="plannedQuantity" />
      <el-table-column label="良品数" align="center" prop="goodQuantity" />
      <el-table-column label="不良品数" align="center" prop="defectQuantity" />
      <el-table-column label="未完成数" align="center" prop="unfinished" />
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  props: {
    tableData: {
      type: Array,
      default: () => {
        return [
          {
            name: "工序1",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序2",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序3",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序4",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序5",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序6",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序7",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
          {
            name: "工序8",
            count: 10,
            planCount: 20,
            ok: 30,
            ng: 40,
            unfinished: 50,
          },
        ];
      },
    },
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 === 0) {
        return "table-row-one";
      }
      return "table-row-two";
    },
  },
};
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
.board-table >>> .el-table__header-wrapper th,
.board-table >>> .el-table__fixed-header-wrapper th {
  color: #fff;
  background-color: #053f8b !important;
}
.board-table >>> th.el-table__cell.is-leaf,
.board-table >>> td.el-table__cell {
  border: 0;
}
.board-table >>> tr {
  background-color: transparent;
}
.board-table::before {
  height: 0;
}
.board-table >>> .el-table__body tr:hover > td.el-table__cell {
  background-color: #2f6c94 !important;
}
.board-table >>> .table-row-one {
  background-color: #022244 !important;
}
.board-table >>> .table-row-two {
  background-color: rgba(95, 152, 220, 0.2) !important;
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
    display: none !important; //隐藏滚动条
  }
}
</style>