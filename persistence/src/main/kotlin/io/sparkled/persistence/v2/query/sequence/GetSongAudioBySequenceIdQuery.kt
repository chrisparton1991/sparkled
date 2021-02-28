package io.sparkled.persistence.v2.query.sequence

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.v2.SongAudioEntity
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetSongAudioBySequenceIdQuery(
    private val sequenceId: Int
) : DbQuery<SongAudioEntity?> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): SongAudioEntity? {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                .bind("sequenceId", sequenceId)
                .mapTo<SongAudioEntity>()
                .findFirst().orElseGet { null }
        }
    }

    companion object {
        private val query = """
            SELECT sa.*
            FROM song_audio sa
            JOIN sequence s on sa.song_id = s.song_id
            WHERE s.id = :sequenceId
        """.trimIndent()
    }
}
