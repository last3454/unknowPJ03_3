<template>
  <div class="container-fluid">
    <div class="row no-gutter">
      <div class="col-md-6 d-none d-md-flex bg-image"></div>
      <div class="col-md-6 bg-light">
        <div class="login d-flex align-items-center py-5">
          <div class="container">
            <div class="row">
              <div class="col-lg-10 col-xl-7 mx-auto">
                <h3 class="display-4">YHCHOI SITE</h3>
                <p class="text-muted mb-4">page using <strong>Vue.js & Bootstrap 4 & Spring-Boot</strong></p>
                <form>
                  <div class="mb-3">
                    <input type="email" v-model="params.loginId" placeholder="아이디" class="form-control rounded-pill border-0 shadow-sm px-4" />
                  </div>
                  <div class="mb-3">
                      <input type="password" v-model="params.loginPwTemp" placeholder="패스워드" class="form-control rounded-pill border-0 shadow-sm px-4 text-primary" />
                  </div>
                  <div class="form-check">
                    <input id="customCheck1" type="checkbox" class="form-check-input" />
                    <label for="customCheck1" class="form-check-label">아이디 저장</label>
                  </div>
                  <div class="d-grid gap-2 mt-2">
                    <button type="submit" class="btn btn-primary btn-block text-uppercase mb-2 rounded-pill shadow-sm" @click.prevent="fnLogin()">로그인</button>
                  </div>
                  <div class="text-center d-flex justify-content-between mt-4"><p>Code by <a href="https://therichpost.com/" class="font-italic text-muted"> 
                  <u>Y.H.CHOI</u></a></p></div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { useLogin } from '@/compositions/useLogin'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { sha512 } from 'js-sha512'
import { useActions } from 'vuex-composition-helpers'

export default {
  name: 'Login',
  setup() {
    const { openAsyncAlert } = useActions(['openAsyncAlert'])
    const store = useStore()
    const route = useRouter()
    const {
      params
    } = useLogin()

    params.value.loginId = 'last3454'
    params.value.loginPwTemp = '1111@'
      
    const fnLogin = async () =>{
      if(!params.value.loginId){
        await openAsyncAlert({ message : '아이디를 입력해주세요.'})
        return
      }
      if(!params.value.loginPwTemp){
        await openAsyncAlert({ message : '비밀번호를 입력해주세요.'})
        return
      }

      const loginParam = {
        loginId : params.value.loginId.trim(),
        loginPw : sha512(params.value.loginPwTemp)
      }
      store.dispatch('storeSignin', loginParam).then(async res => {
        if(res.data.code === '0000'){
          route.push({path : '/main'})
        }
      })
    }

    return {
      params,
      fnLogin
    }
  }
}
</script>

<style scoped>
.login,
.image {
  min-height: 100vh;
}
.bg-image {
  background-image: url('@/assets/images/body1.svg');
  background-size: cover;
  background-position: center center;
}
</style>