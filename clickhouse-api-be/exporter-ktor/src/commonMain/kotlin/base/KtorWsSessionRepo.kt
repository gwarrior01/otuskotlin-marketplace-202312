package tech.relialab.kotlin.clickhouse.exporter.app.ktor.base

import tech.relialab.kotlin.clickhouse.exporter.common.ws.ExporterWsSession
import tech.relialab.kotlin.clickhouse.exporter.common.ws.ExporterWsSessionRepo

class KtorWsSessionRepo: ExporterWsSessionRepo {
    private val sessions: MutableSet<ExporterWsSession> = mutableSetOf()
    override fun add(session: ExporterWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: ExporterWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}
