package tech.relialab.kotlin.clickhouse.exporter.app.ktor.plugins

import io.ktor.server.application.*
import tech.relialab.kotlin.logging.common.LoggerProvider
import tech.relialab.kotlin.logging.kermit.mpLoggerKermit

actual fun Application.getLoggerProviderConf(): LoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp" -> LoggerProvider { mpLoggerKermit(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp, socket and logback (default)")
}
