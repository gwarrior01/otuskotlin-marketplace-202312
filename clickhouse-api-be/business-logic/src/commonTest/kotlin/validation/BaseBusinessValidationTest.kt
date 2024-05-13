package tech.relialab.kotlin.clickhouse.exporter.business.validation

import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand

abstract class BaseBusinessValidationTest {
    protected abstract val command: TemplateCommand
    private val settings by lazy { CoreSettings() }
    protected val processor by lazy { TemplateProcessor(settings) }
}