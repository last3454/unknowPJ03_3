<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'LayoutError',
  setup() {
    alert('에러')
  },
}
</script>