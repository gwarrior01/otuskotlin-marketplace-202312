package tech.relialab.kotlin.clickhouse.exporter.app.ktor

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import tech.relialab.kotlin.clickhouse.exporter.api.v1.apiV1Mapper
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class V1TemplateStubApiTest {

    @Test
    fun create() = v1TestApplication(
        func = "create",
        request = TemplateCreateRequest(
            template = TemplateCreateObject(
                title = "Шаблон",
                description = "Тестовый",
                visibility = TemplateVisibility.PUBLIC,
            ),
            debug = TemplateDebug(
                mode = TemplateRequestDebugMode.STUB,
                stub = TemplateRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<TemplateCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("777", responseObj.template?.id)
    }

    @Test
    fun read() = v1TestApplication(
        func = "read",
        request = TemplateReadRequest(
            template = TemplateReadObject("777"),
            debug = TemplateDebug(
                mode = TemplateRequestDebugMode.STUB,
                stub = TemplateRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<TemplateReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("777", responseObj.template?.id)
    }

    @Test
    fun update() = v1TestApplication(
        func = "update",
        request = TemplateUpdateRequest(
            template = TemplateUpdateObject(
                id = "777",
                title = "Шаблон",
                description = "Тестовый",
                visibility = TemplateVisibility.PUBLIC,
            ),
            debug = TemplateDebug(
                mode = TemplateRequestDebugMode.STUB,
                stub = TemplateRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<TemplateUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("777", responseObj.template?.id)
    }

    @Test
    fun delete() = v1TestApplication(
        func = "delete",
        request = TemplateDeleteRequest(
            template = TemplateDeleteObject(
                id = "777",
            ),
            debug = TemplateDebug(
                mode = TemplateRequestDebugMode.STUB,
                stub = TemplateRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<TemplateDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("777", responseObj.template?.id)
    }

    @Test
    fun search() = v1TestApplication(
        func = "search",
        request = TemplateSearchRequest(
            templateFilter = TemplateSearchFilter(),
            debug = TemplateDebug(
                mode = TemplateRequestDebugMode.STUB,
                stub = TemplateRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<TemplateSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("177", responseObj.templates?.first()?.id)
    }

    private fun v1TestApplication(
        func: String,
        request: BaseRequest,
        function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(AppSettings(coreSettings = CoreSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }
        val response = client.post("/v1/template/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}