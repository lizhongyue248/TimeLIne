package wiki.zyue.client

import configuration.WechatConfiguration
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import wiki.zyue.client.ErrorCode.Companion.fromCode
import wiki.zyue.exception.ClientException

class WechatClient(private val configuration: WechatConfiguration) {
    private val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(Resources)
        install(ContentNegotiation) {
            json(wiki.zyue.configuration.DefaultJson, contentType = ContentType.Any)
        }

        install(DefaultRequest) {
            url("https://api.weixin.qq.com")
        }
    }

    suspend fun code2Session(code: String): CodeSession {
        println(code)
        val response = client.post(Code2Session(configuration.appId, configuration.appSecret, code))
        val body: CodeSession = response.body()
        if (body.errorCode == null) {
            return body
        }
        throw ClientException(fromCode(body.errorCode).message)
    }

}

/**
 * [code2session](https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html)
 */
@Resource("/sns/jscode2session")
private class Code2Session(
    val appid: String,
    val secret: String,
    @SerialName("js_code")
    val jsCode: String,
    @SerialName("grant_type")
    val grantType: String = "authorization_code",
)

private enum class ErrorCode(val code: Int, val message: String) {
    /**
     * 系统繁忙，此时请开发者稍候再试
     */
    SYSTEM_ERROR(-1, "系统繁忙,清稍后再试"),

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 高风险等级用户，小程序登录拦截 。风险等级详见 [用户安全解方案](https://developers.weixin.qq.com/miniprogram/dev/framework/operation.html)
     */
    CODE_BLOCKED(45011, "当前用户风险过高，无法登录。"),

    /**
     * API 调用太频繁，请稍候再试
     */
    MINUTE_RATE_LIMIT(40226, "当前系统访问过高，清稍后再试"),

    /**
     * js_code无效
     */
    INVALID_CODE(40029, "登录操作无效，清重新尝试");

    companion object {
        fun fromCode(errorCode: Int): ErrorCode =
            entries.find {
                it.code == errorCode
            } ?: SUCCESS
    }
}

@Serializable
data class CodeSession(
    @SerialName("session_key")
    val sessionKey: String?,
    @SerialName("unionid")
    val unionId: String?,
    @SerialName("errmsg")
    val errorMessage: String?,
    @SerialName("openid")
    val openId: String?,
    @SerialName("errcode")
    val errorCode: Int?
)
