package tech.relialab.kotlin.clickhouse.exporter.business.stub

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import tech.relialab.kotlin.clickhouse.exporter.stubs.TemplateStub
import kotlin.test.Test
import kotlin.test.assertEquals

class AdDeleteStubTest {

    private val processor = TemplateProcessor()
    val id = TemplateId("777")

    @Test
    fun delete() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.DELETE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.SUCCESS,
            templateRequest = Template(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = TemplateStub.get()
        assertEquals(stub.id, ctx.templateResponse.id)
        assertEquals(stub.title, ctx.templateResponse.title)
        assertEquals(stub.description, ctx.templateResponse.description)
        assertEquals(stub.visibility, ctx.templateResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.DELETE,
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
    fun databaseError() = runTest {
        val ctx = TemplateContext(
            command = TemplateCommand.DELETE,
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
            command = TemplateCommand.DELETE,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.STUB,
            stubCase = TemplateStubs.BAD_TITLE,
            templateRequest = Template(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Template(), ctx.templateResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}