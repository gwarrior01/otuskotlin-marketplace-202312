package tech.relialab.kotlin.clickhouse.exporter.business.stub

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import tech.relialab.kotlin.clickhouse.exporter.stubs.TemplateStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class AdSearchStubTest {

    private val processor = TemplateProcessor()
    val filter = TemplateFilter(searchString = "show")

    @Test
    fun search() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.SEARCH,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.SUCCESS,
            templateFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.templatesResponse.size > 1)
        val first = ctx.templatesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (TemplateStub.get()) {
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.SEARCH,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_ID,
            templateFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.SEARCH,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.DB_ERROR,
            templateFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.SEARCH,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_TITLE,
            templateFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}