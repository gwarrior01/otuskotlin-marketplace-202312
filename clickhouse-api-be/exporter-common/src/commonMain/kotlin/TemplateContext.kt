package tech.relialab.kotlin.clickhouse.exporter.common

import kotlinx.datetime.Instant
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs
import tech.relialab.kotlin.clickhouse.exporter.common.ws.ExporterWsSession

data class TemplateContext(
    var command: TemplateCommand = TemplateCommand.NONE,
    var state: TemplateState = TemplateState.NONE,
    val errors: MutableList<TemplateError> = mutableListOf(),

    var corSettings: CoreSettings = CoreSettings(),
    var workMode: TemplateWorkMode = TemplateWorkMode.PROD,
    var stubCase: TemplateStubs = TemplateStubs.NONE,
    var wsSession: ExporterWsSession = ExporterWsSession.NONE,

    var requestId: TemplateRequestId = TemplateRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var templateRequest: Template = Template(),
    var templateFilterRequest: TemplateFilter = TemplateFilter(),

    var templateResponse: Template = Template(),
    var templatesResponse: MutableList<Template> = mutableListOf(),
)