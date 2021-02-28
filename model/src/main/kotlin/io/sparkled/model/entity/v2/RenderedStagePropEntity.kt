package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.util.IdUtils
import java.util.UUID

@Entity("rendered_stage_prop")
data class RenderedStagePropEntity(
    val id: Int = IdUtils.NO_ID,
    val sequenceId: Int,
    val stagePropUuid: UUID,
    val ledCount: Int,
    val data: ByteArray,
)
