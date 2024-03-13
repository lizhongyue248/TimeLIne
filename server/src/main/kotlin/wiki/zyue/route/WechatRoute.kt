package wiki.zyue.route

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.lang3.StringUtils
import org.koin.ktor.ext.inject
import wiki.zyue.client.WechatClient

@Resource("/wechat")
class WechatRoute {

    @Resource("/auth/{code}")
    class Auth(val parent: WechatRoute, val code: String)

}

fun Application.wechatModule() {
    val wechatClient by inject<WechatClient>()

    routing {
        get<WechatRoute.Auth> { auth ->
            if (StringUtils.isBlank(auth.code)) {
                throw RequestValidationException(
                    "Missing necessary parameters", listOf("Maybe you should add param.")
                )
            }
            call.respond(wechatClient.code2Session(auth.code))
        }
    }
}