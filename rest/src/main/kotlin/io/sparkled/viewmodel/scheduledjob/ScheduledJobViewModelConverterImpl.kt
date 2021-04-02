package io.sparkled.viewmodel.scheduledjob

import io.sparkled.model.entity.v2.ScheduledJobEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import javax.inject.Singleton

@Singleton
class ScheduledJobViewModelConverterImpl(
    private val db: DbService
) : ScheduledJobViewModelConverter() {

    override fun toViewModel(model: ScheduledJobEntity): ScheduledJobViewModel {
        return ScheduledJobViewModel(
            id = model.id,
            action = model.action,
            cronExpression = model.cronExpression,
            value = model.value,
            playlistId = model.playlistId
        )
    }

    override fun toModel(viewModel: ScheduledJobViewModel): ScheduledJobEntity {
        val model = db.getById(viewModel.id) ?: ScheduledJobEntity()

        return model.copy(
            cronExpression = viewModel.cronExpression!!,
            action = viewModel.action!!,
            value = viewModel.value,
            playlistId = viewModel.playlistId
        )
    }
}
