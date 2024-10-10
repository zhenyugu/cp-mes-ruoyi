import Cookies from 'js-cookie'
import {getLogoInfo, getConfigKey} from "@/api/system/config"

const state = {
  sidebar: {
    opened: Cookies.get('sidebarStatus') ? !!+Cookies.get('sidebarStatus') : true,
    withoutAnimation: false,
    hide: false
  },
  device: 'desktop',
  size: Cookies.get('size') || 'medium',
  logoInfo: {},
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    if (state.sidebar.hide) {
      return false;
    }
    state.sidebar.opened = !state.sidebar.opened
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      Cookies.set('sidebarStatus', 1)
    } else {
      Cookies.set('sidebarStatus', 0)
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('sidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  },
  SET_SIZE: (state, size) => {
    state.size = size
    Cookies.set('size', size)
  },
  SET_SIDEBAR_HIDE: (state, status) => {
    state.sidebar.hide = status
  },
  SET_LOGOINFO: (state, logoInfo) => {
    state.logoInfo = logoInfo
  },
  SET_THEME: (state, theme) => {
    state.logoInfo.theme = theme
  }
}

const actions = {
  toggleSideBar({ commit }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({ commit }, { withoutAnimation }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({ commit }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setSize({ commit }, size) {
    commit('SET_SIZE', size)
  },
  toggleSideBarHide({ commit }, status) {
    commit('SET_SIDEBAR_HIDE', status)
  },
  getLogoInfo({commit}) {
    document.getElementsByTagName('body')[0].className = 'theme-light'
    getLogoInfo().then(res => {
      // 设置浏览器icon、标题
      document.querySelector("link[rel*='icon']").href = res.data.browserLogo
      document.title = res.data.sysTitle
      // 设置主题
      let localTheme = localStorage.getItem('theme')
      let theme = res.data.theme || 'theme-light'
      document.getElementsByTagName('body')[0].className = localTheme || theme
      if(!localTheme) localStorage.setItem('theme', localTheme || theme)
      let result = {
        ...res.data,
        theme: localTheme || theme
      }
      commit('SET_LOGOINFO', result)
    })
  },
  setTheme({commit}, theme) {
    commit("SET_THEME", theme)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
