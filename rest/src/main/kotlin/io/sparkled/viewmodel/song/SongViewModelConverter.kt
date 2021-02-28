package io.sparkled.viewmodel.song

import io.sparkled.model.entity.v2.SongEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class SongViewModelConverter : ModelConverter<SongEntity, SongViewModel>, ViewModelConverter<SongViewModel, SongEntity>
