<template>
  <view id="account">
    <view class="account-bg" />
    <view class="account-header">
      <view class="theme"
        ><uni-icons type="starhalf" color="black" size="32"
      /></view>
      <view class="info">
        <image
          class="avatar"
          src="https://image.zyue.wiki/zhongyue_avatar.jpg"
          mode="scaleToFill"
        />
        <view class="name">Li ZhongYue</view>
      </view>
      <view class="vip">
        <view class="vip-info">
          <image
            class="vip-image"
            src="/static/icons/vip.svg"
            mode="scaleToFill"
          />
          <view>升级会员享受更多权益</view>
        </view>
        <view class="vip-action"> 成为会员 </view>
      </view>
      <view class="action-card">
        <view class="action-block">
          <image
            class="action-image"
            src="/static/icons/language.svg"
            mode="scaleToFill"
          />
          <view class="action-name">语言</view>
        </view>
        <view class="action-block">
          <image
            class="action-image"
            src="/static/icons/backup.svg"
            mode="scaleToFill"
          />
          <view class="action-name">还原</view>
        </view>
        <view class="action-block">
          <image
            class="action-image"
            src="/static/icons/restore.svg"
            mode="scaleToFill"
          />
          <view class="action-name">备份</view>
        </view>
      </view>
      <view class="action-list">
        <view class="action-item">
          <image
            class="action-image-start"
            src="/static/icons/good.svg"
            mode="scaleToFill"
          />
          <view class="action-item-name">好评</view>
          <uni-icons type="right" color="black" size="24" />
        </view>
        <view class="divider" />
        <view class="action-item">
          <image
            class="action-image-start"
            src="/static/icons/feedback.svg"
            mode="scaleToFill"
          />
          <view class="action-item-name">反馈</view>
          <uni-icons type="right" color="black" size="24" />
        </view>
      </view>
    </view>
  </view>
  <bottom-nav />
</template>

<script setup lang="ts">
import BottomNav from '@/components/BottomNav.vue'
</script>

<style scoped lang="scss">
#account {
  padding: $content-padding;
  margin-bottom: $nav-bottom-margin;
  .account-bg {
    // #ifdef MP-WEIXIN
    height: calc(484rpx + var(--status-bar-height));
    // #endif
    // #ifdef MP-QQ
    height: calc(406rpx + var(--status-bar-height));
    // #endif
    width: 100%;
    background: linear-gradient(120deg, #fdf5fc 0%, #f1f1fe 100%);
    position: absolute;
    top: 0;
    left: 0;
    z-index: -1;
  }
  .account-header {
    // #ifdef MP-WEIXIN
    margin-top: 140rpx;
    // #endif
    // #ifdef MP-QQ
    margin-top: 70rpx;
    // #endif
    display: flex;
    flex-direction: column;
    .theme {
      text-align: right;
    }
    .info {
      display: flex;
      align-items: center;
      .avatar {
        width: 136rpx;
        height: 136rpx;
        border-radius: 50%;
      }
      .name {
        margin-left: 24rpx;
        color: #1a1d22;
        font-size: 52rpx;
        font-weight: bolder;
      }
    }
  }
  .vip {
    margin: 68rpx auto 0 auto;
    width: 596rpx;
    background: linear-gradient(125deg, #ffead7 0%, #f9c893 100%);
    @extend .flex-center;
    border-radius: 24rpx 24rpx 0 0;
    padding: 16rpx;
    justify-content: space-between;
    .vip-info {
      display: flex;
      color: #853c00;
      font-size: 28rpx;
      align-items: center;
      .vip-image {
        width: 48rpx;
        height: 48rpx;
        margin-right: 14rpx;
      }
    }
    .vip-action {
      background: #722f00;
      border-radius: 24rpx;
      color: #ffffff;
      font-size: 28rpx;
      padding: 4rpx 18rpx;
    }
  }
  .action-card {
    @extend .common-radius;
    display: flex;
    justify-content: space-between;
    background-color: #fff;
    padding: 48rpx 72rpx;
    .action-block {
      .action-image {
        width: 80rpx;
        height: 80rpx;
      }
      .action-name {
        font-size: 24rpx;
        text-align: center;
      }
    }
  }
  .action-list {
    @extend .common-radius;
    display: flex;
    background-color: #fff;
    margin-top: 40rpx;
    flex-direction: column;
    .action-item {
      padding: 46rpx;
      display: flex;
      align-items: center;
      .action-image-start {
        width: 40rpx;
        height: 40rpx;
        padding-right: 40rpx;
      }
      .action-item-name {
        flex: 1;
      }
    }
    .divider {
      background: #e6e6e6;
      width: 590rpx;
      margin: 0 auto;
      height: 2rpx;
    }
  }
}
</style>
<style>
page {
  background-color: #f5f5f5;
}
</style>
