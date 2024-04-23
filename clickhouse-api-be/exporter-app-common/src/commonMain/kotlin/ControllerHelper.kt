package tech.relialab.kotlin.clickhouse.exporter.app.common

import kotlinx.datetime.Clock
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import kotlin.reflect.KClass

suspend inline fun <T> IAppSettings.controllerHelper(
    crossinline getRequest: suspend TemplateContext.() -> Unit,
    crossinline toResponse: suspend TemplateContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = coreSettings.loggerProvider.logger(clazz)
    val ctx = TemplateContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
//            data = ctx.toLog(logId) // todo
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
//            data = ctx.toLog(logId) // todo
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
//            data = ctx.toLog(logId) // todo
        )
        ctx.state = TemplateState.FAILING
//        ctx.errors.add(e.asMkplError()) // todo
        processor.exec(ctx)
        if (ctx.command == TemplateCommand.NONE) {
            ctx.command = TemplateCommand.READ
        }
        ctx.toResponse()
    }
}