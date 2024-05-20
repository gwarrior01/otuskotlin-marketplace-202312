package tech.relialab.kotlin.clickhouse.exporter.business.validation

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TemplateState.FAILING, ctx.state)
}

fun validationIdTrim(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId(" \n\t 123-234-abc-ABC \n\t "),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TemplateState.FAILING, ctx.state)
}

fun validationIdEmpty(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId(""),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TemplateState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationIdFormat(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId("!@#\$%^&*(),.{}"),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TemplateState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}