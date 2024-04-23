package tech.relialab.kotlin.clickhouse.exporter.common

import tech.relialab.kotlin.logging.common.LoggerProvider

data class CoreSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
//    val wsSessions: IMkplWsSessionRepo = IMkplWsSessionRepo.NONE,
) {
    companion object {
        val NONE = CoreSettings()
    }
}
