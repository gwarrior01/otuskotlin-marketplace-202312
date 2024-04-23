package tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1

import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs

fun TemplateContext.fromTransport(request: BaseRequest) = when (request) {
    is TemplateCreateRequest -> fromTransport(request)
    is TemplateReadRequest -> fromTransport(request)
    is TemplateUpdateRequest -> fromTransport(request)
    is TemplateDeleteRequest -> fromTransport(request)
    is TemplateSearchRequest -> fromTransport(request)
}

private fun String?.toTemplateId() = this?.let { TemplateId(it) } ?: TemplateId.NONE
private fun String?.toTemplateLock() = this?.let { TemplateLock(it) } ?: TemplateLock.NONE

private fun TemplateDebug?.transportToWorkMode(): TemplateWorkMode = when (this?.mode) {
    TemplateRequestDebugMode.PROD -> TemplateWorkMode.PROD
    TemplateRequestDebugMode.TEST -> TemplateWorkMode.TEST
    TemplateRequestDebugMode.STUB -> TemplateWorkMode.STUB
    null -> TemplateWorkMode.PROD
}

private fun TemplateDebug?.transportToStubCase(): TemplateStubs = when (this?.stub) {
    TemplateRequestDebugStubs.SUCCESS -> TemplateStubs.SUCCESS
    TemplateRequestDebugStubs.NOT_FOUND -> TemplateStubs.NOT_FOUND
    TemplateRequestDebugStubs.BAD_ID -> TemplateStubs.BAD_ID
    TemplateRequestDebugStubs.BAD_TITLE -> TemplateStubs.BAD_TITLE
    TemplateRequestDebugStubs.BAD_DESCRIPTION -> TemplateStubs.BAD_DESCRIPTION
    TemplateRequestDebugStubs.BAD_VISIBILITY -> TemplateStubs.BAD_VISIBILITY
    TemplateRequestDebugStubs.CANNOT_DELETE -> TemplateStubs.CANNOT_DELETE
    TemplateRequestDebugStubs.BAD_SEARCH_STRING -> TemplateStubs.BAD_SEARCH_STRING
    null -> TemplateStubs.NONE
}

fun TemplateContext.fromTransport(request: TemplateCreateRequest) {
    command = TemplateCommand.CREATE
    templateRequest = request.template?.toInternal() ?: Template()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TemplateContext.fromTransport(request: TemplateReadRequest) {
    command = TemplateCommand.READ
    templateRequest = request.template.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TemplateReadObject?.toInternal(): Template = if (this != null) {
    Template(id = id.toTemplateId())
} else {
    Template()
}

fun TemplateContext.fromTransport(request: TemplateUpdateRequest) {
    command = TemplateCommand.UPDATE
    templateRequest = request.template?.toInternal() ?: Template()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TemplateContext.fromTransport(request: TemplateDeleteRequest) {
    command = TemplateCommand.DELETE
    templateRequest = request.template.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TemplateDeleteObject?.toInternal(): Template = if (this != null) {
    Template(
        id = id.toTemplateId(),
        lock = lock.toTemplateLock(),
    )
} else {
    Template()
}

fun TemplateContext.fromTransport(request: TemplateSearchRequest) {
    command = TemplateCommand.SEARCH
    templateFilterRequest = request.templateFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TemplateSearchFilter?.toInternal(): TemplateFilter = TemplateFilter(
    searchString = this?.searchString ?: ""
)

private fun TemplateCreateObject.toInternal(): Template = Template(
    title = this.title ?: "",
    description = this.description ?: "",
    visibility = this.visibility.fromTransport(),
)

private fun TemplateUpdateObject.toInternal(): Template = Template(
    id = this.id.toTemplateId(),
    title = this.title ?: "",
    description = this.description ?: "",
    visibility = this.visibility.fromTransport(),
    lock = lock.toTemplateLock(),
)

private fun TemplateVisibility?.fromTransport(): TemplateVisibilityClient = when (this) {
    TemplateVisibility.PUBLIC -> TemplateVisibilityClient.VISIBLE_PUBLIC
    TemplateVisibility.OWNER_ONLY -> TemplateVisibilityClient.VISIBLE_TO_OWNER
    TemplateVisibility.REGISTERED_ONLY -> TemplateVisibilityClient.VISIBLE_TO_GROUP
    null -> TemplateVisibilityClient.NONE
}