package tech.relialab.kotlin.clickhouse.exporter.common.models
enum class TemplateCommand {
    NONE,
    CREATE,
    READ,
    UPDATE,
    DELETE,
    SEARCH,
}