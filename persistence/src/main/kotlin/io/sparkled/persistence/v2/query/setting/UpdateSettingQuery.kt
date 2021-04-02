package io.sparkled.persistence.v2.query.setting

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi

class UpdateSettingQuery(
    private val code: String,
    private val value: String
) : DbQuery<Unit> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper) {
        jdbi.perform { handle ->
            handle
                .createUpdate(query)
                .bind("code", code)
                .bind("value", value)
                .execute()
        }
    }

    companion object {
        private val query = """
            INSERT INTO setting (code, value)
            VALUES(:code, :value) 
            ON CONFLICT(code) 
            DO UPDATE SET value=excluded.value;
        """.trimIndent()
    }
}
