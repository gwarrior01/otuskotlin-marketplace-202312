package tech.relialab.kotlin.clickhouse.exporter.business.validation

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateFilter
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateState
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BusinessValidationSearchTest : BaseBusinessValidationTest() {
    override val command = TemplateCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = TemplateContext(
            command = command,
            state = TemplateState.NONE,
            workMode = TemplateWorkMode.TEST,
            templateFilterRequest = TemplateFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(TemplateState.FAILING, ctx.state)
    }
}