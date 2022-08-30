import { reactive, toRefs } from "vue"
import axios from "@/utils/customAxios"

export const useLogin = () => {
  const state = reactive({
    params: {
      loginId: "",
      loginPw: "",
      loginPwTemp: ""
    }
  })

  const fetchLogin = () => {
    
  }

  return {
    ...toRefs(state)
  }
}