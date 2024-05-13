package tech.relialab.kotlin.clickhouse.exporter.business.stub

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class AdUpdateStubTest {

    private val processor = TemplateProcessor()
    val id = TemplateId("777")
    val title = "title 666"
    val description = "desc 666"
    val visibility = TemplateVisibilityClient.VISIBLE_PUBLIC

    @Test
    fun update() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.UPDATE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.SUCCESS,
            templateRequest = Template(
                id = id,
                title = title,
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.templateResponse.id)
        assertEquals(title, ctx.templateResponse.title)
        assertEquals(description, ctx.templateResponse.description)
        assertEquals(visibility, ctx.templateResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.UPDATE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_ID,
            templateRequest = Template(),
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.UPDATE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_TITLE,
            templateRequest = Template(
                id = id,
                title = "",
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDescription() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.UPDATE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_DESCRIPTION,
            templateRequest = Template(
                id = id,
                title = title,
                description = "",
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.UPDATE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.DB_ERROR,
            templateRequest = Template(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.UPDATE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_SEARCH_STRING,
            templateRequest = Template(
                id = id,
                title = title,
                description = description,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}