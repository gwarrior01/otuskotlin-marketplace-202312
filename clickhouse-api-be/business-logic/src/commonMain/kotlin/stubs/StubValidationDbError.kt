package tech.relialab.kotlin.clickhouse.exporter.business.stubs

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateError
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import worker

fun ICorChainDsl<TemplateContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Ошибка базы данных
    """.trimIndent()
    on { stubCase == TemplateStubs.DB_ERROR && state == TemplateState.RUNNING }
    handle {
        fail(
            TemplateError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}