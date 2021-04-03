package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.util.IdUtils
import java.util.*

@Entity("sequence_channel", idField = "uuid")
data class SequenceChannelEntity(
    val uuid: UUID = IdUtils.NO_UUID,
    val sequenceId: Int = -1,
    val stagePropUuid: UUID = IdUtils.NO_UUID,
    val name: String = "",
    val displayOrder: Int = -1,
    val channelJson: String = "[]"
)
