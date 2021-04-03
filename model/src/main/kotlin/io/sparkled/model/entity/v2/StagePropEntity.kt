package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.util.IdUtils
import java.util.UUID

@Entity(name = "stage_prop", idField = "uuid")
data class StagePropEntity(
    val uuid: UUID = IdUtils.NO_UUID,
    val stageId: Int = -1,
    val code: String = "",
    val name: String = "",
    val type: String = "",
    val ledCount: Int = 0,
    val reverse: Boolean = false,
    val positionX: Int = 0,
    val positionY: Int = 0,
    val scaleX: Float = 1f,
    val scaleY: Float = 1f,
    val rotation: Int = 0,
    val brightness: Int = 15, // TODO move to constant
    val displayOrder: Int = -1,
)
