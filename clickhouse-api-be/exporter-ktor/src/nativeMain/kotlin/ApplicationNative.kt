package tech.relialab.kotlin.clickhouse.exporter.app.ktor

import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, environment = applicationEngineEnvironment {
//        val conf = YamlConfigLoader().load("./application.yaml")
//            ?: throw RuntimeException("Cannot read application.yaml")
//        println(conf)
//        config = conf
//        println("File read")

        module {
            module()
        }

//        connector {
//            port = conf.port
//            host = conf.host
//        }
        connector {
            host = "0.0.0.0"
            port = 8080
        }
        println("Starting")
    }).start(true)
}