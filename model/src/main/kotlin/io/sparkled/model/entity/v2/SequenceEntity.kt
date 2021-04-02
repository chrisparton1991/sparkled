package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.entity.SequenceStatus
import io.sparkled.model.util.IdUtils

@Entity(name = "sequence", idField = "id")
data class SequenceEntity(
    val id: Int = IdUtils.NO_ID,
    val songId: Int = -1,
    val stageId: Int = -1,
    val name: String = "",
    val framesPerSecond: Int = -1,
    val status: SequenceStatus = SequenceStatus.NEW,
)
