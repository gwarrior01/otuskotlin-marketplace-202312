package tech.relialab.kotlin.clickhouse.exporter.app.ktor

import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
        module {
            module()
        }
        connector {
            host = "0.0.0.0"
            port = 8080
        }
    }).start(true)
}