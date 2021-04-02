package io.sparkled.persistence.v2.query.song

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetSongBySequenceIdQuery(
        private val sequenceId: Int
) : DbQuery<SongEntity?> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): SongEntity? {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                    .bind("sequenceId", sequenceId)
                    .mapTo<SongEntity>()
                    .findFirst().orElseGet { null }
        }
    }

    companion object {
        private const val query = """
            SELECT s.*
            FROM song s
            JOIN sequence seq ON s.id = seq.song_id
            WHERE s.id = :sequenceId
        """
    }
}
