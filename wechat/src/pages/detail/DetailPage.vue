<template>
  <view id="detail">
    <view id="detail-bg" />
    <view class="detail-nav">
      <uni-icons type="left" color="white" size="24" />
    </view>
    <view class="detail-header">
      <view class="detail-title">艳雪要上岸</view>
      <view class="detail-num">已经发生了 154 个事件</view>
    </view>
  </view>
  <view class="detail-content">
    <view class="data-year">
      <view>2024</view>
      <uni-icons type="more-filled" color="black" size="24" />
    </view>
    <view class="data-content">
      <view class="data-time">
        <view class="data-time-badge" />
        5月10日
      </view>
      <view class="data-info">
        <view class="data-title">千万别吃早餐</view>
        <view class="data-description"
          >千万别吃早餐千万别吃早餐千万别吃早餐
        </view>
        <view class="data-date">2024-02-26</view>
      </view>
    </view>
  </view>
  <uni-fab ref="fab" horizontal="right" vertical="bottom" :popMenu="false" />
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
#detail {
  padding: $content-padding;
  display: flex;
  flex-direction: column;
  #detail-bg {
    position: absolute;
    background-color: #1a1d22;
    width: 100%;
    top: 0;
    left: 0;
    height: calc(426rpx + var(--status-bar-height));
    z-index: -1;
  }
  .detail-nav {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-top: 56rpx;
  }
  .detail-header {
    color: #fff;
    text-align: center;
    .detail-title {
      font-size: 52rpx;
      margin-top: 36rpx;
      font-weight: bolder;
    }
    .detail-num {
      color: rgba($color: #fff, $alpha: 0.5);
      margin-top: 16rpx;
    }
  }
}
.detail-content {
  margin-top: -12rpx;
  background: #fff;
  border-radius: 48rpx 48rpx 0 0;
  padding: 40rpx;
  .data-year {
    font-size: 52rpx;
    color: #1c1d21;
    font-weight: bolder;
    line-height: 72rpx;
    display: flex;
    justify-content: space-between;
  }
  .data-content {
    display: flex;
    margin-top: 22rpx;
    justify-content: space-between;
    .data-time {
      white-space: nowrap;
      margin-right: 88rpx;
      padding-left: 30rpx;
      border-left: solid 1rpx #ff7979;
      margin-left: 20rpx;
      color: rgba($color: #000, $alpha: 0.5);
      position: relative;
      .data-time-badge {
        width: 8rpx;
        height: 8rpx;
        position: absolute;
        border-radius: 50%;
        left: -12rpx;
        top: 12rpx;
        background-color: #fff;
        border-radius: 50%;
        border: 4px solid #ff7979;
      }
    }
    .data-info {
      border-radius: 0rpx 24rpx 24rpx 24rpx;
      background-color: #fbefef;
      padding: 20rpx;
      font-size: 28rpx;
      display: flex;
      flex-direction: column;
      color: rgba($color: #000, $alpha: 0.5);
      .data-title {
        font-size: 30rpx;
        font-weight: bolder;
        color: black;
      }
      .data-description {
        margin-top: 10rpx;
      }
      .data-date {
        margin-top: 10rpx;
      }
    }
  }
}
</style>
