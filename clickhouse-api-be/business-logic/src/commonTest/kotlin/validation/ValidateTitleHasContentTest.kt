package tech.relialab.kotlin.clickhouse.exporter.business.validation

import kotlinx.coroutines.test.runTest
import rootChain
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.Template
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateFilter
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleHasContentTest {

    @Test
    fun emptyString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateValidating = Template(title = ""))
        chain.exec(ctx)
        assertEquals(TemplateState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun noContent() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateValidating = Template(title = "12!@#$%^&*()_+-="))
        chain.exec(ctx)
        assertEquals(TemplateState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateFilterValidating = TemplateFilter(searchString = "Ж"))
        chain.exec(ctx)
        assertEquals(TemplateState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain {
            validateTitleHasContent("")
        }.build()
    }
}