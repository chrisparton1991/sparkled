package io.sparkled.persistence.v2.query.sequence

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.QSequenceChannel.Companion.sequenceChannel
import io.sparkled.model.entity.SequenceChannel
import io.sparkled.model.entity.v2.SequenceChannelEntity
import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.persistence.DbQuery
import io.sparkled.persistence.QueryFactory
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetSequenceChannelsBySequenceIdQuery(
    private val sequenceId: Int
) : DbQuery<SequenceChannelEntity?> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): SequenceChannelEntity? {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                .bind("sequenceId", sequenceId)
                .mapTo<SequenceChannelEntity>()
                .findFirst().orElseGet { null }
        }
    }

    companion object {
        private val query = """
            SELECT *
            FROM sequence_channel
            WHERE sequence_id = :sequenceId
            ORDER BY display_order
        """.trimIndent()
    }
}
