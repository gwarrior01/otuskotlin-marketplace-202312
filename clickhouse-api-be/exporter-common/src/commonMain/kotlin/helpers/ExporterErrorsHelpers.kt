package tech.relialab.kotlin.clickhouse.exporter.common.helpers

import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateError
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.logging.common.LogLevel

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

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = TemplateError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)