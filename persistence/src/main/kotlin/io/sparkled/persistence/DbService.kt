package io.sparkled.persistence

import io.sparkled.model.util.IdUtils
import io.sparkled.persistence.v2.query.common.*

interface DbService {
    fun init()
    fun <T> query(query: DbQuery<T>): T
}

inline fun <reified T : Any> DbService.getAll(
    orderBy: String? = null,
    desc: Boolean = false
): List<T> {
    return query(GetAllQuery(T::class, orderBy, desc))
}

inline fun <reified T : Any> DbService.getById(id: Any?): T? {
    return query(GetByIdQuery(T::class, id ?: IdUtils.NO_ID))
}

inline fun <reified T : Any> DbService.insert(entity: T): String {
    return query(InsertQuery(entity))
}

inline fun <reified T : Any> DbService.update(entity: T, vararg fieldsToUpdate: String) {
    return query(UpdateQuery(entity, *fieldsToUpdate))
}

inline fun <reified T : Any> DbService.deleteById(id: Any) {
    getById<T>(id)?.let {
        delete(it)
    }
}

inline fun <reified T : Any> DbService.delete(entity: T) {
    return query(DeleteQuery(entity))
}
