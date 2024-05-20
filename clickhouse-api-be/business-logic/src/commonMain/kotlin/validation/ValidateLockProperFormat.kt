package tech.relialab.kotlin.clickhouse.exporter.business.validation

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.errorValidation
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateLock
import worker

fun ICorChainDsl<TemplateContext>.validateLockProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { templateValidating.lock != TemplateLock.NONE && !templateValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = templateValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}