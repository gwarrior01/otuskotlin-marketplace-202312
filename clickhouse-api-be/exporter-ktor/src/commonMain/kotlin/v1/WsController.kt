package tech.relialab.kotlin.clickhouse.exporter.app.ktor.v1

import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import tech.relialab.kotlin.clickhouse.exporter.api.v1.apiV1RequestDeserialize
import tech.relialab.kotlin.clickhouse.exporter.api.v1.apiV1ResponseSerialize
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.BaseRequest
import tech.relialab.kotlin.clickhouse.exporter.app.common.controllerHelper
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.AppSettings
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.base.KtorWsSessionV1
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.fromTransport
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.toTransportInit
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.toTransportTemplate
import kotlin.reflect.KClass

private val clWsV1: KClass<*> = WebSocketSession::wsHandlerV1::class
suspend fun WebSocketSession.wsHandlerV1(appSettings: AppSettings) = with(KtorWsSessionV1(this)) {
    // Обновление реестра сессий
    val sessions = appSettings.coreSettings.wsSessions
    sessions.add(this)

    // Handle init request
    appSettings.controllerHelper(
        {
            command = TemplateCommand.INIT
            wsSession = this@with
        },
        { outgoing.send(Frame.Text(apiV1ResponseSerialize(toTransportInit()))) },
        clWsV1,
        "wsV1-init"
    )

    // Handle flow
    incoming.receiveAsFlow()
        .mapNotNull { it ->
            val frame = it as? Frame.Text ?: return@mapNotNull
            // Handle without flow destruction
            try {
                appSettings.controllerHelper(
                    {
                        fromTransport(apiV1RequestDeserialize<BaseRequest>(frame.readText()))
                        wsSession = this@with
                    },
                    {
                        val result = apiV1ResponseSerialize(toTransportTemplate())
                        // If change request, response is sent to everyone
                        outgoing.send(Frame.Text(result))
                    },
                    clWsV1,
                    "wsV1-handle"
                )

            } catch (_: ClosedReceiveChannelException) {
                sessions.remove(this@with)
            } catch (e: Throwable) {
                println("FFF")
            }
        }
        .onCompletion {
            // Handle finish request
            appSettings.controllerHelper(
                {
                    command = TemplateCommand.FINISH
                    wsSession = this@with
                },
                { },
                clWsV1,
                "wsV1-finish"
            )
            sessions.remove(this@with)
        }
        .collect()
}