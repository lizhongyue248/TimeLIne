<script setup lang="ts">
import { onLaunch, onShow, onHide } from '@dcloudio/uni-app'
onLaunch(() => {
  console.log('App Launch')
  // #ifdef H5
  void uni.hideTabBar()
  // #endif
})
onShow(() => {
  console.log('App Show')
  // uni.login({
  //   provider: 'weixin',
  //   success: function (loginRes) {
  //     // 登录成功
  //     console.log(loginRes)
  //     uni.getUserInfo({
  //       provider: 'weixin',
  //       success: function (info) {
  //         // 获取用户信息成功, info.authResult保存用户信息
  //         console.log(info)
  //       }
  //     })
  //   },
  //   fail: function (err) {
  //     console.error(err)
  //   }
  // })
})
onHide(() => {
  console.log('App Hide')
})
</script>
<style></style>
