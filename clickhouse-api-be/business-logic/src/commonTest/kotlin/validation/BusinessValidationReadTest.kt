package tech.relialab.kotlin.clickhouse.exporter.business.validation

import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import kotlin.test.Test

class BusinessValidationReadTest: BaseBusinessValidationTest() {
    override val command = TemplateCommand.READ

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)
}