package tech.relialab.kotlin.clickhouse.exporter.api.v1

import kotlinx.serialization.encodeToString
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request: BaseRequest = TemplateCreateRequest(
        debug = TemplateDebug(
            mode = TemplateRequestDebugMode.STUB,
            stub = TemplateRequestDebugStubs.BAD_TITLE
        ),
        template = TemplateCreateObject(
            title = "Template title",
            description = "Template description",
            visibility = TemplateVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(BaseRequest.serializer(), request)

        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"title\":\\s*\"Template title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"description\":\\s*\"Template description\""))
        assertContains(json, Regex("\"visibility\":\\s*\"public\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString<BaseRequest>(json) as TemplateCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"template": null}
        """.trimIndent()
        val obj = apiV1Mapper.decodeFromString<TemplateCreateRequest>(jsonString)

        assertEquals(null, obj.template)
    }
}