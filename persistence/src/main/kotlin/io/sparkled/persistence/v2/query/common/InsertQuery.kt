package io.sparkled.persistence.v2.query.common

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.annotation.Entity
import io.sparkled.persistence.DbQuery
import io.sparkled.persistence.v2.util.toSnakeCase
import org.jdbi.v3.core.Jdbi
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

class InsertQuery<T : Any>(
    private val entity: T
) : DbQuery<String> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): String {
        val properties = entity::class.memberProperties
        val entityAnnotation = entity::class.findAnnotation<Entity>()!!

        val query = queryCache.computeIfAbsent(entity::class) {

            val tableName = entityAnnotation.name
            val names = properties
                .filter { it.name != entityAnnotation.idField }
                .map { toSnakeCase(it.name) }
            val valueTemplates = names.map { ":$it" }

            """
                INSERT INTO $tableName(${names.joinToString(",")})
                VALUES (${valueTemplates.joinToString(",")})
            """.trimIndent()
        }

        val result = jdbi.perform { handle ->
            handle.createUpdate(query).apply {
                properties.forEach {
                    bind(toSnakeCase(it.name), it.getter.call(entity))
                }
            }.executeAndReturnGeneratedKeys(toSnakeCase(entityAnnotation.idField))
        }

        return result.mapTo(String::class.java).first()
    }

    companion object {
        private val queryCache = ConcurrentHashMap<KClass<out Any>, String>()
    }
}
