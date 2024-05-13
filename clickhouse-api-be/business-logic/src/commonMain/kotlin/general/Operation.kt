package tech.relialab.kotlin.clickhouse.exporter.bisiness.general

import ICorChainDsl
import chain
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState

fun ICorChainDsl<TemplateContext>.operation(
    title: String,
    command: TemplateCommand,
    block: ICorChainDsl<TemplateContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == TemplateState.RUNNING }
}