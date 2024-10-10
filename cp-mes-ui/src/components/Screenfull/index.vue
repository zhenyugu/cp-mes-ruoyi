<template>
  <div>
    <!-- <svg-icon :icon-class="isFullscreen?'exit-fullscreen':'fullscreen'" @click="click" /> -->
    <div style="height: 100%;display:flex;align-items:center;">
      <svg class="icon-fullscreen" @click="click" >
        <use :xlink:href="isFullscreen?`#icon-exit-fullscreen`:`#icon-fullscreen`"/>
      </svg>
    </div>
  </div>
</template>

<script>
import screenfull from 'screenfull'

export default {
  name: 'Screenfull',
  data() {
    return {
      isFullscreen: false
    }
  },
  mounted() {
    this.init()
  },
  beforeDestroy() {
    this.destroy()
  },
  methods: {
    click() {
      if (!screenfull.isEnabled) {
        this.$message({ message: '你的浏览器不支持全屏', type: 'warning' })
        return false
      }
      screenfull.toggle()
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen
    },
    init() {
      if (screenfull.isEnabled) {
        screenfull.on('change', this.change)
      }
    },
    destroy() {
      if (screenfull.isEnabled) {
        screenfull.off('change', this.change)
      }
    }
  }
}
</script>

<style scoped>
.icon-fullscreen {
  /* display: inline-block; */
  cursor: pointer;
  fill: var(--icon-color);;
  width: 20px;
  height: 20px;
}
</style>
