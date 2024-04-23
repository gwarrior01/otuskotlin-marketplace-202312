package tech.relialab.kotlin.clickhouse.exporter.bisiness

import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.stubs.TemplateStub

@Suppress("unused", "RedundantSuspendModifier")
class TemplateProcessor(val corSettings: CoreSettings) {
    suspend fun exec(ctx: TemplateContext) {
        ctx.templateResponse = TemplateStub.get()
        ctx.templatesResponse = TemplateStub.prepareSearchList("template search").toMutableList()
        ctx.state = TemplateState.RUNNING
    }
}
