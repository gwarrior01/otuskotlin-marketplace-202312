package tech.relialab.kotlin.clickhouse.exporter.bisiness.general

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import worker

fun ICorChainDsl<TemplateContext>.initStatus(title: String) = worker {
    this.title = title
    this.description = """
        Этот обработчик устанавливает стартовый статус обработки. Запускается только в случае не заданного статуса.
    """.trimIndent()
    on { state == TemplateState.NONE }
    handle { state = TemplateState.RUNNING }
}