<script setup lang="ts">
import { onLaunch, onShow, onHide } from '@dcloudio/uni-app'
import { wechatAuth } from './api/wechat'

onLaunch(() => {
  console.log('App Launch')
  // #ifdef H5
  void uni.hideTabBar()
  // #endif
})
onShow(() => {
  console.log('App Show')
  uni.login({
    provider: 'weixin',
    success: async loginRes => {
      // 登录成功
      const data = await wechatAuth(loginRes.code)
      console.log(data)
    },
    fail: err => {
      console.error(err)
    }
  })
})
onHide(() => {
  console.log('App Hide')
})
</script>
<style></style>
