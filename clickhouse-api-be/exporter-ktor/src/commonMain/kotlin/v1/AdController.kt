package tech.relialab.kotlin.clickhouse.exporter.app.ktor.v1

import io.ktor.server.application.*
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.AppSettings
import kotlin.reflect.KClass

val clCreate: KClass<*> = ApplicationCall::createTemplate::class
suspend fun ApplicationCall.createTemplate(appSettings: AppSettings) =
    processV1<TemplateCreateRequest, TemplateCreateResponse>(appSettings, clCreate,"create")

val clRead: KClass<*> = ApplicationCall::createTemplate::class
suspend fun ApplicationCall.readTemplate(appSettings: AppSettings) =
    processV1<TemplateReadRequest, TemplateReadResponse>(appSettings, clRead, "read")

val clUpdate: KClass<*> = ApplicationCall::createTemplate::class
suspend fun ApplicationCall.updateTemplate(appSettings: AppSettings) =
    processV1<TemplateUpdateRequest, TemplateUpdateResponse>(appSettings, clUpdate, "update")

val clDelete: KClass<*> = ApplicationCall::createTemplate::class
suspend fun ApplicationCall.deleteTemplate(appSettings: AppSettings) =
    processV1<TemplateDeleteRequest, TemplateDeleteResponse>(appSettings, clDelete, "delete")

val clSearch: KClass<*> = ApplicationCall::createTemplate::class
suspend fun ApplicationCall.searchTemplate(appSettings: AppSettings) =
    processV1<TemplateSearchRequest, TemplateSearchResponse>(appSettings, clSearch, "search")