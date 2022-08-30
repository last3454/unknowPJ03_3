import { createApp, defineAsyncComponent } from 'vue'
import { store } from './store/index.js'
import App from './App.vue'
import router from './router'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.js"
import "@/assets/css/common.css";
import "@/assets/css/style.css";
import "@/assets/css/popup.css";
import "@/assets/fonts/style.css";
import "popper.js/dist/umd/popper.min.js";

const app = createApp(App)
app.use(store)
app.use(router)
app.component('ModalPopup', defineAsyncComponent(() => import('@/components/comm/ModalPopup.vue')))
app.mount('#app')