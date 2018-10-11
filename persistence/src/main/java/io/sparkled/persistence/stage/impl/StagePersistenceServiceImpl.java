package io.sparkled.persistence.stage.impl;

import com.google.inject.persist.Transactional;
import io.sparkled.model.entity.Stage;
import io.sparkled.model.entity.StageProp;
import io.sparkled.persistence.QueryFactory;
import io.sparkled.persistence.stage.StagePersistenceService;
import io.sparkled.persistence.stage.impl.query.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StagePersistenceServiceImpl implements StagePersistenceService {

    private QueryFactory queryFactory;

    @Inject
    public StagePersistenceServiceImpl(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    @Transactional
    public Stage createStage(Stage stage) {
        return new SaveStageQuery(stage).perform(queryFactory);
    }

    @Override
    @Transactional
    public List<Stage> getAllStages() {
        return new GetAllStagesQuery().perform(queryFactory);
    }

    @Override
    @Transactional
    public Optional<Stage> getStageById(int stageId) {
        return new GetStageByIdQuery(stageId).perform(queryFactory);
    }

    @Override
    @Transactional
    public List<StageProp> getStagePropsByStageId(Integer stageId) {
        return new GetStagePropsByStageIdQuery(stageId).perform(queryFactory);
    }

    @Override
    @Transactional
    public Optional<StageProp> getStagePropByUuid(Integer stageId, UUID uuid) {
        return new GetStagePropByUuidQuery(stageId, uuid).perform(queryFactory);
    }

    @Override
    @Transactional
    public void saveStage(Stage stage, List<StageProp> stageProps) {
        stage = new SaveStageQuery(stage).perform(queryFactory);
        new SaveStagePropsQuery(stage, stageProps).perform(queryFactory);
    }

    @Override
    @Transactional
    public void deleteStage(int stageId) {
        new DeleteStageQuery(stageId).perform(queryFactory);
    }
}
