import Vue from 'vue'
import Router from 'vue-router'
const originalPush = Router.prototype.push

Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
Vue.use(Router)
const createRouter = () =>
  new Router({
    mode: 'hash',
    scrollBehavior: () => ({
      y: 0
    }),
    routes: monitorRouter
  })
const monitorRouter = [
  {
    path: '/',
    redirect: '/index/campingList'
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('@/components/index'),
    children: [
      {
          path: 'addressList',
          name: 'addressList',
          component: () => import('@/components/addressList'),
          // meta: { title: "追番记录" }
      },
      {
          path: 'campingList',
          name: 'campingList',
          component: () => import('@/components/campingList'),
          // meta: { title: "追番记录" }
      }
    ]
  },
]
const router = createRouter()
export default router
