package tech.relialab.kotlin.clickhouse.exporter.common.models

data class TemplateFilter(
    var searchString: String = "",
    var ownerId: TemplateUserId = TemplateUserId.NONE,
)