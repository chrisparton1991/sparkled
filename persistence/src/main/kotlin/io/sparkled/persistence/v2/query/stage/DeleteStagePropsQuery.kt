package io.sparkled.persistence.v2.query.stage

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory
import java.util.UUID

class DeleteStagePropsQuery(
    private val stagePropUuids: Collection<UUID>
) : DbQuery<Unit> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper) {
        jdbi.perform { handle ->
            val rowsDeleted = handle
                .createUpdate(query)
                .bindList("stagePropUuids", stagePropUuids)
                .execute()

            logger.info("Deleted $rowsDeleted stage prop(s).")
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DeleteStagePropsQuery::class.java)
        private val query = """
            DELETE FROM rendered_stage_prop WHERE stage_prop_uuid IN (<stagePropUuids>);
            DELETE FROM sequence_channel WHERE stage_prop_uuid IN (<stagePropUuids>);
            DELETE FROM stage_prop WHERE uuid IN (<stagePropUuids>);
        """.trimIndent()
    }
}
