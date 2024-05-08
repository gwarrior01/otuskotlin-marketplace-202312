package tech.relialab.kotlin.clickhouse.exporter.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.BaseRequest
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.BaseResponse
import tech.relialab.kotlin.clickhouse.exporter.app.common.controllerHelper
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.AppSettings
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.fromTransport
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.toTransportTemplate
import kotlin.reflect.KClass

suspend inline fun <reified Q : BaseRequest, @Suppress("unused") reified R : BaseResponse> ApplicationCall.processV1(
    appSettings: AppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    {
        fromTransport(receive<Q>())
    },
    {
        respond(toTransportTemplate())
    },
    clazz,
    logId,
)