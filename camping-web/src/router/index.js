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
    redirect: '/index/addressList'
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
      },
      {
          path: 'campingList',
          name: 'campingList',
          component: () => import('@/components/campingList'),
      },
        {
            path: 'friendList',
            name: 'friendList',
            component: () => import('@/components/friendList'),
        }
    ]
  },
]
const router = createRouter()
export default router
