package io.sparkled.viewmodel.sequence.channel

import io.sparkled.model.entity.v2.SequenceChannelEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class SequenceChannelViewModelConverter : ModelConverter<SequenceChannelEntity, SequenceChannelViewModel>,
    ViewModelConverter<SequenceChannelViewModel, SequenceChannelEntity>
