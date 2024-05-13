package tech.relialab.kotlin.clickhouse.exporter.business.validation

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.errorValidation
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import worker

fun ICorChainDsl<TemplateContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { templateValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}