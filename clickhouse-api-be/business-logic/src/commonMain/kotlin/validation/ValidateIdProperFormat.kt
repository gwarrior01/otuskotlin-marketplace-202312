package tech.relialab.kotlin.clickhouse.exporter.business.validation

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.errorValidation
import tech.relialab.kotlin.clickhouse.exporter.common.helpers.fail
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateId
import worker

fun ICorChainDsl<TemplateContext>.validateIdProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z#:-]+$")
    on { templateValidating.id != TemplateId.NONE && !templateValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = templateValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}