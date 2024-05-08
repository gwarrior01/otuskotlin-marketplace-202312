package tech.relialab.kotlin.clickhouse.exporter.stubs

import tech.relialab.kotlin.clickhouse.exporter.common.models.*

object Stubs {
    val TEST_TEMPLATE: Template
        get() = Template(
            id = TemplateId("777"),
            title = "Тестовый шаблон",
            description = "Дает возможность использовать в системе stubs",
            ownerId = TemplateUserId("user-1"),
            visibility = TemplateVisibilityClient.VISIBLE_PUBLIC,
            permissionsClient = mutableSetOf(
                TemplatePermissionClient.READ,
                TemplatePermissionClient.UPDATE,
                TemplatePermissionClient.DELETE,
                TemplatePermissionClient.MAKE_VISIBLE_PUBLIC,
                TemplatePermissionClient.MAKE_VISIBLE_GROUP,
                TemplatePermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
}