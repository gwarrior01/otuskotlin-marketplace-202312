package tech.relialab.kotlin.clickhouse.exporter.app.common

import kotlinx.coroutines.test.runTest
import tech.relialab.kotlin.clickhouse.exporter.api.v1.models.*
import tech.relialab.kotlin.clickhouse.exporter.business.TemplateProcessor
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.fromTransport
import tech.relialab.kotlin.clickhouse.exporter.mappers.kmp.v1.toTransportTemplate
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerTest {

    private val request = TemplateCreateRequest(
        template = TemplateCreateObject(
            title = "some template",
            description = "some description of some template",
            visibility = TemplateVisibility.PUBLIC,
        ),
        debug = TemplateDebug(mode = TemplateRequestDebugMode.STUB, stub = TemplateRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IAppSettings = object : IAppSettings {
        override val coreSettings: CoreSettings = CoreSettings()
        override val processor: TemplateProcessor = TemplateProcessor(coreSettings)
    }

    class TestApplicationCall(private val request: BaseRequest) {
        var res: BaseResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : BaseRequest> receive(): T = request as T
        fun respond(res: BaseResponse) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createTemplateKtor(appSettings: IAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<TemplateCreateRequest>()) },
            { toTransportTemplate() },
            ControllerTest::class,
            "controller-test"
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createTemplateKtor(appSettings) }
        val res = testApp.res as TemplateCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}