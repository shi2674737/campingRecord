import Vue from 'vue'
import App from './App.vue'
import router from '@/router/'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI) //加载elementUI组件

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

document.title = "露营记录"

// router.beforeEach((to, from, next) => {
//   /* 路由发生变化修改页面title */
//   if (to.meta.title) {
//     document.title = to.meta.title
//   }
//   next()
// })