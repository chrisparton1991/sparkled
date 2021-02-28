package io.sparkled.persistence.v2.query.sequence

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory

class DeleteSequencesQuery(
    private val sequenceIds: Collection<Int>
) : DbQuery<Unit> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper) {
        jdbi.perform { handle ->
            val rowsDeleted = handle
                .createUpdate(query)
                .bindList("sequenceIds", sequenceIds)
                .execute()

            logger.info("Deleted $rowsDeleted sequence(s).")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DeleteSequencesQuery::class.java)
        private val query = """
            DELETE FROM playlist_sequence WHERE sequence_Id IN (<sequenceIds>);
            DELETE FROM rendered_stage_prop WHERE sequence_Id IN (<sequenceIds>);
            DELETE FROM sequence_channel WHERE sequence_Id IN (<sequenceIds>);
            DELETE FROM sequence WHERE id IN (<sequenceIds>);
        """.trimIndent()
    }
}
