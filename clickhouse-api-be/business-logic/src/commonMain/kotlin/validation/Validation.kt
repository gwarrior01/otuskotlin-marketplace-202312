package tech.relialab.kotlin.clickhouse.exporter.business.validation

import ICorChainDsl
import chain
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState

fun ICorChainDsl<TemplateContext>.validation(block: ICorChainDsl<TemplateContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == TemplateState.RUNNING }
}