package io.sparkled.viewmodel.scheduledjob.search

import io.sparkled.model.entity.v2.ScheduledJobEntity
import io.sparkled.viewmodel.ModelCollectionConverter

abstract class ScheduledJobSearchViewModelConverter :
    ModelCollectionConverter<ScheduledJobEntity, ScheduledJobSearchViewModel>
