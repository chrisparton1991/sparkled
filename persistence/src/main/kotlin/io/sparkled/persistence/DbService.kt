package io.sparkled.persistence

import io.sparkled.model.util.IdUtils
import io.sparkled.persistence.playlist.PlaylistPersistenceService
import io.sparkled.persistence.scheduledjob.ScheduledJobPersistenceService
import io.sparkled.persistence.sequence.SequencePersistenceService
import io.sparkled.persistence.setting.SettingPersistenceService
import io.sparkled.persistence.song.SongPersistenceService
import io.sparkled.persistence.stage.StagePersistenceService
import io.sparkled.persistence.v2.query.common.*

interface DbService {
    val playlist: PlaylistPersistenceService
    val scheduledJob: ScheduledJobPersistenceService
    val sequence: SequencePersistenceService
    val setting: SettingPersistenceService
    val song: SongPersistenceService
    val stage: StagePersistenceService
    fun init()
    fun <T> query(query: DbQuery<T>): T
}

inline fun <reified T : Any> DbService.getAll(
    orderBy: String? = null,
    desc: Boolean = false
): List<T> {
    return query(GetAllQuery(T::class, orderBy, desc))
}

inline fun <reified T : Any> DbService.getById(id: Int?): T? {
    return query(GetByIdQuery(T::class, id ?: IdUtils.NO_ID))
}

inline fun <reified T : Any> DbService.insert(entity: T): String {
    return query(InsertQuery(entity))
}

inline fun <reified T : Any> DbService.update(entity: T, vararg fieldsToUpdate: String) {
    return query(UpdateQuery(entity, *fieldsToUpdate))
}

inline fun <reified T : Any> DbService.delete(entity: T) {
    return query(DeleteQuery(entity))
}
