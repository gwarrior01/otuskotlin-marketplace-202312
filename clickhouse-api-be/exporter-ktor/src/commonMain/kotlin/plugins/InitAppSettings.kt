package tech.relialab.kotlin.clickhouse.exporter.app.ktor.plugins

import io.ktor.server.application.*
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.AppSettings
import tech.relialab.kotlin.clickhouse.exporter.bisiness.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings

fun Application.initAppSettings(): AppSettings {
    val corSettings = CoreSettings(
        loggerProvider = getLoggerProviderConf(),
    )
    return AppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        coreSettings = corSettings,
        processor = TemplateProcessor(corSettings),
    )
}