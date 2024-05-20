package tech.relialab.kotlin.clickhouse.exporter.business.validation

import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import kotlin.test.Test

class BusinessValidationCreateTest: BaseBusinessValidationTest() {
    override val command: TemplateCommand = TemplateCommand.CREATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)
}