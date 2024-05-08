package tech.relialab.kotlin.clickhouse.exporter.mappers.v1

import org.junit.Test
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import kotlin.test.assertEquals

class MapperTest {
    
    @Test
    fun fromTransport() {
        val req = TemplateCreateRequest(
            debug = TemplateDebug(
                mode = TemplateRequestDebugMode.STUB,
                stub = TemplateRequestDebugStubs.SUCCESS,
            ),
            template = TemplateCreateObject(
                title = "title",
                description = "desc",
                visibility = TemplateVisibility.PUBLIC,
            ),
        )

        val context = TemplateContext()
        context.fromTransport(req)

        assertEquals(TemplateStubs.SUCCESS, context.stubCase)
        assertEquals(TemplateWorkMode.STUB, context.workMode)
        assertEquals("title", context.templateRequest.title)
        assertEquals(TemplateVisibilityClient.VISIBLE_PUBLIC, context.templateRequest.visibility)
    }

    @Test
    fun toTransport() {
        val context = TemplateContext(
            requestId = TemplateRequestId("1234"),
            command = TemplateCommand.CREATE,
            templateResponse = Template(
                title = "title",
                description = "desc",
                visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            ),
            errors = mutableListOf(
                TemplateError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = TemplateState.RUNNING,
        )

        val req = context.toTransportTemplate() as TemplateCreateResponse

        assertEquals("title", req.template?.title)
        assertEquals("desc", req.template?.description)
        assertEquals(TemplateVisibility.PUBLIC, req.template?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}