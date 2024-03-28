package tech.relialab.kotlin.clickhouse.exporter.common.models

data class TemplateError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)