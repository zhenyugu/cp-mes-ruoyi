<template>
  <div id="container" @click="onMouseClick">
    <div class="progress-box" id="progress" v-if="isShow">
      <div id="progress-outer" class="progress-outer">
        <div id="progress-inner" class="progress-inner">{{percentage}}</div>
      </div>
    </div>
    <div id="dialog">
      <div class="close-icon" @click="hiddenDialog">×</div>
      <div style="padding:5px 10px;font-size: 20px;">{{dialogData.name}}</div>
      <div style="width: 80%;margin: 20px auto;">今日用电：{{dialogData.energy}} kW.h</div>
      <div style="width: 80%;margin: 20px auto;">今日用水：{{dialogData.water}} t</div>
    </div>
    <div class="back-btn" @click="hiddenDialog">
      <svg t="1698052353147" class="icon" 
        viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="20477" width="32" height="32"><path d="M94.9504 732.13952c-18.84672 0-29.85472-31.49824-29.85472-31.49824V254.93504l509.77792 477.55776c-0.01536 0-370.78016-0.25088-479.9232-0.35328z m766.68928 32.21504c-69.64224 69.64224-159.3088 63.70304-159.3088 63.70304s58.57792-125.83936 63.71328-223.01696c3.45088-65.36704-35.78368-107.89888-63.71328-127.45216-38.86592-27.2384-96.74752-32.896-159.32416 0-78.56128 41.27744-159.3088 127.45216-159.3088 127.45216L192.53248 382.0288S338.47808 264.11008 479.2832 222.71488c198.45632-58.36288 343.23968 15.88224 414.21312 127.44192 99.1232 155.776 81.41824 300.88704-31.85664 414.19776z" fill="#ffffff" p-id="20478"></path></svg>
      <div style="text-align:center;font-size: 12px;margin-top: -8px;">还原</div>
    </div>
  </div>
</template>

<script>
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import { RGBELoader } from "three/examples/jsm/loaders/RGBELoader.js";
import { MTLLoader } from "three/examples/jsm/loaders/MTLLoader.js";
import { OBJLoader } from "three/examples/jsm/loaders/OBJLoader.js";
import { EffectComposer } from "three/examples/jsm/postprocessing/EffectComposer.js"
import { RenderPass } from "three/examples/jsm/postprocessing/RenderPass.js"
import { OutlinePass } from "three/examples/jsm/postprocessing/OutlinePass.js"
import { ShaderPass } from "three/examples/jsm/postprocessing/ShaderPass.js"
import { FXAAShader } from "three/examples/jsm/shaders/FXAAShader.js"
import { GammaCorrectionShader } from "three/examples/jsm/shaders/GammaCorrectionShader.js"
import { CSS2DRenderer, CSS2DObject } from "three/examples/jsm/renderers/CSS2DRenderer";
import {debounce} from "@/utils/index"
import TWEEN from "@tweenjs/tween.js"
// THREE.ColorManagement.enabled = false;
let camera, scene, renderer, mesh, controls, cone, coneTwo,composer,outlinePass,renderPass, labelRenderer, dialogLabel;
let mouse = new THREE.Vector2()
let raycaster = new THREE.Raycaster()

