<template>
  <div>
    <router-view></router-view>
  </div>
  <teleport to="#common-modal">
    <modal-popup ref="modal"></modal-popup>
  </teleport>  
</template>

<script>
export default {
  name: 'LayoutBlank',
  setup() {
    
  },
}
</script>