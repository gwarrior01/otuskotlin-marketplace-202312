package tech.relialab.kotlin.clickhouse.exporter.business.stubs

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateError
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import worker

fun ICorChainDsl<TemplateContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == TemplateState.RUNNING }
    handle {
        fail(
            TemplateError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}