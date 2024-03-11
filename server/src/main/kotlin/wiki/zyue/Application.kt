package wiki.zyue

import Greeting
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

fun main() {
    val env = applicationEngineEnvironment {
        envConfig()
    }
    embeddedServer(Netty, env).start(wait = true)
}

fun ApplicationEngineEnvironmentBuilder.envConfig() {
    module {
        module()
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
            json()
        }
        install(CallId)
    }
    connector {
        host = "0.0.0.0"
        port = 8080
    }
}

fun Application.module() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}
