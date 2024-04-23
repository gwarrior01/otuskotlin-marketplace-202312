package tech.relialab.kotlin.clickhouse.exporter.app.ktor

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import tech.relialab.kotlin.clickhouse.exporter.api.v1.apiV1Mapper
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.plugins.initAppSettings
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.v1.v1Template

fun Application.module(
    appSettings: AppSettings = initAppSettings()
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost()
    }
    install(WebSockets)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("v1") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            v1Template(appSettings)
        }
    }
}