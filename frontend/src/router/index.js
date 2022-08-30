import { nextTick } from "vue"
import { createRouter, createWebHistory } from "vue-router"

const routes = [
  {
    path: '/:pathMatch(.*)*',
    redirect: '/error/404'
  },
  {
    path: '/error',
    name: 'error',
    meta: {
      layout: 'error'
    },
    childrens: [
      {
        path: '404',
        name: 'error-404',
        component: () => import('@/view/error/Error404.vue')
      }
    ]
  },
  {
    path: '/main',
    name: 'main',
    component: () => import('@/view/Main.vue'),
    meta:{
      title: '메인'
    }
  },
  {
    path: '/',
    name: 'login',
    component: () => import('@/view/Login.vue'),
    meta:{
      layout: 'blank',
      title: '로그인'
    }
  }
]

const router = createRouter({
  history : createWebHistory (),
  routes,
  mode: 'history'
})

router.beforeEach((to,from)=> {
  const title = to.meta.title === undefined ? '' : to.meta.title
  nextTick(()=>{
    document.title = title
  })
})

export default router