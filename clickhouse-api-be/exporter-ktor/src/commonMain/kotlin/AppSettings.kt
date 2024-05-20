package tech.relialab.kotlin.clickhouse.exporter.app.ktor

import tech.relialab.kotlin.clickhouse.exporter.app.common.IAppSettings
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings

data class AppSettings(
    val appUrls: List<String> = emptyList(),
    override val coreSettings: CoreSettings = CoreSettings(),
    override val processor: TemplateProcessor = TemplateProcessor(coreSettings),
): IAppSettings