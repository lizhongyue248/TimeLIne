<template>
  <view class="nav">
    <uni-icons
      :type="path === Routes.HOME ? 'home-filled' : 'home'"
      :color="path === Routes.HOME ? 'rgb(97,148,247)' : 'black'"
      @click="() => switchTab(Routes.HOME)"
      size="36"
    />
    <uni-icons
      :type="path === Routes.ACCOUNT ? 'person-filled' : 'person'"
      :color="path === Routes.ACCOUNT ? 'rgb(97,148,247)' : 'black'"
      @click="() => switchTab(Routes.ACCOUNT)"
      size="36"
    />
    <view class="plus">
      <uni-icons type="plusempty" color="white" size="32" />
    </view>
  </view>
</template>

<script setup lang="ts">
import Routes from '@/types/Routes'
import { onReady } from '@dcloudio/uni-app'
import { ref } from 'vue'
const path = ref('')

const current = getCurrentPages()
onReady(() => {
  path.value = current[current.length - 1].route ?? ''
})

const switchTab = (path: string) => {
  void uni.switchTab({
    url: `/${path}`
  })
}
</script>

<style scoped lang="scss">
.nav {
  width: 100%;
  position: absolute;
  bottom: 0;
  height: 120rpx;
  box-shadow: 0rpx -4rpx 40rpx 0rpx rgba(0, 0, 0, 0.1);
  border-radius: 50rpx 50rpx 0rpx 0rpx;
  padding-bottom: constant(safe-area-inset-bottom);
  padding-bottom: env(safe-area-inset-bottom);
  display: flex;
  justify-content: space-evenly;
  padding-top: 34rpx;
  gap: 150rpx;
  .plus {
    @extend .common-radius;
    @extend .flex-center;
    position: absolute;
    background-color: $inverse-bg-color;
    width: 136rpx;
    height: 136rpx;
    bottom: calc(88rpx + constant(safe-area-inset-bottom));
    bottom: calc(88rpx + env(safe-area-inset-bottom));
  }
}
</style>
