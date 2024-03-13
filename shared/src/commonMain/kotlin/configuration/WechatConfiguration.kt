package configuration

import kotlinx.serialization.Serializable

@Serializable
data class WechatConfiguration(
    val appId: String,
    val appSecret: String
)