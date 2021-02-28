package io.sparkled.viewmodel.song

import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import io.sparkled.persistence.v2.query.common.GetByIdQuery
import io.sparkled.viewmodel.exception.ViewModelConversionException
import javax.inject.Singleton

@Singleton
class SongViewModelConverterImpl(
    private val db: DbService
) : SongViewModelConverter() {

    override fun toViewModel(model: SongEntity): SongViewModel {
        return SongViewModel()
            .setId(model.id)
            .setName(model.name)
            .setArtist(model.artist)
            .setAlbum(model.album)
            .setDurationMs(model.durationMs)
    }

    override fun toModel(viewModel: SongViewModel): SongEntity {
        val songId = viewModel.getId()
        val model = getSong(songId)

        return model.copy(
            name = viewModel.getName()!!,
            artist = viewModel.getArtist()!!,
            album = viewModel.getAlbum()!!,
            durationMs = viewModel.getDurationMs()!!
        )
    }

    private fun getSong(songId: Int?): SongEntity {
        return db.getById(songId) ?: throw ViewModelConversionException("Song with ID of '$songId' not found.")
    }
}
