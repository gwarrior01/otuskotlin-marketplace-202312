package tech.relialab.kotlin.clickhouse.exporter.bisiness.stubs

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateError
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import worker

fun ICorChainDsl<TemplateContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    this.description = """
        Ошибка валидации для заголовка шаблона
    """.trimIndent()

    on { stubCase == TemplateStubs.BAD_TITLE && state == TemplateState.RUNNING }
    handle {
        fail(
            TemplateError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}