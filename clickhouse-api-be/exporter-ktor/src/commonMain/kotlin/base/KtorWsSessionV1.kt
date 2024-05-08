package tech.relialab.kotlin.clickhouse.exporter.app.ktor.base

import io.ktor.websocket.*
import tech.relialab.kotlin.clickhouse.exporter.api.v1.apiV1ResponseSerialize
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.BaseResponse
import tech.relialab.kotlin.clickhouse.exporter.common.ws.ExporterWsSession

data class KtorWsSessionV1(
    private val session: WebSocketSession
) : ExporterWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is BaseResponse)
        session.send(Frame.Text(apiV1ResponseSerialize(obj)))
    }
}