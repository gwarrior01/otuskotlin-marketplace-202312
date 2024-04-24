package tech.relialab.kotlin.clickhouse.exporter.common.helpers

import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateError

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