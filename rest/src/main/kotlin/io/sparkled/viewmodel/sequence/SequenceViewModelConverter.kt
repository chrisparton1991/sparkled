package io.sparkled.viewmodel.sequence

import io.sparkled.model.entity.v2.SequenceEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class SequenceViewModelConverter : ModelConverter<SequenceEntity, SequenceViewModel>,
        ViewModelConverter<SequenceViewModel, SequenceEntity>
