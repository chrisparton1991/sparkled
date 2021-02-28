package io.sparkled.viewmodel.stage

import io.sparkled.model.entity.v2.StageEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class StageViewModelConverter : ModelConverter<StageEntity, StageViewModel>,
    ViewModelConverter<StageViewModel, StageEntity>
