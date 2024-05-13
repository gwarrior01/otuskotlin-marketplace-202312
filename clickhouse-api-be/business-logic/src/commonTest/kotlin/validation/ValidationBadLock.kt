package validation

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationLockCorrect(command: TemplateCommand, processor: TemplateProcessor) = runTest {
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

fun validationLockTrim(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock(" \n\t 123-234-abc-ABC \n\t "),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TemplateState.FAILING, ctx.state)
}

fun validationLockEmpty(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock(""),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TemplateState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationLockFormat(command: TemplateCommand, processor: TemplateProcessor) = runTest {
    val ctx = TemplateContext(
        command = command,
        state = TemplateState.NONE,
        workMode = TemplateWorkMode.TEST,
        templateRequest = Template(
            id = TemplateId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            lock = TemplateLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(TemplateState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}