package io.sparkled.persistence.v2.query.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.v2.partial.PlaylistSummaryEntityPartial
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetPlaylistSummariesQuery : DbQuery<List<PlaylistSummaryEntityPartial>> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): List<PlaylistSummaryEntityPartial> {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                .mapTo<PlaylistSummaryEntityPartial>()
                .list()
        }
    }

    companion object {
        private val query = """
            SELECT p.id as playlistId, COUNT(s.id) as sequenceCount, SUM(so.duration_ms) as durationSeconds
            FROM playlist p
            LEFT JOIN playlist_sequence ps on p.id = ps.playlist_id
            LEFT JOIN sequence s on ps.sequence_id = s.id
            LEFT JOIN song so on s.song_id = so.id
            GROUP BY p.id
        """.trimIndent()
    }
}
