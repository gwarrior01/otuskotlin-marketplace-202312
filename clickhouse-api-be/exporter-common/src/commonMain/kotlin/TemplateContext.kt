package tech.relialab.kotlin.clickhouse.exporter.common

import kotlinx.datetime.Instant
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.common.stubs.TemplateStubs

data class TemplateContext(
    var command: TemplateCommand = TemplateCommand.NONE,
    var state: TemplateState = TemplateState.NONE,
    val errors: MutableList<TemplateError> = mutableListOf(),

    var workMode: TemplateWorkMode = TemplateWorkMode.PROD,
    var stubCase: TemplateStubs = TemplateStubs.NONE,

    var requestId: TemplateRequestId = TemplateRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var adRequest: Template = Template(),
    var adFilterRequest: TemplateFilter = TemplateFilter(),

    var adResponse: Template = Template(),
    var adsResponse: MutableList<Template> = mutableListOf(),
)