package configuration

import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val wechat: WechatConfiguration
)
