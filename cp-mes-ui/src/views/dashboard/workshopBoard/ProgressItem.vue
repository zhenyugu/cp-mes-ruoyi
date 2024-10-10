<template>
  <div class="progress-item">
    <div class="progress-item-left" @click="handleClick">
      <div class="item-circle" :class="circleClassName(task.procedureStatus)">
        <i :class="handleStatus(task.procedureStatus)"></i>
      </div>
      <div class="item-text">{{task.procedureName}}</div>
    </div>
    <div v-if="showLine" class="progress-item-line"></div>
  </div>
</template>

<script>
export default {
  props: {
    showLine: {
      type: Boolean,
      default: true
    },
    task: {
      type: Object,
      default: () => {
        return {
          taskName: '工序1',
          status: '1'
        }
      }
    }
  },
  methods: {
    handleClick(){
      this.$router.push({path:'/produce/jobBooking'})
    },
    handleStatus(status) {
      switch (status) {
        case '0':
          return 'el-icon-minus'
        case '1':
          return 'el-icon-more'
        case '2':
          return 'el-icon-check'
        default:
          return 'el-icon-minus'
      }
    },
    circleClassName(status) {
      switch(status) {
        case '0':
          return 'status-circle-1'
        case '1':
          return 'status-circle-2'
        case '2':
          return 'status-circle-3'
        default:
          return 'status-circle-1'
      }
    }
  }
}
</script>

<style scoped>
.progress-item {
  display: flex;
  align-items: center;
}
.progress-item-left {
  text-align: center;
  cursor: pointer;
}
.progress-item-line {
  height: 4px;
  width: 16px;
  background-color: #fff;
  border-radius: 10px;
  margin: 0 6px 20px;
}
.item-circle {
  height: 30px;
  width: 30px;
  border: 4px solid #fff;
  border-radius: 50%;
  margin: 0 auto;
}
.item-text {
  color: #fff;
  font-size: 12px;
  word-break: break-all;
  white-space: nowrap;
}
.status-circle-1 {
  color: #fff;
  border-color: #fff;
}
.status-circle-2 {
  color: #2a84fa;
  border-color: #2a84fa;
}
.status-circle-3 {
  color: #7be9f1;
  border-color: #7be9f1;
}
</style>