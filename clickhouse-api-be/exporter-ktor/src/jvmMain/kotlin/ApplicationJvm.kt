package tech.relialab.kotlin.clickhouse.exporter.app.ktor

import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.plugins.initAppSettings
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.v1.v1Template

// function with config (application.conf)
fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.moduleJvm(
    appSettings: AppSettings = initAppSettings(),
) {
    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CallLogging) {
        level = Level.INFO
    }
    module(appSettings)

    // Неофициальное задание. Попробуйте сделать этот код работающим
//    val rabbitServer = RabbitApp(appSettings, this@moduleJvm)
//    rabbitServer?.start()

//    routing {
//        route("v1") {
////            install(ContentNegotiation) {
//////                jackson {
//////                    setConfig(apiV1Mapper.serializationConfig)
//////                    setConfig(apiV1Mapper.deserializationConfig)
//////                }
////
////                json()
////            }
//            v1Template(appSettings)
////            webSocket("/ws") {
////                wsHandlerV1(appSettings)
////            }
//        }
//
//    }
}

