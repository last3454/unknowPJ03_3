module.exports = {
  env: {
    browser: true
  },
  extends: [
    // plugin vue에서 제공하는 기본적인 vue.js 코드 검사
    // https://eslint.vuejs.org/rules/html-closing-bracket-newline.html
    // 'plugin:vue/vue3-essential', // Lv1
    "plugin:vue/vue3-strongly-recommended", //Lv2
    // 'plugin:vue/vue3-recommended', // Lv3
    // js - https://eslint.org/docs/rules/
    "eslint:recommended", // eslint에서 권장하는 코드 규칙으로 js 검사
  ],
  rules: {
    "vue/html-closing-bracket-newline": [
      "error",
      {
        singleline: "never",
        multiline: "never",
      },
    ]
  }
}