package io.sparkled.persistence.v2.query.sequence

import com.fasterxml.jackson.databind.ObjectMapper
import io.sparkled.model.entity.v2.RenderedStagePropEntity
import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.model.render.RenderedStagePropData
import io.sparkled.model.render.RenderedStagePropDataMap
import io.sparkled.model.util.SequenceUtils
import io.sparkled.persistence.DbQuery
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class GetRenderedStagePropsBySequenceQuery(
    private val sequence: SequenceEntity,
    private val song: SongEntity
) : DbQuery<RenderedStagePropDataMap> {

    override fun execute(jdbi: Jdbi, objectMapper: ObjectMapper): RenderedStagePropDataMap {
        return jdbi.perform { handle ->
            handle.createQuery(query)
                .bind("sequenceId", sequence.id)
                .mapTo<RenderedStagePropEntity>()
                .list()
                .associateTo(RenderedStagePropDataMap()) {
                    it.stagePropUuid to RenderedStagePropData(
                        0,
                        SequenceUtils.getFrameCount(song, sequence) - 1,
                        it.ledCount,
                        it.data
                    )
                }
        }
    }

    companion object {
        private const val query = "SELECT * FROM rendered_stage_prop WHERE sequence_id = :sequenceId"
    }
}
