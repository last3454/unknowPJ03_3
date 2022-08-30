<template>
  <component :is="showLayout">
    <router-view></router-view>
  </component>
  <!--로딩바 추가할 자리 -->
</template>

<script>
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import { computed, defineAsyncComponent } from 'vue'

export default {
  name: 'App',
  components: {
    LayoutCommon : defineAsyncComponent(() => import('@/components/layout/LayoutCommon.vue')),
    LayoutBlank : defineAsyncComponent(() => import('@/components/layout/LayoutBlank.vue')),
    LayoutError : defineAsyncComponent(() => import('@/components/layout/LayoutError.vue'))
  },
  setup() {
    const store = useStore()
    const route = useRoute()

    const showLayout = computed(() => {
      if(route.meta.layout === 'blank'){
        return 'layout-blank'
      } else if(route.meta.layout === 'error'){
        return 'layout-error'
      }
      return 'layout-common'
    })

    return {
      store,
      showLayout
    }
  }
}
</script>