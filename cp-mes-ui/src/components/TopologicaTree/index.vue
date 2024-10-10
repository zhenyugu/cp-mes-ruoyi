<template>
  <div class="tree-container">
    <el-tree
        :data="topologyOptions"
        :props="defaultProps"
        :expand-on-click-node="false"
        :filter-node-method="filterNode"
        ref="tree"
        node-key="id"
        :default-checked-keys="defaultSelect"
        default-expand-all
        highlight-current
        @node-click="handleNodeClick"
        :show-checkbox="showCheckbox"
        @check="handleNodeClick"
        check-strictly
      />
    <!-- <el-scrollbar height="100%">

    </el-scrollbar> -->
  </div>
</template>

<script>
import { topologyTreeSelect } from "@/api/system/itemTopology";
// import Treeselect from "@riophae/vue-treeselect";
// import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  // components: { Treeselect },
  props: {
    showCheckbox: {
      type: Boolean,
      default: false,
    },
    itemType: {
      type: String,
      default: 'building'
    }
  },
  data() {
    return {
      topologyOptions: [],
      defaultProps: {
        children: "children",
        label: "label",
      },
      defaultSelect: [],
    };
  },
  // watch: {
  //   defaultSelect: function (newVal, oldVal) {
  //     if (newVal) {
  //       this.$nextTick(() => {
  //         document
  //           .querySelector(".el-tree-node__children .el-tree-node__content")
  //           .click();
  //       });
  //     }
  //   },
  // },
  methods: {
    getTopologyTree() {
      let data = {
        itemType: this.itemType
      }
      topologyTreeSelect(data).then((response) => {
        this.topologyOptions = response.data;
        if (response.data && response.data.length > 0) {
          this.defaultSelect.push(this.topologyOptions[0].id);
          this.$nextTick(() => {
            this.$refs.tree.setCurrentKey(this.topologyOptions[0].id); //默认选中第一条
            this.handleNodeClick(this.topologyOptions[0]);// 默认点击第一个
          })
        }
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.$emit("selectAreaId",this.$refs.tree.getCheckedKeys());
      this.$emit("treeOptions",this.topologyOptions)
      this.$emit("selectItem",data)
    },
  },
  created() {
    this.getTopologyTree()
  },
};
</script>

<style lang="scss">
.tree-container {
  height: 100%;
  width: 100%;
  overflow: scroll;
  .el-tree {
    min-width: 360px;
  }
}
// .tree-container::-webkit-scrollbar{
//     width:6px;
//     height:6px;
//     background: transparent;
// }
// .tree-container::-webkit-scrollbar-track{
//     background: transparent;
//     border-radius:3px;
// }
// .tree-container::-webkit-scrollbar-thumb{
//     background: #dddee0;
//     border-radius:3px;
// }
// .tree-container::-webkit-scrollbar-thumb:hover{
//     background: #c7c9cc;
// }
// .tree-container::-webkit-scrollbar-corner{
//     background: transparent;
// }
</style>
