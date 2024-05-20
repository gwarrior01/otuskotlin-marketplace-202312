package tech.relialab.kotlin.clickhouse.exporter.business.validation

import kotlinx.coroutines.test.runTest
import rootChain
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateFilter
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {

    @Test
    fun emptyString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateFilterValidating = TemplateFilter(searchString = ""))
        chain.exec(ctx)
        assertEquals(TemplateState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateFilterValidating = TemplateFilter(searchString = "  "))
        chain.exec(ctx)
        assertEquals(TemplateState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateFilterValidating = TemplateFilter(searchString = "12"))
        chain.exec(ctx)
        assertEquals(TemplateState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateFilterValidating = TemplateFilter(searchString = "123"))
        chain.exec(ctx)
        assertEquals(TemplateState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = TemplateContext(state = TemplateState.RUNNING, templateFilterValidating = TemplateFilter(searchString = "12".repeat(51)))
        chain.exec(ctx)
        assertEquals(TemplateState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchStringLength("")
        }.build()
    }
}