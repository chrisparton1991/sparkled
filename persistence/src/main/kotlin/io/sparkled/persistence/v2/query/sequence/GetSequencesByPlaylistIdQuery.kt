package io.sparkled.persistence.v2.query.sequence

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetSequencesByPlaylistIdQuery(
    private val playlistId: Int?,
) : DbQuery<List<SequenceEntity>> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): List<SequenceEntity> {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                .bind("playlistId", playlistId ?: -1)
                .mapTo<SequenceEntity>()
                .list()
        }
    }

    companion object {
        private const val query = """
            SELECT s.*
            FROM sequence s
            JOIN playlist_sequence ps on s.id = ps.sequence_id
            WHERE ps.playlist_id = :playlistId
            ORDER BY ps.display_order
        """
    }
}
