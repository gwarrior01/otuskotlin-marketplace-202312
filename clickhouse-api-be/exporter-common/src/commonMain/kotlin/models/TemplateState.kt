package tech.relialab.kotlin.clickhouse.exporter.common.models

enum class TemplateState {
    NONE,
    RUNNING,
    FAILING,
    FINISHING,
}