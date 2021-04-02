package io.sparkled.viewmodel.playlist

import io.sparkled.model.entity.v2.PlaylistEntity
import io.sparkled.persistence.DbService
import io.sparkled.persistence.getById
import javax.inject.Singleton

@Singleton
class PlaylistViewModelConverterImpl(
    private val db: DbService
) : PlaylistViewModelConverter() {

    override fun toViewModel(model: PlaylistEntity): PlaylistViewModel {
        return PlaylistViewModel()
            .setId(model.id)
            .setName(model.name)
    }

    override fun toModel(viewModel: PlaylistViewModel): PlaylistEntity {
        val model = db.getById(viewModel.getId()) ?: PlaylistEntity()
        return model.copy(name = viewModel.getName()!!)
    }
}
