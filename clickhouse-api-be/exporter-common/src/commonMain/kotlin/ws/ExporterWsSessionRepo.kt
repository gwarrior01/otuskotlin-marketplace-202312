package tech.relialab.kotlin.clickhouse.exporter.common.ws

interface ExporterWsSessionRepo {
    fun add(session: ExporterWsSession)
    fun clearAll()
    fun remove(session: ExporterWsSession)
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : ExporterWsSessionRepo {
            override fun add(session: ExporterWsSession) {}
            override fun clearAll() {}
            override fun remove(session: ExporterWsSession) {}
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
