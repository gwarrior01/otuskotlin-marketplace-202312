@file:Suppress("unused")

package tech.relialab.kotlin.clickhouse.exporter.api.v1

import kotlinx.serialization.json.Json
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.BaseRequest
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.BaseResponse

@Suppress("JSON_FORMAT_REDUNDANT_DEFAULT")
val apiV1Mapper = Json {
    ignoreUnknownKeys = true
}

@Suppress("UNCHECKED_CAST")
fun <T : BaseRequest> apiV2RequestDeserialize(json: String) =
    apiV1Mapper.decodeFromString<BaseRequest>(json) as T

fun apiV2ResponseSerialize(obj: BaseResponse): String =
    apiV1Mapper.encodeToString(BaseResponse.serializer(), obj)

@Suppress("UNCHECKED_CAST")
fun <T : BaseResponse> apiV2ResponseDeserialize(json: String) =
    apiV1Mapper.decodeFromString<BaseResponse>(json) as T

@Suppress("unused")
fun apiV2RequestSerialize(obj: BaseRequest): String =
    apiV1Mapper.encodeToString(BaseRequest.serializer(), obj)
