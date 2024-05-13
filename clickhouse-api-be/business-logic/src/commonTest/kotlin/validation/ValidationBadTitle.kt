package tech.relialab.kotlin.clickhouse.exporter.business.validation

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import tech.relialab.kotlin.clickhouse.exporter.stubs.TemplateStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = TemplateStub.get()

fun validationTitleCorrect(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = stub.id,
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TemplateState.FAILING, ctx.state)
    assertEquals("abc", ctx.templateValidated.title)
}

fun validationTitleTrim(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = stub.id,
            title = " \n\t abc \t\n ",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TemplateState.FAILING, ctx.state)
    assertEquals("abc", ctx.templateValidated.title)
}

fun validationTitleEmpty(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = stub.id,
            title = "",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TemplateState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

fun validationTitleSymbols(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TemplateState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}