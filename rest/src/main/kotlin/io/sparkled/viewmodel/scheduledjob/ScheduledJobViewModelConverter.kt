package io.sparkled.viewmodel.scheduledjob

import io.sparkled.model.entity.v2.ScheduledJobEntity
import io.sparkled.viewmodel.ModelConverter
import io.sparkled.viewmodel.ViewModelConverter

abstract class ScheduledJobViewModelConverter : ModelConverter<ScheduledJobEntity, ScheduledJobViewModel>,
    ViewModelConverter<ScheduledJobViewModel, ScheduledJobEntity>
