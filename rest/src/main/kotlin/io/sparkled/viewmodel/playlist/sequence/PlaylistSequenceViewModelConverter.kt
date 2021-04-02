package io.sparkled.viewmodel.playlist.sequence

import io.sparkled.model.entity.v2.PlaylistSequenceEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class PlaylistSequenceViewModelConverter : ModelConverter<PlaylistSequenceEntity, PlaylistSequenceViewModel>,
    ViewModelConverter<PlaylistSequenceViewModel, PlaylistSequenceEntity>
