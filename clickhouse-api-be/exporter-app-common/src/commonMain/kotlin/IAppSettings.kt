package tech.relialab.kotlin.clickhouse.exporter.app.common

import tech.relialab.kotlin.clickhouse.exporter.bisiness.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings

interface IAppSettings {
    val processor: TemplateProcessor
    val coreSettings: CoreSettings
}