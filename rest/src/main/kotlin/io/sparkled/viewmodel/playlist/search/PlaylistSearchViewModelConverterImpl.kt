package io.sparkled.viewmodel.playlist.search

import io.sparkled.model.entity.v2.PlaylistEntity
import io.sparkled.model.playlist.PlaylistSummary
import io.sparkled.persistence.playlist.PlaylistPersistenceService
import javax.inject.Singleton

@Singleton
class PlaylistSearchViewModelConverterImpl(
    private val playlistPersistenceService: PlaylistPersistenceService
) : PlaylistSearchViewModelConverter() {

    override fun toViewModels(models: Collection<PlaylistEntity>): List<PlaylistSearchViewModel> {
        val playlistSummaries = playlistPersistenceService.getPlaylistSummaries()
        return models.map { model -> toViewModel(model, playlistSummaries) }
    }

    private fun toViewModel(model: PlaylistEntity, playlistSummaries: Map<Int, PlaylistSummary>): PlaylistSearchViewModel {
        val summary = playlistSummaries[model.id]!!

        return PlaylistSearchViewModel()
            .setId(model.id)
            .setName(model.name)
            .setSequenceCount(summary.getSequenceCount())
            .setDurationSeconds(summary.getDurationSeconds())
    }
}
