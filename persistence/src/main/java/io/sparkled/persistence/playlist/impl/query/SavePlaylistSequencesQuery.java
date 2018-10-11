package io.sparkled.persistence.playlist.impl.query;

import io.sparkled.model.entity.Playlist;
import io.sparkled.model.entity.PlaylistSequence;
import io.sparkled.model.validator.PlaylistSequenceValidator;
import io.sparkled.persistence.PersistenceQuery;
import io.sparkled.persistence.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class SavePlaylistSequencesQuery implements PersistenceQuery<Void> {

    private static final Logger logger = LoggerFactory.getLogger(SavePlaylistSequencesQuery.class);

    private final Playlist playlist;
    private final List<PlaylistSequence> playlistSequences;

    public SavePlaylistSequencesQuery(Playlist playlist, List<PlaylistSequence> playlistSequences) {
        this.playlist = playlist;
        this.playlistSequences = playlistSequences;
    }

    @Override
    public Void perform(QueryFactory queryFactory) {
        PlaylistSequenceValidator playlistSequenceValidator = new PlaylistSequenceValidator();
        playlistSequences.forEach(playlistSequenceValidator::validate);

        EntityManager entityManager = queryFactory.getEntityManager();
        playlistSequences.forEach(entityManager::merge);
        logger.info("Saved {} playlist sequence(s) for playlist {}.", playlistSequences.size(), playlist.getId());

        deleteRemovedPlaylistSequences(queryFactory);
        return null;
    }

    private void deleteRemovedPlaylistSequences(QueryFactory queryFactory) {
        List<UUID> uuidsToDelete = getPlaylistSequenceUuidsToDelete(queryFactory);
        new DeletePlaylistSequencesQuery(uuidsToDelete).perform(queryFactory);
    }

    private List<UUID> getPlaylistSequenceUuidsToDelete(QueryFactory queryFactory) {
        List<UUID> uuidsToKeep = playlistSequences.stream().map(PlaylistSequence::getUuid).collect(toList());
        uuidsToKeep = uuidsToKeep.isEmpty() ? noUuids : uuidsToKeep;

        return queryFactory
                .select(qPlaylistSequence.uuid)
                .from(qPlaylistSequence)
                .where(qPlaylistSequence.playlistId.eq(playlist.getId()).and(qPlaylistSequence.uuid.notIn(uuidsToKeep)))
                .fetch();
    }
}
