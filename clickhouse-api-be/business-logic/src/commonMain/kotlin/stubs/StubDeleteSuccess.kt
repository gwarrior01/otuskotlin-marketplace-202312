package tech.relialab.kotlin.clickhouse.exporter.bisiness.stubs

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import tech.relialab.kotlin.clickhouse.exporter.stubs.TemplateStub
import tech.relialab.kotlin.logging.common.LogLevel
import worker

fun ICorChainDsl<TemplateContext>.stubDeleteSuccess(title: String, corSettings: CoreSettings) = worker {
    this.title = title
    this.description = """
        Успешное удаление шаблона
    """.trimIndent()
    on { stubCase == TemplateStubs.SUCCESS && state == TemplateState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubDeleteSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = TemplateState.FINISHING
            val stub = TemplateStub.prepareResult {
                templateRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            }
            templateResponse = stub
        }
    }
}