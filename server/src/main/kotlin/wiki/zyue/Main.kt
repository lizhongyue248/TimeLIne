package wiki.zyue

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import wiki.zyue.client.WechatClient
import wiki.zyue.configuration.ApplicationConfiguration
import wiki.zyue.route.wechatModule

fun main() {
    val env = applicationEngineEnvironment {
        envConfig()
    }
    embeddedServer(Netty, env).start(wait = true)
}

fun ApplicationEngineEnvironmentBuilder.envConfig() {
    module {
        install(CORS) {
            anyHost()
            allowHeader(HttpHeaders.ContentType)
        }
        install(Compression)
        install(StatusPages)
        install(Resources)
        install(RequestValidation)
        install(CallLogging)
        install(ContentNegotiation) {
            json(wiki.zyue.configuration.DefaultJson)
        }
        install(CallId)
        install(Koin) {
            printLogger()
            modules(
                org.koin.dsl.module {
                    single { WechatClient(ApplicationConfiguration.wechat) }
                }
            )
        }
        wechatModule()
        mainModule()
    }
    connector {
        host = "0.0.0.0"
        port = 8080
    }
}

fun Application.mainModule() {
    routing {
        get { call.respondText("Welcome to server.") }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
    }
}
