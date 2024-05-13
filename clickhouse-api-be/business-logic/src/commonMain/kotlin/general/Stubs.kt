package tech.relialab.kotlin.clickhouse.exporter.bisiness.general

import ICorChainDsl
import chain
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateWorkMode

fun ICorChainDsl<TemplateContext>.stubs(title: String, block: ICorChainDsl<TemplateContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == TemplateWorkMode.STUB && state == TemplateState.RUNNING }
}