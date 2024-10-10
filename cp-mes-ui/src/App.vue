<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";

export default {
  name: "App",
  components: { ThemePicker },
  // metaInfo() {
  //   return {
  //     title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
  //     titleTemplate: title => {
  //       return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
  //     }
  //   }
  // }
  mounted() {
    let theme = localStorage.getItem('theme')
    document.getElementById('app').style = theme == 'theme-dark' ? 'background: #46576e;' : 'background: #3671e8;'
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
<style>
/* 去除百度地图标志 */
.BMap_cpyCtrl {
  display: none !important;
}

.anchorBL {
  display: none !important;
}
/* 树结构下拉框 */
.vue-treeselect--has-value .vue-treeselect__input {
  vertical-align: middle !important;
}
</style>
