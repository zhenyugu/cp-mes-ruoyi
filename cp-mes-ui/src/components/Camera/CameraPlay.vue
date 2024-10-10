<template>
  <div class="camera-window">
    <div class="camera-title">{{ video.cameraName }}</div>
    <div ref="videoBox" class="video-box" :id="code"></div>
  </div>
</template>
<script>
import EZUIKit from "ezuikit-js";
import { getUrlBySerialNumber } from "@/api/system/cameraConfig";
export default {
  // 单个视频播放组件
  name: "cameraPlay",
  props: ["code"],
  data() {
    return {
      player: null,
      video: {
        cameraName: "",
      },
    };
  },
  methods: {
    getMonitors() {
      getUrlBySerialNumber(this.code)
        .then((response) => {
          this.video = response.data;
        })
        .then(() => {
          this.initVideo();
        });
    },
    initVideo() {
      console.log("mounted 组件挂载完毕状态=======》.");
      this.player = new EZUIKit.EZUIKitPlayer({
        id: this.code, // 视频容器ID
        accessToken: this.video.cameraToken,
        url: this.video.url,
        // simple - 极简版; pcLive-pc直播；pcRec-pc回放；	mobileLive-移动端直播；mobileRec-移动端回放;security - 安防版;voice-语音版;
        template: 'e92ab948240a43579973104642a265b4', //模板
        //plugin: ['talk'], // 加载插件，talk-对讲
        audio: 0,
        width: this.$refs.videoBox.offsetWidth,
        height: this.$refs.videoBox.offsetHeight,
      });
    },
  },
  mounted() {
    this.getMonitors();
  },
  created() {
  },
  beforeDestroy() {
    if (this.player) {
      this.player.stop();
      this.player = null
    }
  },
};
</script>
<style scoped>
.camera-window {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.video-box {
  height: calc(100% - 40px);
  width: 100%;
}
.camera-title {
  height: 40px;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.05);
  color: #fff;
  line-height: 40px;
  padding: 0 12px;
}
</style>
