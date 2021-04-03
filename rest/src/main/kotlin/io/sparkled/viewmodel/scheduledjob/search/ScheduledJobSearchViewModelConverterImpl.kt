package io.sparkled.viewmodel.scheduledjob.search

import io.sparkled.model.entity.v2.PlaylistEntity
import io.sparkled.model.entity.v2.ScheduledJobEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getAll
import javax.inject.Singleton

@Singleton
class ScheduledJobSearchViewModelConverterImpl(
    private val db: DbService
) : ScheduledJobSearchViewModelConverter() {

    override fun toViewModels(models: Collection<ScheduledJobEntity>): List<ScheduledJobSearchViewModel> {
        val playlists = db.getAll<PlaylistEntity>().associateBy { it.id }
        return models.map { toViewModel(it, playlists) }.toList()
    }

    private fun toViewModel(model: ScheduledJobEntity, playlists: Map<Int, PlaylistEntity>): ScheduledJobSearchViewModel {
        return ScheduledJobSearchViewModel(
            id = model.id,
            action = model.action,
            cronExpression = model.cronExpression,
            playlistId = model.playlistId,
            playlistName = playlists[model.playlistId]?.name
        )
    }
}
