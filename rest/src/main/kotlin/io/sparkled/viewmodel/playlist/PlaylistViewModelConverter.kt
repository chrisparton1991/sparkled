package io.sparkled.viewmodel.playlist

import io.sparkled.model.entity.v2.PlaylistEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class PlaylistViewModelConverter : ModelConverter<PlaylistEntity, PlaylistViewModel>,
    ViewModelConverter<PlaylistViewModel, PlaylistEntity>
