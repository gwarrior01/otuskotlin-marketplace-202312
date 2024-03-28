package tech.relialab.kotlin.clickhouse.exporter.common.models

enum class TemplatePermissionClient {
    READ,
    UPDATE,
    DELETE,
    MAKE_VISIBLE_PUBLIC,
    MAKE_VISIBLE_GROUP,
    MAKE_VISIBLE_OWNER,
}