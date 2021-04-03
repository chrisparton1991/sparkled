package io.sparkled.model.entity.v2

import io.sparkled.model.annotation.Entity
import io.sparkled.model.entity.ScheduledJobAction
import io.sparkled.model.util.IdUtils

@Entity(name = "scheduled_job", idField = "id")
data class ScheduledJobEntity(
    val id: Int = IdUtils.NO_ID,
    val action: ScheduledJobAction = ScheduledJobAction.NONE,
    val cronExpression: String = "",
    val value: String? = "",
    val playlistId: Int? = null,
)