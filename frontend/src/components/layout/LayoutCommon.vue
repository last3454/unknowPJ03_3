<template>
  <header-tree></header-tree>
  <router-view></router-view>
  <footer-menu></footer-menu>
  <teleport to="#common-modal">
    <modal-popup></modal-popup>
  </teleport>
</template>
<script>
import { defineAsyncComponent } from 'vue'
export default {
  name: 'LayoutCommon',
  components: {
    HeaderTree : defineAsyncComponent(() => import('@/components/comm/HeaderTree.vue')),
    FooterMenu : defineAsyncComponent(() => import('@/components/comm/FooterMenu.vue'))
  },  
  setup(){
    return {}
  }
}
</script>