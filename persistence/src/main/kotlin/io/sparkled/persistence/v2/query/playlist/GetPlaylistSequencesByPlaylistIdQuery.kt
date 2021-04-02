package io.sparkled.persistence.v2.query.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.v2.PlaylistSequenceEntity
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetPlaylistSequencesByPlaylistIdQuery(
    private val playlistId: Int?,
) : DbQuery<List<PlaylistSequenceEntity>> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): List<PlaylistSequenceEntity> {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                .bind("playlistId", playlistId ?: -1)
                .mapTo<PlaylistSequenceEntity>()
                .list()
        }
    }

    companion object {
        private val query = """
            SELECT *
            FROM playlist_sequence
            WHERE playlist_id = :playlistId
            ORDER BY display_order
        """.trimIndent()
    }
}
