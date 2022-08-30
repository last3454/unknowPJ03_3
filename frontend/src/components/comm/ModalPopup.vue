<template>
  <div class="modal msg open" v-show="alertInfo.isOpen"> 
    <div class="modal-wrap">
      <div class="modal-content">
        <div class="modal-cont-wrap">
          <div class="modal-cont-msg">
            <p class="tit">{{ alertInfo.message }}</p>
            <!-- <p class="txt">{{ alertInfo.message }}</p> -->
          </div>
          <div class="btn-wrap">
            <a href="javascript:;" @click.prevent="closeAsyncAlert()" class="btn typeB"><span>확인</span></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useActions} from 'vuex-composition-helpers'

export default {
  name: 'ModalPopup',
  setup() {
    const store = useStore()
    const alertInfo = computed(() => store.getters.getAlterInfo())
    const {closeAsyncAlert} = useActions(['closeAsyncAlert'])
    return {
     alertInfo,
     closeAsyncAlert
    }
  }
}
</script>