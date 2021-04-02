package io.sparkled.persistence.v2.query.playlist

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory

class DeletePlaylistSequencesByPlaylistIdQuery(
    private val playlistId: Int
) : DbQuery<Unit> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper) {
        jdbi.perform { handle ->
            val rowsDeleted = handle
                .createUpdate(query)
                .bind("playlistId", playlistId)
                .execute()

            logger.info("Deleted $rowsDeleted playlist sequence(s).")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DeletePlaylistSequencesByPlaylistIdQuery::class.java)
        private const val query = "DELETE FROM playlist_sequence WHERE playlist_id = :playlistId"
    }
}
