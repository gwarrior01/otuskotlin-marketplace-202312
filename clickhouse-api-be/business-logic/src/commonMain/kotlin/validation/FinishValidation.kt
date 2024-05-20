package tech.relialab.kotlin.clickhouse.exporter.business.validation

import ICorChainDsl
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import worker

fun ICorChainDsl<TemplateContext>.finishTemplateValidation(title: String) = worker {
    this.title = title
    on { state == TemplateState.RUNNING }
    handle {
        templateValidated = templateValidating
    }
}

fun ICorChainDsl<TemplateContext>.finishTemplateFilterValidation(title: String) = worker {
    this.title = title
    on { state == TemplateState.RUNNING }
    handle {
        templateFilterValidated = templateFilterValidating
    }
}