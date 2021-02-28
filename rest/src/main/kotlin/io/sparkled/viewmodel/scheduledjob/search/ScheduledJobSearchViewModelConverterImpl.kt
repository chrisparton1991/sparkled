package io.sparkled.viewmodel.scheduledjob.search

import io.sparkled.model.entity.v2.ScheduledJobEntity
import io.sparkled.persistence.playlist.PlaylistPersistenceService
import javax.inject.Singleton

@Singleton
class ScheduledJobSearchViewModelConverterImpl(
    private val playlistPersistenceService: PlaylistPersistenceService
) : ScheduledJobSearchViewModelConverter() {

    override fun toViewModels(models: Collection<ScheduledJobEntity>): List<ScheduledJobSearchViewModel> {
        val playlistNames = playlistPersistenceService.getPlaylistNames()
        return models.map { toViewModel(it, playlistNames) }.toList()
    }

    private fun toViewModel(model: ScheduledJobEntity, playlistNames: Map<Int, String>): ScheduledJobSearchViewModel {
        return ScheduledJobSearchViewModel(
            id = model.id,
            action = model.action,
            cronExpression = model.cronExpression,
            playlistId = model.playlistId,
            playlistName = playlistNames[model.playlistId]
        )
    }
}
