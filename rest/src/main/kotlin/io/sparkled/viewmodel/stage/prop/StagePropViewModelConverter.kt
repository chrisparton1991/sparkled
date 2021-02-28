package io.sparkled.viewmodel.stage.prop

import io.sparkled.model.entity.v2.StagePropEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class StagePropViewModelConverter : ModelConverter<StagePropEntity, StagePropViewModel>,
    ViewModelConverter<StagePropViewModel, StagePropEntity>