export default {
  name: "Fourth",
  components: {},
  data() {
    return {
      // theta: 0,
      // r: 5,
      percentage: '0%',
      isShow: true,
      coneHeight: false,
      cameraInit: {
        x: 700,
        y: 0,
        z: 1400
      },
      controlInit: {
        x: 0,
        y: 0,
        z: 0
      },
      domHeight: 0,
      domWidth: 0,
      dialogData: {
        name: 'A栋',
        water: 12,
        energy: 36
      }
    };
  },
  mounted() {
    this.getDomSize()
    this.init();
    window.onresize = () => {
      this.onWindowResize();
    };
    // window.addEventListener("click", this.onMouseClick)
    window.addEventListener("mousemove", this.onMouseMove)
  },
  methods: {
    getDomSize() {
      let dom = document.getElementById("build-model")
      if(!dom) {
        return
      }
      this.domHeight = dom.clientHeight
      this.domWidth = dom.clientWidth
    },
    init() {
      let that = this
      let container = document.getElementById("container");
      // 创建场景
      scene = new THREE.Scene();
      // 设置场景背景
      scene.background = new THREE.CubeTextureLoader()
        .setPath(`/static/images/`)
        .load(['right.jpg', 'left.jpg', 'top.jpg', 'bottom.jpg', 'front.jpg', 'back.jpg'])

      // 创建相机
      camera = new THREE.PerspectiveCamera(
        40,
        that.domWidth / that.domHeight,
        0.1,
        5000
      );
      camera.position.set(this.cameraInit.x, this.cameraInit.y, this.cameraInit.z);

      const ambientLight = new THREE.AmbientLight(0xffffff); // 环境光
      scene.add(ambientLight);

      // 平面
      let geometry = new THREE.CircleGeometry( 5000 ) ;
      let material = new THREE.MeshBasicMaterial( {color: '#f6f6f6', side: THREE.DoubleSide} );
      let plane = new THREE.Mesh( geometry, material );
      plane.rotateX(Math.PI/2);
      plane.position.set(0, -201, 0)
      // scene.add( plane );

      // 锥体
      let conegeo = new THREE.ConeGeometry(30, 40, 6)
      let conemater = new THREE.MeshLambertMaterial({
        color: '#175aa7',
        transparent: true,
        opacity: 0.8
      })
      cone = new THREE.Mesh(conegeo, conemater)
      cone.rotateX(Math.PI)
      cone.position.set(-300, 420, 150)
      cone.name = 'cone'
      scene.add(cone)
      coneTwo = new THREE.Mesh(conegeo, conemater)
      coneTwo.rotateX(Math.PI)
      coneTwo.position.set(300, 420, -160)
      coneTwo.name = 'coneTwo'
      scene.add(coneTwo)



      // const ambientLight = new THREE.AmbientLight(0xffffff, 0.2);
      // this.scene.add(ambientLight);

      // const pointLight = new THREE.PointLight(0xffffff, 0.6);
      // this.scene.add(this.camera);
      // this.camera.add(pointLight);

      // 创建辅助坐标轴
      let axes = new THREE.AxesHelper(2000);
      axes.position.set(0, -200, 0)
      // scene.add(axes);

      // 添加rgbe加载器
      // let hdrLoader = new RGBELoader().setPath('textures/equirectangular/')
      // hdrLoader.load('royal_esplanade_1k.hdr', (texture) => {
      //   texture.mapping = THREE.EquirectangularReflectionMapping
      //   this.scene.background = texture
      //   this.scene.environment = texture
      // })

      const mtlLoader = new MTLLoader();
      mtlLoader.setPath(`/static/obj/exampleOne/`);
      mtlLoader.load("file.mtl", function (materials) {
        materials.preload();

        const objLoader = new OBJLoader();
        objLoader.setMaterials(materials);
        objLoader.setPath(`/static/obj/exampleOne/`);
        objLoader.load(
          "file.obj",
          function (object) {
            that.isShow = false
            object.scale.set(0.01, 0.01, 0.01);
            object.position.set(0, -200, 0)
            scene.add(object);
          },
          function (xhr) {
            that.percentage  = Math.round((xhr.loaded / xhr.total) * 100, 2) + '%'
            document.getElementById('progress-inner').style.width = that.percentage
          }
        );
      });

      // 创建渲染器
      renderer = new THREE.WebGLRenderer({
        antialias: true,
        logarithmicDepthBuffer: true,
      });
      renderer.setSize(that.domWidth, that.domHeight);
      // renderer.setClearColor("#f6f6f6", 1.0);
      container.appendChild(renderer.domElement);

      // 添加控制器
      controls = new OrbitControls(camera, renderer.domElement);
      controls.maxDistance = 2000
      controls.maxPolarAngle = Math.PI / 2

      labelRenderer = new CSS2DRenderer()
      labelRenderer.setSize(that.domWidth, that.domHeight)
      labelRenderer.domElement.style.position = 'absolute'
      labelRenderer.domElement.style.top = 0
      // 将渲染器设置为点击穿透
      labelRenderer.domElement.style.pointerEvents = 'none';
      container.appendChild(labelRenderer.domElement)

      this.render();
    },

    onWindowResize() {
      this.getDomSize()

      camera.aspect = this.domWidth / this.domHeight;
      camera.updateProjectionMatrix();

      renderer.setSize(this.domWidth, this.domHeight);

      renderer.render(scene, camera);

      labelRenderer.setSize(this.domWidth, this.domHeight);

      labelRenderer.render(scene, camera);
    },
    render() {
      if(cone.position.y == 420) {
        this.coneHeight = false
      }
      if(cone.position.y == 400) {
        this.coneHeight = true
      }
      if(this.coneHeight) {
        cone.translateY(-0.5)
        coneTwo.translateY(-0.5)
      } else {
        cone.translateY(0.5)
        coneTwo.translateY(0.5)
      }
      requestAnimationFrame(this.render);
      renderer.render(scene, camera);

      if(composer) {
        composer.render()
      }

      TWEEN.update()

      if(labelRenderer) {
        labelRenderer.render(scene, camera)
      }
    },

    onMouseClick(event) {
      if(!event) {
        return
      }

      mouse.x = (event.offsetX / this.domWidth) * 2 - 1;
      mouse.y = -(event.offsetY / this.domHeight) * 2 + 1;
      // 通过鼠标点的位置和当前相机的矩阵计算出raycaster
      raycaster.setFromCamera(mouse, camera);
      const intersects = raycaster.intersectObjects(scene.children)
      if (intersects.length > 0) {
        if(intersects[0].object.name == 'cone') {
          let target1 = new THREE.Vector3(-200, 600, 1500)
          let target2 = new THREE.Vector3(-200, 220, 150)
          this.cameraMove(camera.position, controls.target, target1, target2)
          let position = {x: -200, y: 470, z: 150}
          this.dialogData = {
            name: 'A栋',
            water: 12,
            energy: 36
          }
          this.showTip(position)
        }
        if(intersects[0].object.name == 'coneTwo') {
          // this.cameraMove(700, 500, 1000)
          // this.outLineObj([intersects[0].object])
          let target1 = new THREE.Vector3(400, 600, 1200)
          let target2 = new THREE.Vector3(200, 220, -150)
          this.cameraMove(camera.position, controls.target, target1, target2)
          let position = {x: 260, y: 470, z: -160}
          this.dialogData = {
            name: 'B栋',
            water: 9,
            energy: 42
          }
          this.showTip(position)
        }
      }
    },

    // 显示弹窗
    showTip(position) {
      let toolTip = document.getElementById("dialog")
      dialogLabel = new CSS2DObject(toolTip)
      dialogLabel.position.set(position.x, position.y, position.z)
      scene.add(dialogLabel)
      toolTip.style.visibility = "visible"
      // 将弹窗点击事件设置成‘auto’
      toolTip.style.pointerEvents = "auto"
    },

    // 隐藏弹窗
    hiddenDialog(position) {
      let toolTip = document.getElementById("dialog")
      toolTip.style.visibility = "hidden"
      this.cameraMove(camera.position, controls.target, this.cameraInit, this.controlInit)
    },

    onMouseMove(event) {
      if(!event) {
        return;
      }

      mouse.x = (event.offsetX / this.domWidth) * 2 - 1;
      mouse.y = -(event.offsetY / this.domHeight) * 2 + 1;
      // 通过鼠标点的位置和当前相机的矩阵计算出raycaster
      raycaster.setFromCamera(mouse, camera);
      const intersects = raycaster.intersectObjects(scene.children)
      document.body.style.cursor = 'default'
      if (intersects.length > 0) {
        if(intersects[0].object.name == 'cone' || intersects[0].object.name == 'coneTwo') {
          document.body.style.cursor = 'pointer'
        }
      }
    },
    // 高亮(参数是数组)
    outLineObj(selectedObjects) {
      composer = new EffectComposer(renderer)
      renderPass = new RenderPass(scene, camera)
      composer.addPass(renderPass)
      outlinePass = new OutlinePass(new THREE.Vector2(window.innerWidth, window.innerHeight), scene, camera)
      outlinePass.selectedObjects = selectedObjects
      outlinePass.edgeStrength = 10.0 // 边框的亮度
      outlinePass.edgeGlow = 0 // 光晕[0, 1]
      outlinePass.usePatternTexture = false // 是否使用父级的材质
      outlinePass.edgeThickness = 1.0 // 边框宽度
      outlinePass.downSampleRatio = 1 // 边框弯曲度
      outlinePass.pulsePeriod = 2.5 // 呼吸闪烁的速度
      outlinePass.visibleEdgeColor.set(parseInt(0x00ff00)) // 呼吸显示的颜色
      outlinePass.hiddenEdgeColor = new THREE.Color(0, 0, 0)// 呼吸消失的颜色
      outlinePass.clear = true
      composer.addPass(outlinePass)
      // 自定义的着色器通道 作为参数
      var effectFXAA = new ShaderPass(FXAAShader)
      effectFXAA.uniforms.resolution.value.set(1 / window.innerWidth, 1 / window.innerHeight)
      effectFXAA.renderToScreen = true
      composer.addPass(effectFXAA)
    },

    cameraMove(current1, current2, target1, target2) {
      let tween = new TWEEN.Tween({
        x1: current1.x,
        y1: current1.y,
        z1: current1.z,
        x2: current2.x,
        y2: current2.y,
        z2: current2.z,
      })
      tween.to({
        x1: target1.x,
        y1: target1.y,
        z1: target1.z,
        x2: target2.x,
        y2: target2.y,
        z2: target2.z,
      }, 1000)
      tween.onUpdate(obj => {
        camera.position.x = obj.x1
        camera.position.y = obj.y1
        camera.position.z = obj.z1
        controls.target.x = obj.x2
        controls.target.y = obj.y2
        controls.target.z = obj.z2
        controls.update()
      })
      tween.easing(TWEEN.Easing.Sinusoidal.InOut)
      tween.start()
      tween.onComplete(() => {
        // console.log('tween finished');
      })
    },
    // 视角还原按钮
    cameraBack() {
      this.cameraMove(camera.position, controls.target, this.cameraInit, this.controlInit)
    }
  },
  beforeDestroy() {
    window.removeEventListener("onresize", this.onWindowResize())
    window.removeEventListener("click", this.onMouseClick())
    window.removeEventListener("mousemove", this.onMouseMove())
  }
};
</script>

<style>
#container {
  height: 100%;
  width: 100%;
}
.progress-box {
  position: absolute;
  top: 50%;
  left: 30%;
  width: 40%;
}
.progress-outer {
  width: 100%;
  height: 26px;
  background: #d7d7d7;
  border-radius: 30px;
}
.progress-inner {
  width: 0%;
  height: 100%;
  background: #2479d0;
  border-radius: 30px;
  color: #fff;
  line-height: 30px;
  padding: 0 10px;
  text-align: right;
}
#dialog {
  height: 150px;
  width: 200px;
  position: absolute;
  background: rgba(36, 121, 208, 0.8);
  border-radius: 10px;
  visibility: hidden;
  color: #fff;
}
.close-icon {
  position: absolute;
  right: 0;
  padding:2px 10px;
  font-size:26px;
  cursor: pointer;
}
.back-btn {
  position: absolute;
  right: 350px;
  top: 30px;
  cursor: pointer;
  color: #fff;
  /* border: 1px solid; */
}
</style>
