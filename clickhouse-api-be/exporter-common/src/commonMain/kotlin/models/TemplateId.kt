package tech.relialab.kotlin.clickhouse.exporter.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class TemplateId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TemplateId("")
    }
}