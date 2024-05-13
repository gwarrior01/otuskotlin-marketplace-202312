package tech.relialab.kotlin.clickhouse.exporter.business

import rootChain
import tech.relialab.kotlin.clickhouse.exporter.business.general.initStatus
import tech.relialab.kotlin.clickhouse.exporter.business.general.operation
import tech.relialab.kotlin.clickhouse.exporter.business.stubs.*
import tech.relialab.kotlin.clickhouse.exporter.business.validation.*
import tech.relialab.kotlin.clickhouse.exporter.common.CoreSettings
import tech.relialab.kotlin.clickhouse.exporter.common.TemplateContext
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateCommand
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateId
import tech.relialab.kotlin.clickhouse.exporter.common.models.TemplateLock
import worker

@Suppress("unused", "RedundantSuspendModifier")
class TemplateProcessor(
    val corSettings: CoreSettings = CoreSettings.NONE
) {
    suspend fun exec(ctx: TemplateContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<TemplateContext> {
        initStatus("Инициализация статуса")

        operation("Создание шаблона", TemplateCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                this.stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в templateValidating") { templateValidating = templateRequest.deepCopy() }
                worker("Очистка id") { templateValidating.id = TemplateId.NONE }
                worker("Очистка заголовка") { templateValidating.title = templateValidating.title.trim() }
                worker("Очистка описания") { templateValidating.description = templateValidating.description.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateDescriptionNotEmpty("Проверка, что описание не пусто")
                validateDescriptionHasContent("Проверка символов")

                finishTemplateValidation("Завершение проверок")
            }
        }
        operation("Получение шаблона", TemplateCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в templateValidating") { templateValidating = templateRequest.deepCopy() }
                worker("Очистка id") { templateValidating.id = TemplateId(templateValidating.id.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")

                finishTemplateValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Изменение шаблона", TemplateCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в templateValidating") { templateValidating = templateRequest.deepCopy() }
                worker("Очистка id") { templateValidating.id = TemplateId(templateValidating.id.asString().trim()) }
                worker("Очистка lock") { templateValidating.lock = TemplateLock(templateValidating.lock.asString().trim()) }
                worker("Очистка заголовка") { templateValidating.title = templateValidating.title.trim() }
                worker("Очистка описания") { templateValidating.description = templateValidating.description.trim() }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                validateTitleNotEmpty("Проверка на непустой заголовок")
                validateTitleHasContent("Проверка на наличие содержания в заголовке")
                validateDescriptionNotEmpty("Проверка на непустое описание")
                validateDescriptionHasContent("Проверка на наличие содержания в описании")

                finishTemplateValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Удаление шаблона", TemplateCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в templateValidating") { templateValidating = templateRequest.deepCopy() }
                worker("Очистка id") { templateValidating.id = TemplateId(templateValidating.id.asString().trim()) }
                worker("Очистка lock") { templateValidating.lock = TemplateLock(templateValidating.lock.asString().trim()) }
                validateIdNotEmpty("Проверка на непустой id")
                validateIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                finishTemplateValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Поиск шаблонов", TemplateCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adFilterValidating") { templateValidating = templateValidating.deepCopy() }
                validateSearchStringLength("Валидация длины строки поиска в фильтре")

                finishTemplateFilterValidation("Успешное завершение процедуры валидации")
            }
        }
    }.build()
}