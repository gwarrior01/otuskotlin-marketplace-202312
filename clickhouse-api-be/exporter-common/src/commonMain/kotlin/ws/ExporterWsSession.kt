package tech.relialab.kotlin.clickhouse.exporter.common.ws

interface ExporterWsSession {
    suspend fun <T> send(obj: T)
    companion object {
        val NONE = object : ExporterWsSession {
            override suspend fun <T> send(obj: T) {

            }
        }
    }
}
