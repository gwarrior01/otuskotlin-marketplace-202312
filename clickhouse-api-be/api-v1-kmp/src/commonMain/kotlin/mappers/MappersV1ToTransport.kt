package tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1

import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.exceptions.UnknownTemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.common.models.*

fun TemplateContext.toTransportTemplate(): BaseResponse = when (val cmd = command) {
    TemplateCommand.CREATE -> toTransportCreate()
    TemplateCommand.READ -> toTransportRead()
    TemplateCommand.UPDATE -> toTransportUpdate()
    TemplateCommand.DELETE -> toTransportDelete()
    TemplateCommand.SEARCH -> toTransportSearch()
    TemplateCommand.NONE -> throw UnknownTemplateCommand(cmd)
}

fun TemplateContext.toTransportCreate() = TemplateCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = templateResponse.toTransportTemplate()
)

fun TemplateContext.toTransportRead() = TemplateReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = templateResponse.toTransportTemplate()
)

fun TemplateContext.toTransportUpdate() = TemplateUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = templateResponse.toTransportTemplate()
)

fun TemplateContext.toTransportDelete() = TemplateDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = templateResponse.toTransportTemplate()
)

fun TemplateContext.toTransportSearch() = TemplateSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    templates = templatesResponse.toTransportTemplate()
)

fun List<Template>.toTransportTemplate(): List<TemplateResponseObject>? = this
    .map { it.toTransportTemplate() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Template.toTransportTemplate(): TemplateResponseObject = TemplateResponseObject(
    id = id.takeIf { it != TemplateId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != TemplateUserId.NONE }?.asString(),
    visibility = visibility.toTransportTemplate(),
    permissions = permissionsClient.toTransportTemplate(),
)

private fun Set<TemplatePermissionClient>.toTransportTemplate(): Set<TemplatePermissions>? = this
    .map { it.toTransportTemplate() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun TemplatePermissionClient.toTransportTemplate() = when (this) {
    TemplatePermissionClient.READ -> TemplatePermissions.READ
    TemplatePermissionClient.UPDATE -> TemplatePermissions.UPDATE
    TemplatePermissionClient.MAKE_VISIBLE_OWNER -> TemplatePermissions.MAKE_VISIBLE_OWN
    TemplatePermissionClient.MAKE_VISIBLE_GROUP -> TemplatePermissions.MAKE_VISIBLE_GROUP
    TemplatePermissionClient.MAKE_VISIBLE_PUBLIC -> TemplatePermissions.MAKE_VISIBLE_PUBLIC
    TemplatePermissionClient.DELETE -> TemplatePermissions.DELETE
}

private fun TemplateVisibilityClient.toTransportTemplate(): TemplateVisibility? = when (this) {
    TemplateVisibilityClient.VISIBLE_PUBLIC -> TemplateVisibility.PUBLIC
    TemplateVisibilityClient.VISIBLE_TO_GROUP -> TemplateVisibility.REGISTERED_ONLY
    TemplateVisibilityClient.VISIBLE_TO_OWNER -> TemplateVisibility.OWNER_ONLY
    TemplateVisibilityClient.NONE -> null
}

private fun List<TemplateError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportTemplate() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TemplateError.toTransportTemplate() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun TemplateState.toResult(): ResponseResult? = when (this) {
    TemplateState.RUNNING -> ResponseResult.SUCCESS
    TemplateState.FAILING -> ResponseResult.ERROR
    TemplateState.FINISHING -> ResponseResult.SUCCESS
    TemplateState.NONE -> null
}