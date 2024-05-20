package tech.relialab.kotlin.clickhouse.exporter.business.stubs

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateId
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateVisibilityClient
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import tech.relialab.kotlin.clickhouse.exporter.stubs.TemplateStub
import tech.relialab.kotlin.logging.common.LogLevel
import worker

fun ICorChainDsl<TemplateContext>.stubUpdateSuccess(title: String, corSettings: CoreSettings) = worker {
    this.title = title
    this.description = """
        Успешное изменение шаблона
    """.trimIndent()
    on { stubCase == TemplateStubs.SUCCESS && state == TemplateState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubUpdateSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = TemplateState.FINISHING
            val stub = TemplateStub.prepareResult {
                templateRequest.id.takeIf { it != TemplateId.NONE }?.also { this.id = it }
                templateRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
                templateRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
                templateRequest.visibility.takeIf { it != TemplateVisibilityClient.NONE }?.also { this.visibility = it }
            }
            templateResponse = stub
        }
    }
}