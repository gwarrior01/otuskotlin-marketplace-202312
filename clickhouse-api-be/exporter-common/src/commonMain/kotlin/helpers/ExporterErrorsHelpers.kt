package tech.relialab.kotlin.clickhouse.exporter.common.helpers

import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateError
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState

fun Throwable.asTemplateError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = TemplateError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

inline fun TemplateContext.addError(vararg error: TemplateError) = errors.addAll(error)

inline fun TemplateContext.fail(error: TemplateError) {
    addError(error)
    state = TemplateState.FAILING
}