package io.sparkled.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.persistence.playlist.PlaylistPersistenceService
import io.sparkled.persistence.scheduledjob.ScheduledJobPersistenceService
import io.sparkled.persistence.sequence.SequencePersistenceService
import io.sparkled.persistence.song.SongPersistenceService
import io.sparkled.persistence.stage.StagePersistenceService
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DbServiceImpl(
    @Named("sqlite")
    private val jdbi: Jdbi,

    override val playlist: PlaylistPersistenceService,
    override val scheduledJob: ScheduledJobPersistenceService,
    override val sequence: SequencePersistenceService,
    override val song: SongPersistenceService,
    override val stage: StagePersistenceService,
    private val objectMapper: ObjectMapper
) : DbService {

    init {
        jdbi.installPlugin(KotlinPlugin())
    }

    override fun init() {
        jdbi.withHandle<Unit, Nothing> { handle ->
            val result = handle.createQuery("SELECT 1 as test").map { rs, _ -> rs.getInt("test") }.first()
            if (result != 1) {
                throw RuntimeException("Failed to connect to database.")
            }
        }
    }

    override fun <T> query(query: DbQuery<T>): T {
        return query.execute(jdbi, objectMapper)
    }
}
