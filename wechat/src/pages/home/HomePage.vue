<template>
  <view id="home">
    <view class="header">
      <view class="info">
        <image
          class="avatar"
          src="https://image.zyue.wiki/zhongyue_avatar.jpg"
          mode="scaleToFill"
        />
        <view class="name">Hi，LiZhongYue</view>
      </view>
    </view>
    <view class="premium!">
      <view class="main">
        <view class="star">
          <uni-icons type="star-filled" color="white" size="24" />
        </view>
        <view class="content">
          <view class="title">Go Premium!</view>
          <view class="description"
            >Get unlimited access to all our features!
          </view>
        </view>
      </view>
      <view class="nav">
        <uni-icons type="paperplane" color="white" size="24" />
      </view>
    </view>
    <view class="event">
      <view class="title">Events</view>
      <view class="content">
        <view class="data" v-for="item in data" :key="item">
          <view class="close-icon">
            <uni-icons type="close" color="black" size="24" />
          </view>
          <view class="icon">
            <uni-icons type="calendar" color="black" size="24" />
            <view class="badge" />
          </view>
          <view class="name">{{ item }}</view>
          <view class="date">2024-10-21</view>
        </view>
      </view>
    </view>
  </view>
  <bottom-nav />
</template>

<script setup lang="ts">
import BottomNav from '@/components/BottomNav.vue'
import { ref } from 'vue'
const data = ref(['艳雪要上岸', '忠强也要上岸', '中月要搞钱'])
</script>

<style scoped></style>

<style scoped lang="scss">
#home {
  padding: $content-padding;
  .header {
    display: flex;
    padding-top: 56rpx;
    align-items: center;
    justify-content: space-between;
    .info {
      display: flex;
      align-items: center;
      .avatar {
        width: 96rpx;
        height: 96rpx;
        border-radius: 24rpx;
      }
      .name {
        padding-left: 12rpx;
        font-size: $font-size-title;
        font-weight: bolder;
      }
    }
  }
  .premium {
    @extend .common-radius;
    padding: 44rpx 0 0 32rpx;
    margin-top: 48rpx;
    background-color: $inverse-bg-color;
    color: $inverse-font-color;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    .main {
      display: flex;
      margin-right: 42rpx;
      .star {
        padding: 20rpx;
        background-color: rgba($color: #fff, $alpha: 0.4);
        border-radius: 50%;
        width: 38rpx;
        height: 38rpx;
        aspect-ratio: 1/1;
        @extend .flex-center;
      }
      .content {
        padding-left: 52rpx;
        .title {
          font-weight: bolder;
          font-size: 40rpx;
        }
        .description {
          color: rgba($color: #fff, $alpha: 0.6);
          font-size: 36rpx;
          margin-bottom: 54rpx;
          margin-top: 16rpx;
        }
      }
    }
    .nav {
      @extend .flex-center;
      width: 100rpx;
      height: 100rpx;
      background: #6194f7;
      border-radius: 50%;
      aspect-ratio: 1/1;
      border: none;
      margin-bottom: -1rpx;
      margin-right: -1rpx;
    }
  }
  .event {
    .title {
      font-size: $font-size-title;
      margin-top: 68rpx;
      font-weight: bolder;
    }
    .content {
      margin-top: 54rpx;
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
      .data {
        padding: 32rpx;
        background-color: #fef6ec;
        position: relative;
        @extend .common-radius;
        .close-icon {
          position: absolute;
          top: 20rpx;
          right: 20rpx;
        }
        .icon {
          position: relative;
          .badge {
            position: absolute;
            bottom: 0;
            left: 24rpx;
            border-radius: 50%;
            background-color: rgba($color: #a897ff, $alpha: 0.4);
            width: 22rpx;
            height: 22rpx;
          }
        }
        .name {
          padding-top: 20rpx;
          font-weight: 1000;
        }
        .date {
          padding-top: 8rpx;
          color: rgba($color: #000000, $alpha: 0.4);
        }
      }
    }
  }
}
</style>
