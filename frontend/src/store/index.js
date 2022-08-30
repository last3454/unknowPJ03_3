import axios from "@/utils/customAxios"
import { createStore } from "vuex"

export const store = createStore({
  state: () =>({
    accessToken: '',
    alertInfo: {},
    alertAsync: {}
  }),
  getters: {
    getAccessToken: (state) => () => {
      return state.accessToken
    },
    getAlterInfo: (state) => () =>{
      return state.alertInfo
    }
  },
  //Vuex 상태를 변경할 수 있는 유일한 메서드
  mutations: {
    SET_ACCESS_TOKEN (state, accessToken){
      if(accessToken){
        state.accessToken = accessToken
        sessionStorage.setItem("accessToken",accessToken)
      }
    },
    SET_ALTER (state, { resolve, reject }){
      state.alertAsync.resolve = resolve
      state.alertAsync.reject = reject
    },
    OPEN_ALTER (state, payload){
      const scrollTop = window.scrollY || window.document.documentElement.scrollTop
      state.alertInfo = {
        isOpen: true,
        scrollTop,
        ...payload
      }
      window.document.querySelector('html').style.overflow = 'hidden'
    },
    CLOSE_ALTER(state, type){
      state.alertInfo = {
        isOpen: false
      }
      window.document.querySelector('html').style.overflow = 'auto'
      window.scrollTop(0, state.alertInfo.scrollTop)
      state.alertAsync.resolve(type === 'OK')
      state.alertAsync.resolve = undefined
      state.alertAsync.reject = undefined
    }
  },
  //actions은 비동기 처리를 포함할 수 있는 메서드
  //액션을 사용하는 일반적인 경우는 데이터 가공 또는 비동기 처리를 실시한 후
  //그 결과를 뮤테이션에 전달하고 뮤테이션을 커밋하는 것
  actions: {
    storeSignin (context, payload){
      return axios({
        method: 'post',
        url : '/api/auth/signin',
        data: payload
      }).then(async response =>{
        return response
      }).catch(function (error){
        console.log(error)
      })
    },
    openAsyncPopup (context, payload){

    },
    closeAsyncPopup (context, payload){

    },
    openAsyncAlert (context, payload){
      const opt = {
        ...payload,
        type: 'ALTER'
      }
      context.commit('OPEN_ALTER', opt)
      return new Promise((resolve, reject) =>{
        context.commit('SET_ALTER', {resolve, reject})
      })
    },
    closeAsyncAlert (context, payload){
      context.commit('CLOSE_ALTER', 'OK')
    }
  }
})