package tech.relialab.kotlin.clickhouse.exporter.common.exceptions

import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand


class UnknownTemplateCommand(command: TemplateCommand) : Throwable("Wrong command $command at mapping toTransport stage")
