package tech.relialab.kotlin.clickhouse.exporter.mappers.v1.exceptions

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to MkplContext")