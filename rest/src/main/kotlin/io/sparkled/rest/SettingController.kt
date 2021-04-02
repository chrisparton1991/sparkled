package io.sparkled.rest

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Put
import io.micronaut.spring.tx.annotation.Transactional
import io.sparkled.model.entity.Setting
import io.sparkled.model.entity.v2.SettingEntity
import io.sparkled.model.setting.SettingsConstants
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getAll
import io.sparkled.persistence.getById
import io.sparkled.persistence.v2.query.setting.UpdateSettingQuery

@Controller("/api/settings")
open class SettingController(
    private val db: DbService
) {

    @Get("/")
    @Transactional(readOnly = true)
    open fun getAllSettings(): HttpResponse<Any> {
        val settings = db.getAll<SettingEntity>()
        return HttpResponse.ok(settings)
    }

    @Get("/{code}")
    @Transactional(readOnly = true)
    open fun getSetting(code: String): HttpResponse<Any> {

        return if (code == SettingsConstants.Brightness.CODE) {
            val setting = db.getById<SettingEntity>(SettingsConstants.Brightness.CODE)
            HttpResponse.ok(setting)
        } else {
            return HttpResponse.notFound()
        }
    }

    @Put("/{code}")
    @Transactional
    open fun updateSetting(code: String, setting: Setting): HttpResponse<Any> {
        db.query(UpdateSettingQuery(
            code = SettingsConstants.Brightness.CODE,
            value = setting.getValue() ?: "0"
        ))

        return HttpResponse.ok()
    }
}
