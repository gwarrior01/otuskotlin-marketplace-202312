package tech.relialab.kotlin.clickhouse.exporter.mappers.v1

import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.exceptions.UnknownTemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.common.models.*


fun TemplateContext.toTransportAd(): BaseResponse = when (val cmd = command) {
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
    template = adResponse.toTransportAd()
)

fun TemplateContext.toTransportRead() = TemplateReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = adResponse.toTransportAd()
)

fun TemplateContext.toTransportUpdate() = TemplateUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = adResponse.toTransportAd()
)

fun TemplateContext.toTransportDelete() = TemplateDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    template = adResponse.toTransportAd()
)

fun TemplateContext.toTransportSearch() = TemplateSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    templates = adsResponse.toTransportAd()
)

fun List<Template>.toTransportAd(): List<TemplateResponseObject>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Template.toTransportAd(): TemplateResponseObject = TemplateResponseObject(
    id = id.takeIf { it != TemplateId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != TemplateUserId.NONE }?.asString(),
    visibility = visibility.toTransportAd(),
    permissions = permissionsClient.toTransportAd(),
)

private fun Set<TemplatePermissionClient>.toTransportAd(): Set<TemplatePermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun TemplatePermissionClient.toTransportAd() = when (this) {
    TemplatePermissionClient.READ -> TemplatePermissions.READ
    TemplatePermissionClient.UPDATE -> TemplatePermissions.UPDATE
    TemplatePermissionClient.MAKE_VISIBLE_OWNER -> TemplatePermissions.MAKE_VISIBLE_OWN
    TemplatePermissionClient.MAKE_VISIBLE_GROUP -> TemplatePermissions.MAKE_VISIBLE_GROUP
    TemplatePermissionClient.MAKE_VISIBLE_PUBLIC -> TemplatePermissions.MAKE_VISIBLE_PUBLIC
    TemplatePermissionClient.DELETE -> TemplatePermissions.DELETE
}

private fun TemplateVisibilityClient.toTransportAd(): TemplateVisibility? = when (this) {
    TemplateVisibilityClient.VISIBLE_PUBLIC -> TemplateVisibility.PUBLIC
    TemplateVisibilityClient.VISIBLE_TO_GROUP -> TemplateVisibility.REGISTERED_ONLY
    TemplateVisibilityClient.VISIBLE_TO_OWNER -> TemplateVisibility.OWNER_ONLY
    TemplateVisibilityClient.NONE -> null
}

private fun List<TemplateError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TemplateError.toTransportAd() = Error(
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