package tech.relialab.kotlin.clickhouse.exporter.api.v1

import kotlinx.serialization.encodeToString
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response: BaseResponse = TemplateCreateResponse(
        template = TemplateResponseObject(
            title = "Template title",
            description = "Template description",
            visibility = TemplateVisibility.PUBLIC,
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(response)

        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"title\":\\s*\"Template title\""))
        assertContains(json, Regex("\"description\":\\s*\"Template description\""))
        assertContains(json, Regex("\"visibility\":\\s*\"public\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(response)
        val obj = apiV1Mapper.decodeFromString<BaseResponse>(json) as TemplateCreateResponse

        assertEquals(response, obj)
    }
}