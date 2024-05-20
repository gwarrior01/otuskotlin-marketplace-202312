package tech.relialab.kotlin.clickhouse.exporter.stubs

import tech.relialab.kotlin.clickhouse.exporter.common.models.Template
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateId
import tech.relialab.kotlin.clickhouse.exporter.stubs.Stubs.TEST_TEMPLATE

object TemplateStub {
    fun get(): Template = TEST_TEMPLATE.copy()

    fun prepareResult(block: Template.() -> Unit): Template = get().apply(block)

    fun prepareSearchList(filter: String) = listOf(
        templateTest("177", filter),
        templateTest("277", filter),
        templateTest("377", filter),
        templateTest("477", filter),
        templateTest("577", filter),
        templateTest("677", filter),
    )

    private fun templateTest(id: String, filter: String) =
        templateBase(TEST_TEMPLATE, id = id, filter = filter)

    private fun templateBase(base: Template, id: String, filter: String) = base.copy(
        id = TemplateId(id),
        title = "$filter $id",
        description = "desc $filter $id",
    )

}