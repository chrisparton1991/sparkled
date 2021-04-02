package io.sparkled.viewmodel.playlist.search

import io.sparkled.model.entity.v2.PlaylistEntity
import io.sparkled.model.entity.v2.partial.PlaylistSummaryEntityPartial
import io.sparkled.persistence.DbService
import io.sparkled.persistence.v2.query.playlist.GetPlaylistSummariesQuery
import javax.inject.Singleton

@Singleton
class PlaylistSearchViewModelConverterImpl(
    private val db: DbService
) : PlaylistSearchViewModelConverter() {

    override fun toViewModels(models: Collection<PlaylistEntity>): List<PlaylistSearchViewModel> {
        val playlistSummaries = db.query(GetPlaylistSummariesQuery())
        return models.map { model -> toViewModel(model, playlistSummaries) }
    }

    private fun toViewModel(model: PlaylistEntity, playlistSummaries: List<PlaylistSummaryEntityPartial>): PlaylistSearchViewModel {
        val summary = playlistSummaries.find { it.playlistId == model.id }

        return PlaylistSearchViewModel()
            .setId(model.id)
            .setName(model.name)
            .setSequenceCount(summary?.sequenceCount)
            .setDurationSeconds(summary?.durationSeconds)
    }
}
