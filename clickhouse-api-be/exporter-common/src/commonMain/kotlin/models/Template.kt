package tech.relialab.kotlin.clickhouse.exporter.common.models

data class Template(
    var id: TemplateId = TemplateId.NONE,
    var title: String = "",
    var description: String = "",
    var ownerId: TemplateUserId = TemplateUserId.NONE,
    var visibility: TemplateVisibilityClient = TemplateVisibilityClient.NONE,
    var lock: TemplateLock = TemplateLock.NONE,
    val permissionsClient: MutableSet<TemplatePermissionClient> = mutableSetOf()
) {
    fun deepCopy(): Template = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Template()
    }

}