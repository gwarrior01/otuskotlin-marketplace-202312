package tech.relialab.kotlin.clickhouse.exporter.api.log.mapper

import kotlinx.datetime.Clock
import tech.relialab.kotlin.clickhouse.exporter.api.log.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*

fun TemplateContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "exporter",
    ad = toTemplateLog(),
    errors = errors.map { it.toLog() },
)

private fun TemplateContext.toTemplateLog(): TemplateLogModel? {
    val adNone = Template()
    return TemplateLogModel(
        requestId = requestId.takeIf { it != TemplateRequestId.NONE }?.asString(),
        requestTemplate = templateRequest.takeIf { it != adNone }?.toLog(),
        responseTemplate = templateResponse.takeIf { it != adNone }?.toLog(),
        responseTemplates = templatesResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
        requestFilter = templateFilterRequest.takeIf { it != TemplateFilter() }?.toLog(),
    ).takeIf { it != TemplateLogModel() }
}

private fun TemplateFilter.toLog() = TemplateFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != TemplateUserId.NONE }?.asString()
)

private fun TemplateError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun Template.toLog() = TemplateLog(
    id = id.takeIf { it != TemplateId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    visibility = visibility.takeIf { it != TemplateVisibilityClient.NONE }?.name,
    ownerId = ownerId.takeIf { it != TemplateUserId.NONE }?.asString(),
    permissionsClient = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)