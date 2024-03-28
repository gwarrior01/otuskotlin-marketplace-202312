package tech.relialab.kotlin.clickhouse.exporter.common.models

enum class TemplateVisibilityClient {
    NONE,
    VISIBLE_TO_OWNER,
    VISIBLE_TO_GROUP,
    VISIBLE_PUBLIC,
}