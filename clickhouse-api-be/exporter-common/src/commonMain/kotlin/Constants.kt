package tech.relialab.kotlin.clickhouse.exporter.common

import kotlinx.datetime.Instant

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
val Instant.Companion.NONE
    get() = INSTANT_NONE