package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.util.IdUtils
import java.util.*

@Entity(name = "playlist_sequence", idField = "uuid")
data class PlaylistSequenceEntity(
    val uuid: UUID = IdUtils.NO_UUID,
    val playlistId: Int = -1,
    val sequenceId: Int = -1,
    val displayOrder: Int = -1,
)
