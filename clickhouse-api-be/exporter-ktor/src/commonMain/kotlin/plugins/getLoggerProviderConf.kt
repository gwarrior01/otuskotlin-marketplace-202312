package tech.relialab.kotlin.clickhouse.exporter.app.ktor.plugins

import io.ktor.server.application.*
import tech.relialab.kotlin.logging.common.LoggerProvider

expect fun Application.getLoggerProviderConf(): LoggerProvider
