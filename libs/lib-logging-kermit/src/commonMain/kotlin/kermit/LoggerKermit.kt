package tech.relialab.kotlin.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import tech.relialab.kotlin.logging.common.LogWrapper
import kotlin.reflect.KClass

@Suppress("unused")
fun mpLoggerKermit(loggerId: String): LogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return LoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
    )
}

@Suppress("unused")
fun mpLoggerKermit(cls: KClass<*>): LogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return LoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName?: "",
    )
}