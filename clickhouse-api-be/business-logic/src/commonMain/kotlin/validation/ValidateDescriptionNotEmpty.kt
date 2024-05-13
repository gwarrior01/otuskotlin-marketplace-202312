package tech.relialab.kotlin.clickhouse.exporter.business.validation

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.errorValidation
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import worker

fun ICorChainDsl<TemplateContext>.validateDescriptionNotEmpty(title: String) = worker {
    this.title = title
    on { templateValidating.description.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}