package tech.relialab.kotlin.clickhouse.exporter.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import tech.relialab.kotlin.clickhouse.exporter.app.ktor.AppSettings

fun Route.v1Template(appSettings: AppSettings) {
    route("template") {
        post("create") {
            call.createTemplate(appSettings)
        }
        post("read") {
            call.readTemplate(appSettings)
        }
        post("update") {
            call.updateTemplate(appSettings)
        }
        post("delete") {
            call.deleteTemplate(appSettings)
        }
        post("search") {
            call.searchTemplate(appSettings)
        }
    }
}