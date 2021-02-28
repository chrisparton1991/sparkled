package io.sparkled.viewmodel.stage.search

import io.sparkled.model.entity.v2.StageEntity
import javax.inject.Singleton

@Singleton
class StageSearchViewModelConverterImpl : StageSearchViewModelConverter() {

    override fun toViewModels(models: Collection<StageEntity>): List<StageSearchViewModel> {
        return models.map(this::toViewModel).toList()
    }

    private fun toViewModel(model: StageEntity): StageSearchViewModel {
        return StageSearchViewModel()
            .setId(model.id)
            .setName(model.name)
    }
}
